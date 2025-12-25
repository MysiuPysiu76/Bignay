package com.mysiupysiu.bignay.events;

import com.mysiupysiu.bignay.utils.CreatedWorldDate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.nio.file.Path;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldCreationEvent {

    @SubscribeEvent
    public static void onWorldCreate(LevelEvent.CreateSpawnPosition event) throws IOException {
        if (event.getLevel().isClientSide()) return;
        ServerLevel level = (ServerLevel) event.getLevel();

        if (!level.isClientSide() && level.getServer().isSingleplayer()) {
            MinecraftServer server = level.getServer();
            Path worldDir = server.getWorldPath(LevelResource.ROOT);

            CreatedWorldDate.setCreatedDate(worldDir.normalize().getFileName().toString());
        }
    }
}
