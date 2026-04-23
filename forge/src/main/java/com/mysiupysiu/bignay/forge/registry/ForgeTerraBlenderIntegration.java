package com.mysiupysiu.bignay.forge.registry;

import com.mysiupysiu.bignay.world.worldgen.biome.VerdantForestBiome;
import terrablender.api.Regions;

public class ForgeTerraBlenderIntegration {

    public static void registerRegions() {
        Regions.register(new VerdantForestBiome());
    }
}
