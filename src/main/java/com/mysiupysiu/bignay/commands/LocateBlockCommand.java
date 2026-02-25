package com.mysiupysiu.bignay.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class LocateBlockCommand {

    private static final int SEARCH_RADIUS = 2048;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("locate").then(Commands.literal("block").then(Commands.argument("block_or_tag", ResourceOrTagKeyArgument.resourceOrTagKey(Registries.BLOCK)).executes(ctx -> {
            DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType((obj) -> Component.translatable("commands.locate.block.invalid", obj));

            var arg = ResourceOrTagKeyArgument.getResourceOrTagKey(ctx, "block_or_tag", Registries.BLOCK, NOT_FOUND_EXCEPTION);

            String displayName = arg.unwrap().map(key -> key.location().toString(), tag -> "#" + tag.location());
            Predicate<BlockState> predicate = state -> arg.test(state.getBlockHolder());

            searchBlockAsync(ctx.getSource(), displayName, predicate);
            return 1;
        }))));
    }

    private static void searchBlockAsync(CommandSourceStack source, String name, Predicate<BlockState> predicate) {
        CompletableFuture.runAsync(() -> {
            ServerLevel level = source.getLevel();
            MinecraftServer server = level.getServer();
            BlockPos origin = BlockPos.containing(source.getPosition());

            BlockPos found = findNearestBlock(level, origin, predicate);

            server.execute(() -> {
                if (found == null) {
                    source.sendFailure(Component.translatable("commands.locate.block.not_found", name));
                } else {
                    sendFoundMessage(source, name, origin, found);
                }
            });
        });
    }

    private static BlockPos findNearestBlock(ServerLevel level, BlockPos origin, Predicate<BlockState> predicate) {
        int originChunkX = origin.getX() >> 4;
        int originChunkZ = origin.getZ() >> 4;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockPos bestPos = null;
        double bestDistSq = Double.MAX_VALUE;

        int maxChunkRadius = SEARCH_RADIUS >> 4;

        for (int r = 0; r <= maxChunkRadius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;

                    int chunkX = originChunkX + dx;
                    int chunkZ = originChunkZ + dz;

                    if (!level.hasChunk(chunkX, chunkZ)) continue;

                    var chunk = level.getChunk(chunkX, chunkZ);
                    int minX = chunkX << 4;
                    int minZ = chunkZ << 4;

                    var heightmap = chunk.getOrCreateHeightmapUnprimed(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING);

                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            int yTop = heightmap.getFirstAvailable(x, z);

                            for (int y = yTop; y >= level.getMinBuildHeight(); y--) {
                                pos.set(minX + x, y, minZ + z);
                                BlockState state = chunk.getBlockState(pos);

                                if (predicate.test(state)) {
                                    double distSq = pos.distSqr(origin);
                                    if (distSq < bestDistSq) {
                                        bestDistSq = distSq;
                                        bestPos = pos.immutable();

                                        if (bestDistSq <= 4) return bestPos;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (bestPos != null) return bestPos;
        }
        return bestPos;
    }

    private static void sendFoundMessage(CommandSourceStack source, String name, BlockPos origin, BlockPos found) {
        String tpCommand = String.format("/tp @s %d %d %d", found.getX(), found.getY(), found.getZ());

        MutableComponent cords = Component.literal("[" + found.getX() + ", " + found.getY() + ", " + found.getZ() + "]").withStyle(style -> style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tpCommand)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip"))));
        MutableComponent message = Component.translatable("commands.locate.block.success", name, cords, (int) Math.sqrt(found.distSqr(origin)));

        source.sendSuccess(() -> message, false);
    }
}
