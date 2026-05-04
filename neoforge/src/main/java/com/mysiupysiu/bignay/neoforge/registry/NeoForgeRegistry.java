package com.mysiupysiu.bignay.neoforge.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.BignayBlockEntities;
import com.mysiupysiu.bignay.registry.init.BignayBlocks;
import com.mysiupysiu.bignay.registry.init.BignayEntities;
import com.mysiupysiu.bignay.registry.init.BignayItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BignayMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BignayMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BignayMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, BignayMod.MODID);

    public static void register(IEventBus bus) {
        block(bus);
        blockEntities(bus);
        item(bus);
        entities(bus);
    }

    private static void block(IEventBus bus) {
        for (Registrar.Entry<Block> entry : BignayBlocks.BLOCKS.getEntries()) {
            DeferredHolder<Block, Block> obj = BLOCKS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCKS.register(bus);
    }

    private static void blockEntities(IEventBus bus) {
        for (Registrar.Entry<BlockEntityType<?>> entry : BignayBlockEntities.BLOCK_ENTITIES.getEntries()) {
            DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> obj = BLOCK_ENTITIES.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCK_ENTITIES.register(bus);
    }

    private static void item(IEventBus bus) {
        for (Registrar.Entry<Item> entry : BignayItems.ITEMS.getEntries()) {
            DeferredHolder<Item, Item> obj = ITEMS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        ITEMS.register(bus);
    }

    private static void entities(IEventBus bus) {
        for (Registrar.Entry<EntityType<?>> entry : BignayEntities.ENTITIES.getEntries()) {
            DeferredHolder<EntityType<?>, EntityType<?>> obj = ENTITY_TYPES.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        ENTITY_TYPES.register(bus);
    }
}
