package com.mysiupysiu.bignay.utils.screenshot;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysiupysiu.bignay.utils.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
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

    private static class Sections {
        public Map<String, WorldScreenshots> singleplayer = new HashMap<>();
        public Map<String, WorldScreenshots> multiplayer = new HashMap<>();
    }

    private static class Root {
        public Sections screenshots = new Sections();
    }

    public static void saveSingleplayerScreenshot(UUID uuid, String folderName, String screenshotName) {
        if (uuid == null || screenshotName == null) return;
        String key = uuid.toString();
        try {
            Root root = readRoot();
            WorldScreenshots ws = root.screenshots.singleplayer.getOrDefault(key, new WorldScreenshots());
            ws.folder = folderName;
            if (ws.screenshots == null) ws.screenshots = new ArrayList<>();
            ws.screenshots.add(screenshotName);
            root.screenshots.singleplayer.put(key, ws);
            writeRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMultiplayerScreenshot(String ip, String name, String screenshotName) {
        if (ip == null || screenshotName == null) return;
        String key = normalizeIpKey(ip);
        try {
            Root root = readRoot();
            WorldScreenshots ws = root.screenshots.multiplayer.getOrDefault(key, new WorldScreenshots());
            ws.folder = name;
            if (ws.screenshots == null) ws.screenshots = new ArrayList<>();
            ws.screenshots.add(screenshotName);
            root.screenshots.multiplayer.put(key, ws);
            writeRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String normalizeIpKey(String ip) {
        return ip == null ? "unknown" : ip.trim().toLowerCase(Locale.ROOT);
    }

    public static List<Path> getAll() {
        try {
            if (!Files.exists(SCREENSHOTS_DIR)) return Collections.emptyList();
            try (Stream<Path> stream = Files.list(SCREENSHOTS_DIR)) {
                return stream.filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".png")).sorted(getPathComparator()).collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getPerSingleplayer(UUID uuid) {
        if (uuid == null) return Collections.emptyList();
        Root root = readRootNoThrow();
        WorldScreenshots ws = root.screenshots.singleplayer.get(uuid.toString());
        if (ws != null && ws.screenshots != null) return new ArrayList<>(ws.screenshots);
        return Collections.emptyList();
    }

    public static List<String> getPerMultiplayer(String ip) {
        if (ip == null) return Collections.emptyList();
        String key = normalizeIpKey(ip);
        Root root = readRootNoThrow();
        WorldScreenshots ws = root.screenshots.multiplayer.get(key);
        if (ws != null && ws.screenshots != null) return new ArrayList<>(ws.screenshots);
        return Collections.emptyList();
    }

    public static List<Path> getPathsForSingleplayer(UUID uuid) {
        List<String> names = getPerSingleplayer(uuid);
        return namesToExistingPaths(names);
    }

    public static List<Path> getPathsForMultiplayer(String ip) {
        List<String> names = getPerMultiplayer(ip);
        return namesToExistingPaths(names);
    }

    private static List<Path> namesToExistingPaths(List<String> names) {
        if (names.isEmpty()) return Collections.emptyList();
        List<Path> out = new ArrayList<>(names.size());
        for (String n : names) {
            Path p = SCREENSHOTS_DIR.resolve(n);
            if (Files.exists(p)) out.add(p);
        }
        out.sort(getPathComparator());
        return out;
    }

    public static List<Map.Entry<String, WorldScreenshots>> getWorldsSortedByCountDesc() {
        Root root = readRootNoThrow();
        List<Map.Entry<String, WorldScreenshots>> entries = new ArrayList<>();

        root.screenshots.singleplayer.forEach((k, v) -> entries.add(new AbstractMap.SimpleEntry<>("single:" + k, v)));
        root.screenshots.multiplayer.forEach((k, v) -> entries.add(new AbstractMap.SimpleEntry<>("multi:" + k, v)));

        entries.sort((a, b) -> Integer.compare(safeSize(b.getValue().screenshots), safeSize(a.getValue().screenshots)));
        return entries;
    }

    public static int safeSize(List<String> l) {
        return l == null ? 0 : l.size();
    }

    public static void tryRename(String oldName, String newName) {
        if (oldName == null || newName == null) return;
        try {
            Root root = readRoot();
            boolean changed = false;

            for (WorldScreenshots ws : root.screenshots.singleplayer.values()) {
                if (ws.screenshots != null) {
                    for (int i = 0; i < ws.screenshots.size(); i++) {
                        if (oldName.equals(ws.screenshots.get(i))) {
                            ws.screenshots.set(i, newName);
                            changed = true;
                            break;
                        }
                    }
                }
            }

            for (WorldScreenshots ws : root.screenshots.multiplayer.values()) {
                if (ws.screenshots != null) {
                    for (int i = 0; i < ws.screenshots.size(); i++) {
                        if (oldName.equals(ws.screenshots.get(i))) {
                            ws.screenshots.set(i, newName);
                            changed = true;
                            break;
                        }
                    }
                }
            }

            if (changed) writeRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tryDelete(List<String> names) {
        try {
            if (names == null || names.isEmpty()) return;
            Set<String> toDelete = new HashSet<>(names);

            Root root = readRoot();
            boolean changed = false;

            for (WorldScreenshots ws : root.screenshots.singleplayer.values()) {
                if (ws.screenshots != null) {
                    if (ws.screenshots.removeIf(toDelete::contains)) changed = true;
                }
            }

            for (WorldScreenshots ws : root.screenshots.multiplayer.values()) {
                if (ws.screenshots != null) {
                    if (ws.screenshots.removeIf(toDelete::contains)) changed = true;
                }
            }

            if (changed) writeRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tryDelete(String name) {
        tryDelete(List.of(name));
    }


    private static Root readRootNoThrow() {
        try {
            return readRoot();
        } catch (Exception e) {
            return new Root();
        }
    }

    private static Root readRoot() throws IOException {
        if (!SCREENSHOTS.exists()) return new Root();

        try (FileReader reader = new FileReader(SCREENSHOTS)) {
            Type type = new TypeToken<Root>() {
            }.getType();
            Root data = GSON.fromJson(reader, type);
            if (data == null) return new Root();
            if (data.screenshots == null) data.screenshots = new Sections();
            if (data.screenshots.singleplayer == null) data.screenshots.singleplayer = new HashMap<>();
            if (data.screenshots.multiplayer == null) data.screenshots.multiplayer = new HashMap<>();
            return data;
        } catch (Exception e) {
            try {
                File backup = new File(SCREENSHOTS.getParentFile(), "screenshots_broken.json");
                SCREENSHOTS.renameTo(backup);
            } catch (Exception ignored) {
            }
            return new Root();
        }
    }

    public static String getSingleplayerWorldName(String worldFolderName) {
        try {
            File savesFolder = new File(Minecraft.getInstance().gameDirectory, "saves");
            File worldFolder = new File(savesFolder, worldFolderName);
            File levelDat = new File(worldFolder, "level.dat");

            if (!levelDat.exists()) return worldFolderName;

            CompoundTag rootTag = NbtIo.readCompressed(levelDat);
            if (rootTag == null) return worldFolderName;

            CompoundTag dataTag = rootTag.contains("Data") ? rootTag.getCompound("Data") : rootTag;
            if (dataTag.contains("LevelName")) {
                String levelName = dataTag.getString("LevelName");
                if (!levelName.isEmpty()) {
                    return levelName;
                }
            }

            return worldFolderName;
        } catch (Exception e) {
            return worldFolderName;
        }
    }

    private static void writeRoot(Root root) throws IOException {
        File parent = SCREENSHOTS.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        try (FileWriter writer = new FileWriter(SCREENSHOTS)) {
            GSON.toJson(root, writer);
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

        if (ModConfig.SCREENSHOTS_VIEWER_SORT_TO_OLDEST.get()) pathComparator = pathComparator.reversed();
        return pathComparator;
    }
}
