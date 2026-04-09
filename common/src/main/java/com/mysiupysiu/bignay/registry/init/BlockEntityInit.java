//package com.mysiupysiu.bignay.registry.content;
//
//import com.mysiupysiu.bignay.blocks.CustomCampfireRenderer;
//import com.mysiupysiu.bignay.blocks.CustomHangingSignBlockEntity;
//import com.mysiupysiu.bignay.blocks.SignsBlockEntity;
////import net.minecraftforge.eventbus.api.IEventBus;
////import net.minecraftforge.eventbus.api.SubscribeEvent;
////import net.minecraftforge.fml.common.Mod;
////import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
////import net.minecraftforge.registries.DeferredRegister;
////import net.minecraftforge.registries.ForgeRegistries;
////import net.minecraftforge.registries.RegistryObject;
//
//public class BlockEntityInit {
//
////    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "bignay");
//
//
//
////    public static void register(IEventBus eventBus) {
////        BLOCK_ENTITIES.register(eventBus);
////    }
//
//    @SubscribeEvent
//    public static void onClientSetup(FMLClientSetupEvent event) {
//        BlockEntityRenderers.register(ACACIA_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(BIRCH_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(CHERRY_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(CRIMSON_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(DARK_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(JUNGLE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(MANGROVE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SPRUCE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(WARPED_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(VERDANT_CAMPFIRE.get(), CustomCampfireRenderer::new);
////    public static final RegistrySupplier<BlockEntityType<SignsBlockEntity>> VERDANT_SIGN = campfire("verdant_sign", () -> BlockEntityType.Builder.of(SignsBlockEntity::new, BlockInit.VERDANT_SIGN.get(), BlockInit.VERDANT_WALL_SIGN.get()).build(null));
////    public static final RegistrySupplier<BlockEntityType<CustomHangingSignBlockEntity>> VERDANT_HANGING_SIGN = campfire("verdant_hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, BlockInit.VERDANT_HANGING_SIGN.get(), BlockInit.VERDANT_WALL_HANGING_SIGN.get()).build(null));
//
//    private static RegistrySupplier<BlockEntityType<CampfiresBlockEntity>> campfire(String id, RegistrySupplier<Block> block) {
//        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(ss, block.get()).build(null));
//    }
//        BlockEntityRenderers.register(SOUL_ACACIA_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_BIRCH_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_CHERRY_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_CRIMSON_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_DARK_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_JUNGLE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_MANGROVE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_SPRUCE_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_WARPED_CAMPFIRE.get(), CustomCampfireRenderer::new);
//        BlockEntityRenderers.register(SOUL_VERDANT_CAMPFIRE.get(), CustomCampfireRenderer::new);
//    }
//}
