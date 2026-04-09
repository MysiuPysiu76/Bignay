package com.mysiupysiu.bignay.forge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.world.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.world.commands.LocateEntityCommand;
import com.mysiupysiu.bignay.world.commands.NameCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Commands {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        LocateBlockCommand.register(event.getDispatcher());
        LocateEntityCommand.register(event.getDispatcher());
        NameCommand.register(event.getDispatcher());
    }
}
