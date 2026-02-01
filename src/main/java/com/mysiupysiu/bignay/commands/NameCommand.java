package com.mysiupysiu.bignay.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import java.util.Collection;

public class NameCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("name").requires(source -> source.hasPermission(2)).then(Commands.argument("entities", EntityArgument.entities()).then(Commands.argument("name", StringArgumentType.greedyString()).executes(context -> {

            Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
            String name = StringArgumentType.getString(context, "name");

            for (Entity entity : entities) {
                entity.setCustomName(Component.literal(name));
                entity.setCustomNameVisible(true);
            }

            context.getSource().sendSuccess(() -> Component.translatable("commands.name.set." + (entities.size() == 1 ? "single" : "multiple"), entities.size(), name), true);

            return entities.size();
        }))));
    }
}