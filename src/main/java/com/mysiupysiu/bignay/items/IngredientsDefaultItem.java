package com.mysiupysiu.bignay.items;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.List;

public class IngredientsDefaultItem extends Item implements CreativeTabProvider {

    public IngredientsDefaultItem() {
        super(new Properties());
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.INGREDIENTS);
    }
}
