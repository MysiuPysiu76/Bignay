package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class TrapDoorsBlock extends TrapDoorBlock implements BuildingBlocks {

    public TrapDoorsBlock(Properties properties, BlockSetType blockSetType) {
        super(blockSetType, properties.noOcclusion());
    }

    public TrapDoorsBlock(Block block, BlockSetType blockSetType) {
        this(BlockBehaviour.Properties.ofFullCopy(block), blockSetType);
    }
}
