package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BignayMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BignayMod.MODID);

    private static final List<RegistryObject<Item>> NATURAL_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<Block> WAX_BLOCK = registerBlock("wax_block", WaxBlock::new);
    public static final RegistryObject<Block> WITHER_BONE_BLOCK = registerBlock("wither_bone_block", WitherBoneBlock::new);
    public static final RegistryObject<Block> ARCHAEOLOGY_TABLE_BLOCK = registerBlock("archaeology_table", ArchaeologyTableBlock::new);

    public static final RegistryObject<Block> ACACIA_CAMPFIRE = registerBlock("acacia_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> BIRCH_CAMPFIRE = registerBlock("birch_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> CHERRY_CAMPFIRE = registerBlock("cherry_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> CRIMSON_CAMPFIRE = registerBlock("crimson_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> DARK_OAK_CAMPFIRE = registerBlock("dark_oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> JUNGLE_CAMPFIRE = registerBlock("jungle_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> MANGROVE_CAMPFIRE = registerBlock("mangrove_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> OAK_CAMPFIRE = registerBlock("oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> SPRUCE_CAMPFIRE = registerBlock("spruce_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> WARPED_CAMPFIRE = registerBlock("warped_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));

    public static final RegistryObject<Block> SOUL_ACACIA_CAMPFIRE = registerBlock("soul_acacia_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_BIRCH_CAMPFIRE = registerBlock("soul_birch_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_CHERRY_CAMPFIRE = registerBlock("soul_cherry_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_CRIMSON_CAMPFIRE = registerBlock("soul_crimson_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_DARK_OAK_CAMPFIRE = registerBlock("soul_dark_oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_JUNGLE_CAMPFIRE = registerBlock("soul_jungle_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_MANGROVE_CAMPFIRE = registerBlock("soul_mangrove_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_OAK_CAMPFIRE = registerBlock("soul_oak_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_SPRUCE_CAMPFIRE = registerBlock("soul_spruce_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_WARPED_CAMPFIRE = registerBlock("soul_warped_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));

    public static final RegistryObject<Block> HOLLOW_ACACIA_LOG = registerBlock("hollow_acacia_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BAMBOO_BLOCK = registerBlock("hollow_bamboo_block", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BIRCH_LOG = registerBlock("hollow_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CHERRY_LOG = registerBlock("hollow_cherry_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CRIMSON_STEM = registerBlock("hollow_crimson_stem", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_DARK_OAK_LOG = registerBlock("hollow_dark_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_JUNGLE_LOG = registerBlock("hollow_jungle_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_MANGROVE_LOG = registerBlock("hollow_mangrove_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_OAK_LOG = registerBlock("hollow_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_SPRUCE_LOG = registerBlock("hollow_spruce_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_WARPED_STEM = registerBlock("hollow_warped_stem", HollowLogBlock::new);

    public static final RegistryObject<Block> HOLLOW_STRIPPED_ACACIA_LOG = registerBlock("hollow_stripped_acacia_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_BAMBOO_BLOCK = registerBlock("hollow_stripped_bamboo_block", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_BIRCH_LOG = registerBlock("hollow_stripped_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_CHERRY_LOG = registerBlock("hollow_stripped_cherry_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_CRIMSON_STEM = registerBlock("hollow_stripped_crimson_stem", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlock("hollow_stripped_dark_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_JUNGLE_LOG = registerBlock("hollow_stripped_jungle_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_MANGROVE_LOG = registerBlock("hollow_stripped_mangrove_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_OAK_LOG = registerBlock("hollow_stripped_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_SPRUCE_LOG = registerBlock("hollow_stripped_spruce_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_WARPED_STEM = registerBlock("hollow_stripped_warped_stem", HollowLogBlock::new);

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIRS = registerBlock("cracked_stone_brick_stairs", StairsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = registerBlock("cracked_deepslate_brick_stairs", StairsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_STAIRS = registerBlock("cracked_deepslate_tile_stairs", StairsBlock::new);
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_STAIRS = registerBlock("cracked_polished_blackstone_brick_stairs", StairsBlock::new);
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_STAIRS = registerBlock("cracked_nether_brick_stairs", StairsBlock::new);
    public static final RegistryObject<Block> SMOOTH_STONE_STAIRS = registerBlock("smooth_stone_stairs", StairsBlock::new);

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = registerBlock("cracked_stone_brick_slab", SlabsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_SLAB = registerBlock("cracked_deepslate_brick_slab", SlabsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_SLAB = registerBlock("cracked_deepslate_tile_slab", SlabsBlock::new);
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("cracked_polished_blackstone_brick_slab", SlabsBlock::new);
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_SLAB = registerBlock("cracked_nether_brick_slab", SlabsBlock::new);

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = registerBlock("cracked_stone_brick_wall", WallsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_WALL = registerBlock("cracked_deepslate_brick_wall", WallsBlock::new);
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_WALL = registerBlock("cracked_deepslate_tile_wall", WallsBlock::new);
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = registerBlock("cracked_polished_blackstone_brick_wall", WallsBlock::new);
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_WALL = registerBlock("cracked_nether_brick_wall", WallsBlock::new);
    public static final RegistryObject<Block> STONE_WALL = registerBlock("stone_wall", WallsBlock::new);

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANKS = registerBlock("vertical_acacia_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANKS = registerBlock("vertical_bamboo_planks", () -> new VerticalPlanksBlock(Blocks.BAMBOO_PLANKS));
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANKS = registerBlock("vertical_birch_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANKS = registerBlock("vertical_cherry_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANKS = registerBlock("vertical_crimson_planks", () -> new VerticalPlanksBlock(Blocks.CRIMSON_PLANKS));
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANKS = registerBlock("vertical_dark_oak_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANKS = registerBlock("vertical_jungle_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANKS = registerBlock("vertical_mangrove_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANKS = registerBlock("vertical_oak_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANKS = registerBlock("vertical_spruce_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANKS = registerBlock("vertical_warped_planks", () -> new VerticalPlanksBlock(Blocks.WARPED_PLANKS));

    public static final RegistryObject<Block> VERDANT_STEM = registerBlock("verdant_stem", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> STRIPPED_VERDANT_STEM = registerBlock("stripped_verdant_stem", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> VERDANT_HYPHAE = registerBlock("verdant_hyphae", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> STRIPPED_VERDANT_HYPHAE = registerBlock("stripped_verdant_hyphae", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> HOLLOW_VERDANT_STEM = registerBlock("hollow_verdant_stem", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_VERDANT_STEM = registerBlock("hollow_stripped_verdant_stem", HollowLogBlock::new);

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        RegistryObject<Item> blockItem = ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        NATURAL_TAB_ITEMS.add(blockItem);
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            NATURAL_TAB_ITEMS.forEach(event::accept);
        }
    }
}
