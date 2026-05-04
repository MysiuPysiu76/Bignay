package com.mysiupysiu.bignay.neoforge.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BignayMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BignayMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BignayMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, BignayMod.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, BignayMod.MODID);
    public static final DeferredRegister<Instrument> INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, BignayMod.MODID);

    public static void register(IEventBus bus) {
        block(bus);
        blockEntities(bus);
        item(bus);
        entities(bus);
        sounds(bus);
        instruments(bus);

        bus.addListener(NeoForgeRegistry::onBuildCreativeTabs);
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

    private static void sounds(IEventBus bus) {
        for (Registrar.Entry<SoundEvent> entry : BignaySounds.SOUNDS.getEntries()) {
            DeferredHolder<SoundEvent, SoundEvent> obj = SOUNDS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        SOUNDS.register(bus);
    }

    private static void instruments(IEventBus bus) {
        for (Registrar.Entry<Instrument> entry : BignayInstruments.INSTRUMENTS.getEntries()) {
            DeferredHolder<Instrument, Instrument> obj = INSTRUMENTS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        INSTRUMENTS.register(bus);
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        BignayItems.addItemsToTabs(event.getTabKey(), event);

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            BignayInstruments.COPPER_HORNS.forEach(entry -> {
                Instrument instrument = entry.get();
                ResourceLocation res = BuiltInRegistries.INSTRUMENT.getKey(instrument);

                if (res != null && !res.getPath().equals("empty")) {
                    ItemStack stack = new ItemStack(BignayItems.COPPER_HORN.get());
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("instrument", res.toString());
                    stack.setTag(nbt);
                    event.accept(stack);
                }
            });
        }
    }
}
