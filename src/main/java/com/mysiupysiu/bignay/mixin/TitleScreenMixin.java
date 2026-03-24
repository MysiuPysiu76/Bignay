package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.screen.screenshot.ScreenshotsViewerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void replaceRealmsButton(CallbackInfo ci) {

        Button realms = this.children().stream()
                .filter(w -> w instanceof Button)
                .map(b -> (Button) b)
                .filter(b -> b.getMessage().equals(Component.translatable("menu.online")))
                .toList()
                .get(0);

        this.removeWidget(realms);

        this.addRenderableWidget(Button.builder(Component.literal("Screenshots"), b -> {
            this.minecraft.setScreen(new ScreenshotsViewerScreen());
        }).bounds(this.width / 2 + 2, this.height / 4 + 48 + 24 * 2, 98, 20).build());
    }
}
