package com.mysiupysiu.bignay.screen.screenshot;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ScreenshotsOptionsScreen extends Screen {

    protected ScreenshotsOptionsScreen() {
        super(Component.translatable("screenshotsViewer.options.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(Button.builder(getShowExtensionButtonTitle(), btn -> {
            ScreenshotsGrid.setShowFileExtension(!ScreenshotsGrid.isShowFileExtension());
            btn.setMessage(getShowExtensionButtonTitle());
        }).bounds(centerX - 100, centerY - 50, 200, 20).build());

        this.addRenderableWidget(Button.builder(getSortButtonTitle(), btn -> {
            ScreenshotsViewerScreen.setToOldest(!ScreenshotsViewerScreen.isToOldest());
            btn.setMessage(getSortButtonTitle());
        }).bounds(centerX - 100, centerY - 25, 200, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(centerX - 100, centerY + 20, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(gui, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(new ScreenshotsViewerScreen());
    }

    private Component getSortButtonTitle() {
        return Component.translatable("screenshotsViewer.options.sort_" + (ScreenshotsViewerScreen.isToOldest() ? "oldest" : "newest"));
    }

    private Component getShowExtensionButtonTitle() {
        return  Component.translatable("screenshotsViewer.options.show_extension_" + (ScreenshotsGrid.isShowFileExtension() ? "yes" : "no"));
    }
}
