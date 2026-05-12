package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BuildingBlock extends Block implements BuildingBlocks {

    public BuildingBlock(Properties p) {
        super(p);
    }

    public BuildingBlock(Block b) {
        super(BlockBehaviour.Properties.ofFullCopy(b));
    }
}
