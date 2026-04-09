package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class NetherRootsBlock extends RootsBlock implements NaturalBlocks {

    public NetherRootsBlock(MapColor color) {
        super(BlockBehaviour.Properties.of().mapColor(color).replaceable().noCollission().instabreak().sound(SoundType.ROOTS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    }
}
