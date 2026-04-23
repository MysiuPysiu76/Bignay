package com.mysiupysiu.bignay.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mysiupysiu.bignay.config.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FabricConfig {

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("bignay.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void register() {
        load();

        BignayConfig.saveCallback = FabricConfig::save;
    }

    public static void load() {
        File configFile = CONFIG_PATH.toFile();
        if (!configFile.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);

            for (ConfigCategory category : BignayConfig.CATEGORIES) {
                if (json.has(category.getName())) {
                    JsonObject categoryJson = json.getAsJsonObject(category.getName());

                    for (ConfigOption<?> option : category.getOptions()) {
                        if (categoryJson.has(option.getName())) {
                            loadOption(option, categoryJson);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadOption(ConfigOption<?> option, JsonObject json) {
        String name = option.getName();

        if (!json.has(name)) return;

        if (option instanceof BooleanOption boolOpt) {
            boolOpt.set(json.get(name).getAsBoolean());
        } else if (option instanceof IntOption intOpt) {
            intOpt.set(json.get(name).getAsInt());
        } else if (option instanceof EnumOption<?> enumOpt) {
            String jsonValue = json.get(name).getAsString();

            for (Object enumConstant : enumOpt.getValues()) {
                if (enumConstant.toString().equalsIgnoreCase(jsonValue)) {
                    ((ConfigOption<Object>) enumOpt).set(enumConstant);
                    break;
                }
            }
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();

        for (ConfigCategory category : BignayConfig.CATEGORIES) {
            JsonObject categoryJson = new JsonObject();
            for (ConfigOption<?> option : category.getOptions()) {
                Object value = option.get();
                if (value instanceof Boolean b) categoryJson.addProperty(option.getName(), b);
                else if (value instanceof Number n) categoryJson.addProperty(option.getName(), n);
                else categoryJson.addProperty(option.getName(), value.toString());
            }
            root.add(category.getName(), categoryJson);
        }

        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
