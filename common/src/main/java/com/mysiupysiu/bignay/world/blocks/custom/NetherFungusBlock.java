package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class NetherFungusBlock extends FungusBlock implements NaturalBlocks {

    public NetherFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Block block) {
        super(feature, block, Properties.of().mapColor(MapColor.NETHER).instabreak().noCollission().sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY));
    }
}
