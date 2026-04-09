package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.world.items.tabs.Ingredients;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.List;

public class IngredientsDefaultItem extends Item implements Ingredients {

    public IngredientsDefaultItem() {
        super(new Properties());
    }

    public IngredientsDefaultItem(Item.Properties properties) {
        super(properties);
    }
}
