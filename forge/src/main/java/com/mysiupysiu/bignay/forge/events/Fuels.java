package com.mysiupysiu.bignay.forge.events;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
