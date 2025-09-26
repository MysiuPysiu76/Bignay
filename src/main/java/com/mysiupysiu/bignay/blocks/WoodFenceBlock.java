package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoodFenceBlock  extends FenceBlock {

    public WoodFenceBlock(Block block) {
        super(BlockBehaviour.Properties.copy((FenceBlock) block));
    }

}