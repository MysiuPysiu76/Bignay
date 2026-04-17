package com.mysiupysiu.bignay.forge.mixin;

import com.mysiupysiu.bignay.client.screen.BignayLoadingScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraftforge.client.loading.ForgeLoadingOverlay;
import net.minecraftforge.fml.earlydisplay.DisplayWindow;
import net.minecraftforge.fml.loading.progress.ProgressMeter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ForgeLoadingOverlay.class)
public abstract class ForgeLoadingOverlayMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private ReloadInstance reload;
    @Shadow @Final private Consumer<Optional<Throwable>> onFinish;
    @Shadow private long fadeOutStart;

    @Shadow @Final private DisplayWindow displayWindow;
    @Shadow @Final private ProgressMeter progress;

    @Unique
    private final BignayLoadingScreen bignay$renderer = new BignayLoadingScreen();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void bignay$onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        int width = this.minecraft.getWindow().getGuiScaledWidth();
        int height = this.minecraft.getWindow().getGuiScaledHeight();
        boolean isDark = this.minecraft.options.darkMojangStudiosBackground().get();
        guiGraphics.fill(0, 0, width, height, isDark ? 0xFF000000 : 0xFFEF323D);

        this.bignay$renderer.render(guiGraphics, this.reload, this::bignay$finishLoading);

        ci.cancel();
    }

    @Unique
    private void bignay$finishLoading() {
        if (this.fadeOutStart == -1L) {
            this.bignay$renderer.stopSound(this.minecraft);

            if (this.progress != null) this.progress.complete();
            if (this.displayWindow != null) this.displayWindow.close();

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
