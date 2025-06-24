package com.mysiupysiu.bignay;

import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.items.ItemInit;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BignayMod.MODID)
public class BignayMod {
    public static final String MODID = "bignay";

    public BignayMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);

        modEventBus.addListener(BlockInit::addCreative);
        modEventBus.addListener(ItemInit::addCreative);
    }
}
