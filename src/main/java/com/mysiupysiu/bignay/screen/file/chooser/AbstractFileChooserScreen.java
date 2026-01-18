package com.mysiupysiu.bignay.screen.file.chooser;

import com.mysiupysiu.bignay.utils.FileType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

abstract class AbstractFileChooserScreen extends Screen {

    private final boolean requireDirectory = isRequireDirectory();
    private static final int MARGIN = 20;

    private Screen previousScreen;
    private FilesSelectionGrid list;
    private Path currentDir;
    private List<File> entries;
    private Consumer<File> onConfirm;

    private boolean showHidden = false;
    protected Set<FileType> fileTypes;
    protected Component filterComponent = Component.empty();
    private Button hiddenToggleButton;
    private boolean draggingScrollbar = false;

    public AbstractFileChooserScreen(Component component) {
        super(component);
        this.fileTypes = new LinkedHashSet<>();
    }

    protected abstract Stream<File> getFiles(File[] files);

    protected abstract boolean isRequireDirectory();

    @Override
    protected void init() {
        super.init();

        int btnWidth = 90;
        int btnHeight = 20;
        int btnY = MARGIN + 4;
        int centerX = this.width / 2;

        this.list = new FilesSelectionGrid(this.width, this.height, 65, this.height - 40, this);
        this.list.setColumns(6);
        this.list.setPath(currentDir);
        this.list.setOnPathUpdate(file -> this.currentDir = file.toPath());
        this.addRenderableWidget(this.list);
        this.goHome();

        Button refreshButton = Button.builder(Component.translatable("fileChooser.refresh"), b -> updateContent()).bounds(centerX - 195, btnY, btnWidth, btnHeight).build();
        refreshButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.refresh.description")));
        refreshButton.setTooltipDelay(200);
        this.addRenderableWidget(refreshButton);

        Button homeButton = Button.builder(Component.translatable("fileChooser.home"), b -> goHome()).bounds(centerX - 95, btnY, btnWidth, btnHeight).build();
        homeButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.home.description")));
        homeButton.setTooltipDelay(200);
        this.addRenderableWidget(homeButton);

        Button upButton = Button.builder(Component.translatable("fileChooser.up"), b -> goUp()).bounds(centerX + 5, btnY, btnWidth, btnHeight).build();
        upButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.up.description")));
        upButton.setTooltipDelay(200);
        this.addRenderableWidget(upButton);

        this.hiddenToggleButton = Button.builder(getHiddenFilesButtonLabel(), b -> toggleHiddenFiles()).bounds(centerX + 105, btnY, btnWidth, btnHeight).build();
        this.addRenderableWidget(this.hiddenToggleButton);

        int bottomY = this.height - 30;

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.confirm"), b ->
            this.fileConfirm()
        ).bounds(centerX + 5, bottomY, btnWidth, btnHeight).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.cancel"), b ->
            Minecraft.getInstance().setScreen(this.previousScreen)
        ).bounds(centerX + 105, bottomY, btnWidth, btnHeight).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, MARGIN - 10, 0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("fileChooser.currentPath", this.currentDir.toString()), this.width / 2 - 193, MARGIN + 30, 0xBBBBBB, false);
        graphics.drawString(this.font, this.filterComponent, this.width / 2 - 193, this.height - 25, 0xBBBBBB, false);

        if (this.entries == null) this.entries = List.of();
    }

    @Override

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggingScrollbar) {
            this.draggingScrollbar = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    protected void updateContent() {
        this.list.setContent(getEntries(this.currentDir.toFile()));
    }

    private void goHome() {
        this.currentDir = Paths.get(System.getProperty("user.home", "."));
        updateContent();
    }

    private void goUp() {
        Path parrentPath = this.currentDir.getParent();
        if (parrentPath == null) return;
        this.currentDir = parrentPath;
        updateContent();
    }

    private void toggleHiddenFiles() {
        this.showHidden = !this.showHidden;
        this.hiddenToggleButton.setMessage(getHiddenFilesButtonLabel());
        updateContent();
    }

    void fileConfirm() {
        if (this.onConfirm != null) {
            File target = this.list.getSelectedFile();
            if (target == null) return;
            boolean isDirectory = target.isDirectory();

            if (isDirectory) {
                if (this.requireDirectory) {
                    this.onConfirm.accept(target);
                } else {
                    this.currentDir = target.toPath();
                    updateContent();
                }
            } else {
                this.onConfirm.accept(target);
            }
        }
    }

    List<File> getEntries(File dir) {
        File[] files = dir.listFiles();
        if (files == null) files = new File[0];

        return getFiles(files)
                .filter(f -> !f.isHidden() || this.showHidden)
                .sorted(Comparator.comparing((File f) -> !f.isDirectory())
                .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    private Component getHiddenFilesButtonLabel() {
        return Component.translatable("fileChooser.hidden_files_" + (this.showHidden ? "on" : "off"));
    }

    public void setPreviousScreen(Screen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public void setOnConfirm(Consumer<File> onConfirm) {
        this.onConfirm = onConfirm;
    }

    void setPath(Path path) {
        this.currentDir = path;
    }
}
