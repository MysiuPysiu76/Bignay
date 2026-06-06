package com.mysiupysiu.bignay.world.tags;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BignayTags {

    public static class Blocks {
        public static final TagKey<Block> PUMPKINS = TagKey.create(Registries.BLOCK, resource("pumpkins"));
        public static final TagKey<Block> END_CRYSTAL_PLACEABLE = TagKey.create(Registries.BLOCK, resource("end_crystal_placeable"));
    }

    public static class Items {
        public static final TagKey<Item> HORNS = TagKey.create(Registries.ITEM, resource("horns"));
        public static final TagKey<Item> BIG_ITEMS = TagKey.create(Registries.ITEM, resource("big_items"));
        public static final TagKey<Item> POTTERY_SHERDS = TagKey.create(Registries.ITEM, resource("pottery_sherds"));
        public static final TagKey<Item> FIRE_PROVIDERS = TagKey.create(Registries.ITEM, resource("fire_providers"));
    }

    public static class Instruments {
        public static final TagKey<Instrument> COPPER_HORNS = TagKey.create(Registries.INSTRUMENT, resource("copper_horns"));
    }

    private static ResourceLocation resource(String id) {
        return ResourceLocation.tryBuild(BignayMod.MODID, id);
    }
}
