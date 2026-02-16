package com.mysiupysiu.bignay.worldgen.decoration;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.worldgen.decoration.tree.AbstractTrunkVineDecorator;
import com.mysiupysiu.bignay.worldgen.decoration.tree.CavityTreeDecorator;
import com.mysiupysiu.bignay.worldgen.decoration.tree.ForestTreeDecoration;
import com.mysiupysiu.bignay.worldgen.decoration.tree.SwampTreeDecoration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DecoratorInit {

    public static final DeferredRegister<TreeDecoratorType<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, BignayMod.MODID);

    public static final RegistryObject<TreeDecoratorType<AbstractTrunkVineDecorator>> SWAMP_TRUNK_VINE = DECORATORS.register("swamp_trunk_vine", () -> new TreeDecoratorType<>(SwampTreeDecoration.CODEC));
    public static final RegistryObject<TreeDecoratorType<AbstractTrunkVineDecorator>> FOREST_TRUNK_VINE = DECORATORS.register("forest_trunk_vine", () -> new TreeDecoratorType<>(ForestTreeDecoration.CODEC));

    public static final RegistryObject<TreeDecoratorType<CavityTreeDecorator>> CAVITY = DECORATORS.register("cavity", () -> new TreeDecoratorType<>(CavityTreeDecorator.CODEC));

    public static void register(IEventBus eventBus) {
        DECORATORS.register(eventBus);
    }
}
