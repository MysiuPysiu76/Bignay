package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FenceGatesBlock extends FenceGateBlock implements BuildingBlocks {

    public FenceGatesBlock(Properties properties, WoodType woodType) {
        super(woodType, properties);
    }

    public FenceGatesBlock(Block block, WoodType woodType) {
        this(BlockBehaviour.Properties.ofFullCopy(block), woodType);
    }
}
