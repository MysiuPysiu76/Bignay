package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.BignayBlocks;
import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class PalePumpkinBlock extends Block implements NaturalBlocks {

    public PalePumpkinBlock(MapColor color) {
        super(Properties.of().mapColor(color).instrument(NoteBlockInstrument.DIDGERIDOO).strength(1.0F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    }
}
