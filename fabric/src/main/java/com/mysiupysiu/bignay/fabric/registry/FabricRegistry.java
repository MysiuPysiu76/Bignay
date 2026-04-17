package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.*;
import com.mysiupysiu.bignay.world.items.tabs.BignayTabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FabricRegistry {

    public static void register() {
        blocks();
        blockEntities();
        items();
        entities();
        sounds();
        recipes();
        instruments();
        particles();
        menus();
        treeDecorators();
    }

    private static void blocks() {
        for (Registrar.Entry<Block> entry : BignayBlocks.BLOCKS.getEntries()) {
            Block block = Registry.register(
                    BuiltInRegistries.BLOCK,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(block);
        }
    }

    private static void blockEntities() {
        for (Registrar.Entry<BlockEntityType<?>> entry : BignayBlockEntities.BLOCK_ENTITIES.getEntries()) {
            BlockEntityType<?> type = Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(type);
        }
    }

    private static void items() {
        for (Registrar.Entry<Item> entry : BignayItems.ITEMS.getEntries()) {
            Item item = Registry.register(
                    BuiltInRegistries.ITEM,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(item);
        }
    }

    private static void entities() {
        for (Registrar.Entry<EntityType<?>> entry : BignayEntities.ENTITIES.getEntries()) {
            EntityType<?> type = Registry.register(
                    BuiltInRegistries.ENTITY_TYPE,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(type);
        }
    }

    private static void sounds() {
        for (Registrar.Entry<SoundEvent> entry : BignaySounds.SOUNDS.getEntries()) {
            SoundEvent sound = Registry.register(
                    BuiltInRegistries.SOUND_EVENT,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(sound);
        }
    }

    private static void recipes() {
        for (Registrar.Entry<RecipeSerializer<?>> entry : BignayRecipes.RECIPES.getEntries()) {
            RecipeSerializer<?> recipe = Registry.register(
                    BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(recipe);
        }
    }

    private static void instruments() {
        for (Registrar.Entry<Instrument> entry : BignayInstruments.INSTRUMENTS.getEntries()) {
            Instrument instrument = Registry.register(
                    BuiltInRegistries.INSTRUMENT,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(instrument);
        }
    }

    private static void particles() {
        for (Registrar.Entry<ParticleType<?>> entry : BignayParticles.PARTICLES.getEntries()) {
            ParticleType<?> type = Registry.register(
                    BuiltInRegistries.PARTICLE_TYPE,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(type);
        }
    }

    private static void menus() {
        for (Registrar.Entry<MenuType<?>> entry : MenuInit.MENUS.getEntries()) {
            MenuType<?> menuType = Registry.register(
                    BuiltInRegistries.MENU,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());

            entry.set(menuType);
        }
    }

    private static void treeDecorators() {
        for (Registrar.Entry<TreeDecoratorType<?>> entry : BignayTreeDecorators.DECORATORS.getEntries()) {
            TreeDecoratorType<?> registeredType = Registry.register(
                    BuiltInRegistries.TREE_DECORATOR_TYPE,
                    new ResourceLocation(BignayMod.MODID, entry.getId()),
                    entry.getSupplier().get());
            entry.set(registeredType);
        }
    }

    public static void addToCreativeTab() {
        for (var tabKey : BignayTabs.getAllTabs()) {
            ItemGroupEvents.modifyEntriesEvent(tabKey).register(output -> {
                BignayItems.addItemsToTabs(tabKey, output);

                if (tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                    for (var entry : BignayInstruments.COPPER_HORNS) {
                        Instrument instrument = entry.get();
                        ResourceLocation id = BuiltInRegistries.INSTRUMENT.getKey(instrument);

                        if (id != null && !id.getPath().equals("empty")) {
                            ItemStack stack = new ItemStack(BignayItems.COPPER_HORN.get());
                            CompoundTag nbt = new CompoundTag();

                            nbt.putString("instrument", id.toString());

                            stack.setTag(nbt);
                            output.accept(stack);
                        }
                    }
                }
            });
        }
    }
}
