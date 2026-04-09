package com.mysiupysiu.bignay.fabric;

import com.mysiupysiu.bignay.world.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.world.commands.LocateEntityCommand;
import com.mysiupysiu.bignay.world.commands.NameCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Commands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LocateBlockCommand.register(dispatcher);
            LocateEntityCommand.register(dispatcher);
            NameCommand.register(dispatcher);
        });
    }
}
