package com.mysiupysiu.bignay.client.screen.world;

import com.mysiupysiu.bignay.client.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.client.screen.file.chooser.FolderChooserScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.world.WorldExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.nio.file.Path;

public class WorldExportScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private final Screen previousScreen;
    private File destinationFile;
    private final File sourceWorld;
    private final long worldSizeBytes;
    private boolean exportPlayerData = true;

    private EditBox nameField;
    private Button selectDestButton;
    private Button exportButton;
    private String worldName;

    public WorldExportScreen(Screen previousScreen, LevelStorageSource.LevelStorageAccess levelAccess) {
        super(Component.translatable("selectWorld.edit.export"));
        this.levelAccess = levelAccess;
        this.previousScreen = previousScreen;
        this.sourceWorld = Path.of(System.getProperty("user.dir")).resolve("saves").resolve(levelAccess.getLevelId()).toFile();
        this.worldSizeBytes = FileUtils.computeFolderSize(sourceWorld.toPath());
        this.worldName = levelAccess.getLevelId();
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
        this.nameField = new EditBox(this.font, inputX, nameFieldY, inputWidth, 18, Component.translatable("selectWorld.enterName"));
        this.nameField.setValue(this.worldName);
        this.nameField.setMaxLength(128);
        this.addRenderableWidget(this.nameField);

        Button exportPlayerDataButton = Button.builder(getExportPlayerDataLabel(), b -> {
            this.exportPlayerData = !this.exportPlayerData;
            b.setMessage(getExportPlayerDataLabel());
        }).bounds(inputX, nameFieldY + rowGap + 50, inputWidth, 20).build();
        exportPlayerDataButton.setTooltip(Tooltip.create(Component.translatable("exportWorld.exportPlayerData.tooltip")));
        this.addRenderableWidget(exportPlayerDataButton);

        this.selectDestButton = Button.builder(Component.translatable("exportWorld.choose"), b -> {
            FolderChooserScreen folderChooser = new FolderChooserScreen();
            folderChooser.setOnConfirm(this::setDestinationFile);
            folderChooser.setPreviousScreen(this);
            this.worldName = this.nameField.getValue();
            Minecraft.getInstance().setScreen(folderChooser);
        }).bounds(inputX + inputWidth - 98, nameFieldY + rowGap + 20, 98, 20).build();
        this.addRenderableWidget(this.selectDestButton);

        int btnY = this.height - 40;
        Button cancelButton = Button.builder(Component.translatable("gui.cancel"), b -> {
            Minecraft.getInstance().setScreen(this.previousScreen);
        }).bounds(centerX + 10, btnY, 120, 20).build();
        this.addRenderableWidget(cancelButton);

        this.exportButton = Button.builder(Component.translatable("exportWorld.export"), b -> {
            WorldExporter exporter = new WorldExporter(levelAccess, this.sourceWorld);
            exporter.setDestination(this.destinationFile);
            exporter.setExportPlayerData(this.exportPlayerData);
            exporter.setWorldName(this.worldName);

            Minecraft.getInstance().setScreen(new OperationWithProgressScreen("exportWorld.progress.title", exporter));
        }).bounds(centerX - 130, btnY, 120, 20).build();

        this.exportButton.active = (this.destinationFile != null);
        this.addRenderableWidget(this.exportButton);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);

        com.mojang.blaze3d.systems.RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 100);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFFFF);

        final int centerX = this.width / 2;
        int y = 60;
        int rowGap = 28;
        int inputWidth = 260;
        int inputX = centerX - inputWidth / 2;

        String sizeText = (this.worldSizeBytes >= 0) ? FileUtils.humanReadableByteCount(this.worldSizeBytes) : "-";
        graphics.drawString(this.font, Component.translatable("exportWorld.size", sizeText), inputX, y, 0xFFFFFFFF, false);

        int nameFieldY = y + rowGap + 12;
        graphics.drawString(this.font, Component.translatable("selectWorld.enterName"), inputX, nameFieldY - 12, 0xFFAAAAAA, false);

        Component labelComponent = Component.translatable("exportWorld.chosen-destination");
        graphics.drawString(this.font, labelComponent, inputX, nameFieldY + rowGap + 6, 0xFFFFFFFF, false);

        String destinationInfo = (this.destinationFile != null) ? this.destinationFile.getAbsolutePath() : Component.translatable("exportWorld.chosen-destination.none").getString();
        int pathX = inputX + this.font.width(labelComponent);
        int buttonX = this.selectDestButton.getX();
        int maxWidth = Math.max(buttonX - pathX - 8, (inputX + inputWidth) - pathX);

        String shortDest = destinationInfo;
        while (this.font.width(shortDest) > maxWidth && shortDest.length() > 5) {
            shortDest = "..." + shortDest.substring(4);
        }

        graphics.drawString(this.font, shortDest, pathX, nameFieldY + rowGap + 6, 0xFFFFFFFF, false);
        graphics.pose().popPose();

        super.render(graphics, mouseX, mouseY, delta);
    }

    public void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
        Minecraft.getInstance().setScreen(this);
        if (this.exportButton != null) this.exportButton.active = (destinationFile != null);
    }

    private Component getExportPlayerDataLabel() {
        return this.exportPlayerData ? Component.translatable("exportWorld.exportPlayerData.on") : Component.translatable("exportWorld.exportPlayerData.off");
    }
}
