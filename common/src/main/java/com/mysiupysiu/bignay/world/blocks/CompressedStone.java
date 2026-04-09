package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

public class CompressedStone extends Block implements BuildingBlocks {

    public CompressedStone() {
        super(BlockBehaviour.Properties.of().strength(2.5F, 6F).pushReaction(PushReaction.BLOCK).sound(SoundType.STONE));
    }
}
