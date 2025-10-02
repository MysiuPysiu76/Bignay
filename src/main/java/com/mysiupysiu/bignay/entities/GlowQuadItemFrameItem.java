package com.mysiupysiu.bignay.entities;

import net.minecraft.world.level.Level;

public class GlowQuadItemFrameItem extends AbstractQuadItemFrameItem {

    public GlowQuadItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    protected QuadItemFrameEntity createEntity(Level level) {
        return new GlowQuadItemFrameEntity(EntityInit.GLOW_QUAD_ITEM_FRAME.get(), level);
    }
}
