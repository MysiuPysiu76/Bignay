package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class SignsItem extends SignItem implements FunctionalBlocks {

    public SignsItem(RegistrySupplier<Block> standing, RegistrySupplier<Block> wall) {
        super(new Item.Properties().stacksTo(16), standing.get(), wall.get());
    }
}
