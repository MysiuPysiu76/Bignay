package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

public class SeedsItem extends ItemNameBlockItem implements FunctionalBlocks {

    public SeedsItem(RegistrySupplier<Block> block) {
        super(block.get(), new Item.Properties());
    }
}
