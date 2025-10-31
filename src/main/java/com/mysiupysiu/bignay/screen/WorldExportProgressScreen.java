package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.ProgressListener;
import com.mysiupysiu.bignay.utils.WorldExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WorldExportProgressScreen extends Screen implements ProgressListener {

    private final WorldExporter exporter;
    private final WorldExportScreen parent;
    private double progress = 0.0;
    private boolean finished = false;
    private boolean cancelled = false;

    public WorldExportProgressScreen(WorldExportScreen parent, WorldExporter exporter) {
        super(Component.translatable("exportWorld.progress.title"));
        this.parent = parent;
        this.exporter = exporter;
        this.exporter.setProgressListener(this);
    }

    @Override
    protected void init() {
        super.init();
        new Thread(() -> {
            try {
                exporter.execute();
                finished = true;
            } catch (Exception e) {
                e.printStackTrace();
                finished = true;
            }
        }, "World Export Thread").start();

        int centerX = this.width / 2;
        int btnWidth = 100;
        int btnY = this.height - 60;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> {
            cancelled = true;
            exporter.cancel();
            Minecraft.getInstance().setScreen(null);
        }).bounds(centerX - btnWidth / 2, btnY, btnWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.drawCenteredString(this.font, this.title, centerX, centerY - 110, 0xFFFFFF);

        int barWidth = 222;
        int barHeight = 4;
        int filled = (int) (barWidth * progress);

        graphics.fill(centerX - barWidth / 2, centerY - 30, centerX + barWidth / 2, centerY + barHeight - 30, 0x88000000);
        graphics.fill(centerX - barWidth / 2, centerY - 30, centerX - barWidth / 2 + filled, centerY + barHeight - 30, 0xFF00AA00);

        String percentText = String.format("%.0f%%", progress * 100);
        graphics.drawCenteredString(this.font, percentText, centerX, centerY + 24, 0xFFFFFF);

        if (finished) {
            graphics.drawCenteredString(this.font, Component.translatable("exportWorld.done"), centerX, centerY + 70, 0x00FF00);
            parent.closeWorld();
            Minecraft.getInstance().setScreen(null);
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onProgress(double progress) {
        this.progress = progress;
    }

    public WorldExporter getExporter() {
        return exporter;
    }

    public Screen getParent() {
        return parent;
    }
}
