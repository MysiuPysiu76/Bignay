package com.mysiupysiu.bignay.worldgen.decoration.tree;

import com.mojang.serialization.Codec;
import com.mysiupysiu.bignay.worldgen.decoration.DecoratorInit;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SwampTreeDecoration extends AbstractTrunkVineDecorator {

    public static final Codec<AbstractTrunkVineDecorator> CODEC = Codec.unit(SwampTreeDecoration::new);

    @Override
    public int getChance() {
        return 30;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return DecoratorInit.SWAMP_TRUNK_VINE.get();
    }
}
