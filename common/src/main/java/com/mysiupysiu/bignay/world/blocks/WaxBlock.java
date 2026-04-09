package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public class WaxBlock extends Block implements NaturalBlocks {

    public WaxBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_ORANGE)
                .pushReaction(PushReaction.NORMAL)
                .strength(0.6F)
                .sound(SoundType.HONEY_BLOCK)
        );
    }
}
