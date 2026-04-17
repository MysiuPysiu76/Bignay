package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.client.screen.BignayLoadingScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(value = LoadingOverlay.class)
public abstract class LoadingOverlayMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private ReloadInstance reload;
    @Shadow @Final private Consumer<Optional<Throwable>> onFinish;
    @Shadow private long fadeOutStart;

    @Unique
    private final BignayLoadingScreen bignay$renderer = new BignayLoadingScreen();

    /**
     * @author MysiuPysiu
     * @reason Custom loading animation
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.bignay$renderer.render(guiGraphics, this.reload, this::bignay$finishLoading);
    }

    @Unique
    private void bignay$finishLoading() {
        if (this.fadeOutStart == -1L) {
            this.bignay$renderer.stopSound(this.minecraft);
            try {
                this.reload.checkExceptions();
                this.onFinish.accept(Optional.empty());
            } catch (Throwable t) {
                this.onFinish.accept(Optional.of(t));
            }
            this.fadeOutStart = Util.getMillis();
            if (this.minecraft.screen != null) {
                this.minecraft.screen.init(this.minecraft, this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight());
            }
            this.minecraft.setOverlay(null);
        }
    }
}
