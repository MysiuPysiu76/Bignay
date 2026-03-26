package com.mysiupysiu.bignay.utils.instrument;

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

public class InstrumentsInit {

    public static final DeferredRegister<Instrument> INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, BignayMod.MODID);
    private static final List<RegistryObject<Instrument>> COPPER_HORNS = new ArrayList<>();

    public static final RegistryObject<Instrument> SKY_COPPER_HORN = registerCopperHorn("sky_copper_horn", () -> CopperHornInstrument.get(0));
    public static final RegistryObject<Instrument> HYMN_COPPER_HORN = registerCopperHorn("hymn_copper_horn", () -> CopperHornInstrument.get(1));
    public static final RegistryObject<Instrument> WATER_COPPER_HORN = registerCopperHorn("water_copper_horn", () -> CopperHornInstrument.get(2));
    public static final RegistryObject<Instrument> FIRE_COPPER_HORN = registerCopperHorn("fire_copper_horn", () -> CopperHornInstrument.get(3));
    public static final RegistryObject<Instrument> URGE_COPPER_HORN = registerCopperHorn("urge_copper_horn", () -> CopperHornInstrument.get(4));
    public static final RegistryObject<Instrument> TEMPER_COPPER_HORN = registerCopperHorn("temper_copper_horn", () -> CopperHornInstrument.get(5));
    public static final RegistryObject<Instrument> NEST_COPPER_HORN = registerCopperHorn("nest_copper_horn", () -> CopperHornInstrument.get(6));
    public static final RegistryObject<Instrument> LAKE_COPPER_HORN = registerCopperHorn("lake_copper_horn", () -> CopperHornInstrument.get(7));
    public static final RegistryObject<Instrument> RIVER_COPPER_HORN = registerCopperHorn("river_copper_horn", () -> CopperHornInstrument.get(8));
    public static final RegistryObject<Instrument> MOON_COPPER_HORN = registerCopperHorn("moon_copper_horn", () -> CopperHornInstrument.get(9));

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
