package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.BignayBiomes;
import com.mysiupysiu.bignay.world.worldgen.biome.VerdantForestBiome;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class FabricTerraBlenderIntegration implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        BignayBiomes.register();
    }
}
