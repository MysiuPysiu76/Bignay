package com.mysiupysiu.bignay.world.items.tabs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.List;

public interface FoodAndDrinks extends CreativeTabProvider {

    default List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(BignayTabs.FOOD_AND_DRINKS);
    }
}
