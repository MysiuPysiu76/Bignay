package com.mysiupysiu.bignay.registry.init;

import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import com.mysiupysiu.bignay.world.items.instruments.CopperHornInstrument;
import net.minecraft.world.item.Instrument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BignayInstruments {

    public static final Registrar<Instrument> INSTRUMENTS = new Registrar<>();
    public static final List<RegistrySupplier<Instrument>> COPPER_HORNS = new ArrayList<>();

    public static final RegistrySupplier<Instrument> SKY_COPPER_HORN = copperHorn("sky_copper_horn", () -> CopperHornInstrument.get(0));
    public static final RegistrySupplier<Instrument> HYMN_COPPER_HORN = copperHorn("hymn_copper_horn", () -> CopperHornInstrument.get(1));
    public static final RegistrySupplier<Instrument> WATER_COPPER_HORN = copperHorn("water_copper_horn", () -> CopperHornInstrument.get(2));
    public static final RegistrySupplier<Instrument> FIRE_COPPER_HORN = copperHorn("fire_copper_horn", () -> CopperHornInstrument.get(3));
    public static final RegistrySupplier<Instrument> URGE_COPPER_HORN = copperHorn("urge_copper_horn", () -> CopperHornInstrument.get(4));
    public static final RegistrySupplier<Instrument> TEMPER_COPPER_HORN = copperHorn("temper_copper_horn", () -> CopperHornInstrument.get(5));
    public static final RegistrySupplier<Instrument> NEST_COPPER_HORN = copperHorn("nest_copper_horn", () -> CopperHornInstrument.get(6));
    public static final RegistrySupplier<Instrument> LAKE_COPPER_HORN = copperHorn("lake_copper_horn", () -> CopperHornInstrument.get(7));
    public static final RegistrySupplier<Instrument> RIVER_COPPER_HORN = copperHorn("river_copper_horn", () -> CopperHornInstrument.get(8));
    public static final RegistrySupplier<Instrument> MOON_COPPER_HORN = copperHorn("moon_copper_horn", () -> CopperHornInstrument.get(9));

    private static RegistrySupplier<Instrument> copperHorn(String name, Supplier<Instrument> supplier) {
        RegistrySupplier<Instrument> instrument = instrument(name, supplier);
        COPPER_HORNS.add(instrument);
        return instrument;
    }

    private static RegistrySupplier<Instrument> instrument(String name, Supplier<Instrument> supplier) {
        return INSTRUMENTS.register(name, supplier);
    }
}
