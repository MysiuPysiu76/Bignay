package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.utils.Flameable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Flammables {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Flameable::init);
    }
}
