package com.mysiupysiu.bignay.utils;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public record BackupEntry(UUID backupUUID, UUID worldUUID, String file, long created, String version, long size) {

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("BackupUUID", backupUUID);
        tag.putUUID("WorldUUID", worldUUID);
        tag.putString("File", file);
        tag.putLong("Created", created);
        tag.putString("Version", version);
        tag.putLong("Size", size);
        return tag;
    }

    public static BackupEntry load(CompoundTag tag) {
        return new BackupEntry(
                tag.getUUID("BackupUUID"),
                tag.getUUID("WorldUUID"),
                tag.getString("File"),
                tag.getLong("Created"),
                tag.getString("Version"),
                tag.getLong("Size"));
    }
}
