package com.mysiupysiu.bignay.blocks;

import com.mysiupysiu.bignay.utils.CreativeTabProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class HollowLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock, CreativeTabProvider {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HollowLogBlock() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(WATERLOGGED, false));
    }

    public HollowLogBlock(Block block) {
        super(Properties.copy(block).noOcclusion());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(WATERLOGGED, false));
    }

    public HollowLogBlock(RegistryObject<Block> block) {
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
        return this.defaultBlockState()
                .setValue(AXIS, context.getClickedFace().getAxis())
                .setValue(WATERLOGGED, isWater);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
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
            case X -> Shapes.join(Shapes.block(),
                    Shapes.box(0.0, 0.125, 0.125, 1.0, 0.875, 0.875),
                    BooleanOp.ONLY_FIRST);
            case Y -> Shapes.join(Shapes.block(),
                    Shapes.box(0.125, 0.0, 0.125, 0.875, 1.0, 0.875),
                    BooleanOp.ONLY_FIRST);
            case Z -> Shapes.join(Shapes.block(),
                    Shapes.box(0.125, 0.125, 0.0, 0.875, 0.875, 1.0),
                    BooleanOp.ONLY_FIRST);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof AxeItem) {
            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(this);
            if (key != null && !key.getPath().contains("stripped")) {
                String newName = "hollow_stripped_" + key.getPath().substring(7);
                Block stripped = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(key.getNamespace(), newName));
                if (stripped != null && stripped != Blocks.AIR) {
                    BlockState ns = stripped.defaultBlockState()
                            .setValue(AXIS, state.getValue(AXIS))
                            .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
                    level.setBlock(pos, ns, 11);
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
                    if (!level.isClientSide()) stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }

        if (stack.getItem() instanceof BucketItem bucket && bucket.getFluid() == Fluids.WATER) {
            if (!state.getValue(WATERLOGGED)) {
                if (!level.isClientSide()) {
                    level.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
                    level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

                    level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);
                    if (!player.isCreative()) player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
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

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public List<ResourceKey<CreativeModeTab>> getCreativeTabs() {
        return List.of(CreativeModeTabs.BUILDING_BLOCKS);
    }
}
