package com.mysiupysiu.bignay.registry;

import com.mysiupysiu.bignay.registry.core.Registrar;
import com.mysiupysiu.bignay.registry.core.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class BignayParticles {

    public static final Registrar<ParticleType<?>> PARTICLES = new Registrar<>();

    public static final RegistrySupplier<ParticleType<?>> SMALL_SOUL_FLAME = simpleParticle("small_soul_flame", () -> new SimpleParticleType(true) {});

    public static <T extends ParticleType<?>> RegistrySupplier<ParticleType<?>> simpleParticle(String id, Supplier<T> particle) {
        return PARTICLES.register(id, (Supplier<ParticleType<?>>) particle);
    }
}
