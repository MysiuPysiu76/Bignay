package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.BignayTabs;
import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class ChainsBlock extends ChainBlock implements CreativeTabProvider {

    public ChainsBlock() {
        super(BlockBehaviour.Properties.of().forceSolidOn().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(BignayTabs.BUILDING_BLOCKS, BignayTabs.FUNCTIONAL_BLOCKS);
    }
}
