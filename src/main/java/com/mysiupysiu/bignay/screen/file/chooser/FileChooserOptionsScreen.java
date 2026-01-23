package com.mysiupysiu.bignay.screen.file.chooser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

class FileChooserOptionsScreen extends Screen {

    private final Screen parent;
    private boolean showHidden = AbstractFileChooserScreen.isShowHidden();
    private int columns = AbstractFileChooserScreen.getColumns();

    private Button showHiddenButton;
    private Button columnsButton;

    public FileChooserOptionsScreen(Screen parent) {
        super(Component.translatable("fileChooser.options.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int btnW = 160;
        int btnH = 21;

        this.showHiddenButton = Button.builder(getHiddenFilesButtonLabel(), btn -> updateHiddenButton())
                .bounds(centerX - 80, this.height / 2 - 60, btnW, btnH).build();
        this.addRenderableWidget(this.showHiddenButton);

        this.columnsButton = Button.builder(Component.literal("Columns: " + columns), btn ->
                incrementColumns()
        ).bounds(centerX - 80, this.height / 2 - 30, btnW, btnH).build();
        this.addRenderableWidget(this.columnsButton);

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.options.reset"), btn ->
                resetSettings()
        ).bounds(centerX - 110, this.height / 2 + 80, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"), btn ->
                onClose()
        ).bounds(centerX + 10, this.height / 2 + 80, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 25, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        super.onClose();
        AbstractFileChooserScreen.setShowHidden(this.showHidden);
        AbstractFileChooserScreen.setColumns(this.columns);
        Minecraft.getInstance().setScreen(this.parent);
    }

    private void updateHiddenButton() {
        this.showHidden = !this.showHidden;
        this.showHiddenButton.setMessage(getHiddenFilesButtonLabel());
    }

    private void incrementColumns() {
        this.columns++;
        if (this.columns > 7) this.columns = 4;
        this.columnsButton.setMessage(Component.translatable("fileChooser.options.columns", this.columns));
    }

    private Component getHiddenFilesButtonLabel() {
        return Component.translatable("fileChooser.options.hidden_files_" + (this.showHidden ? "on" : "off"));
    }

    private void resetSettings() {
        this.showHidden = false;
        this.columns = 6;
    }
}
