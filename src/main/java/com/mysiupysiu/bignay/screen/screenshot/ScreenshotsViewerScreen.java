package com.mysiupysiu.bignay.screen.screenshot;

import com.mysiupysiu.bignay.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.screen.file.chooser.FolderChooserScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsExporter;
import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import com.mysiupysiu.bignay.utils.screenshot.WorldSelectorData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;
import java.util.*;

public class ScreenshotsViewerScreen extends Screen {

    private static boolean toOldest = true;
    private ScreenshotsGrid grid;
    private Button openButton, renameButton, exportButton, deleteButton;
    private static int columns = 4;

    private String currentWorld = "all";
    private CycleButton<String> worldSelector;

    public ScreenshotsViewerScreen() {
        super(Component.translatable("screenshotsViewer.title"));
    }

    @Override
    protected void init() {
        super.init();

        int gap = 8;
        int gridWidth = this.width - 50;
        int y = this.height - 24;
        int x = this.width / 2;

        int thumbW = (gridWidth - (columns - 1) * gap) / columns;
        int thumbH = (int) (thumbW * (9.0f / 16.0f));
        int textHeight = ScreenshotsGrid.isShowScreenName() ? this.minecraft.font.lineHeight + 4 : 0;
        int itemHeight = thumbH + textHeight + gap;

        this.grid = new ScreenshotsGrid(this.minecraft, this.width, this.height, 32, this.height - 28, itemHeight, thumbW, thumbH, textHeight, gap, columns, this);
        this.addRenderableWidget(grid);

        this.addRenderableWidget(Button.builder(Component.translatable("options.title"), btn ->
                this.minecraft.setScreen(new ScreenshotsOptionsScreen())).bounds(this.width / 2 + 101, 6, 110, 20).build());

        WorldSelectorData selectorData = WorldSelectorData.prepare();
        if (!selectorData.values().contains(this.currentWorld)) this.currentWorld = "all";

        this.worldSelector = CycleButton.builder(selectorData::getLabel)
                .withValues(selectorData.values())
                .withInitialValue(this.currentWorld)
                .displayOnlyValue()
                .create(x - 211, 6, 110, 20, Component.empty(), (button, newValue) -> {
                    this.currentWorld = newValue;
                    if (selectorData.values().size() > 1) this.refreshScreenshots();
                });

        this.addRenderableWidget(this.worldSelector);

        this.refreshScreenshots();

        this.openButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.open"), btn ->
                openSelected()).bounds(x - 192, y, 72, 20).build());

        this.renameButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.rename"), btn ->
                renameSelected()).bounds(x - 114, y, 72, 20).build());

        this.exportButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.export"), btn ->
                exportSelected()).bounds(x - 36, y, 72, 20).build());

        this.deleteButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.delete"), btn ->
                deleteSelected()).bounds(x + 42, y, 72, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, btn ->
                onClose()).bounds(x + 120, y, 72, 20).build());

        this.updateButtons();
    }

    public void refreshScreenshots() {
        List<Path> paths;
        if ("all".equals(this.currentWorld)) {
            paths = ScreenshotsManager.getAll();
        } else if (this.currentWorld.startsWith("single:")) {
            String uuidStr = this.currentWorld.substring("single:".length());
            try {
                UUID uid = UUID.fromString(uuidStr);
                paths = ScreenshotsManager.getPathsForSingleplayer(uid);
            } catch (IllegalArgumentException ex) {
                paths = ScreenshotsManager.getAll();
            }
        } else if (this.currentWorld.startsWith("multi:")) {
            String ip = this.currentWorld.substring("multi:".length());
            paths = ScreenshotsManager.getPathsForMultiplayer(ip);
        } else {
            paths = ScreenshotsManager.getAll();
        }

        grid.refresh(paths);

        try {
            grid.setScrollAmount(0);
        } catch (Throwable ignored) {}

        updateButtons();
    }

    private void exportSelected() {
        FolderChooserScreen folderChooserScreen = new FolderChooserScreen();
        folderChooserScreen.setPreviousScreen(this);
        folderChooserScreen.setOnConfirm(f -> this.minecraft.setScreen(new OperationWithProgressScreen("screenshotsViewer.export.progress", new ScreenshotsExporter(grid.getSelectedPaths(), f.toPath()))));
        this.minecraft.setScreen(folderChooserScreen);
    }

    public void deleteSelected() {
        List<Path> files = grid.getSelectedPaths().toList();
        ScreenshotsManager.tryDelete(files.stream().map(p -> p.getFileName().toString()).toList());
        files.forEach(FileUtils::delete);
        refreshScreenshots();
        updateButtons();
    }

    public void updateButtons() {
        int selCount = grid.getSelectedCount();
        if (openButton != null) openButton.active = (selCount == 1);
        if (renameButton != null) renameButton.active = (selCount == 1);
        if (exportButton != null) exportButton.active = (selCount > 0);
        if (deleteButton != null) deleteButton.active = (selCount > 0);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        super.render(gui, mouseX, mouseY, partialTick);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 12, 0xFFFFFF);
    }

    private void renameSelected() {
        grid.getSelectedPaths().findFirst().ifPresent(p -> {
            this.minecraft.setScreen(new ScreenshotRenameScreen(p));
        });
    }

    private void openSelected() {
        grid.getSelectedPaths().findFirst().ifPresent(p -> openScreenshot(grid.getAllPaths().indexOf(p)));
    }

    public void openScreenshot(int idx) {
        if (idx >= 0 && idx < grid.getAllPaths().size()) {
            this.minecraft.setScreen(new ScreenshotViewScreen(grid.getAllPaths(), idx, this));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Screen.hasControlDown() && keyCode == GLFW.GLFW_KEY_A) {
            grid.selectAll();
            updateButtons();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_DELETE && grid.getSelectedCount() > 0) {
            deleteSelected();
            return true;
        }
        if ((keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) && grid.getSelectedCount() == 1) {
            openSelected();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_F5) {
            this.refreshScreenshots();
            return true;
        }
        if (grid.keyPressed(keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean clicked = super.mouseClicked(mouseX, mouseY, button);
        if (!clicked && button == 0) {
            grid.clearSelection();
            return true;
        }
        return clicked;
    }

    @Override
    public void removed() {
        grid.cleanup();
        super.removed();
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int columns) {
        ScreenshotsViewerScreen.columns = columns;
    }

    public static boolean isToOldest() {
        return toOldest;
    }

    public static void setToOldest(boolean toOldest) {
        ScreenshotsViewerScreen.toOldest = toOldest;
    }
}
