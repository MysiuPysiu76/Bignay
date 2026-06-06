package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import com.mysiupysiu.bignay.world.worldgen.biome.VerdantForestBiome;
import terrablender.api.Regions;

public class BignayBiomes {

    public static final ResourceKey<Biome> VERDANT_FOREST = ResourceKey.create(Registries.BIOME, ResourceLocation.tryBuild(BignayMod.MODID, "verdant_forest"));

    public static void register() {
        Regions.register(new VerdantForestBiome());
    }
}
