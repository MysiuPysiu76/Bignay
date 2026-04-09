package com.mysiupysiu.bignay.world.items.frames;

import com.mysiupysiu.bignay.world.entities.QuadItemFrameEntity;
import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class AbstractQuadItemFrameItem extends Item implements FunctionalBlocks {

    public AbstractQuadItemFrameItem(Properties properties) {
        super(properties);
    }

    protected abstract QuadItemFrameEntity createEntity(Level level);

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Direction dir = context.getClickedFace();
        Player player = context.getPlayer();

        if (!level.isClientSide && player != null) {
            QuadItemFrameEntity entity = createEntity(level);
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
