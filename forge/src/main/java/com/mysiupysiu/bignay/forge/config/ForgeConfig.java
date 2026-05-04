package com.mysiupysiu.bignay.forge.config;

import com.mysiupysiu.bignay.config.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.HashMap;
import java.util.Map;

public class ForgeConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final Map<ConfigOption<?>, ForgeConfigSpec.ConfigValue<?>> FORGE_VALUES = new HashMap<>();

    public static ForgeConfigSpec build() {
        FORGE_VALUES.clear();
        for (ConfigCategory category : BignayConfig.CATEGORIES) {
            BUILDER.push(category.getName()).translation("config.bignay.category." + category.getName());

            for (ConfigOption<?> option : category.getOptions()) {
                registerOption(option);
            }

            BUILDER.pop();
        }
        return BUILDER.build();
    }

    private static void registerOption(ConfigOption<?> option) {
        if (!option.getComment().isEmpty()) BUILDER.comment(option.getComment());
        if (!option.getTranslation().isEmpty()) BUILDER.translation(option.getTranslation());

        ForgeConfigSpec.ConfigValue<?> forgeValue;

        if (option instanceof BooleanOption boolOpt) {
            forgeValue = registerBoolean(boolOpt);
        } else if (option instanceof IntOption intOpt) {
            forgeValue = registerInteger(intOpt);
        } else {
            forgeValue = registerFallback(option);
        }

        FORGE_VALUES.put(option, forgeValue);
    }

    private static ForgeConfigSpec.ConfigValue<Boolean> registerBoolean(BooleanOption option) {
        return BUILDER.define(option.getName(), option.getDefaultValue());
    }

    private static ForgeConfigSpec.ConfigValue<Integer> registerInteger(IntOption option) {
        return BUILDER.defineInRange(option.getName(), option.getDefaultValue(), option.getMin(), option.getMax());
    }

    private static ForgeConfigSpec.ConfigValue<String> registerFallback(ConfigOption<?> option) {
        return BUILDER.define(option.getName(), option.getDefaultValue().toString());
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        FORGE_VALUES.forEach((opt, forge) -> ((ConfigOption<Object>) opt).set(forge.get()));
    }

    @SuppressWarnings("unchecked")
    public static void updateForgeFromBignay() {
        FORGE_VALUES.forEach((opt, forge) -> ((ForgeConfigSpec.ConfigValue<Object>) forge).set(opt.get()));
    }

    public static void register(IEventBus bus) {
        ForgeConfigSpec spec = build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);

        bus.addListener((ModConfigEvent.Loading e) -> load());
        BignayConfig.saveCallback = ForgeConfig::updateForgeFromBignay;
    }
}
