package com.mysiupysiu.bignay.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class LocateEntityCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("locate")
                        .then(Commands.literal("entity")
                                .then(Commands.argument("entity", ResourceLocationArgument.id())
                                        .suggests((ctx, builder) -> {
                                            BuiltInRegistries.ENTITY_TYPE.keySet()
                                                    .forEach(id -> builder.suggest(id.toString()));
                                            return builder.buildFuture();
                                        })
                                        .executes(ctx -> {
                                            ResourceLocation id = ResourceLocationArgument.getId(ctx, "entity");

                                            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(id)) {
                                                ctx.getSource().sendFailure(Component.translatable("commands.locate.entity.invalid", id));
                                                return 0;
                                            }

                                            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(id);
                                            searchEntityAsync(ctx.getSource(), type);
                                            return 1;
                                        })
                                )
                        )
        );
    }

    private static void searchEntityAsync(CommandSourceStack source, EntityType<?> type) {
        CompletableFuture.runAsync(() -> {
            ServerLevel level = source.getLevel();
            MinecraftServer server = level.getServer();

            Entity nearest = findNearestEntity(level, source.getPosition().x, source.getPosition().y, source.getPosition().z, type);

            server.execute(() -> {
                if (nearest == null) {
                    source.sendFailure(Component.translatable("commands.locate.entity.not_found", BuiltInRegistries.ENTITY_TYPE.getKey(type)));
                } else {
                    sendFoundMessage(source, type, nearest);
                }
            });
        });
    }

    private static Entity findNearestEntity(ServerLevel level, double ox, double oy, double oz, EntityType<?> type) {
        Entity best = null;
        double bestDistSq = Double.MAX_VALUE;

        for (Entity entity : level.getEntities().getAll()) {
            if (entity.getType() != type) continue;

            double dx = entity.getX() - ox;
            double dy = entity.getY() - oy;
            double dz = entity.getZ() - oz;

            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                best = entity;
            }
        }
        return best;
    }

    private static void sendFoundMessage(CommandSourceStack source, EntityType<?> type, Entity entity) {
        int x = entity.blockPosition().getX();
        int y = entity.blockPosition().getY();
        int z = entity.blockPosition().getZ();

        String tp = "/tp " + x + " " + y + " " + z;

        MutableComponent coords = Component.literal("[" + x + ", " + y + ", " + z + "]")
                .withStyle(style -> style
                        .withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tp))
                        .withHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("chat.coordinates.tooltip"))));

        MutableComponent msg = Component.translatable(
                "commands.locate.entity.success",
                Component.literal(BuiltInRegistries.ENTITY_TYPE.getKey(type).toString()),
                coords,
                (int) Math.sqrt(entity.distanceToSqr(source.getPosition())));

        source.sendSuccess(() -> msg, false);
    }
}
