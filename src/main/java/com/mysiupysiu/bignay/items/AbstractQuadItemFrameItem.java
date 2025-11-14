package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.entities.QuadItemFrameEntity;
import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class AbstractQuadItemFrameItem extends Item implements CreativeTabProvider {

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

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }
}
