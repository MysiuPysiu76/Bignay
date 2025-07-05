package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
}
