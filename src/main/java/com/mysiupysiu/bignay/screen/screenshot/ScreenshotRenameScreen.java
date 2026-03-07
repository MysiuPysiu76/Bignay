package com.mysiupysiu.bignay.screen.screenshot;

import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotRenameScreen extends Screen {

    private final Path file;
    private final Screen parent;
    private EditBox nameBox;

    public ScreenshotRenameScreen(Path file) {
        this(new ScreenshotsViewerScreen(), file);
    }

    public ScreenshotRenameScreen(Screen parent, Path file) {
        super(Component.translatable("screenshotsViewer.rename.title"));
        this.file = file;
        this.parent = parent;
    }

    @Override
    protected void init() {
        int boxWidth = 200;
        int boxHeight = 20;
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        String currentName = file.getFileName().toString().replace(".png", "");

        nameBox = new EditBox(this.font, centerX - boxWidth / 2, centerY - 10, boxWidth, boxHeight, Component.literal(""));
        nameBox.setValue(currentName);
        nameBox.setMaxLength(100);
        nameBox.setFocused(true);
        this.addRenderableWidget(nameBox);

        this.addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.rename"),
                btn -> renameFile()).bounds(centerX - 100, centerY + 20, 95, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL,
                btn -> onClose()).bounds(centerX + 5, centerY + 20, 95, 20).build());
    }

    private void renameFile() {
        try {
            String newName = nameBox.getValue().trim();
            if (newName.isEmpty()) return;
            newName += ".png";

            Files.move(file, file.resolveSibling(newName));
            ScreenshotsManager.tryRename(file.getFileName().toString(), newName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.minecraft.setScreen(parent);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);
        gui.drawCenteredString(this.font, Component.translatable("screenshotsViewer.rename.title"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        super.onClose();
        this.minecraft.setScreen(parent);
    }
}
