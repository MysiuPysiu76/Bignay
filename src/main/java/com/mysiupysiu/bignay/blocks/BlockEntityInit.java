package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "bignay")
public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "bignay");

    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> ACACIA_CAMPFIRE = BLOCK_ENTITIES.register("acacia_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.ACACIA_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> BIRCH_CAMPFIRE = BLOCK_ENTITIES.register("birch_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.BIRCH_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> CHERRY_CAMPFIRE = BLOCK_ENTITIES.register("cherry_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.CHERRY_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> CRIMSON_CAMPFIRE = BLOCK_ENTITIES.register("crimson_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.CRIMSON_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> DARK_OAK_CAMPFIRE = BLOCK_ENTITIES.register("dark_oak_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.DARK_OAK_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> JUNGLE_CAMPFIRE = BLOCK_ENTITIES.register("jungle_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.JUNGLE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> MANGROVE_CAMPFIRE = BLOCK_ENTITIES.register("mangrove_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.MANGROVE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> OAK_CAMPFIRE = BLOCK_ENTITIES.register("oak_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.OAK_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SPRUCE_CAMPFIRE = BLOCK_ENTITIES.register("spruce_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SPRUCE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> WARPED_CAMPFIRE = BLOCK_ENTITIES.register("warped_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.WARPED_CAMPFIRE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
