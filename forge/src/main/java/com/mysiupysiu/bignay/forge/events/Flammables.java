package com.mysiupysiu.bignay.forge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.utils.Flameable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Flammables {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Flameable::init);
    }
}
