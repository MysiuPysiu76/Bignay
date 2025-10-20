package com.mysiupysiu.bignay.screen;

import net.minecraft.network.chat.Component;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public class FolderChooserScreen extends AbstractFileChooserScreen {

    public FolderChooserScreen() {
        super(Component.translatable("fileChooser.choose_folder"));
    }

    @Override
    protected Stream<File> getFiles(File[] files) {
        return Arrays.stream(files).filter(File::isDirectory);
    }

    @Override
    protected boolean isRequireDirectory() {
        return true;
    }
}
