package com.mysiupysiu.bignay.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileChooserScreen extends Screen {
    private static final int MARGIN = 20;
    private static final int LINE_HEIGHT = 11;
    private static final int VISIBLE_LINES = 16;

    private Path currentDir;
    private List<File> entries;
    private Consumer<File> onConfirm;
    private int scroll = 0;
    private int selectedIndex = -1;

    private long lastClickTime = 0;
    private int lastClickedIndex = -1;

    public FileChooserScreen() {
        super(Component.translatable("fileChooser.title"));
        String home = System.getProperty("user.home", ".");
        currentDir = Paths.get(home);
        reloadEntries();
    }

    private void reloadEntries() {
        File dir = currentDir.toFile();
        File[] files = dir.listFiles();
        if (files == null) files = new File[0];

        entries = Arrays.stream(files)
                .sorted(Comparator.comparing((File f) -> !f.isDirectory())
                        .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        scroll = 0;
        selectedIndex = -1;
    }

    @Override
    protected void init() {
        super.init();

        int btnWidth = 90;
        int btnHeight = 20;

        int centerX = this.width / 2;

        int listY = MARGIN + 48;
        int listEndY = listY + VISIBLE_LINES * LINE_HEIGHT;

        int confirmY = listEndY + 10;
        this.addRenderableWidget(Button.builder(Component.translatable("fileChooser.confirm"), b -> {
                    if (selectedIndex >= 0 && selectedIndex < entries.size()) {
                        File chosenFile = entries.get(selectedIndex);
                        if (onConfirm != null) {
                            onConfirm.accept(chosenFile);
                        }
                        Minecraft.getInstance().setScreen(null);
                    }
                })
                .bounds(centerX - btnWidth / 2, confirmY, btnWidth, btnHeight)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, MARGIN - 10, 0xFFFFFF);

        graphics.drawString(this.font, "Current path: " + currentDir.toString(), MARGIN, MARGIN + 28, 0xAAAAAA, false);

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
                        Minecraft.getInstance().setScreen(null);
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
}
