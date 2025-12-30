package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Random;

public class RandomizerBlock extends Block implements CreativeTabProvider {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<PowerMode> MODE = EnumProperty.create("mode", PowerMode.class);
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public RandomizerBlock() {
        super(BlockBehaviour.Properties.of());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(MODE, PowerMode.OFF)
                .setValue(LOCKED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, LOCKED);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }

        if (level.isClientSide) return;

        Direction facing = state.getValue(FACING);
        Direction front = facing.getOpposite();
        Direction back = facing;

        boolean frontPower = hasSignalFrom(level, pos, front);
        boolean backPower = hasSignalFrom(level, pos, back);

        boolean locked = state.getValue(LOCKED);
        PowerMode mode = state.getValue(MODE);

        if (frontPower != locked) {
            state = state.setValue(LOCKED, frontPower);
            level.setBlock(pos, state, 3);
        }

        if (state.getValue(LOCKED)) return;

        if (backPower && mode == PowerMode.OFF) {
            level.setBlock(pos, state.setValue(MODE, randomMode()), 3);
        }

        if (!backPower && mode != PowerMode.OFF) {
            level.setBlock(pos, state.setValue(MODE, PowerMode.OFF), 3);
        }
    }

    private boolean hasSignalFrom(Level level, BlockPos pos, Direction fromDirection) {
        BlockPos neighbor = pos.relative(fromDirection);
        return level.getSignal(neighbor, fromDirection.getOpposite()) > 0;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
        PowerMode mode = state.getValue(MODE);
        if (mode == PowerMode.OFF) return 0;

        Direction facing = state.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        Direction right = facing.getClockWise();

        if (mode == PowerMode.LEFT && dir == left) return 15;
        if (mode == PowerMode.RIGHT && dir == right) return 15;
        if (mode == PowerMode.BOTH && (dir == left || dir == right)) return 15;
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    private PowerMode randomMode() {
        return new Random().nextBoolean() ? PowerMode.RIGHT : PowerMode.LEFT;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.REDSTONE_BLOCKS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    private enum PowerMode implements StringRepresentable {
        OFF("off"),
        LEFT("left"),
        RIGHT("right"),
        BOTH("both");

        private final String name;

        PowerMode(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
