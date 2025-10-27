package com.mysiupysiu.bignay.util;

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

public class ImportWorldScreen extends Screen {

    private final File source;
    private Button importButton;
    private EditBox nameInput;

    private String levelName = "World name";

    public ImportWorldScreen(File source) {
        super(Component.translatable("importWorld.title"));
        this.source = source;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 220;

        this.nameInput = new EditBox(this.font, centerX - 100, 100, 200, 20, Component.literal(levelName));
        this.nameInput.setValue(levelName);
        this.addRenderableWidget(nameInput);

        this.importButton = Button.builder(Component.translatable("importWorld.import"), b -> importWorld()).bounds(centerX - 110, y, 100, 20).build();
        this.addRenderableWidget(importButton);

        Button cancelButton = Button.builder(Component.translatable("gui.cancel"), b -> Minecraft.getInstance().setScreen(new SelectWorldScreen(this))).bounds(centerX + 10, y, 100, 20).build();
        this.addRenderableWidget(cancelButton);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        this.renderBackground(gui);

        gui.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        gui.drawCenteredString(this.font, Component.literal("File: " + source.getName()), this.width / 2, 60, 0xAAAAAA);

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
