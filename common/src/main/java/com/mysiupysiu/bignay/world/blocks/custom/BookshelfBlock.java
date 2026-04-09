package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BookshelfBlock extends Block implements FunctionalBlocks {

    public BookshelfBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava());
    }
}
