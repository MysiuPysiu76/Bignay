package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PillarBlock extends RotatedPillarBlock implements BuildingBlocks {

    public PillarBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public PillarBlock(RegistrySupplier<Block> block) {
        super(BlockBehaviour.Properties.copy(block.get()));
    }
}
