package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.util.UUID;
import java.util.function.Consumer;

@Mixin(Screenshot.class)
public abstract class ScreenshotMixin {

    @Inject(method = "_grab", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;ioPool()Ljava/util/concurrent/ExecutorService;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void bignay$captureScreenshotMetadata(File gameDirectory, String screenshotNameArg, RenderTarget renderTarget, Consumer<Component> messageConsumer, CallbackInfo ci, NativeImage nativeImage, File screenshotDir, File file2) {

        if (file2 == null) return;

        String finalName = file2.getName();
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) return;

        try {
            if (mc.isLocalServer() && mc.getSingleplayerServer() != null) {
                File worldDir = mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).normalize().toFile();
                UUID worldUUID = WorldInfoReader.getWorldUUID(worldDir);
                String folderName = worldDir.getName();

                ScreenshotsManager.saveSingleplayerScreenshot(worldUUID, folderName, finalName);
            } else if (mc.getCurrentServer() != null) {
                String ip = mc.getCurrentServer().ip;
                String name = mc.getCurrentServer().name;

                ScreenshotsManager.saveMultiplayerScreenshot(ip, name, finalName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
