package com.mysiupysiu.bignay.utils.world;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

public class WorldInfoReader {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private CompoundTag levelData;
    private JsonObject stats;

    public WorldInfoReader(LevelStorageSource.LevelStorageAccess levelAccess) {
        this.levelAccess = levelAccess;
        this.loadLevelDat();
        this.loadStats();
    }

    public String getPlayerTimePlayed() {
        try {
            return humanReadablePlayTime(this.stats.getAsJsonObject("minecraft:custom").getAsJsonPrimitive("minecraft:play_time").getAsInt() / 20);
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerMobsKilledCount() {
        try {
            return getElementsCount(this.stats.getAsJsonObject("minecraft:killed"));
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerBlocksDestroyedCount() {
        try {
            return getElementsCount(this.stats.getAsJsonObject("minecraft:mined"));
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerItemsCraftedCount() {
        try {
            return getElementsCount(this.stats.getAsJsonObject("minecraft:crafted"));
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getPlayerDeathsCount() {
        try {
            return this.stats.getAsJsonObject("minecraft:custom").getAsJsonPrimitive("minecraft:deaths").getAsInt();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerDistanceTraveled() {
        try {
            return Stream.of("walk", "horse", "boat", "minecart", "aviate", "swim", "sprint")
                    .map(this::getDistanceByKey)
                    .mapToLong(Long::longValue).sum();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getPlayerFinishedAdvancementsCount() {
        try {
            Path advancementsDir = this.levelAccess.getLevelPath(LevelResource.ROOT).resolve("advancements");
            Path file = advancementsDir.resolve(Minecraft.getInstance().getUser().getProfileId().toString() + ".json");

            if (!Files.exists(file)) return 0;

            int sum = 0;
            try (Reader reader = Files.newBufferedReader(file)) {
                JsonObject json = new Gson().fromJson(reader, JsonObject.class);
                for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                    if (entry.getKey().equals("DataVersion") || entry.getKey().startsWith("minecraft:recipes/")) continue;

                    if (entry.getValue().isJsonObject()) {
                        JsonObject obj = entry.getValue().getAsJsonObject();
                        if (obj.has("done") && obj.get("done").getAsBoolean()) {
                            sum++;
                        }
                    }
                }
            }

            return sum;
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerLastPlayed() {
        try {
            return Files.getLastModifiedTime(this.levelAccess.getLevelPath(LevelResource.LEVEL_DATA_FILE)).toMillis();
        } catch (Exception e) {
            return null;
        }
    }

    public String getWorldName() {
        try {
            return this.levelData.getCompound("Data").getString("LevelName");
        } catch (Exception e) {
            return null;
        }
    }

    public Long getWorldCreatedDate() {
        return CreatedWorldDate.getCreatedDate(levelAccess.getLevelId());
    }

    public String getWorldVersion() {
        try {
            return this.levelData.getCompound("Data").getCompound("Version").getString("Name");
        } catch (Exception e) {
            return null;
        }
    }

    public Long getWorldSeed() {
        try {
            return this.levelData.getCompound("Data").getCompound("WorldGenSettings").getLong("seed");
        } catch (Exception e) {
            return null;
        }
    }

    public String getWorldDifficulty() {
        try {
            return this.levelData.getCompound("Data").getString("Difficulty");
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean getWorldHardcore() {
        try {
            return this.levelData.getCompound("Data").getBoolean("hardcore");
        } catch (Exception e) {
            return null;
        }
    }

    public String getWorldSize() {
        try {
            return FileUtils.humanReadableByteCount(this.levelAccess.getLevelPath(LevelResource.ROOT));
        } catch (Exception e) {
            return "N/A";
        }
    }

    public Long getWorldDay() {
        try {
            return this.levelData.getCompound("Data").getLong("DayTime") / 24000;
        } catch (Exception e) {
            return null;
        }
    }

    public UUID getWorldUUID() {
        try {
            return getWorldUUID(this.levelAccess.getLevelPath(LevelResource.ROOT));
        } catch (Exception e) {
            return UUID.randomUUID();
        }
    }

    public String getWorldGameMode() {
        try {
            if (this.levelData != null && this.levelData.contains("Data", 10)) {
                CompoundTag data = this.levelData.getCompound("Data");
                int mode = data.getInt("GameType");

                return switch (mode) {
                    case 0 -> "Survival";
                    case 1 -> "Creative";
                    case 2 -> "Adventure";
                    case 3 -> "Spectator";
                    default -> "Survival";
                };
            }
        } catch (Exception e) {
            return "Survival";
        }
        return "Survival";
    }

    public Boolean getWorldCheatsEnabled() {
        try {
            if (this.levelData != null && this.levelData.contains("Data", 10)) {
                return this.levelData.getCompound("Data").getBoolean("allowCommands");
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static UUID getWorldUUID(Path worldDir) throws IOException {
        Path file = worldDir.resolve("data").resolve("uuid.dat");

        if (Files.exists(file)) {
            try (InputStream is = Files.newInputStream(file)) {
                CompoundTag tag = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());
                if (tag != null && tag.hasUUID("UUID")) {
                    return tag.getUUID("UUID");
                }
            }
        }

        return createWorldUUID(file);
    }

    private static UUID createWorldUUID(Path file) throws IOException {
        UUID uuid = UUID.randomUUID();
        CompoundTag tag = new CompoundTag();
        tag.putUUID("UUID", uuid);

        Files.createDirectories(file.getParent());
        try (OutputStream os = Files.newOutputStream(file)) {
            NbtIo.writeCompressed(tag, os);
        }
        return uuid;
    }

    private void loadLevelDat() {
        Path levelDatPath = this.levelAccess.getLevelPath(LevelResource.LEVEL_DATA_FILE);

        try {
            if (Files.exists(levelDatPath)) {
                if (levelDatPath.toString().endsWith(".zip")) {
                    try (ZipFile zip = new ZipFile(levelDatPath.toFile())) {
                        ZipEntry entry = zip.getEntry("level.dat");
                        if (entry != null) {
                            try (InputStream is = zip.getInputStream(entry)) {
                                this.levelData = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());
                            }
                        }
                    }
                } else {
                    try (InputStream is = Files.newInputStream(levelDatPath)) {
                        this.levelData = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());
                    }
                }
            }
        } catch (Exception e) {
            this.levelData = new CompoundTag();
        }
    }

    private void loadStats() {
        try {
            Path statsDir = this.levelAccess.getLevelPath(LevelResource.ROOT).resolve("stats");
            Path statsFile = statsDir.resolve(Minecraft.getInstance().getUser().getProfileId().toString() + ".json");

            if (Files.exists(statsFile)) {
                try (Reader reader = Files.newBufferedReader(statsFile)) {
                    this.stats = new Gson().fromJson(reader, JsonObject.class).getAsJsonObject("stats");
                }
            }
        } catch (Exception e) {
            this.stats = new JsonObject();
        }
    }

    private Long getElementsCount(JsonObject object) {
        if (object == null) return 0L;
        long sum = 0;
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            JsonElement elem = entry.getValue();
            if (elem.isJsonPrimitive() && elem.getAsJsonPrimitive().isNumber()) {
                sum += elem.getAsInt();
            }
        }
        return sum;
    }

    private Long getDistanceByKey(String key) {
        try {
            JsonObject custom = this.stats.getAsJsonObject("minecraft:custom");
            return custom.getAsJsonPrimitive("minecraft:" + key + "_one_cm").getAsLong();
        } catch (Exception ex) {
            return 0L;
        }
    }

    public static String humanReadablePlayTime(long seconds) {
        if (seconds < 0) return "0s";
        if (seconds < 60) return seconds + "s";
        if (seconds < 3600) return (seconds / 60) + "m";
        if (seconds < 86400) return (seconds / 3600) + "h";
        return (seconds / 86400) + "d";
    }
}
