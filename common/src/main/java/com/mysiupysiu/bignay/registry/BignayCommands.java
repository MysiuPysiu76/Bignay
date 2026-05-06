package com.mysiupysiu.bignay.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mysiupysiu.bignay.world.commands.LocateBlockCommand;
import com.mysiupysiu.bignay.world.commands.LocateEntityCommand;
import com.mysiupysiu.bignay.world.commands.NameCommand;
import net.minecraft.commands.CommandSourceStack;

public class BignayCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LocateBlockCommand.register(dispatcher);
        LocateEntityCommand.register(dispatcher);
        NameCommand.register(dispatcher);
    }
}
