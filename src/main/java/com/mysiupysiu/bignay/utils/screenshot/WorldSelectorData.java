package com.mysiupysiu.bignay.utils.screenshot;

import net.minecraft.network.chat.Component;
import java.util.*;

public record WorldSelectorData(List<String> values, Map<String, Component> cache) {

    public static WorldSelectorData prepare() {
        List<String> values = new ArrayList<>();
        Map<String, Component> cache = new HashMap<>();

        values.add("all");
        int total = ScreenshotsManager.getAll().size();
        cache.put("all", Component.translatable("screenshotsViewer.all", total));

        List<Map.Entry<String, ScreenshotsManager.WorldScreenshots>> sorted = ScreenshotsManager.getWorldsSortedByCountDesc();

        for (Map.Entry<String, ScreenshotsManager.WorldScreenshots> entry : sorted) {
            String key = entry.getKey();
            ScreenshotsManager.WorldScreenshots ws = entry.getValue();
            int count = (ws == null || ws.screenshots == null) ? 0 : ws.screenshots.size();

            values.add(key);

            if (key.startsWith("single:")) {
                String folder = (ws == null || ws.folder == null) ? key.substring(7) : ws.folder;
                String displayName = ScreenshotsManager.getSingleplayerWorldName(folder);
                cache.put(key, Component.literal(displayName + " (" + count + ")"));
            } else if (key.startsWith("multi:")) {
                String folder = (ws == null || ws.folder == null) ? key.substring(6) : ws.folder;
                cache.put(key, Component.literal(folder + " (" + count + ")"));
            }
        }

        return new WorldSelectorData(values, cache);
    }

    public Component getLabel(String key) {
        return cache.getOrDefault(key, Component.literal(key));
    }
}
