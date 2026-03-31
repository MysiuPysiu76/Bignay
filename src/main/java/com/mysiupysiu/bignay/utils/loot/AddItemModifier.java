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

public class AddItemModifier extends LootModifier {

    public static final Supplier<Codec<AddItemModifier>> CODEC = () -> RecordCodecBuilder.create(inst -> codecStart(inst).and(inst.group(Codec.STRING.fieldOf("id").forGetter(m -> m.targetId), Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance))).apply(inst, AddItemModifier::new));

    private final String targetId;
    private final float chance;

    public AddItemModifier(LootItemCondition[] conditionsIn, String targetId, float chance) {
        super(conditionsIn);
        this.targetId = targetId;
        this.chance = chance;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= this.chance) {
            Item itemToDrop = resolveItem(context);
            if (itemToDrop != null) {
                generatedLoot.add(new ItemStack(itemToDrop, 1));
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
                List<Item> tagItems = tag.stream().toList();
                return tagItems.get(context.getRandom().nextInt(tagItems.size()));
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
