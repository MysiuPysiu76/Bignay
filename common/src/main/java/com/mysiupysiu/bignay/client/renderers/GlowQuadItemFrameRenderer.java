package com.mysiupysiu.bignay.client.renderers;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.models.GlowQuadItemFrameModel;
import com.mysiupysiu.bignay.client.models.QuadItemFrameModel;
import com.mysiupysiu.bignay.world.entities.QuadItemFrameEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GlowQuadItemFrameRenderer extends QuadItemFrameRenderer {

    public static final ModelLayerLocation GLOW_QUAD_ITEM_FRAME_LAYER = new ModelLayerLocation(new ResourceLocation(BignayMod.MODID, "glow_quad_item_frame"), "main");

    public GlowQuadItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new QuadItemFrameModel(ctx.bakeLayer(GLOW_QUAD_ITEM_FRAME_LAYER)));
    }

    @Override
    public ResourceLocation getTextureLocation(QuadItemFrameEntity entity) {
        return GlowQuadItemFrameModel.TEXTURE;
    }
}
