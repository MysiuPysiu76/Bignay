package com.mysiupysiu.bignay.client.screen.file.chooser;

import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class ColumnsSliderButton extends AbstractSliderButton {

    private static final int min = 4;
    private static final int max = 7;
    private int pos = BignayConfig.files.columns.get();

    public ColumnsSliderButton(int x, int y, int w, int h) {
        super(x, y, w, h, Component.empty(), (double)(BignayConfig.files.columns.get() - min) / (max - min));
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.pos = (int) Math.round(this.value * (max - min) + min);
        this.setMessage(Component.translatable("fileChooser.options.columns", this.pos));
    }

    @Override
    protected void applyValue() {
        this.pos = (int) Math.round(this.value * (max - min) + min);
    }

    public int getPosition() {
        return this.pos;
    }

    public void reset() {
        this.pos = BignayConfig.files.columns.reset();
    }
}
