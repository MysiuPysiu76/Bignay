package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WallsBlock extends WallBlock implements BuildingBlocks {

    public WallsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).strength(2.0f).requiresCorrectToolForDrops());
    }

    public WallsBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public WallsBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }
}
