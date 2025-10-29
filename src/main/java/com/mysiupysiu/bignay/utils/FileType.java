package com.mysiupysiu.bignay.utils;

public enum FileType {
    ZIP("zip");

    private final String extension;

    FileType(String name) {
        this.extension = name;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType fromExtension(String ext) {
        if (ext == null) return null;
        String lower = ext.toLowerCase();

        for (FileType type : values()) {
            if (type.extension.equals(lower)) {
                return type;
            }
        }
        return null;
    }
}
