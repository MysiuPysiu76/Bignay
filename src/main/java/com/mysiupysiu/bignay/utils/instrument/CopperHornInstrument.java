package com.mysiupysiu.bignay.utils.instrument;

import com.mysiupysiu.bignay.utils.SoundsInit;
import net.minecraft.world.item.Instrument;

public class CopperHornInstrument {

    public static Instrument get(int i) {
        return new Instrument(SoundsInit.MELODY.get(i).getHolder().get(), 140, 256);
    }
}
