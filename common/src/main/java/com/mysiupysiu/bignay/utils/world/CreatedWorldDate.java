package com.mysiupysiu.bignay.utils.world;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class CreatedWorldDate {

    public static void setCreatedDate(String levelId) {
        try {
            Path worldPath = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId);
            Path dataFolder = worldPath.resolve("data");
            Path bignayDat = dataFolder.resolve("bignay.dat");
            Path icon = worldPath.resolve("icon.png");

            long time;

            try {
                if (Files.exists(icon)) {
                    BasicFileAttributes attr = Files.readAttributes(icon, BasicFileAttributes.class);
                    time = attr.creationTime().toMillis();
                } else {
                    time = Instant.now().toEpochMilli();
                }
            } catch (Exception ex) {
                time = Instant.now().toEpochMilli();
            }

            if (!Files.exists(dataFolder)) {
                Files.createDirectories(dataFolder);
            }

            CompoundTag root = new CompoundTag();
            root.putLong("CreatedDate", time);

            try (OutputStream os = Files.newOutputStream(bignayDat)) {
                NbtIo.writeCompressed(root, os);
            }

        } catch (Exception e) {
            LoggerFactory.getLogger(CreatedWorldDate.class).error("Could not save created world date: ", e);
        }
    }

    public static Long getCreatedDate(String levelId) {
        try {
            Path file = Minecraft.getInstance().gameDirectory.toPath().resolve("saves").resolve(levelId).resolve("data").resolve("bignay.dat");

            if (!Files.exists(file)) return null;

            try (InputStream is = Files.newInputStream(file)) {
                CompoundTag root = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());

                if (root != null && root.contains("CreatedDate", Tag.TAG_LONG)) {
                    return root.getLong("CreatedDate");
                }
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(CreatedWorldDate.class).error("Error reading created world date: ", e);
            return null;
        }
        return null;
    }
}
