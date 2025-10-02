package com.mysiupysiu.bignay.entities;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class GlowQuadItemFrameModel extends QuadItemFrameModel {

    public static final ResourceLocation TEXTURE = new ResourceLocation("bignay", "textures/entity/glow_quad_item_frame.png");

    public GlowQuadItemFrameModel(ModelPart root) {
        super(root);
    }
}
