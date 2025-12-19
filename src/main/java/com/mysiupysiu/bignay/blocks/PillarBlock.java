package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class PillarBlock extends RotatedPillarBlock implements CreativeTabProvider {

    public PillarBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public PillarBlock(RegistryObject<Block> block) {
        super(BlockBehaviour.Properties.copy(block.get()));
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}