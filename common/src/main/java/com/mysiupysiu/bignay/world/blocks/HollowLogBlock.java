package com.mysiupysiu.bignay.world.blocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.registry.init.BignayBlocks;
import com.mysiupysiu.bignay.world.items.tabs.BuildingBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.function.Supplier;

public class HollowLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock, BuildingBlocks {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Supplier<BiMap<Block, Block>> LOGS = HollowLogBlock::getLogs;

    public HollowLogBlock() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
    }

    public HollowLogBlock(Block block) {
        super(Properties.copy(block).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
    }

    public HollowLogBlock(RegistrySupplier<Block> block) {
        this(block.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        boolean isWater = fluid.getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, isWater);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getHollowShape(state.getValue(AXIS));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    private VoxelShape getHollowShape(Direction.Axis axis) {
        return switch (axis) {
            case X -> Shapes.join(Shapes.block(), Shapes.box(0.0, 0.125, 0.125, 1.0, 0.875, 0.875), BooleanOp.ONLY_FIRST);
            case Y -> Shapes.join(Shapes.block(), Shapes.box(0.125, 0.0, 0.125, 0.875, 1.0, 0.875), BooleanOp.ONLY_FIRST);
            case Z -> Shapes.join(Shapes.block(), Shapes.box(0.125, 0.125, 0.0, 0.875, 0.875, 1.0), BooleanOp.ONLY_FIRST);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ItemTags.AXES)) {
            return getStripped(state).map(strippedState -> {
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (player instanceof ServerPlayer) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }

                level.setBlock(pos, strippedState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, strippedState));

                return InteractionResult.sidedSuccess(level.isClientSide);
            }).orElse(InteractionResult.PASS);
        }

        if (stack.getItem() == Items.BUCKET && state.getValue(WATERLOGGED)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(WATERLOGGED, false), 3);
                level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1f, 1f);
                if (!player.isCreative()) player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    public static Optional<BlockState> getStripped(BlockState blockState) {
        return Optional.ofNullable(LOGS.get().get(blockState.getBlock())).map(block -> block.withPropertiesOf(blockState));
    }

    public static BiMap<Block, Block> getLogs() {
        return ImmutableBiMap.<Block, Block>builder()
                .put(BignayBlocks.HOLLOW_ACACIA_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get())
                .put(BignayBlocks.HOLLOW_BAMBOO_BLOCK.get(), BignayBlocks.HOLLOW_STRIPPED_BAMBOO_BLOCK.get())
                .put(BignayBlocks.HOLLOW_BIRCH_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get())
                .put(BignayBlocks.HOLLOW_CHERRY_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get())
                .put(BignayBlocks.HOLLOW_CRIMSON_STEM.get(), BignayBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get())
                .put(BignayBlocks.HOLLOW_DARK_OAK_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get())
                .put(BignayBlocks.HOLLOW_JUNGLE_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get())
                .put(BignayBlocks.HOLLOW_MANGROVE_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get())
                .put(BignayBlocks.HOLLOW_OAK_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_OAK_LOG.get())
                .put(BignayBlocks.HOLLOW_SPRUCE_LOG.get(), BignayBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get())
                .put(BignayBlocks.HOLLOW_VERDANT_STEM.get(), BignayBlocks.HOLLOW_STRIPPED_VERDANT_STEM.get())
                .put(BignayBlocks.HOLLOW_WARPED_STEM.get(), BignayBlocks.HOLLOW_STRIPPED_WARPED_STEM.get())
                .build();
    }
}
