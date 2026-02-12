package com.mysiupysiu.bignay.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class LocateBlockCommand {

    private static final int SEARCH_RADIUS = 256;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("locate")
                        .then(Commands.literal("block")
                                .then(Commands.argument("block", ResourceLocationArgument.id())
                                        .suggests((ctx, builder) -> {
                                            BuiltInRegistries.BLOCK.keySet().forEach(key -> builder.suggest(key.toString()));
                                            return builder.buildFuture();
                                        })
                                        .executes(ctx -> {
                                            ResourceLocation id = ResourceLocationArgument.getId(ctx, "block");
                                            if (!BuiltInRegistries.BLOCK.containsKey(id)) {
                                                ctx.getSource().sendFailure(Component.translatable("commands.locate.block.invalid", id));
                                                return 0;
                                            }
                                            Block block = BuiltInRegistries.BLOCK.get(id);
                                            searchBlockAsync(ctx.getSource(), block);
                                            return 1;
                                        })
                                )
                        )
        );
    }

    private static void searchBlockAsync(CommandSourceStack source, Block block) {
        CompletableFuture.runAsync(() -> {
            ServerLevel level = source.getLevel();
            MinecraftServer server = level.getServer();
            BlockPos origin = BlockPos.containing(source.getPosition());

            BlockPos found = findNearestBlock(level, origin, block);

            server.execute(() -> {
                if (found == null) {
                    source.sendFailure(Component.translatable("commands.locate.block.not_found", BuiltInRegistries.BLOCK.getKey(block)));
                } else {
                    sendFoundMessage(source, block, origin, found);
                }
            });
        });
    }

    private static BlockPos findNearestBlock(ServerLevel level, BlockPos origin, Block block) {
        int originChunkX = origin.getX() >> 4;
        int originChunkZ = origin.getZ() >> 4;

        int ox = origin.getX();
        int oy = origin.getY();
        int oz = origin.getZ();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        BlockPos bestPos = null;
        int bestDistSq = Integer.MAX_VALUE;

        int maxChunkRadius = SEARCH_RADIUS >> 4;

        for (int r = 0; r <= maxChunkRadius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {

                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;

                    int chunkX = originChunkX + dx;
                    int chunkZ = originChunkZ + dz;

                    if (!level.hasChunk(chunkX, chunkZ)) continue;

                    int minX = (chunkX << 4);
                    int minZ = (chunkZ << 4);
                    int maxX = minX + 15;
                    int maxZ = minZ + 15;

                    int cx = Math.max(minX, Math.min(ox, maxX));
                    int cz = Math.max(minZ, Math.min(oz, maxZ));

                    int chunkDistSq = (cx - ox) * (cx - ox) + (cz - oz) * (cz - oz);
                    if (chunkDistSq > bestDistSq) continue;

                    var chunk = level.getChunk(chunkX, chunkZ);
                    var heightmap = chunk.getOrCreateHeightmapUnprimed(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING);

                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            int yTop = heightmap.getFirstAvailable(x, z);

                            for (int y = yTop; y >= level.getMinBuildHeight(); y--) {
                                pos.set(minX + x, y, minZ + z);

                                if (!chunk.getBlockState(pos).is(block)) continue;

                                int dxB = pos.getX() - ox;
                                int dyB = pos.getY() - oy;
                                int dzB = pos.getZ() - oz;

                                int distSq = dxB * dxB + dyB * dyB + dzB * dzB;

                                if (distSq < bestDistSq) {
                                    bestDistSq = distSq;
                                    bestPos = pos.immutable();

                                    if (bestDistSq <= 4) {
                                        return bestPos;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestPos;
    }

    private static void sendFoundMessage(CommandSourceStack source, Block block, BlockPos origin, BlockPos found) {
        String tpCommand = "/tp " + found.getX() + " " + found.getY() + " " + found.getZ();

        MutableComponent coords = Component.literal("[" + found.getX() + ", " + found.getY() + ", " + found.getZ() + "]")
                .withStyle(style -> style
                        .withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tpCommand))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip"))));

        MutableComponent message = Component.translatable("commands.locate.block.success",
                Component.literal(BuiltInRegistries.BLOCK.getKey(block).toString()),
                coords, calculateDistance(origin, found));

        source.sendSuccess(() -> message, false);
    }

    private static int calculateDistance(BlockPos a, BlockPos b) {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        int dz = a.getZ() - b.getZ();
        return (int)Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));
    }
}
