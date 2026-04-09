package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.world.level.block.NetherSproutsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SproutsBlock extends NetherSproutsBlock implements NaturalBlocks {

    public SproutsBlock(MapColor mapColor) {
        super(BlockBehaviour.Properties.of().mapColor(mapColor).replaceable().noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    }
}
