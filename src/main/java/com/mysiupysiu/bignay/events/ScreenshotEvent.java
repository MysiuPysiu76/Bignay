package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ScreenshotEvent {

    @SubscribeEvent
    public static void onScreenshot(net.minecraftforge.client.event.ScreenshotEvent event) throws IOException {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        String screenshotName = event.getScreenshotFile().getName();

        if (mc.isLocalServer()) {
            File worldDir = mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).normalize().toFile();
            UUID worldUUID = WorldInfoReader.getWorldUUID(worldDir);
            String folderName = worldDir.getName();
            ScreenshotsManager.saveSingleplayerScreenshot(worldUUID, folderName, screenshotName);
        } else {
            String ip = mc.getCurrentServer().ip;
            String name = mc.getCurrentServer().name;
            ScreenshotsManager.saveMultiplayerScreenshot(ip, name, screenshotName);
        }
    }
}
