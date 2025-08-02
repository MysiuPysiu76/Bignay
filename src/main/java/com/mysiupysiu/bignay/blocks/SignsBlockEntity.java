package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SignsBlockEntity extends SignBlockEntity {
    public SignsBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.VERDANT_SIGN.get(), pPos, pBlockState);
    }
}
