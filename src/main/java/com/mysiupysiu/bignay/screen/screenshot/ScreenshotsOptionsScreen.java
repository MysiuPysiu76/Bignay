package com.mysiupysiu.bignay.screen.screenshot;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ScreenshotsOptionsScreen extends Screen {

    public ScreenshotsOptionsScreen() {
        super(Component.translatable("screenshotsViewer.options.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(Button.builder(getSortButtonTitle(), btn -> {
            ScreenshotsViewerScreen.setToOldest(!ScreenshotsViewerScreen.isToOldest());
            btn.setMessage(getSortButtonTitle());
        }).bounds(centerX - 100, centerY - 75, 200, 20).build());

        this.addRenderableWidget(Button.builder(getShowScreenshotNameButtonTitle(), btn -> {
            ScreenshotsGrid.setShowScreenName(!ScreenshotsGrid.isShowScreenName());
            btn.setMessage(getShowScreenshotNameButtonTitle());
        }).bounds(centerX - 100, centerY - 50, 200, 20).build());

        this.addRenderableWidget(Button.builder(getShowExtensionButtonTitle(), btn -> {
            ScreenshotsGrid.setShowFileExtension(!ScreenshotsGrid.isShowFileExtension());
            btn.setMessage(getShowExtensionButtonTitle());
        }).bounds(centerX - 100, centerY - 25, 200, 20).build());

        int min = 2;
        int max = 8;

        double initialSliderValue = (double)(ScreenshotsViewerScreen.getColumns() - min) / (max - min);

        this.addRenderableWidget(new AbstractSliderButton(centerX - 100, centerY, 200, 20, Component.empty(), initialSliderValue) {
            {
                this.updateMessage();
            }

            @Override
            protected void updateMessage() {
                int currentVal = (int) Math.round(this.value * (max - min) + min);
                this.setMessage(Component.translatable("screenshotsViewer.options.columns", currentVal));
            }

            @Override
            protected void applyValue() {
                ScreenshotsViewerScreen.setColumns((int) Math.round(this.value * (max - min) + min));
            }
        });

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(centerX - 100, centerY + 40, 200, 20).build());
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

    private Component getShowScreenshotNameButtonTitle() {
        return  Component.translatable("screenshotsViewer.options.show_screenshot_name_" + (ScreenshotsGrid.isShowScreenName() ? "yes" : "no"));
    }

    private Component getShowExtensionButtonTitle() {
        return  Component.translatable("screenshotsViewer.options.show_extension_" + (ScreenshotsGrid.isShowFileExtension() ? "yes" : "no"));
    }
}
