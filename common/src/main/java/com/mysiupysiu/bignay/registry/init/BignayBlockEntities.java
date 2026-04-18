package com.mysiupysiu.bignay.registry.init;

import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.blocks.be.CampfiresBlockEntity;
import com.mysiupysiu.bignay.world.blocks.be.SignsBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BignayBlockEntities {

    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES = new Registrar<>();

    public static final RegistrySupplier<BlockEntityType<?>> ACACIA_CAMPFIRE = BLOCK_ENTITIES.register("acacia_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.ACACIA_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> BIRCH_CAMPFIRE = BLOCK_ENTITIES.register("birch_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.BIRCH_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> CHERRY_CAMPFIRE = BLOCK_ENTITIES.register("cherry_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.CHERRY_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> CRIMSON_CAMPFIRE = BLOCK_ENTITIES.register("crimson_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.CRIMSON_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> DARK_OAK_CAMPFIRE = BLOCK_ENTITIES.register("dark_oak_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.DARK_OAK_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> JUNGLE_CAMPFIRE = BLOCK_ENTITIES.register("jungle_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.JUNGLE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> MANGROVE_CAMPFIRE = BLOCK_ENTITIES.register("mangrove_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.MANGROVE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> OAK_CAMPFIRE = BLOCK_ENTITIES.register("oak_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.OAK_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SPRUCE_CAMPFIRE = BLOCK_ENTITIES.register("spruce_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SPRUCE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> WARPED_CAMPFIRE = BLOCK_ENTITIES.register("warped_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.WARPED_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> VERDANT_CAMPFIRE = BLOCK_ENTITIES.register("verdant_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.VERDANT_CAMPFIRE.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<?>> SOUL_ACACIA_CAMPFIRE = BLOCK_ENTITIES.register("soul_acacia_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_ACACIA_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_BIRCH_CAMPFIRE = BLOCK_ENTITIES.register("soul_birch_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_BIRCH_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_CHERRY_CAMPFIRE = BLOCK_ENTITIES.register("soul_cherry_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_CHERRY_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_CRIMSON_CAMPFIRE = BLOCK_ENTITIES.register("soul_crimson_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_CRIMSON_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_DARK_OAK_CAMPFIRE = BLOCK_ENTITIES.register("soul_dark_oak_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_DARK_OAK_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_JUNGLE_CAMPFIRE = BLOCK_ENTITIES.register("soul_jungle_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_JUNGLE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_MANGROVE_CAMPFIRE = BLOCK_ENTITIES.register("soul_mangrove_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_MANGROVE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_OAK_CAMPFIRE = BLOCK_ENTITIES.register("soul_oak_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_OAK_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_SPRUCE_CAMPFIRE = BLOCK_ENTITIES.register("soul_spruce_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_SPRUCE_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_WARPED_CAMPFIRE = BLOCK_ENTITIES.register("soul_warped_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_WARPED_CAMPFIRE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<?>> SOUL_VERDANT_CAMPFIRE = BLOCK_ENTITIES.register("soul_verdant_campfire", () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, BignayBlocks.SOUL_VERDANT_CAMPFIRE.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<?>> VERDANT_SIGN = BLOCK_ENTITIES.register("verdant_sign", () -> BlockEntityType.Builder.of(SignsBlockEntity::new, BignayBlocks.VERDANT_SIGN.get(), BignayBlocks.VERDANT_WALL_SIGN.get()).build(null));
//    public static final RegistrySupplier<BlockEntityType<CustomHangingSignBlockEntity>> VERDANT_HANGING_SIGN = BLOCK_ENTITIES.register("verdant_hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, BlockInit.VERDANT_HANGING_SIGN.get(), BlockInit.VERDANT_WALL_HANGING_SIGN.get()).build(null));

    private static RegistrySupplier<BlockEntityType<?>> campfire(String id, RegistrySupplier<Block> block) {
        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, block.get()).build(null));
    }

}



//
//package com.mysiupysiu.bignay.registry.init;
//
//import com.mysiupysiu.bignay.client.renderers.CustomCampfireRenderer;
//import com.mysiupysiu.bignay.registry.Registrar;
//import com.mysiupysiu.bignay.registry.RegistrySupplier;
//import com.mysiupysiu.bignay.world.blocks.be.CustomCampfireBlockEntity;
//import com.mysiupysiu.bignay.world.blocks.custom.CustomCampfireBlock;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//
//public class BignayBlockEntities {
//
//    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES = new Registrar<>();
//
//    public static final RegistrySupplier<BlockEntityType<?>> ACACIA_CAMPFIRE = campfire("acacia_campfire", BignayBlocks.ACACIA_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> BIRCH_CAMPFIRE = campfire("birch_campfire", BignayBlocks.BIRCH_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> CHERRY_CAMPFIRE = campfire("cherry_campfire", BignayBlocks.CHERRY_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> CRIMSON_CAMPFIRE = campfire("crimson_campfire", BignayBlocks.CRIMSON_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> DARK_OAK_CAMPFIRE = campfire("dark_oak_campfire", BignayBlocks.DARK_OAK_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> JUNGLE_CAMPFIRE = campfire("jungle_campfire", BignayBlocks.JUNGLE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> MANGROVE_CAMPFIRE = campfire("mangrove_campfire", BignayBlocks.MANGROVE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> OAK_CAMPFIRE = campfire("oak_campfire", BignayBlocks.OAK_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SPRUCE_CAMPFIRE = campfire("spruce_campfire", BignayBlocks.SPRUCE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> WARPED_CAMPFIRE = campfire("warped_campfire", (BignayBlocks.WARPED_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> VERDANT_CAMPFIRE = campfire("verdant_campfire", BignayBlocks.VERDANT_CAMPFIRE);
//
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_ACACIA_CAMPFIRE = campfire("soul_acacia_campfire", BignayBlocks.SOUL_ACACIA_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_BIRCH_CAMPFIRE = campfire("soul_birch_campfire", BignayBlocks.SOUL_BIRCH_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_CHERRY_CAMPFIRE = campfire("soul_cherry_campfire", BignayBlocks.SOUL_CHERRY_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_CRIMSON_CAMPFIRE = campfire("soul_crimson_campfire", BignayBlocks.SOUL_CRIMSON_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_DARK_OAK_CAMPFIRE = campfire("soul_dark_oak_campfire", BignayBlocks.SOUL_DARK_OAK_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_JUNGLE_CAMPFIRE = campfire("soul_jungle_campfire", BignayBlocks.SOUL_JUNGLE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_MANGROVE_CAMPFIRE = campfire("soul_mangrove_campfire", BignayBlocks.SOUL_MANGROVE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_OAK_CAMPFIRE = campfire("soul_oak_campfire", BignayBlocks.SOUL_OAK_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_SPRUCE_CAMPFIRE = campfire("soul_spruce_campfire", BignayBlocks.SOUL_SPRUCE_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_WARPED_CAMPFIRE = campfire("soul_warped_campfire", BignayBlocks.SOUL_WARPED_CAMPFIRE);
//    public static final RegistrySupplier<BlockEntityType<?>> SOUL_VERDANT_CAMPFIRE = campfire("soul_verdant_campfire", BignayBlocks.SOUL_VERDANT_CAMPFIRE);
//
////    public static final RegistrySupplier<BlockEntityType<SignsBlockEntity>> VERDANT_SIGN = BLOCK_ENTITIES.register("verdant_sign", () -> BlockEntityType.Builder.of(SignsBlockEntity::new, BlockInit.VERDANT_SIGN.get(), BlockInit.VERDANT_WALL_SIGN.get()).build(null));
////    public static final RegistrySupplier<BlockEntityType<CustomHangingSignBlockEntity>> VERDANT_HANGING_SIGN = BLOCK_ENTITIES.register("verdant_hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, BlockInit.VERDANT_HANGING_SIGN.get(), BlockInit.VERDANT_WALL_HANGING_SIGN.get()).build(null));
//
//    private static RegistrySupplier<BlockEntityType<?>> campfire(String id, RegistrySupplier<Block> block) {
//        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, block.get()).build(null));
//    }
//}

