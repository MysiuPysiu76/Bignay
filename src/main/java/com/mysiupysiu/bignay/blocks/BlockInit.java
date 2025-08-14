package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    public static final RegistryObject<Block> VERDANT_PLANKS = registerBlock("verdant_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANKS = registerBlock("vertical_verdant_planks", VerticalPlanksBlock::new);
    public static final RegistryObject<Block> VERDANT_STAIRS = registerBlock("verdant_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERDANT_SLAB = registerBlock("verdant_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERDANT_FENCE = registerBlock("verdant_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE)));
    public static final RegistryObject<Block> VERDANT_FENCE_GATE = registerBlock("verdant_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_DOOR = registerBlock("verdant_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).noOcclusion(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_TRAPDOOR = registerBlock("verdant_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_TRAPDOOR).noOcclusion(), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_PRESSURE_PLATE = registerBlock("verdant_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.CRIMSON_PRESSURE_PLATE), BlockSetType.CRIMSON));
    public static final RegistryObject<Block> VERDANT_BUTTON = registerBlock("verdant_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_BUTTON), BlockSetType.CRIMSON, 30, false));
    public static final RegistryObject<Block> VERDANT_CAMPFIRE = registerBlock("verdant_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> SOUL_VERDANT_CAMPFIRE = registerBlock("soul_verdant_campfire", () -> new CustomCampfireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_CAMPFIRE)));
    public static final RegistryObject<Block> VERDANT_SIGN = registerBlockOnly("verdant_sign", () -> new SignsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_WALL_SIGN = registerBlockOnly("verdant_wall_sign", () -> new WallSignsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_WALL_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_HANGING_SIGN = registerBlockOnly("verdant_hanging_sign", () -> new CustomHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HANGING_SIGN), BignayWoodType.VERDANT));
    public static final RegistryObject<Block> VERDANT_WALL_HANGING_SIGN = registerBlockOnly("verdant_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_WALL_HANGING_SIGN), BignayWoodType.VERDANT));

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANK_STAIRS = registerBlock("vertical_acacia_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANK_STAIRS = registerBlock("vertical_bamboo_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANK_STAIRS = registerBlock("vertical_birch_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANK_STAIRS = registerBlock("vertical_cherry_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANK_STAIRS = registerBlock("vertical_crimson_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANK_STAIRS = registerBlock("vertical_dark_oak_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANK_STAIRS = registerBlock("vertical_jungle_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANK_STAIRS = registerBlock("vertical_mangrove_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANK_STAIRS = registerBlock("vertical_oak_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANK_STAIRS = registerBlock("vertical_spruce_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANK_STAIRS = registerBlock("vertical_verdant_plank_stairs", StairsBlock::new);
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANK_STAIRS = registerBlock("vertical_warped_plank_stairs", StairsBlock::new);

    public static final RegistryObject<Block> VERTICAL_ACACIA_PLANK_SLAB = registerBlock("vertical_acacia_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_BAMBOO_PLANK_SLAB = registerBlock("vertical_bamboo_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_BIRCH_PLANK_SLAB = registerBlock("vertical_birch_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_CHERRY_PLANK_SLAB = registerBlock("vertical_cherry_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_CRIMSON_PLANK_SLAB = registerBlock("vertical_crimson_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_DARK_OAK_PLANK_SLAB = registerBlock("vertical_dark_oak_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_JUNGLE_PLANK_SLAB = registerBlock("vertical_jungle_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_MANGROVE_PLANK_SLAB = registerBlock("vertical_mangrove_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_OAK_PLANK_SLAB = registerBlock("vertical_oak_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_SPRUCE_PLANK_SLAB = registerBlock("vertical_spruce_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_VERDANT_PLANK_SLAB = registerBlock("vertical_verdant_plank_slab", SlabsBlock::new);
    public static final RegistryObject<Block> VERTICAL_WARPED_PLANK_SLAB = registerBlock("vertical_warped_plank_slab", SlabsBlock::new);

    public static final RegistryObject<Block> VERDANT_NYLIUM = registerBlock("verdant_nylium", () -> new NyliumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CRIMSON_NYLIUM).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NYLIUM).randomTicks()));
    public static final RegistryObject<Block> VERDANT_FUNGUS = registerBlock("verdant_fungus", () -> new FungusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instabreak().noCollission().sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY), ModConfiguredFeatures.VERDANT_FUNGUS, VERDANT_NYLIUM.get()));
    public static final RegistryObject<Block> VERDANT_ROOTS = registerBlock("verdant_roots", ()-> new RootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().instabreak().sound(SoundType.ROOTS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> VERDANT_WART_BLOCK = registerBlock("verdant_wart_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.0F).sound(SoundType.WART_BLOCK)));
    public static final RegistryObject<Block> POTTED_VERDANT_FUNGUS = registerBlockOnly("potted_verdant_fungus", () -> flowerPot(VERDANT_FUNGUS.get()));
    public static final RegistryObject<Block> POTTED_VERDANT_ROOTS = registerBlockOnly("potted_verdant_roots", () -> flowerPot(VERDANT_ROOTS.get()));
    public static final RegistryObject<Block> VERDANT_SPROUTS = registerBlock("verdant_sprouts", () -> new NetherSproutsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> PALE_PUMPKIN = registerBlock("pale_pumpkin", () -> new PumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.DIDGERIDOO).strength(1.0F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> CARVED_PALE_PUMPKIN = registerBlock("carved_pale_pumpkin", () -> new EquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> PALE_PUMPKIN_STEM = registerBlockOnly("pale_pumpkin_stem", () -> new StemBlock((StemGrownBlock) PALE_PUMPKIN.get(), ItemInit.PALE_PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.HARD_CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ATTACHED_PALE_PUMPKIN_STEM = registerBlockOnly("attached_pale_pumpkin_stem", () -> new AttachedStemBlock((StemGrownBlock) PALE_PUMPKIN.get(), ItemInit.PALE_PUMPKIN_SEEDS, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> PALE_JACK_O_LANTERN = registerBlock("pale_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 15; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = registerBlock("soul_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 10; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SOUL_PALE_JACK_O_LANTERN = registerBlock("soul_pale_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 10; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_CARVED_PUMPKIN = registerBlock("happy_carved_pumpkin", () -> new EquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_JACK_O_LANTERN = registerBlock("happy_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 15; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_CARVED_PALE_PUMPKIN = registerBlock("happy_carved_pale_pumpkin", () -> new EquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_PALE_JACK_O_LANTERN = registerBlock("happy_pale_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 15; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_SOUL_JACK_O_LANTERN = registerBlock("happy_soul_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 10; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> HAPPY_SOUL_PALE_JACK_O_LANTERN = registerBlock("happy_soul_pale_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 10; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ANXIOUS_CARVED_PUMPKIN = registerBlock("anxious_carved_pumpkin", () -> new EquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ANXIOUS_CARVED_PALE_PUMPKIN = registerBlock("anxious_carved_pale_pumpkin", () -> new EquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ANXIOUS_JACK_O_LANTERN = registerBlock("anxious_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> { return 15; }).isValidSpawn(BlockInit::always).pushReaction(PushReaction.DESTROY)));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        RegistryObject<Item> blockItem = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        NATURAL_TAB_ITEMS.add(blockItem);
        return block;
    }

    private static RegistryObject<Block> registerBlockOnly(String name, Supplier<Block> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
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
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            NATURAL_TAB_ITEMS.forEach(event::accept);
        }
    }

    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return true;
    }
}
