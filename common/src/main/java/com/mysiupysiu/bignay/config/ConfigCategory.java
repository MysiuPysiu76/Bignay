package com.mysiupysiu.bignay.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory {
    private final String name;
    private final List<ConfigOption<?>> options = new ArrayList<>();

    public ConfigCategory(String name) {
        this.name = name;
        BignayConfig.CATEGORIES.add(this);
    }

    public <T extends ConfigOption<?>> T add(T option) {
        options.add(option);
        return option;
    }

    public String getName() {
        return name;
    }

    public List<ConfigOption<?>> getOptions() {
        return options;
    }
}
