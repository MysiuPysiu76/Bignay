package com.mysiupysiu.bignay.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mysiupysiu.bignay.BignayMod;
import com.mysiupysiu.bignay.registry.BignaySounds;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;

import java.util.List;
import java.util.stream.IntStream;

public class BignayLoadingScreen {

    private static final ResourceLocation MOJ = ResourceLocation.tryBuild(BignayMod.MODID, "textures/gui/title/moj.png");
    private static final ResourceLocation ANG = ResourceLocation.tryBuild(BignayMod.MODID, "textures/gui/title/ang.png");
    private static final List<Integer> SEQUENCE = IntStream.range(-38, 38).map(Math::abs).boxed().toList();

    private static final double FPS = 25.0;
    private static final double SCALE = 0.25;
    public static final long TOTAL_ANIM_DURATION = (long) (SEQUENCE.size() * (1000.0 / FPS));

    public static boolean isFirstBoot = true;
    private long animStartTime = -1L;
    private SimpleSoundInstance currentSound;

    public void render(GuiGraphics gui, ReloadInstance reload, Runnable onFinish) {
        Minecraft mc = Minecraft.getInstance();
        long now = Util.getMillis();
        int width = gui.guiWidth();
        int height = gui.guiHeight();

        int bgColor = mc.options.darkMojangStudiosBackground().get() ? 0xFF000000 : 0xFFEF323D;
        gui.fill(RenderType.guiOverlay(), 0, 0, width, height, bgColor);

        if (isFirstBoot && !reload.isDone()) {
            return;
        }

        if (this.animStartTime == -1L) {
            this.animStartTime = now;
            mc.getSoundManager().stop();

            if (isFirstBoot) {
                try {
                    this.currentSound = SimpleSoundInstance.forUI(BignaySounds.LOADING_SCREEN.get(), 1.0F);
                    mc.getSoundManager().play(this.currentSound);
                } catch (Exception ignored) {}
            }
        }

        long elapsed = now - this.animStartTime;

        int frameIdx = Math.min((int) (elapsed / (1000.0 / FPS)), SEQUENCE.size() - 1);
        renderLogo(gui, width, height, SEQUENCE.get(frameIdx));

        if (elapsed >= TOTAL_ANIM_DURATION && reload.isDone()) {
            isFirstBoot = false;
            stopSound(mc);
            onFinish.run();
        }
    }

    private void renderLogo(GuiGraphics gui, int w, int h, int frame) {
        int frameW = 529;
        int frameH = 280;
        int vOffset = frame * frameH;
        int drawW = (int)(frameW * SCALE);
        int drawH = (int)(frameH * SCALE);
        int centerX = w / 2;
        int y = (h - drawH) / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(MOJ, centerX - drawW, y, drawW, drawH, 0.0F, (float)vOffset, frameW, frameH, frameW, 39 * frameH);
        gui.blit(ANG, centerX, y, drawW, drawH, 0.0F, (float)vOffset, frameW, frameH, frameW, 39 * frameH);

        RenderSystem.disableBlend();
    }

    public void stopSound(Minecraft mc) {
        if (this.currentSound != null) {
            mc.getSoundManager().stop(this.currentSound);
            this.currentSound = null;
        }
    }
}
