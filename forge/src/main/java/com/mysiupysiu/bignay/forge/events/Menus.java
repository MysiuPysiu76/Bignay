package com.mysiupysiu.bignay.forge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.menu.ArchaeologyTableScreen;
import com.mysiupysiu.bignay.registry.BignayMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Menus {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(BignayMenus.ARCHAEOLOGY_TABLE_MENU.get(), ArchaeologyTableScreen::new));
    }
}
