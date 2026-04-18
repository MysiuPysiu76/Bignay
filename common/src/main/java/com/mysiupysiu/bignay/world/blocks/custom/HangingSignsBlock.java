package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.blocks.be.HangingSignsBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HangingSignsBlock extends CeilingHangingSignBlock {

    public HangingSignsBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignsBlockEntity(pos, state);
    }
}
