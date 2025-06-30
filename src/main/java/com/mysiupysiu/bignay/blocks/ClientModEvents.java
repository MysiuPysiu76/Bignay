package com.mysiupysiu.bignay.blocks;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "bignay", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(BlockEntityInit.ACACIA_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.BIRCH_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.CHERRY_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.CRIMSON_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.DARK_OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.JUNGLE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.MANGROVE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.OAK_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.SPRUCE_CAMPFIRE.get(), CustomCampfireRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.WARPED_CAMPFIRE.get(), CustomCampfireRenderer::new);
    }
}
