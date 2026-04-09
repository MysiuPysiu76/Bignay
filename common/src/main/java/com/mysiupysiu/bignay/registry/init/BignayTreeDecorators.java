package com.mysiupysiu.bignay.registry.init;

import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.worldgen.decoration.tree.AbstractTrunkVineDecorator;
import com.mysiupysiu.bignay.world.worldgen.decoration.tree.CavityTreeDecorator;
import com.mysiupysiu.bignay.world.worldgen.decoration.tree.ForestTreeDecoration;
import com.mysiupysiu.bignay.world.worldgen.decoration.tree.SwampTreeDecoration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.function.Supplier;

public class BignayTreeDecorators {

    public static final Registrar<TreeDecoratorType<?>> DECORATORS = new Registrar<>();

    public static final RegistrySupplier<TreeDecoratorType<AbstractTrunkVineDecorator>> SWAMP_TRUNK_VINE =
            decorator("swamp_trunk_vine", () -> new TreeDecoratorType<>(SwampTreeDecoration.CODEC));

    public static final RegistrySupplier<TreeDecoratorType<AbstractTrunkVineDecorator>> FOREST_TRUNK_VINE =
            decorator("forest_trunk_vine", () -> new TreeDecoratorType<>(ForestTreeDecoration.CODEC));

    public static final RegistrySupplier<TreeDecoratorType<CavityTreeDecorator>> CAVITY =
            decorator("cavity", () -> new TreeDecoratorType<>(CavityTreeDecorator.CODEC));

    private static <T extends TreeDecorator> RegistrySupplier<TreeDecoratorType<T>> decorator(String id, Supplier<TreeDecoratorType<T>> supplier) {
        return (RegistrySupplier<TreeDecoratorType<T>>) (Object) DECORATORS.register(id, (Supplier<TreeDecoratorType<?>>) (Object) supplier);
    }
}