package com.mysiupysiu.bignay.world.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AncientVinesBlock extends VineBlock {

    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 0, 2);

    public AncientVinesBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.VINE).randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FLOWERS, 0)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FLOWERS);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        int currentFlowers = state.getValue(FLOWERS);
        if (currentFlowers < 2 && random.nextInt(5) == 0) {
            level.setBlock(pos, state.setValue(FLOWERS, currentFlowers + 1), 2);
        }

        if (random.nextInt(4) == 0) {
            Direction randomDir = Direction.getRandom(random);
            BlockPos targetPos = pos.relative(randomDir);

            if (level.isEmptyBlock(targetPos)) {
                Direction wallDir = findSupport(level, targetPos, random);

                if (wallDir != null) {
                    BlockState newState = this.defaultBlockState().setValue(getPropertyForFace(wallDir), true).setValue(FLOWERS, 0);
                    level.setBlock(targetPos, newState, 2);
                }
            }
        }
    }

    private Direction findSupport(ServerLevel level, BlockPos pos, RandomSource random) {
        for (Direction dir : Direction.allShuffled(random)) {
            if (dir == Direction.DOWN) continue;

            if (VineBlock.isAcceptableNeighbour(level, pos.relative(dir), dir)) {
                return dir;
            }
        }
        return null;
    }

    public static BooleanProperty getPropertyForFace(Direction dir) {
        return PipeBlock.PROPERTY_BY_DIRECTION.get(dir);
    }
}
