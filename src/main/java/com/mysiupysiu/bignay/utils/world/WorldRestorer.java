package com.mysiupysiu.bignay.utils.world;

import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.OperationWithProgress;
import com.mysiupysiu.bignay.utils.ProgressListener;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WorldRestorer implements OperationWithProgress {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private final File source;
    private ProgressListener progressListener;
    private volatile boolean canceled = false;
    private File oldFile;
    private File tempFile;

    public WorldRestorer(LevelStorageSource.LevelStorageAccess levelAccess, File source) {
        this.levelAccess = levelAccess;
        this.source = source;
    }

    @Override
    public void execute() {
        File saves = new File(Minecraft.getInstance().gameDirectory, "saves");
        this.oldFile = new File(saves, this.levelAccess.getLevelId());
        this.tempFile = new File(saves, UUID.randomUUID().toString());

        oldFile.renameTo(tempFile);

        try (ZipFile zip = new ZipFile(this.source)) {
            String topFolder = "";

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.equals("level.dat")) {
                    break;
                }

                int slash = name.indexOf('/');
                if (slash > 0) {
                    topFolder = name.substring(0, slash + 1);
                }
            }

            long totalSize = FileUtils.calculateFilesSize(zip);
            long processed = 0;

            entries = zip.entries();
            while (entries.hasMoreElements() && !canceled) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (topFolder != null && !topFolder.isEmpty() && name.startsWith(topFolder)) {
                    name = name.substring(topFolder.length());
                }

                if (name.isEmpty()) continue;
                if (canceled) {
                    return;
                }

                Path outPath = oldFile.toPath().resolve(name).normalize();

                if (entry.isDirectory()) {
                    Files.createDirectories(outPath);
                } else {
                    Files.createDirectories(outPath.getParent());
                    try (InputStream is = zip.getInputStream(entry)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        try (var os = Files.newOutputStream(outPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                            while ((len = is.read(buffer)) != -1 && !canceled) {
                                os.write(buffer, 0, len);
                                processed += len;
                                if (progressListener != null && totalSize > 0) {
                                    double progress = (double) processed / totalSize;
                                    progressListener.onProgress(progress);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!canceled) FileUtils.delete(this.tempFile);
        finish();
    }

    @Override
    public void setProgressScreen(ProgressListener listener) {
        this.progressListener = listener;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void finish() {
        try {
            if (canceled) {
                FileUtils.deleteWorld(this.oldFile);
                tempFile.renameTo(oldFile);
            } else {
                FileUtils.deleteWorld(this.tempFile);
            }
            this.progressListener.onFinish();
            this.levelAccess.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
