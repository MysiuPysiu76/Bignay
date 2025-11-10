package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class StairsBlock extends StairBlock implements CreativeTabProvider {

    public StairsBlock() {
        super(Blocks.STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).requiresCorrectToolForDrops());
    }

    public StairsBlock(Block block) {
        super(block.defaultBlockState(), BlockBehaviour.Properties.copy(block));
    }

    public StairsBlock(RegistryObject<Block> block) {
        this(block.get());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
