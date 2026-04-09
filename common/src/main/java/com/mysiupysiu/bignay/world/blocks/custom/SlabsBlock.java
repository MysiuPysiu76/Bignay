package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SlabsBlock extends SlabBlock implements BuildingBlocks {

    public SlabsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }

    public SlabsBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public SlabsBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }
}
