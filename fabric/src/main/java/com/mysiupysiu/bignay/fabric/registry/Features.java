package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.world.worldgen.BignayPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class Features {

    public static void register() {
        register(Biomes.SOUL_SAND_VALLEY, GenerationStep.Decoration.UNDERGROUND_ORES, BignayPlacedFeatures.AWAKENED_SOUL_SAND);
        register(Biomes.BADLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS);
        register(Biomes.BASALT_DELTAS, GenerationStep.Decoration.SURFACE_STRUCTURES, BignayPlacedFeatures.BASALTIC_LAVA);
        register(Biomes.CRIMSON_FOREST, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_CRIMSON_SPROUTS);
        register(Biomes.DESERT, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH_2);
        register(Biomes.ERODED_BADLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS);
        register(Biomes.MANGROVE_SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH);
        register(Biomes.MUSHROOM_FIELDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_PUFFBALLS);
        register(Biomes.MUSHROOM_FIELDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_MYCELIUM_ROOTS);
        register(Biomes.MUSHROOM_FIELDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_MYCELIUM_SPROUTS);
        register(Biomes.SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.SWAMP_MUSHROOM);
        register(Biomes.SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH);
        register(Biomes.WOODED_BADLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS);
        register(Biomes.OLD_GROWTH_PINE_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH);
        register(Biomes.OLD_GROWTH_SPRUCE_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, BignayPlacedFeatures.PATCH_TALL_DEAD_BUSH);
    }

    private static void register(ResourceKey<Biome> biome, GenerationStep.Decoration generation, ResourceKey<PlacedFeature> feature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(biome), generation, feature);
    }
}
