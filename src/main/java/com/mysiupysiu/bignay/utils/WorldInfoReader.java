package com.mysiupysiu.bignay.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
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
        return humanReadablePlayTime(this.stats.getAsJsonObject("minecraft:custom").getAsJsonPrimitive("minecraft:play_time").getAsInt() / 20);
    }

    public long getPlayerMobsKilledCount() {
        return getElementsCount(this.stats.getAsJsonObject("minecraft:killed"));
    }

    public long getPlayerBlocksDestroyedCount() {
        return getElementsCount(this.stats.getAsJsonObject("minecraft:mined"));
    }

    public long getPlayerItemsCraftedCount() {
        return getElementsCount(this.stats.getAsJsonObject("minecraft:crafted"));
    }

    public int getPlayerDeathsCount() {
        return this.stats.getAsJsonObject("minecraft:custom").getAsJsonPrimitive("minecraft:deaths").getAsInt();
    }

    public long getPlayerDistanceTraveled() {
        return Stream.of("walk", "horse", "boat", "minecart", "aviate", "swim", "sprint")
                .map(this::getDistanceByKey)
                .mapToLong(Long::longValue).sum();
    }

    public int getPlayerFinishedAdvancementsCount() {
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
            return 0;
        }
    }

    public long getPlayerLastPlayed() {
        return levelAccess.getSummary().getLastPlayed();
    }

    public String getWorldName() {
        return this.levelData.getString("LevelName");
    }

    public String getWorldVersion() {
        return this.levelData.getCompound("Version").getString("Name");
    }

    public long getWorldSeed() {
        return this.levelData.getCompound("WorldGenSettings").getLong("seed");
    }

    public String getWorldDifficulty() {
        return this.levelAccess.getSummary().getSettings().difficulty().getDisplayName().getString();
    }

    public String getWorldGameMode() {
        return levelAccess.getSummary().getGameMode().getLongDisplayName().getString();
    }

    public boolean getWorldHardcore() {
        return this.levelData.getBoolean("hardcore");
    }

    public String getWorldSize() {
        try {
            return FileUtils.humanReadableByteCount(this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile().toPath());
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public int getWorldDay() {
        return this.levelData.getInt("DayTime") / 24000;
    }

    public boolean getWorldCheatsEnabled() {
        return this.levelData.getBoolean("allowCommands");
    }

    private void loadLevelDat() {
        Path levelDatPath = this.levelAccess.getLevelPath(LevelResource.LEVEL_DATA_FILE);

        if (levelDatPath.toString().endsWith(".zip")) {
            try (ZipFile zip = new ZipFile(levelDatPath.toFile()); InputStream is = zip.getInputStream(zip.getEntry("level.dat"))) {
                this.levelData = NbtIo.readCompressed(is).getCompound("Data");
            } catch (Exception ignored) {}
        } else {
            try {
                File levelDatFile = levelDatPath.toFile();
                this.levelData = NbtIo.readCompressed(levelDatFile).getCompound("Data");
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

    private int getElementsCount(JsonObject object) {
        int sum = 0;

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

    public LevelStorageSource.LevelStorageAccess getLevelAccess() {
        return levelAccess;
    }
}
