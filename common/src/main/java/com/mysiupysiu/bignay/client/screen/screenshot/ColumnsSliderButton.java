package com.mysiupysiu.bignay.client.screen.screenshot;

import com.mysiupysiu.bignay.config.BignayConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class ColumnsSliderButton extends AbstractSliderButton {

    private static final int min = 1;
    private static final int max = 8;
    private int pos = BignayConfig.screenshots.columns.get();

    public ColumnsSliderButton(int i, int j, int k, int l) {
        super(i, j, k, l, Component.empty(), (double)(BignayConfig.screenshots.columns.get() - min) / (max - min));
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.pos = (int) Math.round(this.value * (max - min) + min);
        this.setMessage(Component.translatable("screenshotsViewer.options.columns", this.pos));
    }

    @Override
    protected void applyValue() {
        this.pos = (int) Math.round(this.value * (max - min) + min);
    }

    public int getPosition() {
        return this.pos;
    }
}
