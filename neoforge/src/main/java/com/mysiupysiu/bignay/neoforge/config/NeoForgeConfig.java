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

    private static final Map<ConfigOption<?>, ModConfigSpec.ConfigValue<?>> FORGE_VALUES = new HashMap<>();
    private static ModConfigSpec SPEC;
    private static ModConfig CONFIG_INSTANCE;

    public static ModConfigSpec build() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        FORGE_VALUES.clear();

        for (ConfigCategory category : BignayConfig.CATEGORIES) {
            builder.push(category.getName());
            for (ConfigOption<?> option : category.getOptions()) {
                registerOption(builder, option);
            }
            builder.pop();
        }
        SPEC = builder.build();
        return SPEC;
    }

    private static void registerOption(ModConfigSpec.Builder builder, ConfigOption<?> option) {
        if (option.getComment() != null && !option.getComment().isEmpty()) {
            builder.comment(option.getComment());
        }

        ModConfigSpec.ConfigValue<?> forgeValue;
        if (option instanceof BooleanOption boolOpt) {
            forgeValue = builder.define(boolOpt.getName(), boolOpt.getDefaultValue());
        } else if (option instanceof IntOption intOpt) {
            forgeValue = builder.defineInRange(intOpt.getName(), intOpt.getDefaultValue(), intOpt.getMin(), intOpt.getMax());
        } else {
            forgeValue = builder.define(option.getName(), option.getDefaultValue().toString());
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
        if (FORGE_VALUES.isEmpty()) return;

        FORGE_VALUES.forEach((opt, forge) -> ((ModConfigSpec.ConfigValue<Object>) forge).set(opt.get()));

        if (CONFIG_INSTANCE != null) {
            CONFIG_INSTANCE.save();
        }
    }

    public static void register(IEventBus bus) {
        ModConfigSpec spec = build();

        bus.addListener((ModConfigEvent.Loading event) -> {
            if (event.getConfig().getSpec() == spec) {
                CONFIG_INSTANCE = event.getConfig();
                syncFromForge();
            }
        });

        bus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getSpec() == spec) {
                syncFromForge();
            }
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);

        BignayConfig.saveCallback = NeoForgeConfig::syncToForge;
    }
}
