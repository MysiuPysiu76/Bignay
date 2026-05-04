package com.mysiupysiu.bignay.neoforge.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.BignayBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BignayMod.MODID);

    public static void register(IEventBus bus) {
        block(bus);
    }

    private static void block(IEventBus bus) {
        for (Registrar.Entry<Block> entry : BignayBlocks.BLOCKS.getEntries()) {
            DeferredHolder<Block, Block> obj = BLOCKS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCKS.register(bus);
    }
}
