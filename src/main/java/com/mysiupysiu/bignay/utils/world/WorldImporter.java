package com.mysiupysiu.bignay.utils.world;

import com.mysiupysiu.bignay.utils.FileUtils;
import com.mysiupysiu.bignay.utils.OperationWithProgress;
import com.mysiupysiu.bignay.utils.ProgressListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WorldImporter implements OperationWithProgress {

    private ProgressListener progressListener;
    private final File source;
    private Path target;
    private String worldName;
    private volatile boolean canceled = false;

    public WorldImporter(File source) {
        this.source = source;
    }

    public static boolean isValidWorld(File source) {
        try (ZipFile zip = new ZipFile(source)) {
            Set<String> topLevelFolders = new HashSet<>();

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.equals("level.dat")) {
                    return true;
                }

                int slash = name.indexOf('/');
                if (slash > 0) {
                    String folder = name.substring(0, slash + 1);
                    topLevelFolders.add(folder);
                }
            }

            if (topLevelFolders.size() != 1) return false;

            String onlyFolder = topLevelFolders.iterator().next();
            return zip.getEntry(onlyFolder + "level.dat") != null;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void execute() {
        if (this.worldName == null || this.worldName.isEmpty()) return;

        chooseTarget();

        try (ZipFile zip = new ZipFile(this.source)) {
            String topFolder = null;

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.equals("level.dat")) {
                    topFolder = "";
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

                Path outPath = this.target.resolve(name).normalize();

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

            if (!canceled) {
                updateWorldName();
            } else {
                deleteDirectory(this.target.toFile());
            }

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        finish();
    }

    public void chooseTarget() {
        this.target = Paths.get(System.getProperty("user.dir"), "saves", this.worldName);

        int i = 0;
        while (this.target.toFile().exists()) {
            this.target = Paths.get(System.getProperty("user.dir"), "saves", this.worldName + "_" + ++i);
        }
    }

    private void deleteDirectory(File dir) {
        if (!dir.exists()) return;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) deleteDirectory(f);
                else f.delete();
            }
        }
        dir.delete();
    }

    public void updateWorldName() throws IOException {
        Path levelDatPath = this.target.resolve("level.dat");

        CompoundTag levelData = NbtIo.readCompressed(Files.newInputStream(levelDatPath));
        CompoundTag data = levelData.getCompound("Data");
        data.putString("LevelName", this.worldName);

        NbtIo.writeCompressed(levelData, Files.newOutputStream(levelDatPath, StandardOpenOption.WRITE));
    }

    public void setWorldName(String worldName) {
        if (worldName == null || worldName.isBlank()) return;
        this.worldName = worldName;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public File getSource() {
        return source;
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
        this.progressListener.onFinish();
    }
}
