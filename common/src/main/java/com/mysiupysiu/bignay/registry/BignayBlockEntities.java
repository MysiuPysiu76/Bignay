package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.blocks.be.CampfiresBlockEntity;
import com.mysiupysiu.bignay.world.blocks.be.HangingSignsBlockEntity;
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
    public static final RegistrySupplier<BlockEntityType<?>> VERDANT_HANGING_SIGN = BLOCK_ENTITIES.register("verdant_hanging_sign", () -> BlockEntityType.Builder.of(HangingSignsBlockEntity::new, BignayBlocks.VERDANT_HANGING_SIGN.get(), BignayBlocks.VERDANT_WALL_HANGING_SIGN.get()).build(null));

    private static RegistrySupplier<BlockEntityType<?>> campfire(String id, RegistrySupplier<Block> block) {
        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(CampfiresBlockEntity::new, block.get()).build(null));
    }
}
