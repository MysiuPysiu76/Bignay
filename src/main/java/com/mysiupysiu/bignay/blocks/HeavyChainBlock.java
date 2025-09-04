package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HeavyChainBlock extends ChainBlock {

    protected static final VoxelShape Y_AXIS_AABB = Block.box(5D, 0.0D, 5D, 11D, 16.0D, 11D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(5D, 5D, 0.0D, 11D, 11D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 5D, 5D, 16.0D, 11D, 11D);

    public HeavyChainBlock() {
        super(BlockBehaviour.Properties.of().forceSolidOn().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState p_51470_, BlockGetter p_51471_, BlockPos p_51472_, CollisionContext p_51473_) {
        return switch (p_51470_.getValue(AXIS)) {
            case Z -> Z_AXIS_AABB;
            case Y -> Y_AXIS_AABB;
            default -> X_AXIS_AABB;
        };
    }
}
