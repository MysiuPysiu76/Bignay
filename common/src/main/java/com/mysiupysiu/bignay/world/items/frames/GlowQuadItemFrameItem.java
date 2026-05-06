package com.mysiupysiu.bignay.world.items.frames;

import com.mysiupysiu.bignay.registry.BignayEntities;
import com.mysiupysiu.bignay.world.entities.GlowQuadItemFrameEntity;
import com.mysiupysiu.bignay.world.entities.QuadItemFrameEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class GlowQuadItemFrameItem extends AbstractQuadItemFrameItem {

    public GlowQuadItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    protected QuadItemFrameEntity createEntity(Level level) {
        return new GlowQuadItemFrameEntity((EntityType<? extends QuadItemFrameEntity>) BignayEntities.GLOW_QUAD_ITEM_FRAME.get(), level);
    }
}
