package com.mysiupysiu.bignay.screen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

public class ScreenshotView extends Screen {

    private final String name;
    private final Screen parent;

    private ResourceLocation texture;
    private int imgW, imgH;
    private boolean failed = false;

    public ScreenshotView(String name, Screen parent) {
        super(Component.literal(name));
        this.name = name;
        this.parent = parent;
    }

    @Override
    protected void init() {
        loadImage();

        int y = this.height - 24;

        this.addRenderableWidget(Button.builder(Component.translatable("screenshotsViewer.delete"), btn -> delete())
                .bounds(this.width / 2 + 4, y, 72, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, btn -> back())
                .bounds(this.width / 2 + 82, y, 72, 20).build());
    }

    @Override
    public void removed() {
        if (this.texture != null) {
            Minecraft.getInstance().getTextureManager().release(this.texture);
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderBackground(gui);

        if (this.failed || this.texture == null) {
            gui.drawCenteredString(this.font, Component.translatable("screenshotsViewer.loadingError"), this.width / 2, this.height / 2, 0xFFFFFF);
            return;
        }

        int maxW = this.width - 55;
        int maxH = this.height - 55;

        double scale = Math.min((double) maxW / imgW, (double) maxH / imgH);

        int drawW = (int) (imgW * scale);
        int drawH = (int) (imgH * scale);

        int x = (this.width - drawW) / 2;
        int y = (this.height - drawH) / 2;

        RenderSystem.setShaderTexture(0, this.texture);
        gui.blit(this.texture, x, y, 0, 0, drawW, drawH, drawW, drawH);

        gui.drawCenteredString(this.font, this.name, this.width / 2, 7, 0xFFFFFF);
        super.render(gui, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        if (key == 256) { // ESC
            Minecraft.getInstance().setScreen(this.parent);
            return true;
        }
        return super.keyPressed(key, scancode, modifiers);
    }

    private void loadImage() {
        try (InputStream is = Files.newInputStream(Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots").resolve(this.name))) {
            NativeImage img = NativeImage.read(is);
            this.imgW = img.getWidth();
            this.imgH = img.getHeight();

            DynamicTexture dynamicTexture = new DynamicTexture(img);
            this.texture = new ResourceLocation("bignay", "screenshot_full_" + UUID.randomUUID());

            Minecraft.getInstance().getTextureManager().register(texture, dynamicTexture);
        } catch (Exception e) {
            this.failed = true;
        }
    }

    private void back() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    private void delete() {
        FileUtils.delete(Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots").resolve(this.name));
        back();
    }
}
