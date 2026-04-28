package com.mysiupysiu.bignay.world.blocks;

import com.mysiupysiu.bignay.world.items.tabs.NaturalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class AncientVinesBlock extends VineBlock implements NaturalBlocks {

    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 0, 2);
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");

    public AncientVinesBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.VINE).randomTicks());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FLOWERS, 0)
                .setValue(CAN_GROW, true)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FLOWERS, CAN_GROW);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS) && state.getValue(CAN_GROW)) {
            level.setBlock(pos, state.setValue(CAN_GROW, false), 2);
            level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean solidSupport = hasSolidSupport(state, level, pos);

        if (!solidSupport) {
            if (state.getValue(FLOWERS) > 0) {
                level.setBlock(pos, state.setValue(FLOWERS, 0), 2);
            }
            return;
        }

        int currentFlowers = state.getValue(FLOWERS);
        if (currentFlowers < 2 && random.nextInt(5) == 0) {
            level.setBlock(pos, state.setValue(FLOWERS, currentFlowers + 1), 2);
        }

        if (state.getValue(CAN_GROW) && random.nextInt(4) == 0) {
            Direction randomDir = Direction.getRandom(random);
            BlockPos targetPos = pos.relative(randomDir);

            if (level.isEmptyBlock(targetPos)) {
                Direction wallDir = findSupport(level, targetPos, random);

                if (wallDir != null) {
                    level.setBlock(targetPos, this.defaultBlockState().setValue(getPropertyForFace(wallDir), true).setValue(FLOWERS, 0).setValue(CAN_GROW, true), 2);
                }
            }
        }
    }

    private boolean hasSolidSupport(BlockState state, ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (dir == Direction.DOWN) continue;
            if (state.getValue(getPropertyForFace(dir))) {
                BlockPos supportPos = pos.relative(dir);
                if (level.getBlockState(supportPos).isFaceSturdy(level, supportPos, dir.getOpposite())) {
                    return true;
                }
            }
        }
        return false;
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
