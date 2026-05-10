package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Fuels {

    @SubscribeEvent
    public static void fuel(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();

        if (com.mysiupysiu.bignay.utils.Fuels.FUELS_MAP.containsKey(item)) {
            event.setBurnTime(com.mysiupysiu.bignay.utils.Fuels.FUELS_MAP.get(item));
        }
    }
}
