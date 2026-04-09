package com.mysiupysiu.bignay.world.items;

import com.mysiupysiu.bignay.world.items.tabs.BignayTabs;
import com.mysiupysiu.bignay.world.items.tabs.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.List;

public class GoldenBerriesItem extends Item implements CreativeTabProvider {

    public GoldenBerriesItem() {
        super(new Properties().food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(1F)
                .build()));
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(BignayTabs.INGREDIENTS, BignayTabs.FOOD_AND_DRINKS);
    }
}
