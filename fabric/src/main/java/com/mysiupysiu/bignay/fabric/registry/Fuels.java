package com.mysiupysiu.bignay.fabric.registry;

import com.mysiupysiu.bignay.registry.BignayItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class Fuels {

    public static void register() {
        com.mysiupysiu.bignay.utils.Fuels.FUELS_LIST.forEach(i -> FuelRegistry.INSTANCE.add(i, i.getBurnTime()));
        com.mysiupysiu.bignay.utils.Fuels.FUELS_MAP.forEach(FuelRegistry.INSTANCE::add);
    }
}
