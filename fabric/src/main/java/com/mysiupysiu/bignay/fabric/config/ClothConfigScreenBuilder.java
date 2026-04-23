package com.mysiupysiu.bignay.fabric.config;

import com.mysiupysiu.bignay.config.BignayConfig;
import com.mysiupysiu.bignay.config.BooleanOption;
import com.mysiupysiu.bignay.config.ConfigOption;
import com.mysiupysiu.bignay.config.IntOption;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenBuilder {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.bignay.title"))
                .setSavingRunnable(FabricConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        for (com.mysiupysiu.bignay.config.ConfigCategory category : BignayConfig.CATEGORIES) {
            ConfigCategory clothCat = builder.getOrCreateCategory(Component.translatable("config.bignay.category." + category.getName()));

            for (ConfigOption<?> option : category.getOptions()) {
                if (option instanceof BooleanOption boolOpt) {
                    clothCat.addEntry(entryBuilder.startBooleanToggle(Component.translatable(option.getTranslation()), boolOpt.get()).setDefaultValue(boolOpt.getDefaultValue()).setSaveConsumer(boolOpt::set).build());
                } else if (option instanceof IntOption intOpt) {
                    clothCat.addEntry(entryBuilder.startIntField(Component.translatable(option.getTranslation()), intOpt.get()).setDefaultValue(intOpt.getDefaultValue()).setMin(intOpt.getMin()).setMax(intOpt.getMax()).setSaveConsumer(intOpt::set).build());
                }
            }
        }
        return builder.build();
    }
}
