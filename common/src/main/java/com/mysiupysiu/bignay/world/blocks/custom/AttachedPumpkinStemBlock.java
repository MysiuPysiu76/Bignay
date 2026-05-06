package com.mysiupysiu.bignay.world.blocks.custom;

import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemGrownBlock;

import java.util.function.Supplier;

public class AttachedPumpkinStemBlock extends AttachedStemBlock {

    public AttachedPumpkinStemBlock(RegistrySupplier<Block> stemGrownBlock, Supplier<Item> seedSupplier, Properties properties) {
        super((StemGrownBlock) stemGrownBlock.get(), seedSupplier, properties);
    }
}