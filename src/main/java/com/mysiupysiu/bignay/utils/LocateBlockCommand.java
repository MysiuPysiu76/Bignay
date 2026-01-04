package com.mysiupysiu.bignay.utils;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class LocateBlockCommand {

    private static final int RADIUS = 256;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("locate")
                        .then(Commands.literal("block")
                                .then(Commands.argument("block", ResourceLocationArgument.id())
                                        .suggests((ctx, builder) -> {
                                            BuiltInRegistries.BLOCK.keySet().forEach(key -> builder.suggest(key.toString()));
                                            return builder.buildFuture();
                                        })
                                        .executes(ctx -> {
                                            ResourceLocation id = ResourceLocationArgument.getId(ctx, "block");
                                            if (!BuiltInRegistries.BLOCK.containsKey(id)) {
                                                ctx.getSource().sendFailure(Component.literal("Unknown block: " + id));
                                                return 0;
                                            }
                                            Block block = BuiltInRegistries.BLOCK.get(id);
                                            CompletableFuture.runAsync(() -> locateBlockAsync(ctx.getSource(), block));
                                            return 1;
                                        })
                                )
                        )
        );
    }

    private static void locateBlockAsync(CommandSourceStack source, Block block) {
        ServerLevel level = source.getLevel();
        BlockPos origin = BlockPos.containing(source.getPosition());

        BlockPos found = findNearest(level, origin, block);

        if (found == null) {
            source.sendFailure(Component.literal("Block not found: " + BuiltInRegistries.BLOCK.getKey(block)));
            return;
        }

        source.sendSuccess(() -> Component.literal("Found " + BuiltInRegistries.BLOCK.getKey(block)
                                + " at X=" + found.getX()
                                + " Y=" + found.getY()
                                + " Z=" + found.getZ()
                ),
                false
        );
    }

    private static BlockPos findNearest(ServerLevel level, BlockPos origin, Block block) {
        for (int r = 0; r <= RADIUS; r++) {
            for (int x = -r; x <= r; x++) {
                for (int z = -r; z <= r; z++) {
                    for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                        BlockPos pos = origin.offset(x, y - origin.getY(), z);
                        if (level.getBlockState(pos).is(block)) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }
}
