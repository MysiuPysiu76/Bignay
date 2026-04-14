package com.mysiupysiu.bignay.client.screen.screenshot;

import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ScreenshotsOptionsScreen extends Screen {

    private ColumnsSliderButton columnsButton;

    public ScreenshotsOptionsScreen() {
        super(Component.translatable("screenshotsViewer.options.title"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 75;

        this.addRenderableWidget(Button.builder(getSortTitle(), btn -> {
            BignayConfig.screenshots.sortToOldest.set(!BignayConfig.screenshots.sortToOldest.get());
            btn.setMessage(getSortTitle());
        }).bounds(x, y, 200, 20).build());

        this.addRenderableWidget(Button.builder(getNameTitle(), btn -> {
            BignayConfig.screenshots.showFileName.set(!BignayConfig.screenshots.showFileName.get());
            btn.setMessage(getNameTitle());
        }).bounds(x, y + 25, 200, 20).build());

        this.addRenderableWidget(Button.builder(getExtTitle(), btn -> {
            BignayConfig.screenshots.showFileExtension.set(!BignayConfig.screenshots.showFileExtension.get());
            btn.setMessage(getExtTitle());
        }).bounds(x, y + 50, 200, 20).build());

        this.columnsButton = this.addRenderableWidget(new ColumnsSliderButton(x, y + 75, 200, 20));

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose())
                .bounds(x, y + 115, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(gui, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        BignayConfig.screenshots.columns.set(columnsButton.getPosition());

        BignayConfig.save();

        if (this.minecraft != null) {
            this.minecraft.setScreen(new ScreenshotsViewerScreen());
        }
    }

    private Component getSortTitle() {
        return Component.translatable("screenshotsViewer.options.sort_" + (BignayConfig.screenshots.sortToOldest.get() ? "oldest" : "newest"));
    }

    private Component getNameTitle() {
        return Component.translatable("screenshotsViewer.options.show_screenshot_name_" + (BignayConfig.screenshots.showFileName.get() ? "yes" : "no"));
    }

    private Component getExtTitle() {
        return Component.translatable("screenshotsViewer.options.show_extension_" + (BignayConfig.screenshots.showFileExtension.get() ? "yes" : "no"));
    }
}
