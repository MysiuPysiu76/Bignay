package com.mysiupysiu.bignay.utils.world;

import com.mysiupysiu.bignay.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.OperationWithProgress;
import com.mysiupysiu.bignay.utils.ProgressListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class WorldDuplicator implements OperationWithProgress {

    private final LevelStorageSource.LevelStorageAccess storageAccess;
    private Path destination;
    private ProgressListener progressListener;

    public WorldDuplicator(LevelStorageSource.LevelStorageAccess storageAccess) {
        this.storageAccess = storageAccess;
    }

    @Override
    public void execute() {
        try {
            Path src = this.storageAccess.getLevelPath(LevelResource.ROOT);

            String worldName = storageAccess.getLevelId();
            int copyId = generateCopyId(worldName);

            LevelStorageSource lss = Minecraft.getInstance().getLevelSource();

            LevelStorageSource.LevelStorageAccess newAccess = lss.createAccess(worldName + " (Copy " + copyId + ")");
            this.destination = newAccess.getLevelPath(LevelResource.ROOT);

            copyFolderWithProgress(src, destination);
            newAccess.renameLevel(worldName + " (" + Component.translatable("gui.copy").getString() + copyId + ")");
            this.finish();
            this.storageAccess.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.storageAccess.close();
            } catch (IOException es) {
                es.printStackTrace();
            }
            this.finish();
        }
    }

    @Override
    public void cancel() {
        Thread.currentThread().interrupt();

        if (destination != null && Files.exists(destination)) {
            try (Stream<Path> walk = Files.walk(destination)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException ignored) {}
                        });
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void finish() {
        this.progressListener.onFinish();
    }

    private int generateCopyId(String base) {
        try {
            File root = Minecraft.getInstance().getLevelSource().getBaseDir().toFile().getCanonicalFile();

            int i = 1;
            String candidate = base + " (Copy " + i + ")";

            while (new File(root, candidate).exists()) {
                i++;
                candidate = base + " (Copy " + i + ")";
            }

            return i;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyFolderWithProgress(Path src, Path dst) throws IOException {
        long totalBytes = FileUtils.computeFolderSize(src);
        final long[] copiedBytes = {0};

        Files.walk(src).forEach(path -> {
            try {
                Path rel = src.relativize(path);
                Path target = dst.resolve(rel);

                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                    return;
                }

                Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);

                copiedBytes[0] += Files.size(path);
                progressListener.onProgress(Math.min(1.0, (double) copiedBytes[0] / totalBytes));

                if (Thread.currentThread().isInterrupted()) {
                    throw new RuntimeException("Copy cancelled");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setProgressScreen(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}
