package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VerticalPlanksBlock extends Block {

    public VerticalPlanksBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }
}
