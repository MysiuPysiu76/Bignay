package com.mysiupysiu.bignay.utils.screenshot;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.NotNull;

import java.io.*;
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

    private static final int CURRENT_FORMAT_VERSION = 2;

    public static class WorldScreenshots {
        public String folder;
        public List<Meta> screenshots = new ArrayList<>();
    }

    private static class Sections {
        public Map<String, WorldScreenshots> singleplayer = new HashMap<>();
        public Map<String, WorldScreenshots> multiplayer = new HashMap<>();
    }

    private static class Root {
        public int version = CURRENT_FORMAT_VERSION;
        public Sections screenshots = new Sections();
    }

    public static class Meta {

        public long timestamp;
        public String name;
        public Position pos;

        public static class Position {

            public int x;
            public int y;
            public int z;
            public String dimension;

            public Position() {}

            public Position(int x, int y, int z, String dimension) {
                this.x = x;
                this.y = y;
                this.z = z;
                this.dimension = dimension;
            }
        }
    }

    public static void saveSingleplayerScreenshot(UUID uuid, String folderName, Meta meta) {
        if (uuid == null || meta == null) return;
        String key = uuid.toString();
        try {
            Root root = readRoot();
            WorldScreenshots ws = root.screenshots.singleplayer.getOrDefault(key, new WorldScreenshots());
            ws.folder = folderName;
            if (ws.screenshots == null) ws.screenshots = new ArrayList<>();
            ws.screenshots.add(meta);
            root.screenshots.singleplayer.put(key, ws);
            writeRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMultiplayerScreenshot(String ip, String name, Meta meta) {
        if (ip == null || meta == null) return;
        String key = normalizeIpKey(ip);
        try {
            Root root = readRoot();
            WorldScreenshots ws = root.screenshots.multiplayer.getOrDefault(key, new WorldScreenshots());
            ws.folder = name;
            if (ws.screenshots == null) ws.screenshots = new ArrayList<>();
            ws.screenshots.add(meta);
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
                return stream.filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".png"))
                        .sorted(getPathComparator())
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Meta> getPerSingleplayer(UUID uuid) {
        if (uuid == null) return Collections.emptyList();
        Root root = readRootNoThrow();
        WorldScreenshots ws = root.screenshots.singleplayer.get(uuid.toString());
        if (ws != null && ws.screenshots != null) return new ArrayList<>(ws.screenshots);
        return Collections.emptyList();
    }

    public static List<Meta> getPerMultiplayer(String ip) {
        if (ip == null) return Collections.emptyList();
        String key = normalizeIpKey(ip);
        Root root = readRootNoThrow();
        WorldScreenshots ws = root.screenshots.multiplayer.get(key);
        if (ws != null && ws.screenshots != null) return new ArrayList<>(ws.screenshots);
        return Collections.emptyList();
    }

    public static List<Path> getPathsForSingleplayer(UUID uuid) {
        List<Meta> metas = getPerSingleplayer(uuid);
        List<String> names = metas.stream().map(m -> m.name).collect(Collectors.toList());
        return namesToExistingPaths(names);
    }

    public static List<Path> getPathsForMultiplayer(String ip) {
        List<Meta> metas = getPerMultiplayer(ip);
        List<String> names = metas.stream().map(m -> m.name).collect(Collectors.toList());
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

    public static int safeSize(List<?> l) {
        return l == null ? 0 : l.size();
    }

    public static void tryRename(String oldName, String newName) {
        if (oldName == null || newName == null) return;
        try {
            Root root = readRoot();
            boolean changed = false;

            for (WorldScreenshots ws : root.screenshots.singleplayer.values()) {
                if (ws.screenshots != null) {
                    for (Meta meta : ws.screenshots) {
                        if (oldName.equals(meta.name)) {
                            meta.name = newName;
                            changed = true;
                        }
                    }
                }
            }

            for (WorldScreenshots ws : root.screenshots.multiplayer.values()) {
                if (ws.screenshots != null) {
                    for (Meta meta : ws.screenshots) {
                        if (oldName.equals(meta.name)) {
                            meta.name = newName;
                            changed = true;
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
                    if (ws.screenshots.removeIf(meta -> toDelete.contains(meta.name))) changed = true;
                }
            }

            for (WorldScreenshots ws : root.screenshots.multiplayer.values()) {
                if (ws.screenshots != null) {
                    if (ws.screenshots.removeIf(meta -> toDelete.contains(meta.name))) changed = true;
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
            JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
            if (jsonObject == null) return new Root();

            int version = jsonObject.has("version") ? jsonObject.get("version").getAsInt() : 1;

            boolean migration = false;
            if (version < CURRENT_FORMAT_VERSION && jsonObject.has("screenshots")) {
                JsonObject screenshotsObj = jsonObject.getAsJsonObject("screenshots");

                if (screenshotsObj.has("singleplayer")) {
                    migration |= migrateSection(screenshotsObj.getAsJsonObject("singleplayer"));
                }
                if (screenshotsObj.has("multiplayer")) {
                    migration |= migrateSection(screenshotsObj.getAsJsonObject("multiplayer"));
                }
            }

            Root data = GSON.fromJson(jsonObject, Root.class);
            if (data == null) return new Root();

            data.version = CURRENT_FORMAT_VERSION;
            if (data.screenshots == null) data.screenshots = new Sections();
            if (data.screenshots.singleplayer == null) data.screenshots.singleplayer = new HashMap<>();
            if (data.screenshots.multiplayer == null) data.screenshots.multiplayer = new HashMap<>();

            if (migration) writeRoot(data);

            return data;
        } catch (Exception e) {
            try {
                File backup = new File(SCREENSHOTS.getParentFile(), "screenshots_broken.json");
                SCREENSHOTS.renameTo(backup);
            } catch (Exception ignored) {}
            return new Root();
        }
    }

    // Transform old String to new Meta object;
    private static boolean migrateSection(JsonObject sectionObj) {
        boolean anyChanges = false;

        for (Map.Entry<String, JsonElement> entry : sectionObj.entrySet()) {
            JsonObject worldObj = entry.getValue().getAsJsonObject();

            if (worldObj.has("screenshots")) {
                JsonArray oldScreenshots = worldObj.getAsJsonArray("screenshots");
                JsonArray newScreenshots = new JsonArray();
                boolean worldChanged = false;

                for (JsonElement element : oldScreenshots) {
                    if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                        String fileName = element.getAsString();

                        JsonObject metaObj = new JsonObject();
                        metaObj.addProperty("name", fileName);

                        long timestamp = System.currentTimeMillis();
                        Path filePath = SCREENSHOTS_DIR.resolve(fileName);
                        if (Files.exists(filePath)) {
                            try {
                                timestamp = Files.getLastModifiedTime(filePath).toMillis();
                            } catch (IOException ignored) {}
                        }
                        metaObj.addProperty("timestamp", timestamp);

                        newScreenshots.add(metaObj);
                        worldChanged = true;
                        anyChanges = true;
                    } else {
                        newScreenshots.add(element);
                    }
                }

                if (worldChanged) {
                    worldObj.add("screenshots", newScreenshots);
                }
            }
        }
        return anyChanges;
    }

    public static String getSingleplayerWorldName(String worldFolderName) {
        try {
            Path savesFolder = Minecraft.getInstance().gameDirectory.toPath().resolve("saves");
            Path worldFolder = savesFolder.resolve(worldFolderName);
            Path levelDatPath = worldFolder.resolve("level.dat");

            if (!Files.exists(levelDatPath)) {
                return worldFolderName;
            }

            try (InputStream is = Files.newInputStream(levelDatPath)) {
                CompoundTag rootTag = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());

                if (rootTag == null) return worldFolderName;

                CompoundTag dataTag = rootTag.contains("Data", 10) ? rootTag.getCompound("Data") : rootTag;

                if (dataTag.contains("LevelName", 8)) {
                    String levelName = dataTag.getString("LevelName");
                    if (!levelName.isEmpty()) {
                        return levelName;
                    }
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

        root.version = CURRENT_FORMAT_VERSION;

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

        if (BignayConfig.screenshots.sortToOldest.get()) pathComparator = pathComparator.reversed();
        return pathComparator;
    }
}
