package com.mysiupysiu.bignay.worldgen.decoration.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.blocks.CavityLog;
import com.mysiupysiu.bignay.worldgen.decoration.DecoratorInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class CavityTreeDecorator extends TreeDecorator {

    public static final Codec<CavityTreeDecorator> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("block", BlockInit.WAX_BLOCK.get()).forGetter(d -> d.log),
            Codec.FLOAT.optionalFieldOf("probability", 0.5f).forGetter(d -> d.probability)
    ).apply(inst, CavityTreeDecorator::new));

    private final CavityLog log;
    private final float probability;

    public CavityTreeDecorator(Block block, float probability) {
        this.log = (CavityLog)block;
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return DecoratorInit.CAVITY.get();
    }

    @Override
    public void place(Context ctx) {
        if (ctx.random().nextFloat() > this.probability) return;

        List<BlockPos> logs = ctx.logs();
        if (logs.size() < 5) return;

        int baseY = logs.get(0).getY();
        int topY  = logs.get(logs.size() - 1).getY();

        int minY = baseY + 3;
        int maxY = topY - 1;
        if (minY > maxY) return;

        int targetY = minY + ctx.random().nextInt(maxY - minY + 1);

        for (BlockPos logPos : logs) {
            if (logPos.getY() != targetY) continue;

            BlockState baseState = this.log.randomBlockState(ctx.random());
            Direction facing = baseState.getValue(CavityLog.FACING);

            for (int i = 0; i < 4; i++) {
                BlockPos frontPos = logPos.relative(facing);

                if (ctx.isAir(frontPos)) {
                    ctx.setBlock(logPos, baseState.setValue(CavityLog.FACING, facing));
                    return;
                }

                facing = facing.getClockWise();
            }

            return;
        }
    }
}
