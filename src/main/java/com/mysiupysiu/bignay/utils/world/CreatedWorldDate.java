package com.mysiupysiu.bignay.utils.world;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.Instant;

public class CreatedWorldDate {

    public static void setCreatedDate(String levelId) throws IOException {
        try {
            Path file = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId).resolve("data").resolve("bignay.dat");
            file.toFile().getParentFile().mkdir();
            CompoundTag root = new CompoundTag();
            root.putLong("CreatedDate", Instant.now().toEpochMilli());
            NbtIo.writeCompressed(root, file.toFile());
        } catch (Exception e) {
            LoggerFactory.getLogger(CreatedWorldDate.class).error("Could not save created world date: ", e);
        }
    }

    public static Long getCreatedDate(String levelId) {
        try {
            Path file = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId).resolve("data").resolve("bignay.dat");

            if (!file.toFile().exists()) return null;

            CompoundTag root = NbtIo.readCompressed(file.toFile());

            return root.getLong("CreatedDate");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
