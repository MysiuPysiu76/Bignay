package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.BignayCommands;
import com.mysiupysiu.bignay.world.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.world.commands.LocateEntityCommand;
import com.mysiupysiu.bignay.world.commands.NameCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Commands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            BignayCommands.register(dispatcher);
        });
    }
}
