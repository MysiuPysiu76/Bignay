package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.screen.file.chooser.FolderChooserScreen;
import com.mysiupysiu.bignay.utils.WorldExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class WorldExportScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private final Screen previousScreen;
    private File destinationFile;
    private final File sourceWorld;
    private final long worldSizeBytes;
    private boolean exportPlayerData = true;
    private boolean finished = false;
    private boolean cancelled = false;

    private EditBox nameField;
    private Button selectDestButton;
    private Button exportButton;
    private String worldName;

    public WorldExportScreen(Screen previousScreen, LevelStorageSource.LevelStorageAccess levelAccess) {
        super(Component.translatable("selectWorld.edit.export"));
        this.levelAccess = levelAccess;
        this.previousScreen = previousScreen;
        try {
            this.sourceWorld = new File(levelAccess.getWorldDir().toFile().getCanonicalPath(), levelAccess.getLevelId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.worldSizeBytes = computeFolderSize(sourceWorld.toPath());
        this.worldName = levelAccess.getSummary().getLevelName();
    }

    @Override
    protected void init() {
        super.init();

        final int centerX = this.width / 2;

        int y = 60;
        int rowGap = 28;
        int inputWidth = 260;
        int inputX = centerX - inputWidth / 2;

        int nameFieldY = y + rowGap + 12;
        nameField = new EditBox(this.font, inputX, nameFieldY, inputWidth, 18, Component.translatable("selectWorld.enterName"));
        nameField.setValue(worldName);
        nameField.setMaxLength(128);
        this.addRenderableWidget(nameField);

        Button exportPlayerDataButton = Button.builder(getExportPlayerDataLabel(), b -> {
            exportPlayerData = !exportPlayerData;
            b.setMessage(getExportPlayerDataLabel());
        }).bounds(inputX, nameFieldY + rowGap + 50, inputWidth, 20).build();
        this.addRenderableWidget(exportPlayerDataButton);

        selectDestButton = Button.builder(Component.translatable("exportWorld.choose"), b -> {
            FolderChooserScreen folderChooser = new FolderChooserScreen();
            folderChooser.setOnConfirm(this::setDestinationFile);
            folderChooser.setPreviousScreen(this);
            worldName = nameField.getValue();
            Minecraft.getInstance().setScreen(folderChooser);
        }).bounds(inputX + inputWidth - 98, nameFieldY + rowGap + 20, 98, 20).build();
        this.addRenderableWidget(selectDestButton);

        int btnY = this.height - 40;
        Button cancelButton = Button.builder(Component.translatable("gui.cancel"), b -> {
            Minecraft.getInstance().setScreen(previousScreen);
        }).bounds(centerX + 10, btnY, 120, 20).build();
        this.addRenderableWidget(cancelButton);

        exportButton = Button.builder(Component.translatable("exportWorld.export"), b -> {
            WorldExporter exporter = new WorldExporter(levelAccess, this.sourceWorld);
            exporter.setDestination(this.destinationFile);
            exporter.setExportPlayerData(this.exportPlayerData);
            exporter.setWorldName(this.worldName);

            Minecraft.getInstance().setScreen(new WorldExportProgressScreen(exporter));
        }).bounds(centerX - 130, btnY, 120, 20).build();

        exportButton.active = (destinationFile != null);
        this.addRenderableWidget(exportButton);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        final int centerX = this.width / 2;
        int y = 60;
        int rowGap = 28;
        int inputWidth = 260;
        int inputX = centerX - inputWidth / 2;

        String sizeText = (worldSizeBytes >= 0) ? humanReadableByteCount(worldSizeBytes) : "-";
        graphics.drawString(this.font, Component.translatable("exportWorld.size", sizeText), inputX, y, 0xFFFFFF, false);

        int nameFieldY = y + rowGap + 12;
        graphics.drawString(this.font, Component.translatable("selectWorld.enterName"), inputX, nameFieldY - 12, 0xAAAAAA, false);

        String label = Component.translatable("exportWorld.chosen-destination").getString();
        graphics.drawString(this.font, label, inputX, nameFieldY + rowGap + 6, 0xFFFFFF, false);

        String destinationInfo = (destinationFile != null) ? destinationFile.getAbsolutePath() : Component.translatable("exportWorld.chosen-destination.none").getString();

        int pathX = inputX + this.font.width(label);
        int inputRight = inputX + inputWidth;
        int buttonX = selectDestButton.getX();
        int maxWidth = Math.max(buttonX - pathX - 8, inputRight - pathX);

        String shortDest = destinationInfo;
        while (this.font.width(shortDest) > maxWidth && shortDest.length() > 5) {
            shortDest = "..." + shortDest.substring(4);
        }

        graphics.drawString(this.font, shortDest, pathX, nameFieldY + rowGap + 6, 0xFFFFFF, false);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private long computeFolderSize(Path p) {
        if (p == null) return -1;
        try (Stream<Path> walk = Files.walk(p)) {
            return walk.filter(Files::isRegularFile).mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    return 0L;
                }
            }).sum();
        } catch (IOException e) {
            return -1;
        }
    }

    public static String humanReadableByteCount(long bytes) {
        if (bytes < 0) return "-";
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGT".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    public void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
        Minecraft.getInstance().setScreen(this);
        if (this.exportButton != null) this.exportButton.active = (destinationFile != null);
    }

    private Component getExportPlayerDataLabel() {
        return exportPlayerData ? Component.translatable("exportWorld.exportPlayerData.on") : Component.translatable("exportWorld.exportPlayerData.off");
    }
}
