package com.mysiupysiu.bignay.entities;

import net.minecraft.world.level.Level;

public class QuadItemFrameItem extends AbstractQuadItemFrameItem {

    public QuadItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    protected QuadItemFrameEntity createEntity(Level level) {
        return new QuadItemFrameEntity(EntityInit.QUAD_ITEM_FRAME.get(), level);
    }
}
