package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class AttachedPumpkinStemBlock extends AttachedStemBlock {

    public AttachedPumpkinStemBlock(String stem, String fruit, String seeds) {
        super(ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(BignayMod.MODID, stem)),
                ResourceKey.create(Registries.BLOCK, ResourceLocation.tryBuild(BignayMod.MODID, fruit)),
                ResourceKey.create(Registries.ITEM, ResourceLocation.tryBuild(BignayMod.MODID, seeds)),
                Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.HARD_CROP).pushReaction(PushReaction.DESTROY));
    }
}
