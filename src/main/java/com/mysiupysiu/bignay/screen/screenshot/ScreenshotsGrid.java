package com.mysiupysiu.bignay.screen.screenshot;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class ScreenshotsGrid extends ObjectSelectionList<ScreenshotsGrid.RowEntry> {
    private final ScreenshotsViewerScreen parent;
    private final List<Path> allPaths = new ArrayList<>();
    private final Map<Path, TextureHolder> textures = new HashMap<>();

    private final Set<Integer> selectedIndices = new HashSet<>();
    private int lastSelectedIndex = -1;
    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_MS = 350L;

    private static boolean showFileExtension = false;
    private static boolean showScreenName = true;

    private final int thumbWidth;
    private final int thumbHeight;
    private final int textHeight;
    private final int gap;
    private final int columns;

    private final ExecutorService loader = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r, "screenshot-loader");
        t.setDaemon(true);
        return t;
    });

    public static boolean isShowScreenName() {
        return showScreenName;
    }

    public static void setShowScreenName(boolean showScreenName) {
        ScreenshotsGrid.showScreenName = showScreenName;
    }

    public static boolean isShowFileExtension() {
        return showFileExtension;
    }

    public static void setShowFileExtension(boolean showFileExtension) {
        ScreenshotsGrid.showFileExtension = showFileExtension;
    }

    public ScreenshotsGrid(Minecraft mc, int width, int height, int top, int bottom, int itemHeight, int thumbWidth, int thumbHeight, int textHeight, int gap, int columns, ScreenshotsViewerScreen parent) {
        super(mc, width, height, top, bottom, itemHeight);
        this.parent = parent;
        this.thumbWidth = Math.max(1, thumbWidth);
        this.thumbHeight = Math.max(1, thumbHeight);
        this.textHeight = Math.max(0, textHeight);
        this.gap = gap;
        this.columns = columns;
    }

    @Override
    protected boolean isSelectedItem(int index) {
        return false;
    }

    public void refresh(List<Path> paths) {
        textures.values().forEach(TextureHolder::unload);
        textures.clear();
        this.clearEntries();
        this.allPaths.clear();
        this.allPaths.addAll(paths);
        this.selectedIndices.clear();
        this.lastSelectedIndex = -1;

        for (int i = 0; i < paths.size(); i += columns) {
            int end = Math.min(i + columns, paths.size());
            this.addEntry(new RowEntry(paths.subList(i, end), i));
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        super.render(gui, mouseX, mouseY, partialTick);

        if (allPaths.isEmpty()) {
            gui.drawCenteredString(Minecraft.getInstance().font, Component.translatable("screenshotsViewer.empty"), this.width / 2, this.y0 + (this.y1 - this.y0) / 2, 0x777777);
        } else {
            manageMemory();
        }
    }

    private void manageMemory() {
        if (this.children().isEmpty()) return;

        int firstVisibleRow = (int) (this.getScrollAmount() / this.itemHeight);
        int visibleRows = this.height / this.itemHeight + 2;

        int preloadStartRow = Math.max(0, firstVisibleRow - 5);
        int preloadEndRow = Math.min(this.children().size() - 1, firstVisibleRow + visibleRows + 5);

        int keepStartRow = Math.max(0, firstVisibleRow - 12);
        int keepEndRow = Math.min(this.children().size() - 1, firstVisibleRow + visibleRows + 12);

        for (int i = 0; i < this.children().size(); i++) {
            RowEntry row = this.children().get(i);

            if (i < keepStartRow || i > keepEndRow) {
                for (Path p : row.rowPaths) {
                    TextureHolder holder = textures.get(p);
                    if (holder != null) holder.unload();
                }
            } else if (i >= preloadStartRow && i <= preloadEndRow) {
                for (Path p : row.rowPaths) {
                    textures.computeIfAbsent(p, TextureHolder::new).load();
                }
            }
        }
    }

    public void cleanup() {
        loader.shutdownNow();
        textures.values().forEach(TextureHolder::unload);
        textures.clear();
    }

    @Override
    public int getRowWidth() {
        return this.width - 50;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 12;
    }

    public int getSelectedCount() {
        return selectedIndices.size();
    }

    public Stream<Path> getSelectedPaths() {
        return selectedIndices.stream().sorted(Comparator.reverseOrder()).map(allPaths::get);
    }

    public List<Path> getAllPaths() {
        return allPaths;
    }

    public void selectAll() {
        selectedIndices.clear();
        for (int i = 0; i < allPaths.size(); i++) {
            selectedIndices.add(i);
        }
        if (!allPaths.isEmpty()) {
            lastSelectedIndex = allPaths.size() - 1;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (allPaths.isEmpty()) return false;

        if (lastSelectedIndex == -1) {
            if (keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_RIGHT) {
                setSelectedIndex(0, Screen.hasShiftDown());
                return true;
            }
        } else {
            int newIndex = lastSelectedIndex;
            if (keyCode == GLFW.GLFW_KEY_LEFT) newIndex--;
            else if (keyCode == GLFW.GLFW_KEY_RIGHT) newIndex++;
            else if (keyCode == GLFW.GLFW_KEY_UP) newIndex -= columns;
            else if (keyCode == GLFW.GLFW_KEY_DOWN) newIndex += columns;

            if (newIndex >= 0 && newIndex < allPaths.size() && newIndex != lastSelectedIndex) {
                setSelectedIndex(newIndex, Screen.hasShiftDown());
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void clearSelection() {
        if (!selectedIndices.isEmpty()) {
            selectedIndices.clear();
            lastSelectedIndex = -1;
            parent.updateButtons();
        }
    }

    private void setSelectedIndex(int index, boolean shift) {
        if (shift && lastSelectedIndex != -1) {
            selectedIndices.clear();
            int start = Math.min(lastSelectedIndex, index);
            int end = Math.max(lastSelectedIndex, index);
            for (int i = start; i <= end; i++) selectedIndices.add(i);
        } else if (!Screen.hasControlDown()) {
            selectedIndices.clear();
            selectedIndices.add(index);
        } else {
            selectedIndices.add(index);
        }

        lastSelectedIndex = index;
        parent.updateButtons();

        int row = index / columns;
        this.ensureVisible(this.children().get(row));
    }

    private class TextureHolder {
        Path path;
        ResourceLocation resource;
        volatile boolean loading = false;
        volatile boolean shouldUnload = false;

        TextureHolder(Path p) {
            this.path = p;
        }

        void load() {
            if (loading || resource != null) return;
            loading = true;
            shouldUnload = false;

            loader.submit(() -> {
                try (InputStream is = Files.newInputStream(path)) {
                    NativeImage img = NativeImage.read(is);
                    Minecraft.getInstance().execute(() -> {
                        if (shouldUnload) {
                            img.close();
                            loading = false;
                            return;
                        }
                        resource = new ResourceLocation("bignay", "thumb_" + UUID.randomUUID());
                        Minecraft.getInstance().getTextureManager().register(resource, new DynamicTexture(img));
                        loading = false;
                    });
                } catch (IOException e) {
                    loading = false;
                }
            });
        }

        void unload() {
            shouldUnload = true;
            if (resource != null) {
                Minecraft.getInstance().getTextureManager().release(resource);
                resource = null;
            }
        }
    }

    public class RowEntry extends ObjectSelectionList.Entry<RowEntry> {
        private final List<Path> rowPaths;
        private final int startIndex;

        public RowEntry(List<Path> paths, int startIndex) {
            this.rowPaths = paths;
            this.startIndex = startIndex;
        }

        @Override
        public void render(GuiGraphics gui, int index, int slotY, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTick) {
            int totalRowContentWidth = (columns * thumbWidth) + ((columns - 1) * gap);
            int startX = x + (rowWidth - totalRowContentWidth) / 2;

            for (int i = 0; i < rowPaths.size(); i++) {
                int globalIdx = startIndex + i;
                Path p = rowPaths.get(i);

                int slotX = startX + i * (thumbWidth + gap);

                textures.computeIfAbsent(p, TextureHolder::new);
                TextureHolder holder = textures.get(p);

                int pad = 1;
                gui.fill(slotX - pad, slotY - pad, slotX + thumbWidth + pad, slotY + thumbHeight + pad, 0x66000000);

                if (holder != null && holder.resource != null) {
                    RenderSystem.setShaderTexture(0, holder.resource);
                    gui.blit(holder.resource, slotX, slotY, 0, 0, thumbWidth, thumbHeight, thumbWidth, thumbHeight);
                }

                if (selectedIndices.contains(globalIdx)) {
                    renderOutline(gui, slotX - pad + 1, slotY - pad + 1, thumbWidth + pad * 2 - 2, thumbHeight + pad * 2 - 2);
                }

                if (textHeight > 0) {
                    String name = p.getFileName().toString();
                    if (!showFileExtension) name = name.replaceAll("\\.png$", "");

                    String display = Minecraft.getInstance().font.plainSubstrByWidth(name, thumbWidth);
                    int nameW = Minecraft.getInstance().font.width(display);
                    int nameX = slotX + (thumbWidth - nameW) / 2;
                    int nameY = slotY + thumbHeight + 3;

                    gui.fill(nameX - 2, nameY - 1, nameX + nameW + 2, nameY + Minecraft.getInstance().font.lineHeight, 0x22000000);
                    gui.drawString(Minecraft.getInstance().font, display, nameX, nameY, 0xAAAAAA, false);
                }
            }
        }

        private void renderOutline(GuiGraphics gui, int x, int y, int w, int h) {
            gui.fill(x - 1, y - 1, x + w + 1, y, 0xFFFFFFFF);
            gui.fill(x - 1, y + h, x + w + 1, y + h + 1, 0xFFFFFFFF);
            gui.fill(x - 1, y, x, y + h, 0xFFFFFFFF);
            gui.fill(x + w, y, x + w + 1, y + h, 0xFFFFFFFF);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int totalRowContentWidth = (columns * thumbWidth) + ((columns - 1) * gap);
            int startX = getRowLeft() + (getRowWidth() - totalRowContentWidth) / 2;
            int rowIndex = ScreenshotsGrid.this.children().indexOf(this);
            int slotY = ScreenshotsGrid.this.getRowTop(rowIndex);

            for (int i = 0; i < rowPaths.size(); i++) {
                int slotX = startX + i * (thumbWidth + gap);

                if (mouseX >= slotX && mouseX <= slotX + thumbWidth && mouseY >= slotY && mouseY <= slotY + thumbHeight) {
                    handleSelection(startIndex + i);
                    return true;
                }
            }
            return false;
        }

        private void handleSelection(int currentIndex) {
            long now = System.currentTimeMillis();

            if (currentIndex == lastSelectedIndex && now - lastClickTime <= DOUBLE_CLICK_MS) {
                parent.openScreenshot(currentIndex);
                return;
            }

            if (Screen.hasControlDown()) {
                if (!selectedIndices.remove(currentIndex)) selectedIndices.add(currentIndex);
            } else if (Screen.hasShiftDown() && lastSelectedIndex != -1) {
                selectedIndices.clear();
                int start = Math.min(lastSelectedIndex, currentIndex);
                int end = Math.max(lastSelectedIndex, currentIndex);
                for (int i = start; i <= end; i++) selectedIndices.add(i);
            } else {
                selectedIndices.clear();
                selectedIndices.add(currentIndex);
            }

            lastSelectedIndex = currentIndex;
            lastClickTime = now;
            parent.updateButtons();
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.literal("Row");
        }
    }
}
