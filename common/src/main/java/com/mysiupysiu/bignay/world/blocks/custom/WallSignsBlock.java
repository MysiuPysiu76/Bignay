package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.blocks.be.SignsBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WallSignsBlock extends WallSignBlock {

    public WallSignsBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    public WallSignsBlock(Block block, WoodType woodType) {
        this(BlockBehaviour.Properties.copy(block), woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignsBlockEntity(pos, state);
    }
}
