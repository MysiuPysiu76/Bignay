package com.mysiupysiu.bignay.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BignayTags {

    public static class Blocks {
        public static final TagKey<Block> PUMPKINS = TagKey.create(Registries.BLOCK, new ResourceLocation("bignay", "pumpkins"));
    }

    public static class Items {
        public static final TagKey<Item> BIG_ITEMS = TagKey.create(Registries.ITEM, new ResourceLocation("bignay", "big_items"));
        public static final TagKey<Item> POTTERY_SHERDS = TagKey.create(Registries.ITEM, new ResourceLocation("bignay", "pottery_sherds"));
    }
}
