package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class VerticalSlabBlock extends Block implements CreativeTabProvider, SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public VerticalSlabBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, Type.NORTH).setValue(WATERLOGGED, false));
    }

    public VerticalSlabBlock(Block block) {
        super(BlockBehaviour.Properties.copy(block));
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, Type.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case NORTH -> Block.box(0, 0, 0, 16, 16, 8);
            case SOUTH -> Block.box(0, 0, 8, 16, 16, 16);
            case WEST -> Block.box(0, 0, 0, 8, 16, 16);
            case EAST -> Block.box(8, 0, 0, 16, 16, 16);
            case DOUBLE -> Block.box(0, 0, 0, 16, 16, 16);
        };
    }

    private static Direction slabFront(Type t) {
        return switch (t) {
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            case EAST -> Direction.EAST;
            default -> Direction.NORTH;
        };
    }

    private boolean isFrontBlocked(Level level, BlockPos pos, BlockState slabState) {
        Direction front = slabFront(slabState.getValue(TYPE));
        BlockPos frontPos = pos.relative(front);

        BlockState frontState = level.getBlockState(frontPos);

        return !frontState.isAir() && frontState.isSolid();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (state.getValue(TYPE) == Type.DOUBLE) return false;
        if (ctx.getItemInHand().getItem() != this.asItem()) return false;

        if (ctx.replacingClickedOnBlock()) {
            Direction clickedFace = ctx.getClickedFace();
            Type type = state.getValue(TYPE);

            if (type == Type.NORTH && clickedFace == Direction.SOUTH) return true;
            if (type == Type.SOUTH && clickedFace == Direction.NORTH) return true;
            if (type == Type.WEST && clickedFace == Direction.EAST) return true;
            if (type == Type.EAST && clickedFace == Direction.WEST) return true;

            return isClickInFreeHalf(ctx, state) || isFrontBlocked(ctx.getLevel(), ctx.getClickedPos(), state);
        }

        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos clickedPos = ctx.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Direction clickedFace = ctx.getClickedFace();
        boolean water = level.getFluidState(clickedPos).getType() == Fluids.WATER;

        if (clickedState.is(this) && clickedState.getValue(TYPE) != Type.DOUBLE) {
            return clickedState.setValue(TYPE, Type.DOUBLE).setValue(WATERLOGGED, false);
        }

        BlockPos behindPos = clickedPos.relative(clickedFace.getOpposite());
        BlockState behindState = level.getBlockState(behindPos);

        if (behindState.is(this) && behindState.getValue(TYPE) != Type.DOUBLE) {
            Direction front = slabFront(behindState.getValue(TYPE));
            if (clickedFace == front.getOpposite()) {
                if (!level.isClientSide()) {
                    level.setBlock(behindPos, behindState.setValue(TYPE, Type.DOUBLE).setValue(WATERLOGGED, false), 3);
                    level.playSound(null, behindPos, this.getSoundType(behindState).getPlaceSound(), net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(ctx.getPlayer(), GameEvent.BLOCK_PLACE, behindPos);

                    if (ctx.getPlayer() != null) {
                        Player p = ctx.getPlayer();
                        if (p instanceof ServerPlayer sp) sp.awardStat(Stats.ITEM_USED.get(this.asItem()));
                        ItemStack stack = ctx.getItemInHand();
                        if (!p.isCreative() && stack != null) stack.shrink(1);
                        p.swing(InteractionHand.MAIN_HAND);
                    }
                }
                return null;
            }
        }

        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) {
            Vec3 hit = ctx.getClickLocation();
            double x = hit.x - clickedPos.getX();
            double z = hit.z - clickedPos.getZ();

            Type choose = (Math.abs(x - 0.5) > Math.abs(z - 0.5)) ? (x < 0.5 ? Type.WEST : Type.EAST) : (z < 0.5 ? Type.NORTH : Type.SOUTH);

            return defaultBlockState().setValue(TYPE, choose).setValue(WATERLOGGED, water);
        }

        return switch (clickedFace) {
            case NORTH -> defaultBlockState().setValue(TYPE, Type.SOUTH).setValue(WATERLOGGED, water);
            case SOUTH -> defaultBlockState().setValue(TYPE, Type.NORTH).setValue(WATERLOGGED, water);
            case WEST -> defaultBlockState().setValue(TYPE, Type.EAST).setValue(WATERLOGGED, water);
            case EAST -> defaultBlockState().setValue(TYPE, Type.WEST).setValue(WATERLOGGED, water);
            default -> defaultBlockState().setValue(WATERLOGGED, water);
        };
    }

    private boolean isClickInFreeHalf(BlockPlaceContext ctx, BlockState slabState) {
        Vec3 hit = ctx.getClickLocation();
        BlockPos pos = ctx.getClickedPos();
        double localX = hit.x - pos.getX();
        double localZ = hit.z - pos.getZ();

        return switch (slabState.getValue(TYPE)) {
            case NORTH -> localZ > 0.5;
            case SOUTH -> localZ < 0.5;
            case WEST -> localX > 0.5;
            case EAST -> localX < 0.5;
            default -> false;
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    private enum Type implements StringRepresentable {
        NORTH("north"), EAST("east"), SOUTH("south"), WEST("west"), DOUBLE("double");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
