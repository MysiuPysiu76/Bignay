package com.mysiupysiu.bignay.client.layers;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayers {

    public static final ModelLayerLocation QUAD_ITEM_FRAME = layer("quad_item_frame");
    public static final ModelLayerLocation GLOW_QUAD_ITEM_FRAME = layer("glow_quad_item_frame");

    private static ModelLayerLocation layer(String id) {
        return new ModelLayerLocation(ResourceLocation.tryBuild(BignayMod.MODID, id), "main");
    }
}
