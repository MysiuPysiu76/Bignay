package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.init.BignayItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootModifiers {

    private static final ResourceLocation ABANDONED_MINESHAFT = new ResourceLocation("minecraft", "chests/abandoned_mineshaft");
    private static final ResourceLocation ANCIENT_CITY = new ResourceLocation("minecraft", "chests/ancient_city");
    private static final ResourceLocation IGLOO_CHEST = new ResourceLocation("minecraft", "chests/igloo_chest");
    private static final ResourceLocation JUNGLE_TEMPLE = new ResourceLocation("minecraft", "chests/jungle_temple");
    private static final ResourceLocation PILLAGER_OUTPOST = new ResourceLocation("minecraft", "chests/pillager_outpost");
    private static final ResourceLocation WOODLAND_MANSION = new ResourceLocation("minecraft", "chests/woodland_mansion");

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;

            if (id.equals(ABANDONED_MINESHAFT)) {
                addRangeItem(tableBuilder, BignayItems.PALE_PUMPKIN_SEEDS.get(), 0.25f, 2, 4);
                addSingleItem(tableBuilder, BignayItems.ANXIOUS_PUMPKIN_PATTERN.get(), 0.25f);
            }

            if (id.equals(ANCIENT_CITY)) {
                addSingleItem(tableBuilder, BignayItems.SKULL_PUMPKIN_PATTERN.get(), 0.3f);
                addHornWithInstrument(tableBuilder, BignayItems.COPPER_HORN.get(), "bignay:sky_copper_horn", 0.05f);
            }

            if (id.equals(IGLOO_CHEST)) {
                addSingleItem(tableBuilder, BignayItems.ANXIOUS_PUMPKIN_PATTERN.get(), 0.33f);
            }

            if (id.equals(JUNGLE_TEMPLE)) {
                addSingleItem(tableBuilder, BignayItems.DERPY_PUMPKIN_PATTERN.get(), 0.3f);
            }

            if (id.equals(PILLAGER_OUTPOST)) {
                addRangeItem(tableBuilder, BignayItems.PALE_PUMPKIN_SEEDS.get(), 0.15f, 2, 4);
                addSingleItem(tableBuilder, BignayItems.ANGRY_PUMPKIN_PATTERN.get(), 0.1f);
                addHornWithInstrument(tableBuilder, BignayItems.COPPER_HORN.get(), "bignay:lake_copper_horn", 0.1f);
            }

            if (id.equals(WOODLAND_MANSION)) {
                addRangeItem(tableBuilder, BignayItems.PALE_PUMPKIN_SEEDS.get(), 0.15f, 2, 4);
            }
        });
    }

    private static void addSingleItem(LootTable.Builder tableBuilder, Item item, float chance) {
        tableBuilder.pool(LootPool.lootPool()
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(item)).build());
    }

    private static void addRangeItem(LootTable.Builder tableBuilder, Item item, float chance, float min, float max) {
        tableBuilder.pool(LootPool.lootPool()
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))).build());
    }

    private static void addHornWithInstrument(LootTable.Builder tableBuilder, Item item, String instrumentId, float chance) {
        CompoundTag tag = new CompoundTag();
        tag.putString("instrument", instrumentId);

        tableBuilder.pool(LootPool.lootPool()
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(item).apply(SetNbtFunction.setTag(tag))).build());
    }
}
