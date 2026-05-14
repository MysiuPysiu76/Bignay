package com.mysiupysiu.bignay.utils.backups;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Backups {

    private static final Path FILE = Minecraft.getInstance().gameDirectory.toPath().resolve("backups.dat");
    private static final List<BackupEntry> BACKUPS = new ArrayList<>();
    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;
        loaded = true;

        try {
            if (!Files.exists(FILE)) return;

            try (InputStream is = Files.newInputStream(FILE)) {
                CompoundTag root = NbtIo.readCompressed(is, NbtAccounter.unlimitedHeap());
                if (root.contains("Backups", Tag.TAG_LIST)) {
                    ListTag list = root.getList("Backups", Tag.TAG_COMPOUND);
                    BACKUPS.clear();
                    for (int i = 0; i < list.size(); i++) {
                        BACKUPS.add(BackupEntry.load(list.getCompound(i)));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load backups.dat", e);
        }
    }

    public static void save() {
        try {
            CompoundTag root = new CompoundTag();
            ListTag list = new ListTag();

            for (BackupEntry e : BACKUPS) {
                list.add(e.save());
            }

            root.put("Backups", list);

            try (OutputStream os = Files.newOutputStream(FILE)) {
                NbtIo.writeCompressed(root, os);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save backups.dat", e);
        }
    }

    public static void addBackup(BackupEntry entry) {
        load();
        BACKUPS.add(entry);
        save();
    }

    public static List<BackupEntry> getBackupsForWorld(UUID worldUUID) {
        load();
        return BACKUPS.stream().filter(b -> b.worldUUID().equals(worldUUID)).toList();
    }

    public static BackupEntry getLatestBackupForWorld(UUID worldUUID) {
        return getBackupsForWorld(worldUUID).stream().max(Comparator.comparingLong(BackupEntry::created)).orElse(null);
    }
}
