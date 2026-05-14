package com.mysiupysiu.bignay.world.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class WeatheringCopperVerticalSlabBlock extends VerticalSlabBlock implements WeatheringCopper {

    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringCopperVerticalSlabBlock(WeatheringCopper.WeatherState state, Block baseBlock) {
        super(baseBlock);
        this.weatherState = state;
    }

    @Override
    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return this.weatherState != WeatheringCopper.WeatherState.OXIDIZED;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (rand.nextFloat() < 0.025F) {
            getNextStageBlock().ifPresent(nextBlock -> {
                BlockState newState = nextBlock.defaultBlockState()
                        .setValue(VerticalSlabBlock.TYPE, state.getValue(VerticalSlabBlock.TYPE))
                        .setValue(VerticalSlabBlock.WATERLOGGED, state.getValue(VerticalSlabBlock.WATERLOGGED));

                level.setBlock(pos, newState, 3);
            });
        }
    }

    private Optional<Block> getNextStageBlock() {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(this);

        if (id == BuiltInRegistries.BLOCK.getDefaultKey()) return Optional.empty();

        String path = id.getPath();
        String nextPath = getNextPath(path);

        if (nextPath != null) {
            ResourceLocation nextId = new ResourceLocation(id.getNamespace(), nextPath);
            Block nextBlock = BuiltInRegistries.BLOCK.get(nextId);

            if (nextBlock != Blocks.AIR) {
                return Optional.of(nextBlock);
            }
        }
        return Optional.empty();
    }

    private static String getNextPath(String currentPath) {
        return switch (currentPath) {
            case "cut_copper_vertical_slab" -> "exposed_cut_copper_vertical_slab";
            case "exposed_cut_copper_vertical_slab" -> "weathered_cut_copper_vertical_slab";
            case "weathered_cut_copper_vertical_slab" -> "oxidized_cut_copper_vertical_slab";
            default -> null;
        };
    }
}
