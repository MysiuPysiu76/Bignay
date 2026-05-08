package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.registry.BignayBlocks;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class Flameable {

    private static FireBlock fire;

    public static void init() {
        fire = ((FireBlock) Blocks.FIRE);

        register_5_20();
        register_30_20();
        register_60_100();
        register_15_60();
        register_5_5();
    }

    private static void register_5_20() {
        register_5_20(BignayBlocks.ACACIA_WOOD_FENCE);
        register_5_20(BignayBlocks.BAMBOO_BLOCK_FENCE);
        register_5_20(BignayBlocks.BIRCH_WOOD_FENCE);
        register_5_20(BignayBlocks.CHERRY_WOOD_FENCE);
        register_5_20(BignayBlocks.DARK_OAK_WOOD_FENCE);
        register_5_20(BignayBlocks.JUNGLE_WOOD_FENCE);
        register_5_20(BignayBlocks.MANGROVE_WOOD_FENCE);
        register_5_20(BignayBlocks.OAK_WOOD_FENCE);
        register_5_20(BignayBlocks.SPRUCE_WOOD_FENCE);

        register_5_20(BignayBlocks.STRIPPED_ACACIA_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_BIRCH_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_CHERRY_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_DARK_OAK_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_JUNGLE_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_MANGROVE_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_OAK_WOOD_FENCE);
        register_5_20(BignayBlocks.STRIPPED_SPRUCE_WOOD_FENCE);

        register_5_20(BignayBlocks.ACACIA_MOSAIC);
        register_5_20(BignayBlocks.BIRCH_MOSAIC);
        register_5_20(BignayBlocks.CHERRY_MOSAIC);
        register_5_20(BignayBlocks.DARK_OAK_MOSAIC);
        register_5_20(BignayBlocks.JUNGLE_MOSAIC);
        register_5_20(BignayBlocks.MANGROVE_MOSAIC);
        register_5_20(BignayBlocks.OAK_MOSAIC);
        register_5_20(BignayBlocks.SPRUCE_MOSAIC);

        register_5_20(BignayBlocks.ACACIA_MOSAIC_SLAB);
        register_5_20(BignayBlocks.BIRCH_MOSAIC_SLAB);
        register_5_20(BignayBlocks.CHERRY_MOSAIC_SLAB);
        register_5_20(BignayBlocks.DARK_OAK_MOSAIC_SLAB);
        register_5_20(BignayBlocks.JUNGLE_MOSAIC_SLAB);
        register_5_20(BignayBlocks.MANGROVE_MOSAIC_SLAB);
        register_5_20(BignayBlocks.OAK_MOSAIC_SLAB);
        register_5_20(BignayBlocks.SPRUCE_MOSAIC_SLAB);

        register_5_20(BignayBlocks.ACACIA_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.BIRCH_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.CHERRY_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.DARK_OAK_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.JUNGLE_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.MANGROVE_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.OAK_MOSAIC_STAIRS);
        register_5_20(BignayBlocks.SPRUCE_MOSAIC_STAIRS);

        register_5_20(BignayBlocks.ACACIA_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.BIRCH_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.CHERRY_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.DARK_OAK_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.JUNGLE_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.MANGROVE_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.OAK_MOSAIC_VERTICAL_SLAB);
        register_5_20(BignayBlocks.SPRUCE_MOSAIC_VERTICAL_SLAB);

        register_5_20(BignayBlocks.ACACIA_VERTICAL_SLAB);
        register_5_20(BignayBlocks.BIRCH_VERTICAL_SLAB);
        register_5_20(BignayBlocks.BAMBOO_VERTICAL_SLAB);
        register_5_20(BignayBlocks.CHERRY_VERTICAL_SLAB);
        register_5_20(BignayBlocks.DARK_OAK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.JUNGLE_VERTICAL_SLAB);
        register_5_20(BignayBlocks.MANGROVE_VERTICAL_SLAB);
        register_5_20(BignayBlocks.OAK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.SPRUCE_VERTICAL_SLAB);

        register_5_20(BignayBlocks.VERTICAL_ACACIA_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_BIRCH_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_BAMBOO_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_CHERRY_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_DARK_OAK_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_JUNGLE_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_MANGROVE_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_OAK_PLANKS);
        register_5_20(BignayBlocks.VERTICAL_SPRUCE_PLANKS);

        register_5_20(BignayBlocks.VERTICAL_ACACIA_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_BIRCH_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_BAMBOO_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_CHERRY_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_DARK_OAK_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_JUNGLE_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_MANGROVE_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_OAK_PLANK_STAIRS);
        register_5_20(BignayBlocks.VERTICAL_SPRUCE_PLANK_STAIRS);

        register_5_20(BignayBlocks.VERTICAL_ACACIA_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_BIRCH_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_BAMBOO_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_CHERRY_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_DARK_OAK_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_JUNGLE_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_MANGROVE_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_OAK_PLANK_SLAB);
        register_5_20(BignayBlocks.VERTICAL_SPRUCE_PLANK_SLAB);

        register_5_20(BignayBlocks.VERTICAL_ACACIA_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_BIRCH_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_BAMBOO_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_CHERRY_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_DARK_OAK_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_JUNGLE_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_MANGROVE_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_OAK_PLANK_VERTICAL_SLAB);
        register_5_20(BignayBlocks.VERTICAL_SPRUCE_PLANK_VERTICAL_SLAB);
    }

    private static void register_30_20() {
        register_30_20(BignayBlocks.ACACIA_BOOKSHELF);
        register_30_20(BignayBlocks.BAMBOO_BOOKSHELF);
        register_30_20(BignayBlocks.BIRCH_BOOKSHELF);
        register_30_20(BignayBlocks.CHERRY_BOOKSHELF);
        register_30_20(BignayBlocks.DARK_OAK_BOOKSHELF);
        register_30_20(BignayBlocks.JUNGLE_BOOKSHELF);
        register_30_20(BignayBlocks.MANGROVE_BOOKSHELF);
        register_30_20(BignayBlocks.OAK_BOOKSHELF);
        register_30_20(BignayBlocks.SPRUCE_BOOKSHELF);
    }

    private static void register_60_100() {
        register_60_100(BignayBlocks.PEONY);
        register_60_100(BignayBlocks.LILAC);
        register_60_100(BignayBlocks.HYACINTH);
        register_60_100(BignayBlocks.LAPIS_LOTUS);
        register_60_100(BignayBlocks.GOLDEN_POPPY);
        register_60_100(BignayBlocks.ROSE);
        register_60_100(BignayBlocks.CYAN_ROSE);
        register_60_100(BignayBlocks.CYAN_ROSE_BUSH);
        register_60_100(BignayBlocks.HELIOTROPE);
        register_60_100(BignayBlocks.MARIGOLD);
        register_60_100(BignayBlocks.TALL_DEAD_BUSH);
    }

    private static void register_5_5() {
        register_5_5(BignayBlocks.ACACIA_CAVITY);
        register_5_5(BignayBlocks.BIRCH_CAVITY);
        register_5_5(BignayBlocks.CHERRY_CAVITY);
        register_5_5(BignayBlocks.DARK_OAK_CAVITY);
        register_5_5(BignayBlocks.JUNGLE_CAVITY);
        register_5_5(BignayBlocks.MANGROVE_CAVITY);
        register_5_5(BignayBlocks.OAK_CAVITY);
        register_5_5(BignayBlocks.SPRUCE_CAVITY);

        register_5_5(BignayBlocks.HOLLOW_ACACIA_LOG);
        register_5_5(BignayBlocks.HOLLOW_BAMBOO_BLOCK);
        register_5_5(BignayBlocks.HOLLOW_BIRCH_LOG);
        register_5_5(BignayBlocks.HOLLOW_CHERRY_LOG);
        register_5_5(BignayBlocks.HOLLOW_DARK_OAK_LOG);
        register_5_5(BignayBlocks.HOLLOW_JUNGLE_LOG);
        register_5_5(BignayBlocks.HOLLOW_MANGROVE_LOG);
        register_5_5(BignayBlocks.HOLLOW_OAK_LOG);
        register_5_5(BignayBlocks.HOLLOW_SPRUCE_LOG);

        register_5_5(BignayBlocks.HOLLOW_STRIPPED_ACACIA_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_BAMBOO_BLOCK);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_BIRCH_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_CHERRY_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_JUNGLE_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_MANGROVE_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_OAK_LOG);
        register_5_5(BignayBlocks.HOLLOW_STRIPPED_SPRUCE_LOG);

        register_5_5(BignayBlocks.CHARCOAL_BLOCK);
    }

    private static void register_15_60() {
        register_15_60(BignayBlocks.ANCIENT_VINES);
    }

    private static void register(Block b, int i, int j) {
        fire.setFlammable(b, i, j);
    }

    private static void register_5_20(RegistrySupplier<Block> b) {
        register(b.get(), 5, 20);
    }

    private static void register_30_20(RegistrySupplier<Block> b) {
        register(b.get(), 30, 20);
    }

    private static void register_60_100(RegistrySupplier<Block> b) {
        register(b.get(), 60, 100);
    }

    private static void register_15_60(RegistrySupplier<Block> b) {
        register(b.get(), 15, 60);
    }

    private static void register_5_5(RegistrySupplier<Block> b) {
        register(b.get(), 5, 5);
    }
}
