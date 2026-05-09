package com.mysiupysiu.bignay.utils.world;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class CreatedWorldDate {

    public static void setCreatedDate(String levelId) {
        try {
            Path world = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId);
            Path dat = world.resolve("data").resolve("bignay.dat");
            Path icon = world.resolve("icon.png");
            long time;

            try {
                BasicFileAttributes attr = Files.readAttributes(icon, BasicFileAttributes.class);
                time = attr.creationTime().toMillis();
            } catch (Exception ex) {
                time = Instant.now().toEpochMilli();
            }

            world.toFile().getParentFile().mkdir();
            CompoundTag root = new CompoundTag();

            root.putLong("CreatedDate", time);
            dat.toFile().getParentFile().mkdir();
            dat.toFile().createNewFile();
            NbtIo.writeCompressed(root, dat.toFile());
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
