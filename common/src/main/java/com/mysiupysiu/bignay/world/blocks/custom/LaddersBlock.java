package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LaddersBlock extends LadderBlock implements FunctionalBlocks {

    public LaddersBlock() {
        this(Blocks.LADDER);
    }

    public LaddersBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }
}
