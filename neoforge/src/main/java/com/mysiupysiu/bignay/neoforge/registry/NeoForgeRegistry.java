package com.mysiupysiu.bignay.neoforge.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.BignayBlocks;
import com.mysiupysiu.bignay.registry.init.BignayItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BignayMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BignayMod.MODID);

    public static void register(IEventBus bus) {
        block(bus);
        item(bus);
    }

    private static void block(IEventBus bus) {
        for (Registrar.Entry<Block> entry : BignayBlocks.BLOCKS.getEntries()) {
            DeferredHolder<Block, Block> obj = BLOCKS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCKS.register(bus);
    }

    private static void item(IEventBus bus) {
        for (Registrar.Entry<Item> entry : BignayItems.ITEMS.getEntries()) {
            DeferredHolder<Item, Item> obj = ITEMS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        ITEMS.register(bus);
    }
}
