package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AwakenedSoulSandBlock extends SoulSandBlock implements NaturalBlocks {

    public AwakenedSoulSandBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SAND));
    }
}
