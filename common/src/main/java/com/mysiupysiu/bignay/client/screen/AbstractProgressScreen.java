package com.mysiupysiu.bignay.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mysiupysiu.bignay.utils.ProgressListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractProgressScreen extends Screen implements ProgressListener {

    private volatile double progress = 0.0;
    private boolean finished = false;
    private Screen afterFinishScreen;

    protected AbstractProgressScreen(Component component) {
        super(component);
    }

    protected abstract void onAction();

    @Override
    protected void init() {
        super.init();
        new Thread(this::onAction, "Operation Thread").start();

        int centerX = this.width / 2;
        int btnWidth = 100;
        int btnY = this.height - 60;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> {
            onCancel();
            Minecraft.getInstance().setScreen(null);
        }).bounds(centerX - btnWidth / 2, btnY, btnWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 100);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        graphics.drawCenteredString(this.font, this.title, centerX, centerY - 110, 0xFFFFFFFF);

        int barWidth = 222;
        int barHeight = 4;
        float clampedProgress = (float) Math.max(0.0, Math.min(1.0, progress));
        int filled = (int) (barWidth * clampedProgress);

        graphics.fill(centerX - barWidth / 2, centerY - 30, centerX + barWidth / 2, centerY + barHeight - 30, 0xFF222222);

        if (filled > 0) {
            graphics.fill(centerX - barWidth / 2, centerY - 30, centerX - barWidth / 2 + filled, centerY + barHeight - 30, 0xFF00AA00);
        }

        String percentText = String.format("%.0f%%", clampedProgress * 100);
        graphics.drawCenteredString(this.font, Component.translatable("progressScreen.complete", percentText), centerX, centerY + 24, 0xFFFFFFFF);
        graphics.pose().popPose();

        if (finished && this.afterFinishScreen != null) {
            Minecraft.getInstance().setScreen(this.afterFinishScreen);
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void onProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public void onFinish() {
        finish();
    }

    public final void finish() {
        this.finished = true;

        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(this.afterFinishScreen));
    }

    public void setAfterFinishScreen(Screen afterFinishScreen) {
        this.afterFinishScreen = afterFinishScreen;
    }
}
