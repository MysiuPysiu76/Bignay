package com.mysiupysiu.bignay.neoforge.config;

import com.mysiupysiu.bignay.config.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class NeoForgeConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final Map<ConfigOption<?>, ModConfigSpec.ConfigValue<?>> VALUES = new HashMap<>();

    public static final ModConfigSpec SPEC = build();

    private NeoForgeConfig() {}

    private static ModConfigSpec build() {
        VALUES.clear();

        for (ConfigCategory category : BignayConfig.CATEGORIES) {
            BUILDER.push(category.getName());

            for (ConfigOption<?> option : category.getOptions()) {
                register(option);
            }

            BUILDER.pop();
        }

        return BUILDER.build();
    }

    private static void register(ConfigOption<?> option) {
        if (!option.getComment().isEmpty()) {
            BUILDER.comment(option.getComment());
        }

        if (!option.getTranslation().isEmpty()) {
            BUILDER.translation(option.getTranslation());
        }

        ModConfigSpec.ConfigValue<?> value;

        if (option instanceof BooleanOption boolOpt) {
            value = BUILDER.define(option.getName(), boolOpt.getDefaultValue());
        } else if (option instanceof IntOption intOpt) {
            value = BUILDER.defineInRange(
                    option.getName(),
                    intOpt.getDefaultValue(),
                    intOpt.getMin(),
                    intOpt.getMax()
            );
        } else if (option instanceof EnumOption<?> enumOpt) {
            value = registerEnum(option.getName(), enumOpt);
        } else {
            value = BUILDER.define(option.getName(), option.getDefaultValue().toString());
        }

        VALUES.put(option, value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ModConfigSpec.ConfigValue<?> registerEnum(String name, EnumOption<?> enumOpt) {
        EnumOption raw = enumOpt;

        return BUILDER.defineEnum(
                name,
                (Enum) raw.getDefaultValue(),
                (java.util.Collection) Arrays.asList(raw.getValues())
        );
    }

    public static void register(IEventBus bus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);

        bus.addListener(NeoForgeConfig::onLoad);
        bus.addListener(NeoForgeConfig::onReload);

        bindAutoSave();
    }

    private static void onLoad(ModConfigEvent.Loading e) {
        if (e.getConfig().getSpec() == SPEC) {
            loadIntoCommon();
        }
    }

    private static void onReload(ModConfigEvent.Reloading e) {
        if (e.getConfig().getSpec() == SPEC) {
            loadIntoCommon();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadIntoCommon() {
        for (var entry : VALUES.entrySet()) {
            ConfigOption<Object> option = (ConfigOption<Object>) entry.getKey();
            Object value = entry.getValue().get();
            option.setSilently(value);
        }
    }

    @SuppressWarnings("unchecked")
    private static void bindAutoSave() {
        for (var entry : VALUES.entrySet()) {
            ConfigOption<Object> option = (ConfigOption<Object>) entry.getKey();
            ModConfigSpec.ConfigValue<Object> value = (ModConfigSpec.ConfigValue<Object>) entry.getValue();

            option.bindOnChange(newValue -> {
                value.set(newValue);
                SPEC.save();
            });
        }
    }
}
