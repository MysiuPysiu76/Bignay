package com.mysiupysiu.bignay.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class WeatheringCopperVerticalSlabBlock extends VerticalSlabBlock implements WeatheringCopper {

    private final WeatheringCopper.WeatherState weatherState;
    private final WeatheringCopperVerticalSlabBlock nextSlab;

    public WeatheringCopperVerticalSlabBlock(WeatheringCopper.WeatherState state, Block block, Block nextSlab) {
        super(block);
        this.weatherState = state;
        this.nextSlab = (WeatheringCopperVerticalSlabBlock)nextSlab;
    }

    @Override
    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return nextSlab != null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (nextSlab != null) {
            BlockState newState = nextSlab.defaultBlockState().setValue(TYPE, state.getValue(TYPE)).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            level.setBlock(pos, newState, 3);
        }
    }
}