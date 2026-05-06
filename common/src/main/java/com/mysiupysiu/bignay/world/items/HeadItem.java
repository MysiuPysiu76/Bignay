package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.tabs.FunctionalBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class HeadItem extends StandingAndWallBlockItem implements FunctionalBlocks {

    public HeadItem(RegistrySupplier<Block> standing, RegistrySupplier<Block> wall) {
        super(standing.get(), wall.get(), new Properties().rarity(Rarity.UNCOMMON), Direction.DOWN);
    }
}
