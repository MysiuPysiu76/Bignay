package com.mysiupysiu.bignay.utils.screenshot;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysiupysiu.bignay.screen.screenshot.ScreenshotsViewerScreen;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreenshotsManager {

    private static final Path SCREENSHOTS_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots");
    private static final File SCREENSHOTS = new File(Minecraft.getInstance().gameDirectory, "screenshots.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class WorldScreenshots {
        public String folder;
        public List<String> screenshots = new ArrayList<>();
    }

    public static void saveScreenshot(UUID uuid, String folderName, String screenshotName) {
        try {
            Map<UUID, WorldScreenshots> data = getFromFile();

            WorldScreenshots ws = data.getOrDefault(uuid, new WorldScreenshots());
            ws.folder = folderName;
            ws.screenshots.add(screenshotName);
            data.put(uuid, ws);

            FileWriter writer = new FileWriter(SCREENSHOTS);
            GSON.toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> getAll() {
        try {
            if (!Files.exists(SCREENSHOTS_DIR)) return Collections.emptyList();
            try (Stream<Path> stream = Files.list(SCREENSHOTS_DIR)) {
                return stream.filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".png"))
                        .sorted(getPathComparator())
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getPerWorld(UUID uuid) {
        Map<UUID, WorldScreenshots> data = getFromFile();
        WorldScreenshots ws = data.get(uuid);
        if (ws != null) return new ArrayList<>(ws.screenshots);
        return Collections.emptyList();
    }

    public static Map<UUID, WorldScreenshots> getWorlds() {
        return Collections.unmodifiableMap(getFromFile());
    }

    public static List<Path> getPathsForWorld(UUID uuid) {
        List<String> names = getPerWorld(uuid);
        if (names.isEmpty()) return Collections.emptyList();
        List<Path> out = new ArrayList<>(names.size());
        for (String n : names) {
            Path p = SCREENSHOTS_DIR.resolve(n);
            if (Files.exists(p)) out.add(p);
        }
        out.sort(getPathComparator());
        return out;
    }

    public static List<Map.Entry<UUID, WorldScreenshots>> getWorldsSortedByCountDesc() {
        Map<UUID, WorldScreenshots> data = getFromFile();
        List<Map.Entry<UUID, WorldScreenshots>> entries = new ArrayList<>(data.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue().screenshots.size(), a.getValue().screenshots.size()));
        return entries;
    }

    public static void tryRename(String oldName, String newName) {
        try {
            Map<UUID, WorldScreenshots> data = getFromFile();
            boolean changed = false;

            for (WorldScreenshots ws : data.values()) {
                for (int i = 0; i < ws.screenshots.size(); i++) {
                    if (ws.screenshots.get(i).equals(oldName)) {
                        ws.screenshots.set(i, newName);
                        changed = true;
                        break;
                    }
                }
            }

            if (changed) {
                FileWriter writer = new FileWriter(SCREENSHOTS);
                GSON.toJson(data, writer);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tryDelete(String name) {
        tryDelete(List.of(name));
    }

    public static void tryDelete(List<String> names) {
        try {
            if (names == null || names.isEmpty()) return;

            Set<String> toDelete = new HashSet<>(names);
            Map<UUID, WorldScreenshots> data = getFromFile();
            boolean changed = false;

            for (WorldScreenshots ws : data.values()) {
                if (ws.screenshots.removeIf(toDelete::contains)) {
                    changed = true;
                }
            }

            if (changed) {
                FileWriter writer = new FileWriter(SCREENSHOTS);
                GSON.toJson(data, writer);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<UUID, WorldScreenshots> getFromFile() {
        if (!SCREENSHOTS.exists()) return new HashMap<>();

        try (FileReader reader = new FileReader(SCREENSHOTS)) {
            Type type = new TypeToken<Map<UUID, WorldScreenshots>>() {}.getType();
            Map<UUID, WorldScreenshots> data = GSON.fromJson(reader, type);

            if (data == null) return new HashMap<>();
            return data;

        } catch (Exception e) {
            try {
                File backup = new File(SCREENSHOTS.getParent(), "screenshots_old.json");
                SCREENSHOTS.renameTo(backup);
            } catch (Exception ignored) {}

            return new HashMap<>();
        }
    }

    private static @NotNull Comparator<Path> getPathComparator() {
        Comparator<Path> pathComparator = Comparator.comparingLong(p -> {
            try {
                return Files.getLastModifiedTime(p).toMillis();
            } catch (IOException e) {
                return 0L;
            }
        });

        if (ScreenshotsViewerScreen.isToOldest()) pathComparator = pathComparator.reversed();
        return pathComparator;
    }
}
