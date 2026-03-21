package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class BarsBlock extends IronBarsBlock implements CreativeTabProvider {

    public BarsBlock() {
        super(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
