package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.screen.ScreenshotsViewerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TitleScreenEvent {

    @SubscribeEvent
    public static void onInitScreen(ScreenEvent.Init.Post event) throws ReflectiveOperationException {
        if (!(event.getScreen() instanceof TitleScreen screen)) return;

        Button realms = event.getListenersList().stream()
                .filter(w -> w instanceof Button)
                .map(w -> (Button) w)
                .filter(b -> b.getMessage().equals(Component.translatable("menu.online")))
                .toList()
                .get(0);

        int[] pos = {realms.getX(), realms.getY(), realms.getWidth(), realms.getHeight()};

        event.removeListener(realms);

        Button screenshots = Button.builder(Component.translatable("menu.screenshots"), b -> Minecraft.getInstance().setScreen(new ScreenshotsViewerScreen()))
                .bounds(pos[0], pos[1], pos[2], pos[3]).build();

        Method addRenderableWidget;

        try {
            addRenderableWidget = Screen.class.getDeclaredMethod("m_142416_", GuiEventListener.class);
        } catch (Exception e) {
            addRenderableWidget = Screen.class.getDeclaredMethod("addRenderableWidget", GuiEventListener.class);
        }

        addRenderableWidget.setAccessible(true);
        addRenderableWidget.invoke(screen, screenshots);
    }
}
