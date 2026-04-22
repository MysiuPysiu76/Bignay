package com.mysiupysiu.bignay.forge.registry;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Biomes {

    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded("terrablender")) {
                ForgeTerraBlenderIntegration.registerRegions();
            }
        });
    }
}
