package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

public class DuplicateWorldScreen extends AbstractProgressScreen {

    private final LevelStorageSource.LevelStorageAccess sourceAccess;
    private Path destination;

    public DuplicateWorldScreen(LevelStorageSource.LevelStorageAccess access) {
        super(Component.translatable("selectWorld.duplicate"));
        this.sourceAccess = access;
    }

    @Override
    protected void onAction() {
        try {
            Path src = this.sourceAccess.getLevelPath(LevelResource.ROOT);

            String worldName = sourceAccess.getLevelId();
            int copyId = generateCopyId(worldName);

            LevelStorageSource lss = Minecraft.getInstance().getLevelSource();

            LevelStorageSource.LevelStorageAccess newAccess = lss.createAccess(worldName + " (Copy " + copyId + ")");
            destination = newAccess.getLevelPath(LevelResource.ROOT);

            copyFolderWithProgress(src, destination);
            newAccess.renameLevel(worldName + " (" + Component.translatable("gui.copy").getString() + copyId + ")");
            finish();
            this.sourceAccess.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.sourceAccess.close();
            } catch (IOException es) {
                es.printStackTrace();
            }
            finish();
        }
    }

    @Override
    public void onCancel() {
        Thread.currentThread().interrupt();

        if (destination != null && Files.exists(destination)) {
            try (Stream<Path> walk = Files.walk(destination)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException ignored) {
                            }
                        });
            } catch (IOException ignored) {}
        }
    }

    private int generateCopyId(String base) {
        LevelStorageSource lss = Minecraft.getInstance().getLevelSource();
        int i = 1;
        String candidate;

        do {
            candidate = base + " (Copy " + i + ")";
            i++;
        } while (lss.levelExists(candidate));

        return i;
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
                onProgress(Math.min(1.0, (double) copiedBytes[0] / totalBytes));

                if (Thread.currentThread().isInterrupted()) {
                    throw new RuntimeException("Copy cancelled");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
