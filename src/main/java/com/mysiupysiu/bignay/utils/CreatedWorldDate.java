package com.mysiupysiu.bignay.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class CreatedWorldDate {

    public static void setCreatedDate(String levelId) throws IOException {
        Path file = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId).resolve("data").resolve("bignay.dat");
        CompoundTag root = new CompoundTag();
        root.putLong("CreatedDate", Instant.now().toEpochMilli());
        NbtIo.writeCompressed(root, file.toFile());
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
