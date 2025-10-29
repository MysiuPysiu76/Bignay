package com.mysiupysiu.bignay.screen.file.chooser;

import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FileChooserScreen extends AbstractFileChooserScreen {

    public FileChooserScreen() {
        super(Component.translatable("fileChooser.choose_file"));
    }

    public FileChooserScreen(FileType... fileType) {
        super(Component.translatable("fileChooser.choose_file"));
        this.fileTypes = new LinkedHashSet<>(Set.of(fileType));
    }

    @Override
    protected Stream<File> getFiles(File[] content) {
        if (fileTypes.isEmpty()) {
            return Arrays.stream(content);
        }

        Stream<File> folders = Arrays.stream(content).filter(File::isDirectory);
        Stream<File> files = Arrays.stream(content).filter(File::isFile).filter(f -> fileTypes.contains(FileUtils.getFileType(f)));

        return Stream.concat(folders, files);
    }

    @Override
    protected boolean isRequireDirectory() {
        return false;
    }

    public void setFilter(FileType... fileType) {
        fileTypes = Set.of(fileType);
        reloadEntries();
    }

    public void addFilter(FileType... fileType) {
        fileTypes.addAll(Set.of(fileType));
        reloadEntries();
    }

    public void clearFilter() {
        fileTypes.clear();
        reloadEntries();
    }

    public void removeFilter(FileType... fileType) {
        fileTypes.removeAll(Set.of(fileType));
        reloadEntries();
    }
}
