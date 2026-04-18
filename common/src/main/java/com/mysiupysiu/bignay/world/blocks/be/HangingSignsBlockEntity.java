package com.mysiupysiu.bignay.world.blocks.be;

import com.mysiupysiu.bignay.registry.init.BignayBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HangingSignsBlockEntity extends SignBlockEntity {
    private static final int MAX_TEXT_LINE_WIDTH = 60;
    private static final int TEXT_LINE_HEIGHT = 9;

    public HangingSignsBlockEntity(BlockPos pos, BlockState state) {
        super(BignayBlockEntities.VERDANT_HANGING_SIGN.get(), pos, state);
    }

    public int getTextLineHeight() {
        return TEXT_LINE_HEIGHT;
    }

    public int getMaxTextLineWidth() {
        return MAX_TEXT_LINE_WIDTH;
    }
}
