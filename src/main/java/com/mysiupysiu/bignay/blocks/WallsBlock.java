package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class WallsBlock extends WallBlock implements CreativeTabProvider {

    public WallsBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).strength(2.0f).requiresCorrectToolForDrops());
    }

    public WallsBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
    }

    public WallsBlock(RegistryObject<Block> block) {
        this(block.get());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
