package com.mysiupysiu.bignay.events;

import com.mojang.datafixers.util.Pair;
import com.mysiupysiu.bignay.screen.WorldExportScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.SymlinkWarningScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.ContentValidationException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = "bignay", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WorldOptionsEvent {

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
                        Minecraft.getInstance().setScreen(new WorldExportScreen(screen, levelStorageAccess));
                    } catch (Exception e) {
                        Minecraft.getInstance().setScreen(null);
                    }
                }
        ).bounds(centerX - 100, baseY, buttonWidth, buttonHeight).build();

        event.addListener(exportButton);

        Minecraft mc = Minecraft.getInstance();

        int x = screen.width / 2 - 100;
        int width = 200;
        int height = 20;

        Button.Builder access = Button.builder(Component.translatable("selectWorld.recreate"), b -> {
            try {
                var levelAccessField = EditWorldScreen.class.getDeclaredField("levelAccess");
                levelAccessField.setAccessible(true);
                LevelStorageSource.LevelStorageAccess levelAccess =
                        (LevelStorageSource.LevelStorageAccess) levelAccessField.get(screen);

                Pair<LevelSettings, WorldCreationContext> pair =
                        mc.createWorldOpenFlows().recreateWorldData(levelAccess);
                LevelSettings levelSettings = pair.getFirst();
                WorldCreationContext worldContext = pair.getSecond();

                Path path = CreateWorldScreen.createTempDataPackDirFromExistingWorld(
                        levelAccess.getLevelPath(LevelResource.DATAPACK_DIR), mc);

                if (worldContext.options().isOldCustomizedWorld()) {
                    mc.setScreen(new ConfirmScreen((confirm) -> {
                        if (confirm) {
                            mc.setScreen(CreateWorldScreen.createFromExisting(mc, screen, levelSettings, worldContext, path));
                        } else {
                            mc.setScreen(screen);
                        }
                    },
                            Component.translatable("selectWorld.recreate.customized.title"),
                            Component.translatable("selectWorld.recreate.customized.text"),
                            CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
                } else {
                    mc.setScreen(CreateWorldScreen.createFromExisting(mc, screen, levelSettings, worldContext, path));
                }

            } catch (ContentValidationException e) {
                mc.setScreen(new SymlinkWarningScreen(screen));
            } catch (Exception e) {
                e.printStackTrace();
                mc.setScreen(new AlertScreen(() -> mc.setScreen(screen),
                        Component.translatable("selectWorld.recreate.error.title"),
                        Component.translatable("selectWorld.recreate.error.text")));
            }
        });
        access.bounds(x, baseY + 24, width, height);
        Button recreateButton = access.build();

        event.addListener(recreateButton);
    }
}
