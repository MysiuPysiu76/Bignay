package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WoodButtonBlock extends ButtonBlock implements BuildingBlocks {

    public WoodButtonBlock(Properties properties, BlockSetType blockSetType) {
        super(blockSetType, 30, properties.noOcclusion());
    }

    public WoodButtonBlock(Block block, BlockSetType blockSetType) {
        this(BlockBehaviour.Properties.ofFullCopy(block), blockSetType);
    }
}
