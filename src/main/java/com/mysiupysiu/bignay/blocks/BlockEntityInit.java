package com.mysiupysiu.bignay.blocks;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> VERDANT_CAMPFIRE = BLOCK_ENTITIES.register("verdant_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.VERDANT_CAMPFIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_ACACIA_CAMPFIRE = BLOCK_ENTITIES.register("soul_acacia_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_ACACIA_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_BIRCH_CAMPFIRE = BLOCK_ENTITIES.register("soul_birch_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_BIRCH_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_CHERRY_CAMPFIRE = BLOCK_ENTITIES.register("soul_cherry_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_CHERRY_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_CRIMSON_CAMPFIRE = BLOCK_ENTITIES.register("soul_crimson_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_CRIMSON_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_DARK_OAK_CAMPFIRE = BLOCK_ENTITIES.register("soul_dark_oak_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_DARK_OAK_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_JUNGLE_CAMPFIRE = BLOCK_ENTITIES.register("soul_jungle_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_JUNGLE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_MANGROVE_CAMPFIRE = BLOCK_ENTITIES.register("soul_mangrove_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_MANGROVE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_OAK_CAMPFIRE = BLOCK_ENTITIES.register("soul_oak_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_OAK_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_SPRUCE_CAMPFIRE = BLOCK_ENTITIES.register("soul_spruce_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_SPRUCE_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_WARPED_CAMPFIRE = BLOCK_ENTITIES.register("soul_warped_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_WARPED_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomCampfireBlockEntity>> SOUL_VERDANT_CAMPFIRE = BLOCK_ENTITIES.register("soul_verdant_campfire", () -> BlockEntityType.Builder.of(CustomCampfireBlockEntity::new, BlockInit.SOUL_VERDANT_CAMPFIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SignsBlockEntity>> VERDANT_SIGN = BLOCK_ENTITIES.register("verdant_sign", () -> BlockEntityType.Builder.of(SignsBlockEntity::new, BlockInit.VERDANT_SIGN.get(), BlockInit.VERDANT_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignBlockEntity>> VERDANT_HANGING_SIGN = BLOCK_ENTITIES.register("verdant_hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, BlockInit.VERDANT_HANGING_SIGN.get(), BlockInit.VERDANT_WALL_HANGING_SIGN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ACACIA_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BIRCH_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(CHERRY_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(CRIMSON_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(DARK_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(JUNGLE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(MANGROVE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SPRUCE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(WARPED_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(VERDANT_CAMPFIRE.get(), CustomCampfireRenderer::new);

        BlockEntityRenderers.register(SOUL_ACACIA_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_BIRCH_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_CHERRY_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_CRIMSON_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_DARK_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_JUNGLE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_MANGROVE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_SPRUCE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_WARPED_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(SOUL_VERDANT_CAMPFIRE.get(), CustomCampfireRenderer::new);
    }
}
