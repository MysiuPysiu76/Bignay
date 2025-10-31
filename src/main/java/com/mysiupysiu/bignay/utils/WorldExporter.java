package com.mysiupysiu.bignay.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WorldExporter {

    private final File source;
    private File destination;
    private String worldName;
    private boolean exportPlayerData;

    public WorldExporter(File source) {
        this.source = source;
    }

    public void execute() {
        File outputZip = new File(this.destination, worldName + ".zip");

        try (FileOutputStream fos = new FileOutputStream(outputZip); ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipFolderRecursive(this.source, this.source.getName(), zos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void zipFolderRecursive(File folder, String parentPath, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files == null) return;

        Set<String> playerDataFolders = Set.of("advancements", "playerdata", "stats");

        for (File file : files) {
            String entryName = parentPath + "/" + file.getName();

            if (file.isDirectory() && !this.exportPlayerData && playerDataFolders.contains(file.getName().toLowerCase())) {
                continue;
            }

            if (file.isDirectory()) {
                zos.putNextEntry(new ZipEntry(entryName + "/"));
                zos.closeEntry();

                zipFolderRecursive(file, entryName, zos);
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(entryName));

                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                    }

                    zos.closeEntry();
                }
            }
        }
    }

    public File getSource() {
        return source;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public boolean isExportPlayerData() {
        return exportPlayerData;
    }

    public void setExportPlayerData(boolean exportPlayerData) {
        this.exportPlayerData = exportPlayerData;
    }
}
