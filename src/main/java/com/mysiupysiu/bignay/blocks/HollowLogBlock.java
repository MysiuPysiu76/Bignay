package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

public class HollowLogBlock extends RotatedPillarBlock {

    public HollowLogBlock() {
        super(Properties.copy(Blocks.OAK_LOG).noOcclusion());
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getHollowShape(state.getValue(AXIS));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
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
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.getItem() instanceof AxeItem) {
            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(state.getBlock());

            if (key == null) return InteractionResult.PASS;

            String blockName = key.getPath();
            boolean isStripped = blockName.contains("stripped");

            if (!isStripped) {
                String newName = "hollow_stripped_" + blockName.substring(7);
                ResourceLocation newKey = new ResourceLocation(key.getNamespace(), newName);

                Block strippedBlock = ForgeRegistries.BLOCKS.getValue(newKey);

                if (strippedBlock != null && strippedBlock != Blocks.AIR) {
                    BlockState newState = strippedBlock.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));

                    level.setBlock(pos, newState, 11);
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (!level.isClientSide) {
                        itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
