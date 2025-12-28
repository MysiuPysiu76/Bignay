package com.mysiupysiu.bignay.worldgen.decoration.tree;

import com.mysiupysiu.bignay.utils.ChanceProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public abstract class AbstractTrunkVineDecorator extends TreeDecorator implements ChanceProvider {

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        context.logs().forEach(log -> {

            if (random.nextInt(0, 100) < getChance()) {
                BlockPos pos = log.west();
                if (context.isAir(pos)) {
                    context.placeVine(pos, VineBlock.EAST);
                }
            }

            if (random.nextInt(0, 100) < getChance()) {
                BlockPos pos = log.east();
                if (context.isAir(pos)) {
                    context.placeVine(pos, VineBlock.WEST);
                }
            }

            if (random.nextInt(0, 100) < getChance()) {
                BlockPos pos = log.north();
                if (context.isAir(pos)) {
                    context.placeVine(pos, VineBlock.SOUTH);
                }
            }

            if (random.nextInt(0, 100) < getChance()) {
                BlockPos pos = log.south();
                if (context.isAir(pos)) {
                    context.placeVine(pos, VineBlock.NORTH);
                }
            }
        });
    }
}
