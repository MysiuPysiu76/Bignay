package com.mysiupysiu.bignay.worldgen.decoration.tree;

import com.mysiupysiu.bignay.utils.ChanceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.material.Fluids;

import java.util.Comparator;

public abstract class AbstractTrunkVineDecorator extends TreeDecorator implements ChanceProvider {

    @Override
    public void place(Context context) {

        RandomSource random = context.random();

        BlockPos bottom = context.logs().stream()
                .min(Comparator.comparingInt(BlockPos::getY))
                .orElse(null);

        if (bottom == null) return;

        boolean west = tryStart(context, random, bottom, Direction.WEST);
        boolean east = tryStart(context, random, bottom, Direction.EAST);
        boolean north = tryStart(context, random, bottom, Direction.NORTH);
        boolean south = tryStart(context, random, bottom, Direction.SOUTH);

        for (BlockPos log : context.logs()) {
            if (log.getY() <= bottom.getY()) continue;

            if (west)  placeIfAir(context, log.west(),  VineBlock.EAST);
            if (east)  placeIfAir(context, log.east(),  VineBlock.WEST);
            if (north) placeIfAir(context, log.north(), VineBlock.SOUTH);
            if (south) placeIfAir(context, log.south(), VineBlock.NORTH);
        }
    }

    private boolean tryStart(Context ctx, RandomSource random, BlockPos bottom, Direction dir) {

        BlockPos pos = offset(bottom, dir);

        while (ctx.level().isStateAtPosition(pos,
                s -> s.getFluidState().is(Fluids.WATER))) {
            pos = pos.above();
        }

        if (!ctx.isAir(pos)) return false;

        if (random.nextInt(0, 100) < getChance()) return false;

        placeVine(ctx, pos, dir);

        return true;
    }

    private void placeIfAir(Context ctx, BlockPos pos, Object face) {
        if (ctx.isAir(pos)) {
            ctx.placeVine(pos, (BooleanProperty) face);
        }
    }

    private void placeVine(Context ctx, BlockPos pos, Direction dir) {
        switch (dir) {
            case WEST -> ctx.placeVine(pos, VineBlock.EAST);
            case EAST -> ctx.placeVine(pos, VineBlock.WEST);
            case NORTH -> ctx.placeVine(pos, VineBlock.SOUTH);
            case SOUTH -> ctx.placeVine(pos, VineBlock.NORTH);
        }
    }

    private BlockPos offset(BlockPos pos, Direction dir) {
        return switch (dir) {
            case WEST -> pos.west();
            case EAST -> pos.east();
            case NORTH -> pos.north();
            case SOUTH -> pos.south();
        };
    }

    private enum Direction {
        WEST, EAST, NORTH, SOUTH
    }
}
