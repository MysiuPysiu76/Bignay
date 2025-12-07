package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.screen.WorldEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WorldOptionsEvent {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) throws Exception {
        if (event.getScreen() instanceof EditWorldScreen screen)
            Minecraft.getInstance().setScreen(new WorldEditScreen(screen));
    }
}
