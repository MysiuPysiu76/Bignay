package com.mysiupysiu.bignay.fabric;

import com.mysiupysiu.bignay.registry.init.BignayItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

class Fuels {

    public static void register() {
        BignayItems.FUELS.forEach(i -> FuelRegistry.INSTANCE.add(i, i.getBurnTime()));
    }
}
