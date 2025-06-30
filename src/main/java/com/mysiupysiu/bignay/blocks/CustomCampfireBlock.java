package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class CustomCampfireBlock extends CampfireBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CustomCampfireBlock(BlockBehaviour.Properties properties) {
        super(false, 1, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, true)
                .setValue(SIGNAL_FIRE, false)
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomCampfireBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return (lvl, pos, blockState, be) -> {
                if (blockState.getValue(LIT)) {
                    addCampfireParticles(lvl, pos, blockState.getValue(SIGNAL_FIRE));
                    if (isCampfire(state.getBlock())) {
                        CustomCampfireBlockEntity.serverTick(level, pos, state, (CustomCampfireBlockEntity) be);
                    }
                }
            };
        } else {
            return isCampfire(state.getBlock()) ? (lvl, pos, st, be) -> CustomCampfireBlockEntity.serverTick(lvl, pos, st, (CustomCampfireBlockEntity) be) : null;
        }
    }

    private boolean isCampfire(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath().contains("campfire");
    }

    public static void addCampfireParticles(Level level, BlockPos pos, boolean signal) {
        RandomSource random = level.getRandom();

        if (random.nextFloat() < 0.11F) {
            for (int i = 0; i < random.nextInt(signal ? 3 : 2) + 1; ++i) {
                double dx = pos.getX() + 0.5D;
                double dy = pos.getY() + 1.0D;
                double dz = pos.getZ() + 0.5D;
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, dx, dy, dz, 0.0D, 0.07D, 0.0D);
            }
        }

        if (signal && random.nextInt(5) == 0) {
            double dx = pos.getX() + 0.5D;
            double dy = pos.getY() + 1.0D;
            double dz = pos.getZ() + 0.5D;
            level.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, dx, dy, dz, 0.0D, 0.07D, 0.0D);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean water = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        boolean signal = context.getLevel().getBlockState(context.getClickedPos().below()).is(Blocks.HAY_BLOCK);

        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, water)
                .setValue(LIT, !water)
                .setValue(SIGNAL_FIRE, signal);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moved) {
        if (!level.isClientSide) {
            boolean isSignalFire = level.getBlockState(pos.below()).is(Blocks.HAY_BLOCK);
            if (state.getValue(SIGNAL_FIRE) != isSignalFire) {
                level.setBlock(pos, state.setValue(SIGNAL_FIRE, isSignalFire), 3);
            }
        }
        super.neighborChanged(state, level, pos, block, fromPos, moved);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!state.getValue(LIT)) {
            if (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE) {
                level.setBlock(pos, state.setValue(LIT, true), 3);
                level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                } else if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        if (state.getValue(LIT) && itemstack.getItem() instanceof ShovelItem) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 10.0F);
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CustomCampfireBlockEntity campfire) {
            for (int i = 0; i < 4; i++) {
                if (campfire.placeFood(itemstack, i)) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CustomCampfireBlockEntity campfireEntity) {
                for (int i = 0; i < 4; ++i) {
                    ItemStack itemstack = campfireEntity.getItem(i);
                    if (!itemstack.isEmpty()) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                    }
                }
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
