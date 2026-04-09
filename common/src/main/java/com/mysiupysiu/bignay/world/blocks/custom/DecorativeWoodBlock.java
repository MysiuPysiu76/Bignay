package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DecorativeWoodBlock extends BuildingBlock {

    public DecorativeWoodBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public DecorativeWoodBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }
}
