package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FlowerPotsBlock extends FlowerPotBlock {

    public FlowerPotsBlock(Block block) {
        super(block, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_FERN));
    }

    public FlowerPotsBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }
}
