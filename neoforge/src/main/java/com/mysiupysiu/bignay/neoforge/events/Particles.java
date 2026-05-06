package com.mysiupysiu.bignay.neoforge.events;

import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.client.menu.ArchaeologyTableScreen;
import com.mysiupysiu.bignay.client.particles.SmallSoulFlameParticle;
import com.mysiupysiu.bignay.registry.BignayParticles;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(modid = BignayMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Particles {

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet((SimpleParticleType) BignayParticles.SMALL_SOUL_FLAME.get(), SmallSoulFlameParticle.Provider::new);
    }
}
