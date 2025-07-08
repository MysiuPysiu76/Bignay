package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallsBlock extends WallBlock {

    public WallsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).strength(2.0f).requiresCorrectToolForDrops());
    }
}
