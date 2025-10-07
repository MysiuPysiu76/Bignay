package com.mysiupysiu.bignay.items;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class HeadItem extends StandingAndWallBlockItem {

    public HeadItem(Block standing, Block wall) {
        super(standing, wall, new Properties().rarity(Rarity.UNCOMMON), Direction.DOWN);
    }
}
