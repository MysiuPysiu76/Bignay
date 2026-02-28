package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.screen.file.chooser.FolderChooserScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.ScreenshotsExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScreenshotsViewerScreen extends Screen {

    private static final Path SCREENSHOTS_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots");
    private ScreenshotsGrid grid;
    private Button openButton, exportButton, deleteButton;

    public ScreenshotsViewerScreen() {
        super(Component.translatable("screenshotsViewer.title"));
    }

    @Override
    protected void init() {
        super.init();

        int columns = 4;
        int gap = 8;
        int gridWidth = this.width - 50;
        int thumbW = (gridWidth - (columns - 1) * gap) / columns;
        int thumbH = (int) (thumbW * (9.0f / 16.0f));
        int itemHeight = thumbH + 20;

        this.grid = new ScreenshotsGrid(this.minecraft, this.width, this.height, 32, this.height - 40, itemHeight, this);
        this.addRenderableWidget(grid);

        refreshScreenshots();

        int y = this.height - 30;
        this.openButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.open"),
                btn -> openSelected()).bounds(this.width / 2 - 154, y, 72, 20).build());

        this.exportButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.export"),
                btn -> exportSelected()).bounds(this.width / 2 - 76, y, 72, 20).build());

        this.deleteButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.delete"),
                btn -> deleteSelected()).bounds(this.width / 2 + 4, y, 72, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, btn -> onClose())
                .bounds(this.width / 2 + 82, y, 72, 20).build());

        updateButtons();
    }

    public void refreshScreenshots() {
        try {
            if (!Files.exists(SCREENSHOTS_DIR)) return;
            List<Path> paths = Files.list(SCREENSHOTS_DIR)
                    .filter(p -> p.getFileName().toString().matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}\\.\\d{2}\\.\\d{2}(_\\d+)?\\.png"))
                    .sorted(Comparator.comparingLong((Path p) -> {
                        try {
                            return Files.getLastModifiedTime(p).toMillis();
                        } catch (IOException e) {
                            return 0L;
                        }
                    }).reversed())
                    .collect(Collectors.toList());

            grid.refresh(paths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportSelected() {
        FolderChooserScreen folderChooserScreen = new FolderChooserScreen();
        folderChooserScreen.setPreviousScreen(this);
        folderChooserScreen.setOnConfirm(f -> this.minecraft.setScreen(
                new OperationWithProgressScreen("screenshotsViewer.export.progress",
                        new ScreenshotsExporter(grid.getSelectedPaths(), f.toPath()))));
        this.minecraft.setScreen(folderChooserScreen);
    }

    public void deleteSelected() {
        grid.getSelectedPaths().forEach(FileUtils::delete);
        refreshScreenshots();
        updateButtons();
    }

    public void updateButtons() {
        int selCount = grid.getSelectedCount();
        if (openButton != null) openButton.active = (selCount == 1);
        if (exportButton != null) exportButton.active = (selCount > 0);
        if (deleteButton != null) deleteButton.active = (selCount > 0);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 12, 0xFFFFFF);
    }

    public void openSelected() {
        Path p = grid.getSelectedPaths().findFirst().orElse(null);
        if (p != null) {
            int idx = grid.getAllPaths().indexOf(p);
            openScreenshot(idx);
        }
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

        if (grid.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }

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
}
