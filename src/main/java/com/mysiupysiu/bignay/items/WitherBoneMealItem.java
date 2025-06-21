package com.mysiupysiu.bignay.items;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WitherBoneMealItem extends Item {

    public WitherBoneMealItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        BlockState state = level.getBlockState(pos);

        if (!level.isClientSide && state.getBlock() == Blocks.NETHER_WART) {
            Integer age = state.getValue(NetherWartBlock.AGE);

            if (age < 3) {
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                context.getItemInHand().shrink(1);

                if(level.random.nextFloat() < 0.8f) {
                    level.setBlock(pos, state.setValue(NetherWartBlock.AGE, age + 1), 2);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }
}