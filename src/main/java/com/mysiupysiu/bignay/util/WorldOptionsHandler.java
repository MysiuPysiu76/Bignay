package com.mysiupysiu.bignay.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WorldOptionsHandler {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof EditWorldScreen screen)) return;

        int centerX = screen.width / 2;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int spacing = 24;

        for (GuiEventListener widget : event.getListenersList()) {
            if (widget instanceof Button button) {
                Component message = button.getMessage();
                if (message.getString().equals(Component.translatable("selectWorld.edit.save").getString()) || message.getString().equals(Component.translatable("gui.cancel").getString())) {
                    button.setY(button.getY() + spacing);
                }
            }
        }

        int baseY = screen.height / 4 + 96 + 5 + spacing;
        Button exportButton = Button.builder(Component.translatable("selectWorld.edit.export"), btn -> {
                    try {
                        Field field = EditWorldScreen.class.getDeclaredField("levelAccess");
                        field.setAccessible(true);
                        LevelStorageSource.LevelStorageAccess levelStorageAccess = (LevelStorageSource.LevelStorageAccess) field.get(screen);
                        Minecraft.getInstance().setScreen(new ExportWorldScreen(screen, levelStorageAccess));
                    } catch (Exception e) {
                        Minecraft.getInstance().setScreen(null);
                    }
                }
        ).bounds(centerX - 100, baseY, buttonWidth, buttonHeight).build();

        event.addListener(exportButton);
    }
}
