package com.mysiupysiu.bignay.screen.file;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class InvalidFileScreen extends Screen {

    private final Screen parent;

    public InvalidFileScreen(Screen parent) {
        super(Component.translatable("fileChooser.invalid_file"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        int buttonWidth = 150;
        int buttonHeight = 20;
        int spacing = 10;

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.choose_another.file"), b ->
                Minecraft.getInstance().setScreen(parent)
        ).bounds(centerX - buttonWidth - spacing / 2, centerY + 30, buttonWidth, buttonHeight).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.back_to_menu"), b ->
                Minecraft.getInstance().setScreen(null)
        ).bounds(centerX + spacing / 2, centerY + 30, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        this.renderBackground(gui);

        gui.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 40, 0xFF5555);

        Component msg = Component.translatable("importWorld.error_file");
        gui.drawCenteredString(this.font, msg, this.width / 2, this.height / 2 - 15, 0xFFFFFF);

        super.render(gui, mouseX, mouseY, delta);
    }
}
