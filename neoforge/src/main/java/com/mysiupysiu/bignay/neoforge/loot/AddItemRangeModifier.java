package com.mysiupysiu.bignay.neoforge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Optional;

public class AddItemRangeModifier extends LootModifier {
    public static final Codec<AddItemRangeModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(Codec.STRING.fieldOf("id").forGetter(m -> m.targetId), Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance), Codec.INT.fieldOf("min").forGetter(m -> m.minCount), Codec.INT.fieldOf("max").forGetter(m -> m.maxCount))).apply(inst, AddItemRangeModifier::new));

    private final String targetId;
    private final float chance;
    private final int minCount;
    private final int maxCount;

    public AddItemRangeModifier(LootItemCondition[] conditionsIn, String targetId, float chance, int min, int max) {
        super(conditionsIn);
        this.targetId = targetId;
        this.chance = chance;
        this.minCount = min;
        this.maxCount = max;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= this.chance) {
            resolveItem(context).ifPresent(item -> {
                int count = minCount >= maxCount ? minCount : context.getRandom().nextInt(maxCount - minCount + 1) + minCount;
                generatedLoot.add(new ItemStack(item, count));
            });
        }
        return generatedLoot;
    }

    private Optional<Item> resolveItem(LootContext context) {
        try {
            if (this.targetId.startsWith("#")) {
                TagKey<Item> tagKey = TagKey.create(Registries.ITEM, ResourceLocation.tryParse(this.targetId.substring(1)));
                var tag = BuiltInRegistries.ITEM.getTag(tagKey);
                return tag.flatMap(t -> t.size() > 0 ? Optional.of(t.get(context.getRandom().nextInt(t.size())).value()) : Optional.empty());
            } else {
                return BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(this.targetId));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
