package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.blocks.be.HangingSignsBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WallHangingSignsBlock extends WallHangingSignBlock {

    public WallHangingSignsBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HangingSignsBlockEntity(pos, state);
    }
}
