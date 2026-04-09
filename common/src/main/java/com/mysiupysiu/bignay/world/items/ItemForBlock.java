package com.mysiupysiu.bignay.world.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ItemForBlock extends BlockItem {

    public ItemForBlock(Supplier<Block> block) {
        super(block.get(), new Properties());
    }
}
