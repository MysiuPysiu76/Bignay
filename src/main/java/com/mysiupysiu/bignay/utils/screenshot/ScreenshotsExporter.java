package com.mysiupysiu.bignay.utils.screenshot;

import com.mysiupysiu.bignay.screen.OperationWithProgressScreen;
import com.mysiupysiu.bignay.screen.screenshot.ScreenshotsViewerScreen;
import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.OperationWithProgress;
import com.mysiupysiu.bignay.utils.ProgressListener;
import net.minecraft.network.chat.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
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

    @Override
    public void setProgressScreen(ProgressListener progressListener) {
        this.progressListener = (OperationWithProgressScreen)progressListener;
        this.progressListener.setAfterFinishScreen(new ScreenshotsViewerScreen());
    }

    @Override
    public void execute() {
        if (files.size() == 1) {
            exportSingleScreenshot();
            return;
        }

        long totalSize = FileUtils.calculateSize(files);
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

    private void exportSingleScreenshot() {
        Path source = this.files.get(0);
        Path target = this.destination.resolveSibling(source.getFileName());

        try {
            if (Files.exists(target)) target = FileUtils.generateUniquePath(target);
            Files.copy(source, target);
            finish();
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to export screenshot", e);
        }
    }
}
