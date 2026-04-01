package com.mysiupysiu.bignay.utils.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.List;
import java.util.function.Supplier;

public class AddItemRangeModifier extends LootModifier {

    public static final Supplier<Codec<AddItemRangeModifier>> CODEC = () -> RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(Codec.STRING.fieldOf("id").forGetter(m -> m.targetId), Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance), Codec.INT.fieldOf("min").forGetter(m -> m.minCount), Codec.INT.fieldOf("max").forGetter(m -> m.maxCount))).apply(inst, AddItemRangeModifier::new));

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
            Item itemToDrop = resolveItem(context);

            if (itemToDrop != null) {
                int count = minCount;
                if (maxCount > minCount) {
                    count = context.getRandom().nextInt(maxCount - minCount + 1) + minCount;
                }
                generatedLoot.add(new ItemStack(itemToDrop, count));
            }
        }
        return generatedLoot;
    }

    private Item resolveItem(LootContext context) {
        if (this.targetId.startsWith("#")) {
            ResourceLocation tagLoc = new ResourceLocation(this.targetId.substring(1));
            TagKey<Item> tagKey = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), tagLoc);
            ITag<Item> tag = ForgeRegistries.ITEMS.tags().getTag(tagKey);

            if (!tag.isEmpty()) {
                List<Item> itemsInTag = tag.stream().toList();
                return itemsInTag.get(context.getRandom().nextInt(itemsInTag.size()));
            }
        } else {
            ResourceLocation itemLoc = new ResourceLocation(this.targetId);
            if (ForgeRegistries.ITEMS.containsKey(itemLoc)) {
                return ForgeRegistries.ITEMS.getValue(itemLoc);
            }
        }
        return null;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
