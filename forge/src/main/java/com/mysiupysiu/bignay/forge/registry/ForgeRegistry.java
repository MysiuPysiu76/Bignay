package com.mysiupysiu.bignay.forge.registry;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.init.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class ForgeRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BignayMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BignayMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BignayMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BignayMod.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BignayMod.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BignayMod.MODID);
    public static final DeferredRegister<Instrument> INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, BignayMod.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BignayMod.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BignayMod.MODID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, BignayMod.MODID);

    public static void register(IEventBus bus) {
        blocks(bus);
        blockEntities(bus);
        items(bus);
        entities(bus);
        sounds(bus);
        recipes(bus);
        instruments(bus);
        particles(bus);
        menus(bus);
        treeDecorators(bus);

        bus.addListener(ForgeRegistry::onBuildCreativeTabs);
    }

    private static void blocks(IEventBus bus) {
        for (Registrar.Entry<Block> entry : BignayBlocks.BLOCKS.getEntries()) {
            RegistryObject<Block> obj = BLOCKS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCKS.register(bus);
    }

    private static void blockEntities(IEventBus bus) {
        for (Registrar.Entry<BlockEntityType<?>> entry : BignayBlockEntities.BLOCK_ENTITIES.getEntries()) {
            RegistryObject<BlockEntityType<?>> obj = BLOCK_ENTITIES.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        BLOCK_ENTITIES.register(bus);
    }

    private static void items(IEventBus bus) {
        for (Registrar.Entry<Item> entry : BignayItems.ITEMS.getEntries()) {
            RegistryObject<Item> obj = ITEMS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        ITEMS.register(bus);
    }

    private static void entities(IEventBus bus) {
        for (Registrar.Entry<EntityType<?>> entry : BignayEntities.ENTITIES.getEntries()) {
            RegistryObject<EntityType<?>> obj = ENTITY_TYPES.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        ENTITY_TYPES.register(bus);
    }

    private static void sounds(IEventBus bus) {
        for (Registrar.Entry<SoundEvent> entry : BignaySounds.SOUNDS.getEntries()) {
            RegistryObject<SoundEvent> obj = SOUNDS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        SOUNDS.register(bus);
    }

    private static void recipes(IEventBus bus) {
        for (Registrar.Entry<RecipeSerializer<?>> entry : BignayRecipes.RECIPES.getEntries()) {
            RegistryObject<RecipeSerializer<?>> obj = RECIPE_SERIALIZERS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        RECIPE_SERIALIZERS.register(bus);
    }

    private static void instruments(IEventBus bus) {
        for (Registrar.Entry<Instrument> entry : BignayInstruments.INSTRUMENTS.getEntries()) {
            RegistryObject<Instrument> obj = INSTRUMENTS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        INSTRUMENTS.register(bus);
    }

    private static void particles(IEventBus bus) {
        for (Registrar.Entry<ParticleType<?>> entry : BignayParticles.PARTICLES.getEntries()) {
            RegistryObject<ParticleType<?>> registeredObject = PARTICLES.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(registeredObject);
        }
        PARTICLES.register(bus);
    }

    private static void menus(IEventBus bus) {
        for (Registrar.Entry<MenuType<?>> entry : MenuInit.MENUS.getEntries()) {
            RegistryObject<MenuType<?>> obj = MENUS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        MENUS.register(bus);
    }

    private static void treeDecorators(IEventBus bus) {
        for (Registrar.Entry<TreeDecoratorType<?>> entry : BignayTreeDecorators.DECORATORS.getEntries()) {
            RegistryObject<TreeDecoratorType<?>> obj = TREE_DECORATORS.register(entry.getId(), entry.getSupplier());
            entry.setSupplier(obj);
        }
        TREE_DECORATORS.register(bus);
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
