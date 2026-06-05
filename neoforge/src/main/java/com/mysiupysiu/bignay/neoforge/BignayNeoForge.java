package com.mysiupysiu.bignay.neoforge;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.neoforge.config.NeoForgeConfig;
import com.mysiupysiu.bignay.neoforge.loot.BignayLootModifiers;
import com.mysiupysiu.bignay.neoforge.network.BignayPacketHandler;
import com.mysiupysiu.bignay.neoforge.registry.Biomes;
import com.mysiupysiu.bignay.neoforge.registry.NeoForgeRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BignayMod.MODID)
public class BignayNeoForge {
    public BignayNeoForge(IEventBus bus) {
        BignayMod.init();

        NeoForgeRegistry.register(bus);
        NeoForgeConfig.register(bus);
        BignayPacketHandler.register();
        bus.addListener(BignayPacketHandler::onRegisterPayloads);
        BignayLootModifiers.register(bus);
        bus.addListener(Biomes::setup);
    }
}
