package com.mysiupysiu.bignay;

import com.mysiupysiu.bignay.blocks.BlockEntityInit;
import com.mysiupysiu.bignay.blocks.BlockInit;
import com.mysiupysiu.bignay.entities.EntityInit;
import com.mysiupysiu.bignay.items.ItemInit;
import com.mysiupysiu.bignay.menu.MenuInit;
import com.mysiupysiu.bignay.utils.ModConfig;
import com.mysiupysiu.bignay.utils.ModInstruments;
import com.mysiupysiu.bignay.utils.RecipeSerializersInit;
import com.mysiupysiu.bignay.utils.SoundsInit;
import com.mysiupysiu.bignay.utils.particles.ParticlesInit;
import com.mysiupysiu.bignay.worldgen.decoration.DecoratorInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BignayMod.MODID)
public class BignayMod {
    public static final String MODID = "bignay";

    public BignayMod() {
        this.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void init(IEventBus e) {
        BlockInit.register(e);
        ItemInit.register(e);
        BlockEntityInit.register(e);
        MenuInit.register(e);
        EntityInit.register(e);
        DecoratorInit.register(e);
        RecipeSerializersInit.register(e);
        ParticlesInit.register(e);
        SoundsInit.register(e);
        ModInstruments.register(e);

        e.addListener(BlockInit::addCreative);
        e.addListener(ItemInit::addCreative);
        e.addListener(ModInstruments::addCreative);
        e.addListener(MenuInit::onClientSetup);
        e.addListener(BlockEntityInit::onClientSetup);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }
}
