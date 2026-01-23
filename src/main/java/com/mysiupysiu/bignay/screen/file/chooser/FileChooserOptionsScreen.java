package com.mysiupysiu.bignay.screen.file.chooser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

class FileChooserOptionsScreen extends Screen {

    private final Screen parent;
    private boolean showHidden = AbstractFileChooserScreen.isShowHidden();

    private Button showHiddenButton;

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
        this.addRenderableWidget(showHiddenButton);
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
        Minecraft.getInstance().setScreen(this.parent);
    }

    private void updateHiddenButton() {
        this.showHidden = !this.showHidden;
        this.showHiddenButton.setMessage(getHiddenFilesButtonLabel());
    }

    private Component getHiddenFilesButtonLabel() {
        return Component.translatable("fileChooser.options.hidden_files_" + (this.showHidden ? "on" : "off"));
    }
}
