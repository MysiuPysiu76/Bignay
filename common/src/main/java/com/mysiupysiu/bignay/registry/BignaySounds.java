package com.mysiupysiu.bignay.registry;

import com.google.common.collect.ImmutableList;
import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.stream.IntStream;

public class BignaySounds {

    public static final Registrar<SoundEvent> SOUNDS = new Registrar<>();

    public static final ImmutableList<RegistrySupplier<SoundEvent>> HARMONY = registerCopperHorn("harmony");
    public static final ImmutableList<RegistrySupplier<SoundEvent>> MELODY = registerCopperHorn("melody");
    public static final ImmutableList<RegistrySupplier<SoundEvent>> BASS = registerCopperHorn("bass");

    public static final RegistrySupplier<SoundEvent> LOADING_SCREEN = registerSound("loading_screen");

    private static ImmutableList<RegistrySupplier<SoundEvent>> registerCopperHorn(String type) {
        return IntStream.range(0, 10).mapToObj(i -> registerCopperHorn(type, i)).collect(ImmutableList.toImmutableList());
    }

    private static RegistrySupplier<SoundEvent> registerCopperHorn(String type, int id) {
        return registerSound(String.format("horn.%s.%d", type, id));
    }

    private static RegistrySupplier<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BignayMod.MODID, name)));
    }
}
