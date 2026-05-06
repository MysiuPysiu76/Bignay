package com.mysiupysiu.bignay.world.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import com.mysiupysiu.bignay.config.BignayConfig;
import com.mysiupysiu.bignay.registry.BignayBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class VerdantForestBiome extends Region {

    public VerdantForestBiome() {
        super(new ResourceLocation("bignay", "nether_region"), RegionType.NETHER, 1);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        if (!BignayConfig.biomes.verdantForest.get()) return;

        Climate.ParameterPoint verdantConditions = Climate.parameters(
                Climate.Parameter.span(0.1F, 1.0F),
                Climate.Parameter.span(0.3F, 1.0F),
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.span(-1.0F, 1.0F),
                0.0F);

        this.addBiome(mapper, verdantConditions, BignayBiomes.VERDANT_FOREST);
    }
}
