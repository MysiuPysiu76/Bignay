package com.mysiupysiu.bignay.world.items.tabs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.List;

@FunctionalInterface
public interface CreativeTabProvider {
    List<ResourceKey<CreativeModeTab>> getCreativeTabs();
}
