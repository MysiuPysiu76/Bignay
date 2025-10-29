package com.mysiupysiu.bignay.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WorldImportScreen extends Screen {

    private final File source;
    private EditBox nameInput;

    private String levelName = "World name";
    private String gameMode;
    private String difficulty;
    private String version;
    private long seed;
    private long daysPlayed;

    public WorldImportScreen(File source) {
        super(Component.translatable("importWorld.title"));
        this.source = source;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 200;

        readLevelDat();

        this.nameInput = new EditBox(this.font, centerX - 100, 60, 200, 20, Component.literal(levelName));
        this.nameInput.setValue(levelName);
        this.addRenderableWidget(nameInput);

        Button importButton = Button.builder(Component.translatable("importWorld.import"), b -> importWorld()).bounds(centerX - 110, y, 100, 20).build();
        this.addRenderableWidget(importButton);

        Button cancelButton = Button.builder(Component.translatable("gui.cancel"), b -> Minecraft.getInstance().setScreen(new SelectWorldScreen(this))).bounds(centerX + 10, y, 100, 20).build();
        this.addRenderableWidget(cancelButton);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        this.renderBackground(gui);

        gui.drawCenteredString(this.font, this.title, this.width / 2, 30, 0xFFFFFF);

        int centerX = this.width / 2;
        int boxY = 100;

        gui.fill(0, boxY, this.width, boxY + 90, 0x88000000);

        int leftX = centerX - 120;
        int rightX = centerX + 20;
        int lineY = boxY + 10;
        int dy = 20;

        gui.drawCenteredString(this.font, Component.translatable("importWorld.file", source.getName()), this.width / 2, lineY, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.version", version), leftX, lineY + dy, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.gamemode", gameMode), leftX, lineY + dy * 2, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.difficulty", difficulty), leftX, lineY + dy * 3, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.seed", seed), rightX, lineY + dy, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.days", daysPlayed), rightX, lineY + dy * 2, 0xFFFFFF);
        gui.drawString(this.font, Component.translatable("importWorld.size", WorldExportScreen.humanReadableByteCount(source.length())), rightX, lineY + dy * 3, 0xFFFFFF);

        super.render(gui, mouseX, mouseY, delta);
    }

    private void importWorld() {
        String worldName = nameInput.getValue().trim();
        if (worldName.isEmpty()) return;

        Path targetDir = Paths.get(System.getProperty("user.dir"), "saves", worldName);

        try (ZipFile zip = new ZipFile(source)) {
            String topFolder = null;

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.equals("level.dat")) {
                    topFolder = "";
                    break;
                }

                int slash = name.indexOf('/');
                if (slash > 0) {
                    topFolder = name.substring(0, slash + 1);
                }
            }

            entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (topFolder != null && !topFolder.isEmpty() && name.startsWith(topFolder)) {
                    name = name.substring(topFolder.length());
                }

                if (name.isEmpty()) continue;

                Path outPath = targetDir.resolve(name).normalize();

                if (entry.isDirectory()) {
                    Files.createDirectories(outPath);
                } else {
                    Files.createDirectories(outPath.getParent());
                    try (InputStream is = zip.getInputStream(entry)) {
                        Files.copy(is, outPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }

            Path levelDatPath = targetDir.resolve("level.dat");

            CompoundTag levelData = NbtIo.readCompressed(Files.newInputStream(levelDatPath));
            CompoundTag data = levelData.getCompound("Data");

            data.putString("LevelName", worldName);

            NbtIo.writeCompressed(levelData, Files.newOutputStream(levelDatPath, StandardOpenOption.WRITE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.getInstance().setScreen(new SelectWorldScreen(this));
    }

    private void readLevelDat() {
        try (ZipFile zip = new ZipFile(source)) {
            String topFolder = null;
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().equals("level.dat")) {
                    topFolder = "";
                    break;
                }
                int slash = entry.getName().indexOf('/');
                if (slash > 0) topFolder = entry.getName().substring(0, slash + 1);
            }

            if (topFolder == null) return;

            String path = topFolder + "level.dat";
            ZipEntry levelDatEntry = zip.getEntry(path);
            if (levelDatEntry == null) return;

            try (InputStream is = zip.getInputStream(levelDatEntry)) {
                CompoundTag levelData = NbtIo.readCompressed(is);
                CompoundTag data = levelData.getCompound("Data");

                this.levelName = data.getString("LevelName");
                this.version = levelData.getCompound("Data").getCompound("Version").getString("Name");

                int gm = data.getInt("GameType");
                switch (gm) {
                    case 0 -> gameMode = "Survival";
                    case 1 -> gameMode = "Creative";
                    case 2 -> gameMode = "Adventure";
                    case 3 -> gameMode = "Spectator";
                    default -> gameMode = "Unknown";
                }

                int diff = data.getInt("Difficulty");
                switch (diff) {
                    case 0 -> difficulty = "Peaceful";
                    case 1 -> difficulty = "Easy";
                    case 2 -> difficulty = "Normal";
                    case 3 -> difficulty = "Hard";
                    default -> difficulty = "Unknown";
                }

                CompoundTag worldGen = data.getCompound("WorldGenSettings");
                if (worldGen.contains("seed")) {
                    this.seed = worldGen.getLong("seed");
                } else {
                    this.seed = 0L;
                }

                daysPlayed = data.getLong("DayTime") / 24000;
            }
        } catch (IOException e) {
            this.levelName = "Unknown";
            this.gameMode = "Unknown";
            this.difficulty = "Unknown";
            this.seed = 0;
            this.daysPlayed = 0;
            this.version = "Unknown";
        }
    }

    public boolean isValidWorld() {
        try (ZipFile zip = new ZipFile(source)) {
            Set<String> topLevelFolders = new HashSet<>();

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.equals("level.dat")) {
                    return true;
                }

                int slash = name.indexOf('/');
                if (slash > 0) {
                    String folder = name.substring(0, slash + 1);
                    topLevelFolders.add(folder);
                }
            }

            if (topLevelFolders.size() != 1) return false;

            String onlyFolder = topLevelFolders.iterator().next();
            return zip.getEntry(onlyFolder + "level.dat") != null;
        } catch (IOException e) {
            return false;
        }
    }

    public File getSource() {
        return source;
    }
}
