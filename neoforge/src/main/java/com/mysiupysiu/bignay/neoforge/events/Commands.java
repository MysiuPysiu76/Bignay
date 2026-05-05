package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.world.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.world.commands.LocateEntityCommand;
import com.mysiupysiu.bignay.world.commands.NameCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Commands {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        LocateBlockCommand.register(event.getDispatcher());
        LocateEntityCommand.register(event.getDispatcher());
        NameCommand.register(event.getDispatcher());
    }
}
