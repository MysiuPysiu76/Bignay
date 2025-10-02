package com.mysiupysiu.bignay.entities;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GlowQuadItemFrameRenderer<T>  extends QuadItemFrameRenderer {

    public GlowQuadItemFrameRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new QuadItemFrameModel(ctx.bakeLayer(BignayMod.ClientModEvents.GLOW_QUAD_ITEM_FRAME_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(QuadItemFrameEntity entity) {
        return GlowQuadItemFrameModel.TEXTURE;
    }
}
