package com.mysiupysiu.bignay.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class QuadItemFrameItem extends Item {

    public QuadItemFrameItem(Properties p) {
        super(p);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Direction dir = context.getClickedFace();
        Player player = context.getPlayer();

        if (!level.isClientSide && player != null) {
            QuadItemFrameEntity entity = new QuadItemFrameEntity(EntityInit.QUAD_ITEM_FRAME.get(), level);
            entity.setPos(pos.getX(), pos.getY(), pos.getZ());
            entity.setDirection(dir);

            if (entity.survives()) {
                level.addFreshEntity(entity);

                if (!player.isCreative()) {
                    context.getItemInHand().shrink(1);
                }

                entity.playPlacementSound();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
