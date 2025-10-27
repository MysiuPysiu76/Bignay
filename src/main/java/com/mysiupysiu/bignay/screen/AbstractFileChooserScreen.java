package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.util.FileType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractFileChooserScreen extends Screen {
    private static final int MARGIN = 20;
    private static final int LINE_HEIGHT = 11;
    private static final int VISIBLE_LINES = 15;

    private Screen previousScreen;
    private Path currentDir;
    private List<File> entries;
    private Consumer<File> onConfirm;
    protected Set<FileType> fileTypes;

    private int scroll = 0;
    private int selectedIndex = -1;
    private boolean showHidden = false;
    private final boolean requireDirectory = isRequireDirectory();

    private long lastClickTime = 0;
    private int lastClickedIndex = -1;

    private Button refreshButton;
    private Button hiddenToggleButton;
    private Button homeButton;
    private Button upButton;

    public AbstractFileChooserScreen(Component component) {
        super(component);
        this.fileTypes = new LinkedHashSet<>();
        this.goHome();
        reloadEntries();
    }

    protected abstract Stream<File> getFiles(File[] files);

    protected abstract boolean isRequireDirectory();

    @Override
    protected void init() {
        super.init();

        int btnWidth = 98;
        int btnHeight = 20;
        int btnY = MARGIN + 4;
        int spacing = 10;

        int hiddenX = this.width - MARGIN - btnWidth - 20;
        int upX = hiddenX - btnWidth - spacing;
        int homeX = upX - btnWidth - spacing;

        refreshButton = Button.builder(Component.translatable("fileChooser.refresh"), b -> reloadEntries()).bounds(MARGIN, btnY, btnWidth, btnHeight).build();
        this.addRenderableWidget(refreshButton);

        homeButton = Button.builder(Component.translatable("fileChooser.home"), b -> goHome()).bounds(homeX, btnY, btnWidth, btnHeight).build();
        this.addRenderableWidget(homeButton);

        upButton = Button.builder(Component.translatable("fileChooser.up"), b -> goUp()).bounds(upX, btnY, btnWidth, btnHeight).build();
        this.addRenderableWidget(upButton);

        hiddenToggleButton = Button.builder(getHiddenFilesButtonLabel(), b -> toggleHiddenFiles()).bounds(hiddenX, btnY, btnWidth, btnHeight).build();
        this.addRenderableWidget(hiddenToggleButton);

        int listY = MARGIN + 48;
        int listEndY = listY + VISIBLE_LINES * LINE_HEIGHT;
        int confirmY = listEndY + 30;

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.confirm"), b -> {
                    if (onConfirm != null) {
                        File target = (selectedIndex >= 0 && selectedIndex < entries.size()) ? entries.get(selectedIndex) : currentDir.toFile();

                        boolean isDirectory = target.isDirectory();

                        if (isDirectory) {
                            if (!requireDirectory) {
                                currentDir = target.toPath();
                                reloadEntries();
                                return;
                            } else {
                                onConfirm.accept(target);
                            }
                        } else {
                            onConfirm.accept(target);
                        }
                    }
                }).bounds(hiddenX, confirmY, btnWidth, btnHeight).build());

        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.cancel"), b -> {
            Minecraft.getInstance().setScreen(previousScreen);
        }).bounds(upX, confirmY, btnWidth, btnHeight).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, MARGIN - 10, 0xFFFFFF);

        graphics.drawString(this.font, "Current path: " + currentDir.toString(), MARGIN, MARGIN + 28, 0xAAAAAA, false);

        refreshButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.refresh.description")));
        refreshButton.setTooltipDelay(200);

        homeButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.home.description")));
        homeButton.setTooltipDelay(200);

        upButton.setTooltip(Tooltip.create(Component.translatable("fileChooser.up.description")));
        upButton.setTooltipDelay(200);

        int listX = MARGIN;
        int listY = MARGIN + 48;
        int listW = this.width - 2 * MARGIN;

        graphics.fill(listX - 2, listY - 2, listX + listW + 2, listY + VISIBLE_LINES * LINE_HEIGHT + 6, 0x88000000);

        for (int i = 0; i < VISIBLE_LINES; i++) {
            int idx = i + scroll;
            int y = listY + i * LINE_HEIGHT;
            if (idx >= entries.size()) break;
            File f = entries.get(idx);
            String prefix = f.isDirectory() ? "[DIR] " : "      ";
            int color = f.isDirectory() ? 0xAADDAA : 0xFFFFFF;

            if (idx == selectedIndex) {
                graphics.fill(listX, y - 2, listX + listW, y + LINE_HEIGHT, 0x40FFFFFF);
            }

            graphics.drawString(this.font, prefix + f.getName(), listX + 4, y, color, false);
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (amount < 0)
            scroll = Math.min(scroll + 1, Math.max(0, entries.size() - VISIBLE_LINES));
        else
            scroll = Math.max(scroll - 1, 0);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int listX = MARGIN;
        int listY = MARGIN + 48;
        int listW = this.width - 2 * MARGIN;

        if (mouseX >= listX && mouseX <= listX + listW && mouseY >= listY && mouseY <= listY + VISIBLE_LINES * LINE_HEIGHT) {
            int localY = (int) mouseY - listY;
            int line = localY / LINE_HEIGHT;
            int idx = scroll + line;

            if (idx >= 0 && idx < entries.size()) {
                File f = entries.get(idx);

                long now = System.currentTimeMillis();
                boolean doubleClick = (idx == lastClickedIndex) && (now - lastClickTime < 250);
                lastClickTime = now;
                lastClickedIndex = idx;

                if (doubleClick) {
                    if (f.isDirectory()) {
                        currentDir = f.toPath();
                        reloadEntries();
                    } else {
                        onConfirm.accept(entries.get(selectedIndex));
                    }
                } else {
                    selectedIndex = idx;
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) { // up
            selectedIndex = Math.max(0, selectedIndex - 1);
            if (selectedIndex < scroll) scroll = selectedIndex;
            return true;
        }
        if (keyCode == 256) { // ESC
            Minecraft.getInstance().setScreen(previousScreen);
            return true;
        }
        if (keyCode == 264) { // down
            selectedIndex = Math.min(entries.size() - 1, selectedIndex + 1);
            if (selectedIndex >= scroll + VISIBLE_LINES) scroll = selectedIndex - VISIBLE_LINES + 1;
            return true;
        }
        if (keyCode == 257 || keyCode == 335) { // Enter
            if (selectedIndex >= 0 && selectedIndex < entries.size()) {
                File f = entries.get(selectedIndex);
                if (f.isDirectory()) {
                    currentDir = f.toPath();
                    reloadEntries();
                } else {
                    Minecraft.getInstance().setScreen(null);
                }
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setOnConfirm(Consumer<File> onConfirm) {
        this.onConfirm = onConfirm;
    }

    private void goUp() {
        Path parent = currentDir.getParent();
        if (parent != null) {
            currentDir = parent;
            reloadEntries();
        }
    }

    protected void reloadEntries() {
        File dir = currentDir.toFile();
        File[] files = dir.listFiles();
        if (files == null) files = new File[0];

        entries = getFiles(files).filter(f -> !f.isHidden() || showHidden).sorted(Comparator.comparing((File f) -> !f.isDirectory())
                        .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());

        scroll = 0;
        selectedIndex = -1;
    }

    private void toggleHiddenFiles() {
        showHidden = !showHidden;
        reloadEntries();
        hiddenToggleButton.setMessage(getHiddenFilesButtonLabel());
    }

    public Screen getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(Screen previousScreen) {
        this.previousScreen = previousScreen;
    }

    private Component getHiddenFilesButtonLabel() {
        return Component.translatable("fileChooser.hidden_files_" + (showHidden ? "on" : "off"));
    }

    private void goHome() {
        currentDir = Paths.get(System.getProperty("user.home", "."));
        reloadEntries();
    }
}
