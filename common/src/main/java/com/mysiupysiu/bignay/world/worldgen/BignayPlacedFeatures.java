package com.mysiupysiu.bignay.world.worldgen;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BignayPlacedFeatures {

    public static final ResourceKey<PlacedFeature> AWAKENED_SOUL_SAND = feature("awakened_soul_sand_patch");
    public static final ResourceKey<PlacedFeature> BASALTIC_LAVA = feature("basaltic_lava");
    public static final ResourceKey<PlacedFeature> PATCH_PUFFBALLS = feature("patch_puffballs");
    public static final ResourceKey<PlacedFeature> PATCH_MYCELIUM_ROOTS = feature("patch_mycelium_roots");
    public static final ResourceKey<PlacedFeature> PATCH_MYCELIUM_SPROUTS = feature("patch_mycelium_sprouts");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_DEAD_BUSH = feature("patch_tall_dead_bush");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_DEAD_BUSH_2 = feature("patch_tall_dead_bush_2");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_DEAD_BUSH_BADLANDS = feature("patch_tall_dead_bush_badlands");
    public static final ResourceKey<PlacedFeature> SWAMP_MUSHROOM = feature("swamp_mushroom");

    private static ResourceKey<PlacedFeature> feature(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BignayMod.MODID, id));
    }
}
