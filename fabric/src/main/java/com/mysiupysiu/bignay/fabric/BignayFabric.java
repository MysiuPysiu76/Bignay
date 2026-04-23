package com.mysiupysiu.bignay.fabric;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.fabric.config.FabricConfig;
import com.mysiupysiu.bignay.fabric.network.BignayPacketHandler;
import com.mysiupysiu.bignay.fabric.registry.LootModifiers;
import com.mysiupysiu.bignay.fabric.registry.Commands;
import com.mysiupysiu.bignay.fabric.registry.FabricRegistry;
import com.mysiupysiu.bignay.fabric.registry.Fuels;
import net.fabricmc.api.ModInitializer;

public class BignayFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BignayMod.init();

        FabricRegistry.register();
        FabricRegistry.addToCreativeTab();

        Commands.register();
        Fuels.register();

        BignayPacketHandler.register();
        FabricConfig.register();

        LootModifiers.register();
    }
}
