package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.init.BignayItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class Fuels {

    public static void register() {
        BignayItems.FUELS.forEach(i -> FuelRegistry.INSTANCE.add(i, i.getBurnTime()));
    }
}
