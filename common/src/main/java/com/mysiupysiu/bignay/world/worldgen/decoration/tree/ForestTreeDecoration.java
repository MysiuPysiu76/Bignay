package com.mysiupysiu.bignay.world.worldgen.decoration.tree;

import com.mojang.serialization.Codec;
import com.mysiupysiu.bignay.registry.init.BignayTreeDecorators;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ForestTreeDecoration extends AbstractTrunkVineDecorator {

    public static final Codec<AbstractTrunkVineDecorator> CODEC = Codec.unit(ForestTreeDecoration::new);

    @Override
    public int getChance() {
        return 45;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return BignayTreeDecorators.FOREST_TRUNK_VINE.get();
    }
}
