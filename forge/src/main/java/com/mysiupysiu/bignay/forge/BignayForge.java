package com.mysiupysiu.bignay.forge;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.forge.config.ForgeConfig;
import com.mysiupysiu.bignay.forge.loot.BignayLootModifiers;
//import com.mysiupysiu.bignay.forge.network.BignayPacketHandler;
import com.mysiupysiu.bignay.forge.registry.Biomes;
import com.mysiupysiu.bignay.forge.registry.ForgeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BignayMod.MODID)
public class BignayForge {
    public BignayForge() {
        BignayMod.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ForgeRegistry.register(bus);
//        BignayPacketHandler.register();
        ForgeConfig.register(bus);
        BignayLootModifiers.register(bus);
        bus.addListener(Biomes::setup);
    }
}
