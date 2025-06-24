package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class WaxBlock extends Block {
    public WaxBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_ORANGE)
                .pushReaction(PushReaction.NORMAL)
                .strength(0.6F)
                .sound(SoundType.HONEY_BLOCK)
        );
    }
}
