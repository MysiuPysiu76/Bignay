package com.mysiupysiu.bignay.world.blocks.be;

import com.mysiupysiu.bignay.registry.BignayBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SignsBlockEntity extends SignBlockEntity {

    public SignsBlockEntity(BlockPos pos, BlockState state) {
        super(BignayBlockEntities.VERDANT_SIGN.get(), pos, state);
    }
}
