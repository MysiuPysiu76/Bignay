package com.mysiupysiu.bignay.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public class NaturalNetherStemBlock extends NetherStemBlock {

    public NaturalNetherStemBlock(MapColor mapColor) {
        super(mapColor);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS);
    }
}
