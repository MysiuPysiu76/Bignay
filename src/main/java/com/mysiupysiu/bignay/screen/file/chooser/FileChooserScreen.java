package com.mysiupysiu.bignay.screen.file.chooser;

import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileChooserScreen extends AbstractFileChooserScreen {

    private Predicate<File> additionalFilter = f -> true;

    public FileChooserScreen() {
        super(Component.translatable("fileChooser.choose_file"));
    }

    public FileChooserScreen(FileType... fileType) {
        super(Component.translatable("fileChooser.choose_file"));
        this.fileTypes = new LinkedHashSet<>(Set.of(fileType));
    }

    @Override
    protected Stream<File> getFiles(File[] content) {
        if (this.fileTypes.isEmpty()) {
            return Arrays.stream(content);
        }

        Stream<File> folders = Arrays.stream(content).filter(File::isDirectory);
        Stream<File> files = Arrays.stream(content).filter(File::isFile).filter(f -> this.fileTypes.contains(FileUtils.getFileType(f)));

        return Stream.concat(folders, files.filter(f -> this.additionalFilter.test(f)));
    }

    @Override
    protected boolean isRequireDirectory() {
        return false;
    }

    public void setFilter(FileType... fileType) {
        this.fileTypes = new LinkedHashSet<>(Set.of(fileType));
    }

    public void addFilter(FileType... fileType) {
        this.fileTypes.addAll(Set.of(fileType));
    }

    public void clearFilter() {
        this.fileTypes.clear();
    }

    public void removeFilter(FileType... fileType) {
        this.fileTypes.removeAll(Set.of(fileType));
        this.filterComponent = Component.empty();
    }

    public void setAdditionalFilter(Predicate<File> p) {
        this.additionalFilter = p;
    }

    public void setFilterText(Component c) {
        this.filterComponent = c;
    }

    public Component getFilterText() {
        return this.filterComponent;
    }
}
