package com.mysiupysiu.bignay.registry.init;

import com.google.common.collect.ImmutableList;
import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.Registrar;
import com.mysiupysiu.bignay.registry.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.stream.IntStream;

public class BignaySounds {

    public static final Registrar<SoundEvent> SOUNDS = new Registrar<>();

    public static final ImmutableList<RegistrySupplier<SoundEvent>> HARMONY = registerCopperHorn("harmony");
    public static final ImmutableList<RegistrySupplier<SoundEvent>> MELODY = registerCopperHorn("melody");
    public static final ImmutableList<RegistrySupplier<SoundEvent>> BASS = registerCopperHorn("bass");

    private static ImmutableList<RegistrySupplier<SoundEvent>> registerCopperHorn(String type) {
        return IntStream.range(0, 10).mapToObj(i -> registerSound(type, i)).collect(ImmutableList.toImmutableList());
    }

    private static RegistrySupplier<SoundEvent> registerSound(String type, int id) {
        String uniqueName = String.format("horn.%s.%d", type, id);
        return SOUNDS.register(uniqueName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BignayMod.MODID, uniqueName)));
    }
}
