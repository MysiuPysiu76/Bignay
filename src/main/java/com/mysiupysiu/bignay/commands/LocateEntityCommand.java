package com.mysiupysiu.bignay.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class LocateEntityCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("locate").then(Commands.literal("entity").then(Commands.argument("entity", StringArgumentType.greedyString()).suggests((ctx, builder) -> {
            BuiltInRegistries.ENTITY_TYPE.keySet().forEach(id -> builder.suggest(id.toString()));
            BuiltInRegistries.ENTITY_TYPE.getTagNames().forEach(tag -> builder.suggest("#" + tag.location()));
            return builder.buildFuture();
        }).executes(ctx -> {
            String input = StringArgumentType.getString(ctx, "entity");
            boolean isTag = input.startsWith("#");
            String rawId = isTag ? input.substring(1) : input;
            ResourceLocation id = ResourceLocation.tryParse(rawId);

            if (id == null) {
                ctx.getSource().sendFailure(Component.translatable("argument.resource.not_found", rawId, "entity_type"));
                return 0;
            }

            if (isTag) {
                TagKey<EntityType<?>> tagKey = TagKey.create(Registries.ENTITY_TYPE, id);
                if (BuiltInRegistries.ENTITY_TYPE.getTag(tagKey).isEmpty()) {
                    ctx.getSource().sendFailure(Component.translatable("argument.resource_tag.not_found", id, "entity_type"));
                    return 0;
                }
                searchEntityAsync(ctx.getSource(), type -> type.is(tagKey), input);
            } else {
                if (!BuiltInRegistries.ENTITY_TYPE.containsKey(id)) {
                    ctx.getSource().sendFailure(Component.translatable("argument.resource.not_found", id, "entity_type"));
                    return 0;
                }
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(id);
                searchEntityAsync(ctx.getSource(), t -> t == type, input);
            }
            return 1;
        }))));
    }

    private static Entity findNearestEntity(ServerLevel level, double ox, double oy, double oz, Predicate<EntityType<?>> filter) {
        Entity best = null;
        double bestDistSq = Double.MAX_VALUE;

        for (Entity entity : level.getEntities().getAll()) {
            if (!filter.test(entity.getType())) continue;

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

    private static void searchEntityAsync(CommandSourceStack source, Predicate<EntityType<?>> filter, String displayName) {
        CompletableFuture.runAsync(() -> {
            ServerLevel level = source.getLevel();
            MinecraftServer server = level.getServer();

            Entity nearest = findNearestEntity(level, source.getPosition().x, source.getPosition().y, source.getPosition().z, filter);

            server.execute(() -> {
                if (nearest == null) {
                    source.sendFailure(Component.translatable("commands.locate.entity.not_found", displayName));
                } else {
                    sendFoundMessage(source, displayName, nearest);
                }
            });
        });
    }

    private static void sendFoundMessage(CommandSourceStack source, String displayName, Entity entity) {
        int x = entity.blockPosition().getX();
        int y = entity.blockPosition().getY();
        int z = entity.blockPosition().getZ();

        String tp = "/tp " + x + " " + y + " " + z;

        MutableComponent cords = Component.literal("[" + x + ", " + y + ", " + z + "]").withStyle(style -> style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tp)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip"))));
        MutableComponent msg = Component.translatable("commands.locate.entity.success", displayName, cords, (int) Math.sqrt(entity.distanceToSqr(source.getPosition())));

        source.sendSuccess(() -> msg, false);
    }
}
