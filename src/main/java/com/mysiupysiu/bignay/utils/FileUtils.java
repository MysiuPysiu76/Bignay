package com.mysiupysiu.bignay.utils;

import java.io.File;

public class FileUtils {

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
}
