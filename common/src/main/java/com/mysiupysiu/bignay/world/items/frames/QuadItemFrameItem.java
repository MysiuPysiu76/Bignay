package com.mysiupysiu.bignay.world.items.frames;

import com.mysiupysiu.bignay.registry.BignayEntities;
import com.mysiupysiu.bignay.world.entities.QuadItemFrameEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class QuadItemFrameItem extends AbstractQuadItemFrameItem {

    public QuadItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    protected QuadItemFrameEntity createEntity(Level level) {
        return new QuadItemFrameEntity((EntityType<? extends QuadItemFrameEntity>) BignayEntities.QUAD_ITEM_FRAME.get(), level);
    }
}
