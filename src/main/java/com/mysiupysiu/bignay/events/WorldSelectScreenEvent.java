package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.screen.file.InvalidFileScreen;
import com.mysiupysiu.bignay.screen.file.chooser.FileChooserScreen;
import com.mysiupysiu.bignay.screen.WorldImportScreen;
import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.WorldImporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.components.Button;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WorldSelectScreenEvent {

    @SubscribeEvent
    public static void onInitScreen(ScreenEvent.Init.Post event) throws ReflectiveOperationException {
        if (!(event.getScreen() instanceof SelectWorldScreen screen)) return;

        Button recreateButton = event.getListenersList().stream()
                .filter(w -> w instanceof Button)
                .map(w -> (Button) w)
                .filter(b -> b.getMessage().equals(Component.translatable("selectWorld.recreate")))
                .toList()
                .get(0);

        int[] pos = {recreateButton.getX(), recreateButton.getY(), recreateButton.getWidth(), recreateButton.getHeight()};

        event.removeListener(recreateButton);

        Button importButton = Button.builder(Component.translatable("importWorld.import"), b -> {
            FileChooserScreen fileChooser = new FileChooserScreen();
            fileChooser.addFilter(FileType.ZIP);
            fileChooser.setOnConfirm(file -> {
                WorldImportScreen importWorld = new WorldImportScreen(file);
                if (WorldImporter.isValidWorld(file)) {
                    Minecraft.getInstance().setScreen(importWorld);
                    return;
                }
                Minecraft.getInstance().setScreen(new InvalidFileScreen(fileChooser));
            });
            fileChooser.setPreviousScreen(screen);
            Minecraft.getInstance().setScreen(fileChooser);
        }).bounds(pos[0], pos[1], pos[2], pos[3]).build();

        Method addRenderableWidget = Screen.class.getDeclaredMethod("addRenderableWidget", GuiEventListener.class);
        addRenderableWidget.setAccessible(true);
        addRenderableWidget.invoke(screen, importButton);
    }
}
