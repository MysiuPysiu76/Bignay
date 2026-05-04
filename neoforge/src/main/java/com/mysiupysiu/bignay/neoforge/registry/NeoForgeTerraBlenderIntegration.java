package com.mysiupysiu.bignay.neoforge.registry;

import com.mysiupysiu.bignay.world.worldgen.biome.VerdantForestBiome;
import terrablender.api.Regions;

public class NeoForgeTerraBlenderIntegration {

    public static void registerRegions() {
        Regions.register(new VerdantForestBiome());
    }
}
