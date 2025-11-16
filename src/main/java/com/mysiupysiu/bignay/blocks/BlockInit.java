package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.items.BurnableBlockItem;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import com.mysiupysiu.bignay.worldgen.ModConfiguredFeatures;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BignayMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BignayMod.MODID);

    public static final RegistryObject<Block> WAX_BLOCK = registerBlock("wax_block", WaxBlock::new);
    public static final RegistryObject<Block> WITHER_BONE_BLOCK = registerBlock("wither_bone_block", WitherBoneBlock::new);
    public static final RegistryObject<Block> ARCHAEOLOGY_TABLE_BLOCK = registerBlock("archaeology_table", ArchaeologyTableBlock::new);

    public static final RegistryObject<Block> VERDANT_STEM = registerBlock("verdant_stem", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> STRIPPED_VERDANT_STEM = registerBlock("stripped_verdant_stem", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> VERDANT_HYPHAE = registerBlock("verdant_hyphae", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> STRIPPED_VERDANT_HYPHAE = registerBlock("stripped_verdant_hyphae", () -> new NetherStemBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> VERDANT_PLANKS = registerBlock("verdant_planks", VerdantPlanksBlock::new);
    public static final RegistryObject<Block> VERDANT_STAIRS = registerBlock("verdant_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERDANT_SLAB = registerBlock("verdant_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERDANT_FENCE = registerBlock("verdant_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE)));
    public static final RegistryObject<Block> VERDANT_FENCE_GATE = registerBlock("verdant_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_DOOR = registerBlock("verdant_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).noOcclusion(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_TRAPDOOR = registerBlock("verdant_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).noOcclusion(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = registerBlock("verdant_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.CRIMSON_PRESSURE_PLATE), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_BUTTON = registerBlock("verdant_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_BUTTON), BlockSetType.CRIMSON, 30, false));
    public static final RegistryObject<Block> VERDANT_SIGN = registerBlockOnly("verdant_sign", () -> new SignsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_WALL_SIGN = registerBlockOnly("verdant_wall_sign", () -> new WallSignsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_WALL_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_HANGING_SIGN = registerBlockOnly("verdant_hanging_sign", () -> new CustomHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HANGING_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = registerBlockOnly("verdant_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_WALL_HANGING_SIGN), BignayWoodType.VERDANT));

    public static final RegistryObject<Block> ACACIA_CAMPFIRE = registerBlock("acacia_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> BIRCH_CAMPFIRE = registerBlock("birch_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> CHERRY_CAMPFIRE = registerBlock("cherry_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> CRIMSON_CAMPFIRE = registerBlock("crimson_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> DARK_OAK_CAMPFIRE = registerBlock("dark_oak_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> JUNGLE_CAMPFIRE = registerBlock("jungle_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> MANGROVE_CAMPFIRE = registerBlock("mangrove_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> OAK_CAMPFIRE = registerBlock("oak_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> SPRUCE_CAMPFIRE = registerBlock("spruce_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> VERDANT_CAMPFIRE = registerBlock("verdant_campfire", CustomCampfireBlock::new);
    public static final RegistryObject<Block> WARPED_CAMPFIRE = registerBlock("warped_campfire", CustomCampfireBlock::new);

    public static final RegistryObject<Block> SOUL_ACACIA_CAMPFIRE = registerBlock("soul_acacia_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_BIRCH_CAMPFIRE = registerBlock("soul_birch_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_CHERRY_CAMPFIRE = registerBlock("soul_cherry_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_CRIMSON_CAMPFIRE = registerBlock("soul_crimson_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_DARK_OAK_CAMPFIRE = registerBlock("soul_dark_oak_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_JUNGLE_CAMPFIRE = registerBlock("soul_jungle_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_MANGROVE_CAMPFIRE = registerBlock("soul_mangrove_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_OAK_CAMPFIRE = registerBlock("soul_oak_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_SPRUCE_CAMPFIRE = registerBlock("soul_spruce_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_VERDANT_CAMPFIRE = registerBlock("soul_verdant_campfire", CustomCampfireBlock.SoulCampfire::new);
    public static final RegistryObject<Block> SOUL_WARPED_CAMPFIRE = registerBlock("soul_warped_campfire", CustomCampfireBlock.SoulCampfire::new);

    public static final RegistryObject<Block> HOLLOW_ACACIA_LOG = registerBlock("hollow_acacia_log", () -> new HollowLogBlock(Blocks.ACACIA_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_BAMBOO_BLOCK = registerBlock("hollow_bamboo_block", () -> new HollowLogBlock(Blocks.BAMBOO_BLOCK), 150);
    public static final RegistryObject<Block> HOLLOW_BIRCH_LOG = registerBlock("hollow_birch_log", () -> new HollowLogBlock(Blocks.BIRCH_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_CHERRY_LOG = registerBlock("hollow_cherry_log", () -> new HollowLogBlock(Blocks.CHERRY_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_CRIMSON_STEM = registerBlock("hollow_crimson_stem", () -> new HollowLogBlock(Blocks.CRIMSON_STEM));
    public static final RegistryObject<Block> HOLLOW_DARK_OAK_LOG = registerBlock("hollow_dark_oak_log", () -> new HollowLogBlock(Blocks.DARK_OAK_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_JUNGLE_LOG = registerBlock("hollow_jungle_log", () -> new HollowLogBlock(Blocks.JUNGLE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_MANGROVE_LOG = registerBlock("hollow_mangrove_log", () -> new HollowLogBlock(Blocks.MANGROVE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_OAK_LOG = registerBlock("hollow_oak_log", () -> new HollowLogBlock(Blocks.OAK_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_SPRUCE_LOG = registerBlock("hollow_spruce_log", () -> new HollowLogBlock(Blocks.SPRUCE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_VERDANT_STEM = registerBlock("hollow_verdant_stem", () -> new HollowLogBlock(VERDANT_STEM));
    public static final RegistryObject<Block> HOLLOW_WARPED_STEM = registerBlock("hollow_warped_stem", () -> new HollowLogBlock(Blocks.WARPED_STEM));

    public static final RegistryObject<Block> HOLLOW_STRIPPED_ACACIA_LOG = registerBlock("hollow_stripped_acacia_log", () -> new HollowLogBlock(Blocks.STRIPPED_ACACIA_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_BAMBOO_BLOCK = registerBlock("hollow_stripped_bamboo_block", () -> new HollowLogBlock(Blocks.STRIPPED_BAMBOO_BLOCK), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_BIRCH_LOG = registerBlock("hollow_stripped_birch_log", () -> new HollowLogBlock(Blocks.STRIPPED_BIRCH_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_CHERRY_LOG = registerBlock("hollow_stripped_cherry_log", () -> new HollowLogBlock(Blocks.STRIPPED_CHERRY_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_CRIMSON_STEM = registerBlock("hollow_stripped_crimson_stem", () -> new HollowLogBlock(Blocks.STRIPPED_CRIMSON_STEM));
    public static final RegistryObject<Block> HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlock("hollow_stripped_dark_oak_log", () -> new HollowLogBlock(Blocks.STRIPPED_DARK_OAK_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_JUNGLE_LOG = registerBlock("hollow_stripped_jungle_log", () -> new HollowLogBlock(Blocks.STRIPPED_JUNGLE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_MANGROVE_LOG = registerBlock("hollow_stripped_mangrove_log", () -> new HollowLogBlock(Blocks.STRIPPED_MANGROVE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_OAK_LOG = registerBlock("hollow_stripped_oak_log", () -> new HollowLogBlock(Blocks.STRIPPED_OAK_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_SPRUCE_LOG = registerBlock("hollow_stripped_spruce_log", () -> new HollowLogBlock(Blocks.STRIPPED_SPRUCE_LOG), 150);
    public static final RegistryObject<Block> HOLLOW_STRIPPED_VERDANT_STEM = registerBlock("hollow_stripped_verdant_stem", () -> new HollowLogBlock(VERDANT_STEM));
    public static final RegistryObject<Block> HOLLOW_STRIPPED_WARPED_STEM = registerBlock("hollow_stripped_warped_stem", () -> new HollowLogBlock(Blocks.STRIPPED_WARPED_STEM));

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIRS = registerBlock("cracked_stone_brick_stairs", () -> new StairsBlock(Blocks.CRACKED_STONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = registerBlock("cracked_deepslate_brick_stairs", () -> new StairsBlock(Blocks.DEEPSLATE_BRICK_STAIRS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_STAIRS = registerBlock("cracked_deepslate_tile_stairs", () -> new StairsBlock(Blocks.DEEPSLATE_TILES));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_STAIRS = registerBlock("cracked_polished_blackstone_brick_stairs", () -> new StairsBlock(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_STAIRS = registerBlock("cracked_nether_brick_stairs", () -> new StairsBlock(Blocks.CRACKED_NETHER_BRICKS));
    public static final RegistryObject<Block> SMOOTH_STONE_STAIRS = registerBlock("smooth_stone_stairs", () -> new StairsBlock(Blocks.SMOOTH_STONE));

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = registerBlock("cracked_stone_brick_slab", () -> new SlabsBlock(Blocks.CRACKED_STONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_SLAB = registerBlock("cracked_deepslate_brick_slab", () -> new SlabsBlock(Blocks.DEEPSLATE_BRICKS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_SLAB = registerBlock("cracked_deepslate_tile_slab", () -> new SlabsBlock(Blocks.DEEPSLATE_TILES));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("cracked_polished_blackstone_brick_slab", () -> new SlabsBlock(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_SLAB = registerBlock("cracked_nether_brick_slab", () -> new SlabsBlock(Blocks.CRACKED_STONE_BRICKS));

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = registerBlock("cracked_stone_brick_wall", () -> new WallsBlock(Blocks.CRACKED_STONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_WALL = registerBlock("cracked_deepslate_brick_wall", () -> new WallsBlock(Blocks.DEEPSLATE_BRICKS));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_WALL = registerBlock("cracked_deepslate_tile_wall", () -> new WallsBlock(Blocks.DEEPSLATE_TILES));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = registerBlock("cracked_polished_blackstone_brick_wall", () -> new WallsBlock(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_WALL = registerBlock("cracked_nether_brick_wall", () -> new WallsBlock(Blocks.CRACKED_NETHER_BRICKS));
    public static final RegistryObject<Block> STONE_WALL = registerBlock("stone_wall", () -> new WallsBlock(Blocks.STONE));

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANKS = registerBlock("vertical_acacia_planks", () -> new DecorativeWoodBlock(Blocks.ACACIA_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANKS = registerBlock("vertical_bamboo_planks", () -> new DecorativeWoodBlock(Blocks.BAMBOO_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANKS = registerBlock("vertical_birch_planks", () -> new DecorativeWoodBlock(Blocks.BIRCH_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANKS = registerBlock("vertical_cherry_planks", () -> new DecorativeWoodBlock(Blocks.CHERRY_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANKS = registerBlock("vertical_crimson_planks", () -> new DecorativeWoodBlock(Blocks.CRIMSON_PLANKS));
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANKS = registerBlock("vertical_dark_oak_planks", () -> new DecorativeWoodBlock(Blocks.DARK_OAK_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANKS = registerBlock("vertical_jungle_planks", () -> new DecorativeWoodBlock(Blocks.JUNGLE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANKS = registerBlock("vertical_mangrove_planks", () -> new DecorativeWoodBlock(Blocks.MANGROVE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANKS = registerBlock("vertical_oak_planks", () -> new DecorativeWoodBlock(Blocks.OAK_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANKS = registerBlock("vertical_spruce_planks", () -> new DecorativeWoodBlock(Blocks.SPRUCE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANKS = registerBlock("vertical_verdant_planks", () -> new DecorativeWoodBlock(VERDANT_PLANKS.get()));
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANKS = registerBlock("vertical_warped_planks", () -> new DecorativeWoodBlock(Blocks.WARPED_PLANKS));

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANK_STAIRS = registerBlock("vertical_acacia_plank_stairs", () -> new StairsBlock(Blocks.ACACIA_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANK_STAIRS = registerBlock("vertical_bamboo_plank_stairs", () -> new StairsBlock(Blocks.BAMBOO_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANK_STAIRS = registerBlock("vertical_birch_plank_stairs", () -> new StairsBlock(Blocks.BIRCH_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANK_STAIRS = registerBlock("vertical_cherry_plank_stairs", () -> new StairsBlock(Blocks.CHERRY_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANK_STAIRS = registerBlock("vertical_crimson_plank_stairs", () -> new StairsBlock(Blocks.CRIMSON_PLANKS));
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANK_STAIRS = registerBlock("vertical_dark_oak_plank_stairs", () -> new StairsBlock(Blocks.DARK_OAK_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANK_STAIRS = registerBlock("vertical_jungle_plank_stairs", () -> new StairsBlock(Blocks.JUNGLE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANK_STAIRS = registerBlock("vertical_mangrove_plank_stairs", () -> new StairsBlock(Blocks.MANGROVE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANK_STAIRS = registerBlock("vertical_oak_plank_stairs", () -> new StairsBlock(Blocks.OAK_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANK_STAIRS = registerBlock("vertical_spruce_plank_stairs", () -> new StairsBlock(Blocks.SPRUCE_PLANKS), 300);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANK_STAIRS = registerBlock("vertical_verdant_plank_stairs", () -> new StairsBlock(VERDANT_PLANKS));
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANK_STAIRS = registerBlock("vertical_warped_plank_stairs", () -> new StairsBlock(Blocks.WARPED_PLANKS));

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANK_SLAB = registerBlock("vertical_acacia_plank_slab", () -> new SlabsBlock(Blocks.ACACIA_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANK_SLAB = registerBlock("vertical_bamboo_plank_slab", () -> new SlabsBlock(Blocks.BAMBOO_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANK_SLAB = registerBlock("vertical_birch_plank_slab", () -> new SlabsBlock(Blocks.BIRCH_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANK_SLAB = registerBlock("vertical_cherry_plank_slab", () -> new SlabsBlock(Blocks.CHERRY_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANK_SLAB = registerBlock("vertical_crimson_plank_slab", () -> new SlabsBlock(Blocks.CRIMSON_PLANKS));
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANK_SLAB = registerBlock("vertical_dark_oak_plank_slab", () -> new SlabsBlock(Blocks.DARK_OAK_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANK_SLAB = registerBlock("vertical_jungle_plank_slab", () -> new SlabsBlock(Blocks.JUNGLE_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANK_SLAB = registerBlock("vertical_mangrove_plank_slab", () -> new SlabsBlock(Blocks.MANGROVE_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANK_SLAB = registerBlock("vertical_oak_plank_slab", () -> new SlabsBlock(Blocks.OAK_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANK_SLAB = registerBlock("vertical_spruce_plank_slab", () -> new SlabsBlock(Blocks.SPRUCE_PLANKS), 150);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANK_SLAB = registerBlock("vertical_verdant_plank_slab", () -> new SlabsBlock(VERDANT_PLANKS));
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANK_SLAB = registerBlock("vertical_warped_plank_slab", () -> new SlabsBlock(Blocks.WARPED_PLANKS));

    public static final RegistryObject<Block> VERDANT_NYLIUM = registerBlock("verdant_nylium", () -> new NetherNyliumBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> VERDANT_FUNGUS = registerBlock("verdant_fungus", () -> new NetherFungusBlock(ModConfiguredFeatures.VERDANT_FUNGUS, VERDANT_NYLIUM.get()));
    public static final RegistryObject<Block> VERDANT_ROOTS = registerBlock("verdant_roots", ()-> new NetherRootsBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> VERDANT_WART_BLOCK = registerBlock("verdant_wart_block", () -> new NetherWartBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> POTTED_VERDANT_FUNGUS = registerBlockOnly("potted_verdant_fungus", () -> flowerPot(VERDANT_FUNGUS.get()));
    public static final RegistryObject<Block> POTTED_VERDANT_ROOTS = registerBlockOnly("potted_verdant_roots", () -> flowerPot(VERDANT_ROOTS.get()));
    public static final RegistryObject<Block> VERDANT_SPROUTS = registerBlock("verdant_sprouts", () -> new SproutsBlock(MapColor.COLOR_GREEN));
    public static final RegistryObject<Block> CRIMSON_SPROUTS = registerBlock("crimson_sprouts", () -> new SproutsBlock(MapColor.CRIMSON_NYLIUM));

    public static final RegistryObject<Block> PALE_PUMPKIN = registerBlock("pale_pumpkin", () -> new PumpkinBlock(MapColor.COLOR_LIGHT_GRAY));
    public static final RegistryObject<Block> CARVED_PALE_PUMPKIN = registerBlock("carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> PALE_PUMPKIN_STEM = registerBlockOnly("pale_pumpkin_stem", () -> new StemBlock((StemGrownBlock) PALE_PUMPKIN.get(), ItemInit.PALE_PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.HARD_CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ATTACHED_PALE_PUMPKIN_STEM = registerBlockOnly("attached_pale_pumpkin_stem", () -> new AttachedStemBlock((StemGrownBlock) PALE_PUMPKIN.get(), ItemInit.PALE_PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> PALE_JACK_O_LANTERN = registerBlock("pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = registerBlock("soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> SOUL_PALE_JACK_O_LANTERN = registerBlock("soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);

    public static final RegistryObject<Block> HAPPY_CARVED_PUMPKIN = registerBlock("happy_carved_pumpkin", EquipablePumpkinBlock::new);
    public static final RegistryObject<Block> HAPPY_CARVED_PALE_PUMPKIN = registerBlock("happy_carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> HAPPY_JACK_O_LANTERN = registerBlock("happy_jack_o_lantern", JackOLantern::new);
    public static final RegistryObject<Block> HAPPY_PALE_JACK_O_LANTERN = registerBlock("happy_pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> HAPPY_SOUL_JACK_O_LANTERN = registerBlock("happy_soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> HAPPY_SOUL_PALE_JACK_O_LANTERN = registerBlock("happy_soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);
    public static final RegistryObject<Block> ANXIOUS_CARVED_PUMPKIN = registerBlock("anxious_carved_pumpkin", EquipablePumpkinBlock::new);
    public static final RegistryObject<Block> ANXIOUS_CARVED_PALE_PUMPKIN = registerBlock("anxious_carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> ANXIOUS_JACK_O_LANTERN = registerBlock("anxious_jack_o_lantern", JackOLantern::new);
    public static final RegistryObject<Block> ANXIOUS_PALE_JACK_O_LANTERN = registerBlock("anxious_pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> ANXIOUS_SOUL_JACK_O_LANTERN = registerBlock("anxious_soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> ANXIOUS_SOUL_PALE_JACK_O_LANTERN = registerBlock("anxious_soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);
    public static final RegistryObject<Block> SKULL_CARVED_PUMPKIN = registerBlock("skull_carved_pumpkin", EquipablePumpkinBlock::new);
    public static final RegistryObject<Block> SKULL_CARVED_PALE_PUMPKIN = registerBlock("skull_carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> SKULL_JACK_O_LANTERN = registerBlock("skull_jack_o_lantern", JackOLantern::new);
    public static final RegistryObject<Block> SKULL_PALE_JACK_O_LANTERN = registerBlock("skull_pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> SKULL_SOUL_JACK_O_LANTERN = registerBlock("skull_soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> SKULL_SOUL_PALE_JACK_O_LANTERN = registerBlock("skull_soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);
    public static final RegistryObject<Block> DERPY_CARVED_PUMPKIN = registerBlock("derpy_carved_pumpkin", EquipablePumpkinBlock::new);
    public static final RegistryObject<Block> DERPY_CARVED_PALE_PUMPKIN = registerBlock("derpy_carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> DERPY_JACK_O_LANTERN = registerBlock("derpy_jack_o_lantern", JackOLantern::new);
    public static final RegistryObject<Block> DERPY_PALE_JACK_O_LANTERN = registerBlock("derpy_pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> DERPY_SOUL_JACK_O_LANTERN = registerBlock("derpy_soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> DERPY_SOUL_PALE_JACK_O_LANTERN = registerBlock("derpy_soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);
    public static final RegistryObject<Block> ANGRY_CARVED_PUMPKIN = registerBlock("angry_carved_pumpkin", EquipablePumpkinBlock::new);
    public static final RegistryObject<Block> ANGRY_CARVED_PALE_PUMPKIN = registerBlock("angry_carved_pale_pumpkin", EquipablePumpkinBlock.PalePumpkin::new);
    public static final RegistryObject<Block> ANGRY_JACK_O_LANTERN = registerBlock("angry_jack_o_lantern", JackOLantern::new);
    public static final RegistryObject<Block> ANGRY_PALE_JACK_O_LANTERN = registerBlock("angry_pale_jack_o_lantern", JackOLantern.Pale::new);
    public static final RegistryObject<Block> ANGRY_SOUL_JACK_O_LANTERN = registerBlock("angry_soul_jack_o_lantern", JackOLantern.Soul::new);
    public static final RegistryObject<Block> ANGRY_SOUL_PALE_JACK_O_LANTERN = registerBlock("angry_soul_pale_jack_o_lantern", JackOLantern.SoulPale::new);

    public static final RegistryObject<Block> BASALT_BRICKS = registerBlock("basalt_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));
    public static final RegistryObject<Block> BASALT_BRICK_SLAB = registerBlock("basalt_brick_slab", () -> new SlabsBlock(BASALT_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICK_STAIRS = registerBlock("basalt_brick_stairs", () -> new StairsBlock(BASALT_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICK_WALL = registerBlock("basalt_brick_wall", () -> new WallsBlock(BASALT_BRICKS));
    public static final RegistryObject<Block> CHISELED_BASALT = registerBlock("chiseled_basalt", () -> new Block(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> BASALT_PILLAR = registerBlock("basalt_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT_STAIRS = registerBlock("smooth_basalt_stairs", () -> new StairsBlock(BASALT_BRICKS.get()));
    public static final RegistryObject<Block> SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab", () -> new SlabsBlock(BASALT_BRICKS.get()));
    public static final RegistryObject<Block> COBBLED_BASALT = registerBlock("cobbled_basalt", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BASALT)));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICKS = registerBlock("cracked_basalt_bricks", () -> new Block(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICK_SLAB = registerBlock("cracked_basalt_brick_slab", () -> new SlabsBlock(CRACKED_BASALT_BRICKS));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICK_STAIRS = registerBlock("cracked_basalt_brick_stairs", () -> new StairsBlock(CRACKED_BASALT_BRICKS));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICK_WALL = registerBlock("cracked_basalt_brick_wall", () -> new WallsBlock(CRACKED_BASALT_BRICKS));
    public static final RegistryObject<Block> BASALTIC_LAVA = registerBlock("basaltic_lava", BasalticLavaBlock::new);

    public static final RegistryObject<Block> HEAVY_CHAIN = registerBlock("heavy_chain", HeavyChainBlock::new);

    public static final RegistryObject<Block> ACACIA_BOOKSHELF = registerBlock("acacia_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> BAMBOO_BOOKSHELF = registerBlock("bamboo_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> BIRCH_BOOKSHELF = registerBlock("birch_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> CHERRY_BOOKSHELF = registerBlock("cherry_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> CRIMSON_BOOKSHELF = registerBlock("crimson_bookshelf", BookshelfBlock::new);
    public static final RegistryObject<Block> DARK_OAK_BOOKSHELF = registerBlock("dark_oak_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> JUNGLE_BOOKSHELF = registerBlock("jungle_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> MANGROVE_BOOKSHELF = registerBlock("mangrove_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> OAK_BOOKSHELF = registerBlock("oak_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> SPRUCE_BOOKSHELF = registerBlock("spruce_bookshelf", BookshelfBlock::new, 300);
    public static final RegistryObject<Block> VERDANT_BOOKSHELF = registerBlock("verdant_bookshelf", BookshelfBlock::new);
    public static final RegistryObject<Block> WARPED_BOOKSHELF = registerBlock("warped_bookshelf", BookshelfBlock::new);

    public static final RegistryObject<Block> BLUE_NETHER_BRICKS = registerBlock("blue_nether_bricks", () -> new BuildingBlock(Blocks.RED_NETHER_BRICKS));
    public static final RegistryObject<Block> BLUE_NETHER_BRICK_SLAB = registerBlock("blue_nether_brick_slab", () -> new SlabsBlock(BLUE_NETHER_BRICKS));
    public static final RegistryObject<Block> BLUE_NETHER_BRICK_STAIRS = registerBlock("blue_nether_brick_stairs", () -> new StairsBlock(BLUE_NETHER_BRICKS));
    public static final RegistryObject<Block> BLUE_NETHER_BRICK_WALL = registerBlock("blue_nether_brick_wall", ()  -> new WallsBlock(BLUE_NETHER_BRICKS));
    public static final RegistryObject<Block> GREEN_NETHER_BRICKS = registerBlock("green_nether_bricks", () -> new BuildingBlock(Blocks.RED_NETHER_BRICKS));
    public static final RegistryObject<Block> GREEN_NETHER_BRICK_SLAB = registerBlock("green_nether_brick_slab", () -> new SlabsBlock(GREEN_NETHER_BRICKS));
    public static final RegistryObject<Block> GREEN_NETHER_BRICK_STAIRS = registerBlock("green_nether_brick_stairs", () -> new StairsBlock(GREEN_NETHER_BRICKS));
    public static final RegistryObject<Block> GREEN_NETHER_BRICK_WALL = registerBlock("green_nether_brick_wall", () -> new WallsBlock(GREEN_NETHER_BRICKS));

    public static final RegistryObject<Block> MOSSY_STONE = registerBlock("mossy_stone", () -> new BuildingBlock(Blocks.STONE));
    public static final RegistryObject<Block> MOSSY_STONE_STAIRS = registerBlock("mossy_stone_stairs", () -> new StairsBlock(MOSSY_STONE));
    public static final RegistryObject<Block> MOSSY_STONE_SLAB = registerBlock("mossy_stone_slab", () -> new SlabsBlock(MOSSY_STONE));
    public static final RegistryObject<Block> MOSSY_STONE_WALL = registerBlock("mossy_stone_wall", () -> new WallsBlock(MOSSY_STONE));

    public static final RegistryObject<Block> ACACIA_WOOD_FENCE = registerBlock("acacia_wood_fence", () -> new WoodFenceBlock(Blocks.ACACIA_FENCE), 300);
    public static final RegistryObject<Block> BAMBOO_BLOCK_FENCE = registerBlock("bamboo_block_fence", () -> new WoodFenceBlock(Blocks.BAMBOO_FENCE), 300);
    public static final RegistryObject<Block> BIRCH_WOOD_FENCE = registerBlock("birch_wood_fence", () -> new WoodFenceBlock(Blocks.BIRCH_FENCE), 300);
    public static final RegistryObject<Block> CHERRY_WOOD_FENCE = registerBlock("cherry_wood_fence", () -> new WoodFenceBlock(Blocks.CHERRY_FENCE), 300);
    public static final RegistryObject<Block> CRIMSON_HYPHAE_FENCE = registerBlock("crimson_hyphae_fence", () -> new WoodFenceBlock(Blocks.CRIMSON_FENCE));
    public static final RegistryObject<Block> DARK_OAK_WOOD_FENCE = registerBlock("dark_oak_wood_fence", () -> new WoodFenceBlock(Blocks.DARK_OAK_FENCE), 300);
    public static final RegistryObject<Block> JUNGLE_WOOD_FENCE = registerBlock("jungle_wood_fence", () -> new WoodFenceBlock(Blocks.JUNGLE_FENCE), 300);
    public static final RegistryObject<Block> MANGROVE_WOOD_FENCE = registerBlock("mangrove_wood_fence", () -> new WoodFenceBlock(Blocks.MANGROVE_FENCE), 300);
    public static final RegistryObject<Block> OAK_WOOD_FENCE = registerBlock("oak_wood_fence", () -> new WoodFenceBlock(Blocks.OAK_FENCE), 300);
    public static final RegistryObject<Block> SPRUCE_WOOD_FENCE = registerBlock("spruce_wood_fence", () -> new WoodFenceBlock(Blocks.SPRUCE_FENCE), 300);
    public static final RegistryObject<Block> VERDANT_HYPHAE_FENCE = registerBlock("verdant_hyphae_fence", () -> new WoodFenceBlock(VERDANT_FENCE.get()));
    public static final RegistryObject<Block> WARPED_HYPHAE_FENCE = registerBlock("warped_hyphae_fence", () -> new WoodFenceBlock(Blocks.WARPED_FENCE));

    public static final RegistryObject<Block> STRIPPED_ACACIA_WOOD_FENCE = registerBlock("stripped_acacia_wood_fence", () -> new WoodFenceBlock(Blocks.ACACIA_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_BIRCH_WOOD_FENCE = registerBlock("stripped_birch_wood_fence", () -> new WoodFenceBlock(Blocks.BIRCH_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_CHERRY_WOOD_FENCE = registerBlock("stripped_cherry_wood_fence", () -> new WoodFenceBlock(Blocks.CHERRY_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_CRIMSON_HYPHAE_FENCE = registerBlock("stripped_crimson_hyphae_fence", () -> new WoodFenceBlock(Blocks.CRIMSON_FENCE));
    public static final RegistryObject<Block> STRIPPED_DARK_OAK_WOOD_FENCE = registerBlock("stripped_dark_oak_wood_fence", () -> new WoodFenceBlock(Blocks.DARK_OAK_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_JUNGLE_WOOD_FENCE = registerBlock("stripped_jungle_wood_fence", () -> new WoodFenceBlock(Blocks.JUNGLE_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_MANGROVE_WOOD_FENCE = registerBlock("stripped_mangrove_wood_fence", () -> new WoodFenceBlock(Blocks.MANGROVE_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_OAK_WOOD_FENCE = registerBlock("stripped_oak_wood_fence", () -> new WoodFenceBlock(Blocks.OAK_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_SPRUCE_WOOD_FENCE = registerBlock("stripped_spruce_wood_fence", () -> new WoodFenceBlock(Blocks.SPRUCE_FENCE), 300);
    public static final RegistryObject<Block> STRIPPED_VERDANT_HYPHAE_FENCE = registerBlock("stripped_verdant_hyphae_fence", () -> new WoodFenceBlock(VERDANT_FENCE.get()));
    public static final RegistryObject<Block> STRIPPED_WARPED_HYPHAE_FENCE = registerBlock("stripped_warped_hyphae_fence", () -> new WoodFenceBlock(Blocks.WARPED_FENCE));

    public static final RegistryObject<Block> ENDERMAN_HEAD = registerBlockOnly("enderman_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> ENDERMAN_WALL_HEAD = registerBlockOnly("enderman_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> SPIDER_HEAD = registerBlockOnly("spider_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> SPIDER_WALL_HEAD = registerBlockOnly("spider_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> BLAZE_HEAD = registerBlockOnly("blaze_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> BLAZE_WALL_HEAD = registerBlockOnly("blaze_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> HUSK_HEAD = registerBlockOnly("husk_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> HUSK_WALL_HEAD = registerBlockOnly("husk_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> SLIME_HEAD = registerBlockOnly("slime_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> SLIME_WALL_HEAD = registerBlockOnly("slime_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> CAVE_SPIDER_HEAD = registerBlockOnly("cave_spider_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> CAVE_SPIDER_WALL_HEAD = registerBlockOnly("cave_spider_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> MAGMA_CUBE_HEAD = registerBlockOnly("magma_cube_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> MAGMA_CUBE_WALL_HEAD = registerBlockOnly("magma_cube_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));
    public static final RegistryObject<Block> DROWNED_HEAD = registerBlockOnly("drowned_head", () -> new HeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_HEAD)));
    public static final RegistryObject<Block> DROWNED_WALL_HEAD = registerBlockOnly("drowned_wall_head", () -> new WallHeadBlock(BlockBehaviour.Properties.copy(Blocks.CREEPER_WALL_HEAD)));

    public static final RegistryObject<Block> ACACIA_MOSAIC = registerBlock("acacia_mosaic", () -> new DecorativeWoodBlock(Blocks.ACACIA_PLANKS), 300);
    public static final RegistryObject<Block> BIRCH_MOSAIC = registerBlock("birch_mosaic", () -> new DecorativeWoodBlock(Blocks.BIRCH_PLANKS), 300);
    public static final RegistryObject<Block> CHERRY_MOSAIC = registerBlock("cherry_mosaic", () -> new DecorativeWoodBlock(Blocks.CHERRY_PLANKS), 300);
    public static final RegistryObject<Block> CRIMSON_MOSAIC = registerBlock("crimson_mosaic", () -> new DecorativeWoodBlock(Blocks.CRIMSON_PLANKS));
    public static final RegistryObject<Block> DARK_OAK_MOSAIC = registerBlock("dark_oak_mosaic", () -> new DecorativeWoodBlock(Blocks.DARK_OAK_PLANKS), 300);
    public static final RegistryObject<Block> JUNGLE_MOSAIC = registerBlock("jungle_mosaic", () -> new DecorativeWoodBlock(Blocks.JUNGLE_PLANKS), 300);
    public static final RegistryObject<Block> MANGROVE_MOSAIC = registerBlock("mangrove_mosaic", () -> new DecorativeWoodBlock(Blocks.MANGROVE_PLANKS), 300);
    public static final RegistryObject<Block> OAK_MOSAIC = registerBlock("oak_mosaic", () -> new DecorativeWoodBlock(Blocks.OAK_PLANKS), 300);
    public static final RegistryObject<Block> SPRUCE_MOSAIC = registerBlock("spruce_mosaic", () -> new DecorativeWoodBlock(Blocks.SPRUCE_PLANKS), 300);
    public static final RegistryObject<Block> VERDANT_MOSAIC = registerBlock("verdant_mosaic", () -> new DecorativeWoodBlock(BlockInit.VERDANT_PLANKS.get()));
    public static final RegistryObject<Block> WARPED_MOSAIC = registerBlock("warped_mosaic", () -> new DecorativeWoodBlock(Blocks.WARPED_PLANKS));

    public static final RegistryObject<Block> ACACIA_MOSAIC_STAIRS = registerBlock("acacia_mosaic_stairs", () -> new StairsBlock(ACACIA_MOSAIC), 300);
    public static final RegistryObject<Block> BIRCH_MOSAIC_STAIRS = registerBlock("birch_mosaic_stairs", () -> new StairsBlock(BIRCH_MOSAIC), 300);
    public static final RegistryObject<Block> CHERRY_MOSAIC_STAIRS = registerBlock("cherry_mosaic_stairs", () -> new StairsBlock(CHERRY_MOSAIC), 300);
    public static final RegistryObject<Block> CRIMSON_MOSAIC_STAIRS = registerBlock("crimson_mosaic_stairs", () -> new StairsBlock(CRIMSON_MOSAIC));
    public static final RegistryObject<Block> DARK_OAK_MOSAIC_STAIRS = registerBlock("dark_oak_mosaic_stairs", () -> new StairsBlock(DARK_OAK_MOSAIC), 300);
    public static final RegistryObject<Block> JUNGLE_MOSAIC_STAIRS = registerBlock("jungle_mosaic_stairs", () -> new StairsBlock(JUNGLE_MOSAIC), 300);
    public static final RegistryObject<Block> MANGROVE_MOSAIC_STAIRS = registerBlock("mangrove_mosaic_stairs", () -> new StairsBlock(MANGROVE_MOSAIC), 300);
    public static final RegistryObject<Block> OAK_MOSAIC_STAIRS = registerBlock("oak_mosaic_stairs", () -> new StairsBlock(OAK_MOSAIC), 300);
    public static final RegistryObject<Block> SPRUCE_MOSAIC_STAIRS = registerBlock("spruce_mosaic_stairs", () -> new StairsBlock(SPRUCE_MOSAIC), 300);
    public static final RegistryObject<Block> VERDANT_MOSAIC_STAIRS = registerBlock("verdant_mosaic_stairs", () -> new StairsBlock(VERDANT_MOSAIC));
    public static final RegistryObject<Block> WARPED_MOSAIC_STAIRS = registerBlock("warped_mosaic_stairs", () -> new StairsBlock(WARPED_MOSAIC));

    public static final RegistryObject<Block> ACACIA_MOSAIC_SLAB = registerBlock("acacia_mosaic_slab", () -> new SlabsBlock(ACACIA_MOSAIC), 300);
    public static final RegistryObject<Block> BIRCH_MOSAIC_SLAB = registerBlock("birch_mosaic_slab", () -> new SlabsBlock(BIRCH_MOSAIC), 300);
    public static final RegistryObject<Block> CHERRY_MOSAIC_SLAB = registerBlock("cherry_mosaic_slab", () -> new SlabsBlock(CHERRY_MOSAIC), 300);
    public static final RegistryObject<Block> CRIMSON_MOSAIC_SLAB = registerBlock("crimson_mosaic_slab", () -> new SlabsBlock(CRIMSON_MOSAIC));
    public static final RegistryObject<Block> DARK_OAK_MOSAIC_SLAB = registerBlock("dark_oak_mosaic_slab", () -> new SlabsBlock(DARK_OAK_MOSAIC), 300);
    public static final RegistryObject<Block> JUNGLE_MOSAIC_SLAB = registerBlock("jungle_mosaic_slab", () -> new SlabsBlock(JUNGLE_MOSAIC), 300);
    public static final RegistryObject<Block> MANGROVE_MOSAIC_SLAB = registerBlock("mangrove_mosaic_slab", () -> new SlabsBlock(MANGROVE_MOSAIC), 300);
    public static final RegistryObject<Block> OAK_MOSAIC_SLAB = registerBlock("oak_mosaic_slab", () -> new SlabsBlock(OAK_MOSAIC), 300);
    public static final RegistryObject<Block> SPRUCE_MOSAIC_SLAB = registerBlock("spruce_mosaic_slab", () -> new SlabsBlock(SPRUCE_MOSAIC), 300);
    public static final RegistryObject<Block> VERDANT_MOSAIC_SLAB = registerBlock("verdant_mosaic_slab", () -> new SlabsBlock(VERDANT_MOSAIC));
    public static final RegistryObject<Block> WARPED_MOSAIC_SLAB = registerBlock("warped_mosaic_slab", () -> new SlabsBlock(WARPED_MOSAIC));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static RegistryObject<Block> registerBlockOnly(String name, Supplier<Block> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier, int burnTime) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BurnableBlockItem(block.get(), new Item.Properties(), burnTime));
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    // Code from net.minecraft.world.level.block.Blocks
    private static FlowerPotBlock flowerPot(Block p_278261_, FeatureFlag... p_278322_) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (p_278322_.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(p_278322_);
        }

        return new FlowerPotBlock(p_278261_, blockbehaviour$properties);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        BLOCKS.getEntries().forEach((entry) -> {
            if (entry.get() instanceof CreativeTabProvider tab) {
                if (tab.getCreativeTabs().contains(event.getTabKey())) {
                    event.accept(entry.get());
                }
            }
        });
    }
}
