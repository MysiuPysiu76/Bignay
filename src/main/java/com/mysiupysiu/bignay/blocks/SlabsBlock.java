package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SlabsBlock extends SlabBlock implements CreativeTabProvider {

    public SlabsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }

    public SlabsBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public SlabsBlock(RegistryObject<Block> block) {
        this(block.get());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
