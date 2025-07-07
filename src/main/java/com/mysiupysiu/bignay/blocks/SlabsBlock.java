package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SlabsBlock extends SlabBlock {

    public SlabsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }
}
