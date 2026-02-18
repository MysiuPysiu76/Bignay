package com.mysiupysiu.bignay.worldgen.decoration.tree;

import com.mojang.serialization.Codec;
import com.mysiupysiu.bignay.worldgen.decoration.DecoratorInit;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ForestTreeDecoration extends AbstractTrunkVineDecorator {

    public static final Codec<AbstractTrunkVineDecorator> CODEC = Codec.unit(ForestTreeDecoration::new);

    @Override
    public int getChance() {
        return 45;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return DecoratorInit.FOREST_TRUNK_VINE.get();
    }
}
