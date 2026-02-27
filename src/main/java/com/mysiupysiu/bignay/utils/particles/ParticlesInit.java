package com.mysiupysiu.bignay.utils.particles;

import com.mysiupysiu.bignay.BignayMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ParticlesInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BignayMod.MODID);

    public static final RegistryObject<SimpleParticleType> SMALL_SOUL_FLAME = registerParticle("small_soul_flame", () -> new SimpleParticleType(true));

    private static RegistryObject<SimpleParticleType> registerParticle(String name, Supplier<SimpleParticleType> supplier) {
        return PARTICLES.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
