package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.blocks.be.SignsBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SignsBlock extends StandingSignBlock {

    public SignsBlock(Properties properties, WoodType woodType) {
        super(woodType, properties);
    }

    public SignsBlock(Block block, WoodType woodType) {
        this(BlockBehaviour.Properties.ofFullCopy(block), woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignsBlockEntity(pos, state);
    }
}
