package com.mysiupysiu.bignay.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelResource;
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

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null || mc.player == null) return;

        try {
            int x = mc.player.blockPosition().getX();
            int y = mc.player.blockPosition().getY();
            int z = mc.player.blockPosition().getZ();

            String dimension = mc.level.dimension().location().toString();

            ScreenshotsManager.Meta meta = new ScreenshotsManager.Meta();
            meta.timestamp = System.currentTimeMillis();
            meta.name = file2.getName();
            meta.pos = new ScreenshotsManager.Meta.Position(x, y, z, dimension);

            if (mc.isLocalServer() && mc.getSingleplayerServer() != null) {
                File worldDir = mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).normalize().toFile();
                UUID worldUUID = WorldInfoReader.getWorldUUID(worldDir.toPath());
                String folderName = worldDir.getName();

                ScreenshotsManager.saveSingleplayerScreenshot(worldUUID, folderName, meta);
            } else if (mc.getCurrentServer() != null) {
                String ip = mc.getCurrentServer().ip;
                String name = mc.getCurrentServer().name;

                ScreenshotsManager.saveMultiplayerScreenshot(ip, name, meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
