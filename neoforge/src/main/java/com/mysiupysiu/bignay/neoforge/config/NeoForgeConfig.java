package com.mysiupysiu.bignay.neoforge.config;

import com.mysiupysiu.bignay.config.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class NeoForgeConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Map<ConfigOption<?>, ModConfigSpec.ConfigValue<?>> FORGE_VALUES = new HashMap<>();
    private static ModConfigSpec SPEC;

    public static ModConfigSpec build() {
        FORGE_VALUES.clear();
        for (ConfigCategory category : BignayConfig.CATEGORIES) {
            BUILDER.push(category.getName());

            for (ConfigOption<?> option : category.getOptions()) {
                registerOption(option);
            }

            BUILDER.pop();
        }
        SPEC = BUILDER.build();
        return SPEC;
    }

    private static void registerOption(ConfigOption<?> option) {
        if (option.getComment() != null && !option.getComment().isEmpty()) {
            BUILDER.comment(option.getComment());
        }

        if (option.getTranslation() != null && !option.getTranslation().isEmpty()) {
            BUILDER.translation(option.getTranslation());
        }

        ModConfigSpec.ConfigValue<?> forgeValue;

        if (option instanceof BooleanOption boolOpt) {
            forgeValue = BUILDER.define(boolOpt.getName(), boolOpt.getDefaultValue());
        } else if (option instanceof IntOption intOpt) {
            forgeValue = BUILDER.defineInRange(intOpt.getName(), intOpt.getDefaultValue(), intOpt.getMin(), intOpt.getMax());
        } else {
            forgeValue = BUILDER.define(option.getName(), option.getDefaultValue().toString());
        }

        FORGE_VALUES.put(option, forgeValue);
    }

    @SuppressWarnings("unchecked")
    public static void syncFromForge() {
        FORGE_VALUES.forEach((opt, forge) -> {
            ((ConfigOption<Object>) opt).set(forge.get());
        });
    }

    @SuppressWarnings("unchecked")
    public static void syncToForge() {
        FORGE_VALUES.forEach((opt, forge) -> {
            ((ModConfigSpec.ConfigValue<Object>) forge).set(opt.get());
            forge.save();
        });
    }

    public static void register(IEventBus bus) {
        ModConfigSpec spec = build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);

        bus.addListener((ModConfigEvent.Loading e) -> syncFromForge());
        bus.addListener((ModConfigEvent.Reloading e) -> syncFromForge());

        BignayConfig.saveCallback = NeoForgeConfig::syncToForge;
    }
}
