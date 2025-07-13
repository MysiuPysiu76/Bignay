package com.mysiupysiu.bignay.blocks;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class NetherStemBlock extends RotatedPillarBlock {

    public NetherStemBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.STEM));
    }
}