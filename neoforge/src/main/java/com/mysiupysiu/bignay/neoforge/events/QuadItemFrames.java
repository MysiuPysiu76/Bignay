package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.layers.ModelLayers;
import com.mysiupysiu.bignay.client.models.GlowQuadItemFrameModel;
import com.mysiupysiu.bignay.client.models.QuadItemFrameModel;
import com.mysiupysiu.bignay.client.renderers.GlowQuadItemFrameRenderer;
import com.mysiupysiu.bignay.client.renderers.QuadItemFrameRenderer;
import com.mysiupysiu.bignay.registry.init.BignayEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class QuadItemFrames {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BignayEntities.QUAD_ITEM_FRAME.get(), QuadItemFrameRenderer::new);
        event.registerEntityRenderer(BignayEntities.GLOW_QUAD_ITEM_FRAME.get(), GlowQuadItemFrameRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.QUAD_ITEM_FRAME, QuadItemFrameModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GLOW_QUAD_ITEM_FRAME, GlowQuadItemFrameModel::createBodyLayer);
    }
}
