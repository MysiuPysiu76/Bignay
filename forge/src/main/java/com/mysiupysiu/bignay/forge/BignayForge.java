package com.mysiupysiu.bignay.forge;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.forge.loot.BignayLootModifiers;
import com.mysiupysiu.bignay.forge.network.BignayPacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BignayMod.MODID)
public class BignayForge {
    public BignayForge() {
        BignayMod.init();

        ForgeRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
        BignayPacketHandler.register();

        BignayLootModifiers.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
