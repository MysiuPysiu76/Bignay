package com.mysiupysiu.bignay.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WorldImporter {

    private final File source;
    private Path target;
    private String worldName;

    public WorldImporter(File source) {
        this.source = source;
    }

    public static boolean isValidWorld(File source) {
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

    public void execute() {
        if (this.worldName == null || this.worldName.isEmpty()) return;

        this.target = Paths.get(System.getProperty("user.dir"), "saves", this.worldName);

        try (ZipFile zip = new ZipFile(this.source)) {
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

                Path outPath = this.target.resolve(name).normalize();

                if (entry.isDirectory()) {
                    Files.createDirectories(outPath);
                } else {
                    Files.createDirectories(outPath.getParent());
                    try (InputStream is = zip.getInputStream(entry)) {
                        Files.copy(is, outPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            updateWorldName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.getInstance().setScreen(new SelectWorldScreen(null));
    }

    public void updateWorldName() throws IOException {
        Path levelDatPath = this.target.resolve("level.dat");

        CompoundTag levelData = NbtIo.readCompressed(Files.newInputStream(levelDatPath));
        CompoundTag data = levelData.getCompound("Data");
        data.putString("LevelName", this.worldName);

        NbtIo.writeCompressed(levelData, Files.newOutputStream(levelDatPath, StandardOpenOption.WRITE));
    }

    public void setWorldName(String worldName) {
        if (worldName == null || worldName.isBlank()) return;
        this.worldName = worldName;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public File getSource() {
        return source;
    }
}
