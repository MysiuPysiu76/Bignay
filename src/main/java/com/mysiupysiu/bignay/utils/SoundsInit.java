package com.mysiupysiu.bignay.utils;

import com.google.common.collect.ImmutableList;
import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.IntStream;

public class SoundsInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BignayMod.MODID);

    public static final ImmutableList<RegistryObject<SoundEvent>> HARMONY = registerHarmonyHornsSoundVariants();
    public static final ImmutableList<RegistryObject<SoundEvent>> MELODY = registerMelodyHornsSoundVariants();
    public static final ImmutableList<RegistryObject<SoundEvent>> BASS = registerBassHornsSoundVariants();

    private static ImmutableList<RegistryObject<SoundEvent>> registerHarmonyHornsSoundVariants() {
        return registerCopperHorn("harmony");
    }

    private static ImmutableList<RegistryObject<SoundEvent>> registerMelodyHornsSoundVariants() {
        return registerCopperHorn("melody");
    }

    private static ImmutableList<RegistryObject<SoundEvent>> registerBassHornsSoundVariants() {
        return registerCopperHorn("bass");
    }

    private static ImmutableList<RegistryObject<SoundEvent>> registerCopperHorn(String type) {
        return IntStream.range(0, 10).mapToObj(i -> registerSound(type, i)).collect(ImmutableList.toImmutableList());
    }

    private static RegistryObject<SoundEvent> registerSound(String type, int id) {
        String uniqueName = String.format("horn.%s.%d", type, id);
        return SOUNDS.register(uniqueName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BignayMod.MODID, uniqueName)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}

