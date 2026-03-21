package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModInstruments {

    public static final DeferredRegister<Instrument> INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, BignayMod.MODID);
    private static final List<RegistryObject<Instrument>> COPPER_HORNS = new ArrayList<>();

    public static final RegistryObject<Instrument> SKY_COPPER_HORN = registerCopperHorn("sky_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(0).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> HYMN_COPPER_HORN = registerCopperHorn("hymn_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(1).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> WATER_COPPER_HORN = registerCopperHorn("water_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(2).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> FIRE_COPPER_HORN = registerCopperHorn("fire_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(3).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> URGE_COPPER_HORN = registerCopperHorn("urge_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(4).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> TEMPER_COPPER_HORN = registerCopperHorn("temper_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(5).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> NEST_COPPER_HORN = registerCopperHorn("nest_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(6).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> LAKE_COPPER_HORN = registerCopperHorn("lake_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(7).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> RIVER_COPPER_HORN = registerCopperHorn("river_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(8).getHolder().get(), 140, 256 ));
    public static final RegistryObject<Instrument> MOON_COPPER_HORN = registerCopperHorn("moon_copper_horn", () -> new Instrument(SoundsInit.MELODY.get(9).getHolder().get(), 140, 256 ));

    private static RegistryObject<Instrument> registerCopperHorn(String name, Supplier<Instrument> supplier) {
        RegistryObject<Instrument> instrument = registerInstrument(name, supplier);
        COPPER_HORNS.add(instrument);
        return instrument;
    }

    private static RegistryObject<Instrument> registerInstrument(String name, Supplier<Instrument> supplier) {
        return INSTRUMENTS.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        INSTRUMENTS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            COPPER_HORNS.forEach(ro -> {
                ItemStack stack = new ItemStack(ItemInit.COPPER_HORN.get());

                CompoundTag nbt = new CompoundTag();
                nbt.putString("instrument", ro.getId().toString());
                stack.setTag(nbt);

                event.accept(stack);
            });
        }
    }
}
