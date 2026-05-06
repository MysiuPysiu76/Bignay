package com.mysiupysiu.bignay.world.worldgen.decoration.tree;

import com.mojang.serialization.Codec;
import com.mysiupysiu.bignay.registry.BignayTreeDecorators;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SwampTreeDecoration extends AbstractTrunkVineDecorator {

    public static final Codec<AbstractTrunkVineDecorator> CODEC = Codec.unit(SwampTreeDecoration::new);

    @Override
    public int getChance() {
        return 30;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return BignayTreeDecorators.SWAMP_TRUNK_VINE.get();
    }
}
