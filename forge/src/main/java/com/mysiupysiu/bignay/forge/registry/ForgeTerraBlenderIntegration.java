package com.mysiupysiu.bignay.forge.registry;

import com.mysiupysiu.bignay.world.worldgen.biome.BignayNetherRegion;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ForgeTerraBlenderIntegration {

    public static void registerRegions() {
        Regions.register(new BignayNetherRegion(new ResourceLocation("bignay", "nether_region"), 2));
    }
}
