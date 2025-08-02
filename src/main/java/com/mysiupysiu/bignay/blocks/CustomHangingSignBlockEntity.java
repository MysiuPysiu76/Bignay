package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.mysiupysiu.bignay.blocks.BlockEntityInit.VERDANT_HANGING_SIGN;

public class CustomHangingSignBlockEntity extends SignBlockEntity {
    private static final int MAX_TEXT_LINE_WIDTH = 60;
    private static final int TEXT_LINE_HEIGHT = 9;

    public CustomHangingSignBlockEntity(BlockPos p_250603_, BlockState p_251674_) {
        super(VERDANT_HANGING_SIGN.get(), p_250603_, p_251674_);
    }

    public int getTextLineHeight() {
        return TEXT_LINE_HEIGHT;
    }

    public int getMaxTextLineWidth() {
        return MAX_TEXT_LINE_WIDTH;
    }
}
