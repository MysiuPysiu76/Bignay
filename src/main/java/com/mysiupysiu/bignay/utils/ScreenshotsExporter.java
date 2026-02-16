package com.mysiupysiu.bignay.utils;

import com.mysiupysiu.bignay.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.screen.ScreenshotsViewerScreen;
import net.minecraft.network.chat.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ScreenshotsExporter implements OperationWithProgress {

    private final List<Path> files;
    private final Path destination;
    private OperationWithProgressScreen progressListener;
    private volatile boolean canceled = false;

    public ScreenshotsExporter(Stream<Path> paths, Path des) {
        this.destination = des.resolve(Component.translatable("screenshotsViewer.export.fileName", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH-mm"))).getString() + ".zip");
        this.files = paths.toList();
    }

    private long calculateSize() {
        long total = 0;
        for (Path p : this.files) {
            try {
                if (Files.exists(p) && Files.isRegularFile(p)) {
                    total += Files.size(p);
                }
            } catch (IOException ignored) {}
        }
        return total;
    }

    @Override
    public void setProgressScreen(ProgressListener progressListener) {
        this.progressListener = (OperationWithProgressScreen)progressListener;
        this.progressListener.setAfterFinishScreen(new ScreenshotsViewerScreen());
    }

    @Override
    public void execute() {
        long totalSize = calculateSize();
        long[] processed = {0};

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(this.destination.toFile()))) {
            byte[] buffer = new byte[4096];

            for (Path file : this.files) {
                if (this.canceled) break;
                if (!Files.isRegularFile(file)) continue;

                try (FileInputStream fis = new FileInputStream(file.toFile())) {
                    zos.putNextEntry(new ZipEntry(file.getFileName().toString()));

                    int len;
                    while ((len = fis.read(buffer)) != -1 && !this.canceled) {
                        zos.write(buffer, 0, len);
                        processed[0] += len;

                        if (this.progressListener != null && totalSize > 0) {
                            double progress = (double) processed[0] / totalSize;
                            this.progressListener.onProgress(progress);
                        }
                    }

                    zos.closeEntry();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (this.canceled) {
            try { Files.deleteIfExists(this.destination); }
            catch (IOException ignored) {}
        }

        finish();
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void finish() {
        if (this.progressListener != null) {
            this.progressListener.onFinish();
        }
    }
}
