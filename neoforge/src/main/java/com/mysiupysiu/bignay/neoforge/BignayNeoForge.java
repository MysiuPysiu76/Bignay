package com.mysiupysiu.bignay.neoforge;

import com.mysiupysiu.bignay.BignayMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BignayMod.MODID)
public class BignayNeoForge {
    public BignayNeoForge(IEventBus bus) {
        BignayMod.init();
    }
}
