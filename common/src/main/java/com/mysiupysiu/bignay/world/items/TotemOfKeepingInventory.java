package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.world.items.tabs.Combat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class TotemOfKeepingInventory extends Item implements Combat {

    public TotemOfKeepingInventory() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
