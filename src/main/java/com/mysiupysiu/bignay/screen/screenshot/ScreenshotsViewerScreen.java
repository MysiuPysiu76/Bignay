package com.mysiupysiu.bignay.screen.screenshot;

import com.mysiupysiu.bignay.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.screen.file.chooser.FolderChooserScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.ScreenshotsExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreenshotsViewerScreen extends Screen {

    private static final Path SCREENSHOTS_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots");
    private static boolean toOldest = true;
    private ScreenshotsGrid grid;
    private Button openButton, renameButton, exportButton, deleteButton;

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

        int y = this.height - 30;
        int x = this.width / 2;

        this.grid = new ScreenshotsGrid(this.minecraft, this.width, this.height, 32, this.height - 40, itemHeight, this);
        this.addRenderableWidget(grid);

        this.addRenderableWidget(Button.builder(Component.translatable("options.title"), btn ->
                this.minecraft.setScreen(new ScreenshotsOptionsScreen()))
                .bounds(this.width / 2 + 100, 6, 100, 20).build());

        refreshScreenshots();

        this.openButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.open"),
                btn -> openSelected()).bounds(x - 192, y, 72, 20).build());

        this.renameButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.rename"),
                btn -> renameSelected()).bounds(x - 114, y, 72, 20).build());

        this.exportButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.export"),
                btn -> exportSelected()).bounds(x - 36, y, 72, 20).build());

        this.deleteButton = addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.delete"),
                btn -> deleteSelected()).bounds(x + 42, y, 72, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, btn -> onClose())
                .bounds(x + 120, y, 72, 20).build());

        this.updateButtons();
    }

    public void refreshScreenshots() {
        try (Stream<Path> stream = Files.list(SCREENSHOTS_DIR)) {
            Comparator<Path> pathComparator = getPathComparator();

            List<Path> paths = stream
                    .filter(p -> p.getFileName().toString().endsWith(".png"))
                    .sorted(pathComparator)
                    .collect(Collectors.toList());

            grid.refresh(paths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static @NotNull Comparator<Path> getPathComparator() {
        Comparator<Path> pathComparator = Comparator.comparingLong(p -> {
            try {
                return Files.getLastModifiedTime(p).toMillis();
            } catch (IOException e) {
                return 0L;
            }
        });

        if (toOldest) pathComparator = pathComparator.reversed();
        return pathComparator;
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
        grid.getSelectedPaths().findFirst().ifPresent(p -> this.minecraft.setScreen(new ScreenshotRenameScreen(p)));
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

    public static boolean isToOldest() {
        return toOldest;
    }

    public static void setToOldest(boolean toOldest) {
        ScreenshotsViewerScreen.toOldest = toOldest;
    }
}
