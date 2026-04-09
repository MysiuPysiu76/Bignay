package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.world.items.tabs.BignayTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public class NaturalNetherStemBlock extends NetherStemBlock {

    public NaturalNetherStemBlock(MapColor mapColor) {
        super(mapColor);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(BignayTabs.BUILDING_BLOCKS, BignayTabs.NATURAL_BLOCKS);
    }
}
