package com.mysiupysiu.bignay.neoforge.registry;


import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class Biomes {

    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("terrablender")) {
                NeoForgeTerraBlenderIntegration.registerRegions();
            }
        });
    }
}
