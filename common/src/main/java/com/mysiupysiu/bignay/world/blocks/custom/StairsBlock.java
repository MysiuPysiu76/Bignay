package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StairsBlock extends StairBlock implements BuildingBlocks {

    public StairsBlock() {
        super(Blocks.STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }

    public StairsBlock(Block block) {
        super(block.defaultBlockState(), BlockBehaviour.Properties.copy(block));
    }

    public StairsBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }
}
