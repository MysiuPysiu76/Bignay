package com.mysiupysiu.bignay.utils.world;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysiupysiu.bignay.utils.CreatedWorldDate;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

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
            File root = this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile();
            File file = new File(new File(root, "advancements"), Minecraft.getInstance().getUser().getUuid() + ".json");

            int sum = 0;
            for (Map.Entry<String, JsonElement> entry : new Gson().fromJson(new FileReader(file), JsonObject.class).getAsJsonObject().entrySet()) {
                if  (!(entry.getKey().contains("DataVersion") || entry.getKey().contains("recipes")))
                    if (entry.getValue().getAsJsonObject().getAsJsonPrimitive("done").getAsBoolean())
                        sum++;
            }

            return sum;
        } catch (Exception e) {
            return null;
        }
    }

    public Long getPlayerLastPlayed() {
        try {
            return levelAccess.getSummary().getLastPlayed();
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
            return this.levelAccess.getSummary().getSettings().difficulty().getDisplayName().getString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getWorldGameMode() {
        try {
            return levelAccess.getSummary().getGameMode().getLongDisplayName().getString();
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
            return FileUtils.humanReadableByteCount(this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile().toPath());
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public Long getWorldDay() {
        try {
            return this.levelData.getCompound("Data").getLong("DayTime") / 24000;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean getWorldCheatsEnabled() {
        try {
            return this.levelData.getCompound("Data").getBoolean("allowCommands");

        } catch (Exception e) {
            return null;
        }
    }

    public UUID getWorldUUID() {
        try {
            Path dataDir = this.levelAccess.getLevelPath(LevelResource.ROOT).resolve("data");
            Files.createDirectories(dataDir);

            Path file = dataDir.resolve("uuid.dat");

            if (Files.exists(file)) {
                CompoundTag tag = NbtIo.readCompressed(file.toFile());
                if (tag.hasUUID("UUID")) {
                    return tag.getUUID("UUID");
                }
            }

            createWorldUUID(file.toFile());
            return getWorldUUID();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read/create world UUID", e);
        }
    }

    private void createWorldUUID(File file) {
        try {
            UUID uuid = UUID.randomUUID();
            CompoundTag tag = new CompoundTag();
            tag.putUUID("UUID", uuid);
            NbtIo.writeCompressed(tag, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLevelDat() {
        Path levelDatPath = this.levelAccess.getLevelPath(LevelResource.LEVEL_DATA_FILE);

        if (levelDatPath.toString().endsWith(".zip")) {
            try (ZipFile zip = new ZipFile(levelDatPath.toFile()); InputStream is = zip.getInputStream(zip.getEntry("level.dat"))) {
                this.levelData = NbtIo.readCompressed(is);
            } catch (Exception ignored) {}
        } else {
            try {
                File levelDatFile = levelDatPath.toFile();
                this.levelData = NbtIo.readCompressed(levelDatFile);
            } catch (IOException ignored) {}
        }
    }

    private void loadStats() {
        try {
            File root = this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile();
            File stats = new File(new File(root, "stats"), Minecraft.getInstance().getUser().getUuid() + ".json");

            this.stats = new Gson().fromJson(new FileReader(stats), JsonObject.class).getAsJsonObject("stats");
        } catch (Exception ignored) {}
    }

    private Long getElementsCount(JsonObject object) {
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
            return this.stats.getAsJsonObject("minecraft:custom").getAsJsonPrimitive("minecraft:" + key + "_one_cm").getAsLong();
        } catch (NullPointerException ex) {
            return 0L;
        }
    }

    public static String humanReadablePlayTime(long seconds) {
        if (seconds < 0) return "-";

        long[] units = {1, 60, 3600, 86400, 31536000};
        String[] symbols = {"s", "m", "h", "d", "y"};

        int exp = 0;

        for (int i = units.length - 1; i >= 0; i--) {
            if (seconds >= units[i]) {
                exp = i;
                break;
            }
        }

        return String.format("%.2f%s", (double) seconds / units[exp], symbols[exp]);
    }
}
