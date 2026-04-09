package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PressurePlatesBock extends PressurePlateBlock implements BuildingBlocks {

    public PressurePlatesBock(Properties properties, BlockSetType blockSetType) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, properties.noOcclusion(), blockSetType);
    }

    public PressurePlatesBock(Block block, BlockSetType blockSetType) {
        this(BlockBehaviour.Properties.copy(block), blockSetType);
    }
}
