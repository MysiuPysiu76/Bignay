package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.screen.world.WorldEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditWorldScreen.class)
public abstract class EditWorldScreenMixin {

    @Shadow @Final private LevelStorageSource.LevelStorageAccess levelAccess;

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        Minecraft.getInstance().setScreen(new WorldEditScreen(this.levelAccess));
        ci.cancel();
    }
}
