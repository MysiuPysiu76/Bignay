package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.utils.screenshot.ScreenshotsManager;
import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ScreenshotEvent {

    @SubscribeEvent
    public static void onScreenshot(net.minecraftforge.client.event.ScreenshotEvent event) throws IOException {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        String screenshotName = event.getScreenshotFile().getName();
        File worldDir = mc.getSingleplayerServer().getWorldPath(LevelResource.ROOT).normalize().toFile();
        UUID worldUUID = WorldInfoReader.getWorldUUID(worldDir);
        String folderName = worldDir.getName();

        ScreenshotsManager.saveScreenshot(worldUUID, folderName, screenshotName);
    }
}
