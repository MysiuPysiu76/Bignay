package com.mysiupysiu.bignay.entities;

import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlowQuadItemFrameEntity extends QuadItemFrameEntity {

    public GlowQuadItemFrameEntity(EntityType<? extends QuadItemFrameEntity> p_31761_, Level p_31762_) {
        super(p_31761_, p_31762_);
    }

    @Override
    public SoundEvent getRemoveItemSound() {
        return SoundEvents.GLOW_ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public SoundEvent getBreakSound() {
        return SoundEvents.GLOW_ITEM_FRAME_BREAK;
    }

    @Override
    public SoundEvent getPlaceSound() {
        return SoundEvents.GLOW_ITEM_FRAME_PLACE;
    }

    @Override
    public SoundEvent getAddItemSound() {
        return SoundEvents.GLOW_ITEM_FRAME_ADD_ITEM;
    }

    @Override
    public SoundEvent getRotateItemSound() {
        return SoundEvents.GLOW_ITEM_FRAME_ROTATE_ITEM;
    }

    @Override
    protected ItemStack getFrameItemStack() {
        return new ItemStack(ItemInit.GLOW_QUAD_ITEM_FRAME.get());
    }
}
