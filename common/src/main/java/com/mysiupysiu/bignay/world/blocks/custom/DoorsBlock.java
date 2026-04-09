package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class DoorsBlock extends DoorBlock implements BuildingBlocks {

    public DoorsBlock(Properties properties, BlockSetType blockSetType) {
        super(properties.noOcclusion(), blockSetType);
    }

    public DoorsBlock(Block block,BlockSetType blockSetType) {
        this(BlockBehaviour.Properties.copy(block), blockSetType);
    }
}
