package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StairsBlock extends StairBlock {

    public StairsBlock() {
        super(Blocks.STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }
}
