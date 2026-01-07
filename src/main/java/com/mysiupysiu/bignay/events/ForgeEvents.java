package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.commands.LocateEntityCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        LocateBlockCommand.register(event.getDispatcher());
        LocateEntityCommand.register(event.getDispatcher());
    }
}
