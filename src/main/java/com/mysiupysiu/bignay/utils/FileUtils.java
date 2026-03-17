package com.mysiupysiu.bignay.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static long computeFolderSize(Path p) {
        if (p == null) return -1;
        try (Stream<Path> walk = Files.walk(p)) {
            return walk.filter(Files::isRegularFile).mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    return 0L;
                }
            }).sum();
        } catch (IOException e) {
            return -1;
        }
    }

    public static long calculateFilesSize(ZipFile zip) {
        return zip.stream()
                .filter(entry -> !entry.isDirectory())
                .mapToLong(ZipEntry::getSize)
                .filter(size -> size > 0)
                .sum();
    }

    public static long calculateSize(List<Path> files) {
        return files.stream()
                .filter(p -> Files.exists(p) && Files.isRegularFile(p))
                .mapToLong(p -> {
                    try {
                        return Files.size(p);
                    } catch (IOException e) {
                        return 0L;
                    }
                })
                .sum();
    }

    public static Path generateUniquePath(Path originalPath) {
        String fileName = originalPath.getFileName().toString();
        String baseName = fileName;
        String extension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }

        Path parent = originalPath.getParent();
        int counter = 1;
        Path newPath = originalPath;

        while (Files.exists(newPath)) {
            String newName = String.format("%s (%d)%s", baseName, counter++, extension);
            newPath = (parent != null) ? parent.resolve(newName) : Path.of(newName);
        }

        return newPath;
    }

    public static boolean delete(File file) {
        if (!file.exists()) return false;
        if (file.isDirectory()) {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        return file.delete();
    }

    public static boolean delete(Path path) {
        return delete(path.toFile());
    }

    public static boolean deleteWorld(File file) {
        if (!file.isDirectory()) throw new IllegalArgumentException("File is File (Not a folder)");

        Arrays.stream(file.listFiles()).filter(f -> f.getName().contains("level.dat")).forEach(File::delete);

        return delete(file);
    }

    public static String humanReadableByteCount(long bytes) {
        if (bytes < 0) return "-";
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGT".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    public static String humanReadableByteCount(Path p) {
        return  humanReadableByteCount(computeFolderSize(p));
    }

    public static String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    public static FileType getFileType(File file) {
        return FileType.fromExtension(getExtension(file.getName()));
    }

    public static void zipFiles(List<Path> files, Path outputZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(outputZip)))) {
            byte[] buffer = new byte[8192];

            for (Path file : files) {
                if (!Files.isRegularFile(file)) continue;

                ZipEntry entry = new ZipEntry(file.getFileName().toString());
                zos.putNextEntry(entry);

                try (InputStream in = Files.newInputStream(file)) {
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }

                zos.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
