package com.mysiupysiu.bignay.world.items.instruments;

import com.mysiupysiu.bignay.registry.BignaySounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Instrument;

public class CopperHornInstrument {

    public static Instrument get(int i) {
        SoundEvent soundEvent = BignaySounds.MELODY.get(i).get();
        Holder<SoundEvent> holder = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent);
        return new Instrument(holder, 140, 256.0F);
    }
}
