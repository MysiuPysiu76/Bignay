package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class NetherWartBlock extends Block implements NaturalBlocks {

    public NetherWartBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).strength(1.0F).sound(SoundType.WART_BLOCK));
    }
}
