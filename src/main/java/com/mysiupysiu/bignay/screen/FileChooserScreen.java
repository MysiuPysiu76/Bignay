package com.mysiupysiu.bignay.screen;

import net.minecraft.network.chat.Component;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileChooserScreen extends AbstractFileChooserScreen {

    public FileChooserScreen() {
        super(Component.translatable("fileChooser.choose_file"));
    }

    @Override
    protected Stream<File> getFiles(File[] files) {
        return Arrays.stream(files);
    }

    @Override
    protected boolean isRequireDirectory() {
        return false;
    }
}
