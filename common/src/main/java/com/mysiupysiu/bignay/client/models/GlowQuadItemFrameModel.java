package com.mysiupysiu.bignay.client.models;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class GlowQuadItemFrameModel extends QuadItemFrameModel {

    public static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(BignayMod.MODID, "textures/entity/glow_quad_item_frame.png");

    public GlowQuadItemFrameModel(ModelPart root) {
        super(root);
    }
}
