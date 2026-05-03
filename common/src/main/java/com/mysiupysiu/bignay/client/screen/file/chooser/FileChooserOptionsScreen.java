package com.mysiupysiu.bignay.client.screen.file.chooser;

import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

class FileChooserOptionsScreen extends Screen {

    private boolean showHidden = BignayConfig.files.showHidden.get();
    private int columns = BignayConfig.files.columns.get();

    private final Screen parent;
    private Button showHiddenButton;
    private Button columnsButton;

    public FileChooserOptionsScreen(Screen parent) {
        super(Component.translatable("fileChooser.options.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        showHidden = BignayConfig.files.showHidden.get();
        columns = BignayConfig.files.columns.get();
        int centerX = this.width / 2;
        int btnW = 160;
        int btnH = 21;

        this.showHiddenButton = Button.builder(getHiddenFilesButtonLabel(), btn -> updateHidden())
                .bounds(centerX - 80, this.height / 2 - 60, btnW, btnH).build();
        this.addRenderableWidget(this.showHiddenButton);

        this.columnsButton = Button.builder(Component.translatable("fileChooser.options.columns", columns), btn -> incrementColumns())
                .bounds(centerX - 80, this.height / 2 - 30, btnW, btnH).build();
        this.addRenderableWidget(this.columnsButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.options.reset"), btn -> resetSettings())
                .bounds(centerX - 110, this.height / 2 + 80, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"), btn -> onClose())
                .bounds(centerX + 10, this.height / 2 + 80, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 25, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        BignayConfig.files.columns.set(this.columns);
        BignayConfig.files.showHidden.set(this.showHidden);
        BignayConfig.save();
        Minecraft.getInstance().setScreen(this.parent);
    }

    private void updateHidden() {
        this.showHidden = !this.showHidden;
        updateHiddenButton();
    }

    private void incrementColumns() {
        this.columns++;
        if (this.columns > 7) this.columns = 4;
        updateColumns();
    }

    private void updateHiddenButton() {
        this.showHiddenButton.setMessage(getHiddenFilesButtonLabel());
    }

    private void updateColumns() {
        this.columnsButton.setMessage(Component.translatable("fileChooser.options.columns", this.columns));
    }

    private Component getHiddenFilesButtonLabel() {
        return Component.translatable("fileChooser.options.hidden_files_" + (this.showHidden ? "on" : "off"));
    }

    private void resetSettings() {
        this.showHidden = BignayConfig.files.showHidden.reset();
        this.columns = BignayConfig.files.columns.reset();

        updateHiddenButton();
        updateColumns();
    }
}
