package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
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
        return List.of(CreativeModeTabs.INGREDIENTS, CreativeModeTabs.FOOD_AND_DRINKS);
    }
}
