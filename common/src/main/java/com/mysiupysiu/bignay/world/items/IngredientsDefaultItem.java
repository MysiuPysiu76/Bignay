package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.world.items.tabs.Ingredients;
import net.minecraft.world.item.Item;

public class IngredientsDefaultItem extends Item implements Ingredients {

    public IngredientsDefaultItem() {
        super(new Properties());
    }

    public IngredientsDefaultItem(Item.Properties properties) {
        super(properties);
    }
}
