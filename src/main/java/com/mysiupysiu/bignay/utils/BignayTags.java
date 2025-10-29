package com.mysiupysiu.bignay.utils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class BignayTags {
    public static class Items {
        public static final TagKey<Item> POTTERY_SHERDS = TagKey.create(Registries.ITEM, new ResourceLocation("bignay", "pottery_sherds"));
    }
}
