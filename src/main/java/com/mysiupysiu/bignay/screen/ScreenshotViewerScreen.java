package com.mysiupysiu.bignay.screen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ScreenshotViewerScreen extends Screen {
    private static final Path SCREENSHOTS_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("screenshots");

    private static final int MIN_COLUMNS = 3;
    private static final int MAX_COLUMNS = 8;
    private int columns = 4;
    private final int gap = 8;

    private int panelX;
    private int panelY;
    private int panelW;
    private int panelH;
    private final int panelTopBottomPadding = 30;

    private int fixedRowImageHeight;

    private int selectedIndex = -1;
    private long lastClickTime = 0L;
    private int lastClickIndex = -1;
    private static final long DOUBLE_CLICK_MS = 350L;
    private static final int SELECT_BORDER_COLOR = 0xFFFFFFFF;
    private static final int SELECT_BORDER_THICKNESS = 1;

    private static final int DARK_BAR_COLOR = 0xA0000000;

    private int prefetchRows = 8;

    private double avgAspectRatio = 9.0 / 16.0;

    private int thumbWidth;
    private final List<Integer> rowHeights = new ArrayList<>();
    private final List<Integer> rowY = new ArrayList<>();
    private int contentHeight = 0;

    private int maxScrollY = 0;
    private int scrollY = 0;

    private final int captionHeight = Minecraft.getInstance().font.lineHeight + 2;

    private List<Path> list;

    private final List<Entry> entries = new ArrayList<>();

    private Button openButton;

    private final int maxConcurrentLoads = Math.min(8, Math.max(3, Runtime.getRuntime().availableProcessors()));
    private final ExecutorService loader = Executors.newFixedThreadPool(maxConcurrentLoads, r -> {
        Thread t = new Thread(r, "screenshot-loader");
        t.setDaemon(true);
        return t;
    });

    private final BitSet queuedIndices = new BitSet();

    private boolean fullscreen = false;
    @Nullable
    private Entry fullscreenEntry = null;

    private volatile boolean layoutDirty = false;
    private long lastScrollTime = 0;
    private static final long SCROLL_GRACE_MS = 120;

    private static final class Entry {
        final Path path;
        final long lastModified;

        volatile ResourceLocation resource;
        volatile DynamicTexture dynamicTexture;
        volatile int origWidth = 0;
        volatile int origHeight = 0;

        volatile boolean loading = false;
        volatile boolean loaded = false;
        volatile boolean failed = false;

        Entry(Path p, long lm) {
            this.path = p;
            this.lastModified = lm;
        }

        void unload(TextureManager tm) {
            if (resource != null) {
                try {
                    tm.release(resource);
                } catch (Throwable ignored) {
                }
                resource = null;
            }
            if (dynamicTexture != null) {
                try {
                    // allow GC
                } catch (Throwable ignored) {
                }
                dynamicTexture = null;
            }
            origWidth = 0;
            origHeight = 0;
            loading = false;
            loaded = false;
            failed = false;
        }
    }

    public ScreenshotViewerScreen() {
        super(Component.translatable("screenshotsViewer.title"));
    }

    @Override
    protected void init() {
        super.init();
        computePanelBounds();
        loadEntries();
        recalcLayout();
        schedulePrefetch(scrollY);

        int y = this.height - 24;

        this.openButton = Button.builder(
                Component.translatable("screenshotsViewer.open"),
                btn -> {
                    if (selectedIndex < 0 || selectedIndex >= entries.size()) return;

                    Entry e = entries.get(selectedIndex);
                    fullscreen = true;
                    fullscreenEntry = e;

                    if ((!e.loaded && !e.loading) || e.failed) {
                        synchronized (queuedIndices) {
                            if (!queuedIndices.get(selectedIndex) && !e.loading && !e.loaded) {
                                queuedIndices.set(selectedIndex);
                                e.loading = true;
                                final int idx = selectedIndex;
                                loader.submit(() -> loadEntry(idx));
                            }
                        }
                    }
                }
        ).bounds(this.width / 2 - 154, y, 72, 20).build();


        this.addRenderableWidget(openButton);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, btn ->
            Minecraft.getInstance().setScreen(null)
        ).bounds(this.width / 2 + 82, y, 72, 20).build());
        updateButtons();
    }

    private void computePanelBounds() {
        int sideMargin = 20;
        panelX = sideMargin;
        panelW = Math.max(100, this.width - sideMargin * 2);
        panelY = panelTopBottomPadding;
        panelH = Math.max(100, this.height - panelTopBottomPadding * 2);
    }

    @Override
    public void removed() {
        super.removed();
        loader.shutdownNow();
        TextureManager tm = Minecraft.getInstance().getTextureManager();
        for (Entry e : entries) e.unload(tm);
    }

    private void loadEntries() {
        entries.clear();
        try {
            if (!Files.exists(SCREENSHOTS_DIR)) return;
            list = Files.list(SCREENSHOTS_DIR)
                    .filter(p -> {
                        String n = p.getFileName().toString();
                        return n.matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}\\.\\d{2}\\.\\d{2}(_\\d+)?\\.png");
                    })
                    .collect(Collectors.toList());

            list.sort(Comparator.comparingLong((Path p) -> {
                try {
                    return Files.getLastModifiedTime(p).toMillis();
                } catch (IOException e) {
                    return 0L;
                }
            }).reversed());

            for (Path p : list) {
                long lm = Files.getLastModifiedTime(p).toMillis();
                entries.add(new Entry(p, lm));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void recalcLayoutPreserveAnchor() {
        int anchorRow = findRowAt(scrollY);
        int offsetInRow = 0;
        if (anchorRow >= 0 && anchorRow < rowY.size()) {
            offsetInRow = scrollY - rowY.get(anchorRow);
            if (offsetInRow < 0) offsetInRow = 0;
        }

        computePanelBounds();
        recalcLayout();

        if (!rowY.isEmpty()) {
            int rowIndex = Math.min(anchorRow, rowY.size() - 1);
            scrollY = rowY.get(rowIndex) + Math.min(offsetInRow, rowHeights.get(rowIndex));
            scrollY = Mth.clamp(scrollY, 0, maxScrollY);
        } else {
            scrollY = 0;
        }
    }

    private void recalcLayout() {
        columns = Mth.clamp(columns, MIN_COLUMNS, MAX_COLUMNS);

        int totalGap = (columns + 1) * gap;
        thumbWidth = Math.max(48, (panelW - totalGap) / columns);

        fixedRowImageHeight = (int) (thumbWidth * avgAspectRatio);
        fixedRowImageHeight = Mth.clamp(fixedRowImageHeight, thumbWidth * 9 / 16, thumbWidth * 3 / 2);

        int rowHeight = fixedRowImageHeight + captionHeight + 2;

        rowHeights.clear();
        rowY.clear();

        int rows = (entries.size() + columns - 1) / columns;

        for (int i = 0; i < rows; i++) {
            rowHeights.add(rowHeight);
        }

        int curY = 2;
        for (int h : rowHeights) {
            rowY.add(curY);
            curY += h + gap;
        }

        contentHeight = curY;
        maxScrollY = Math.max(0, contentHeight - panelH + 10);
        scrollY = Mth.clamp(scrollY, 0, maxScrollY);
    }

    private int findRowAt(int scroll) {
        for (int i = 0; i < rowY.size(); i++) {
            if (rowY.get(i) + rowHeights.get(i) > scroll) return i;
        }
        return Math.max(0, rowY.size() - 1);
    }

    private void schedulePrefetch(int scrollPixel) {
        if (rowHeights.isEmpty()) {
            recalcLayout();
        }

        int firstRow = 0;
        while (firstRow < rowY.size() && rowY.get(firstRow) + rowHeights.get(firstRow) < scrollPixel - gap) firstRow++;

        int visibleRows = 0;
        int h = panelH;
        for (int r = firstRow; r < rowY.size(); r++) {
            int y = rowY.get(r) - scrollPixel;
            if (y > h) break;
            visibleRows++;
        }

        int visibleStart = Math.max(0, firstRow * columns);
        int visibleEnd = Math.min(entries.size(), (firstRow + visibleRows) * columns);

        startLoadingRange(visibleStart, visibleEnd);

        int lastRow = Math.min(rowHeights.size(), firstRow + visibleRows + prefetchRows + 3);
        int prefetchEnd = Math.min(entries.size(), lastRow * columns);

        if (visibleEnd < prefetchEnd) startLoadingRange(visibleEnd, prefetchEnd);

        int keepStart = Math.max(0, visibleStart - (prefetchRows + 2) * columns);
        int keepEnd = Math.min(entries.size(), prefetchEnd + (prefetchRows + 2) * columns);
        unloadFar(keepStart, keepEnd);
    }

    private void startLoadingRange(int startInclusive, int endExclusive) {
        for (int i = startInclusive; i < endExclusive; i++) {
            if (i < 0 || i >= entries.size()) continue;
            Entry e = entries.get(i);
            synchronized (queuedIndices) {
                if (queuedIndices.get(i)) continue;
                if (e.loaded || e.loading) continue;
                queuedIndices.set(i);
                e.loading = true;
            }
            final int idx = i;
            loader.submit(() -> loadEntry(idx));
        }
    }

    private void loadEntry(int index) {
        Entry e = entries.get(index);
        NativeImage img = null;
        try {
            try (InputStream is = Files.newInputStream(e.path)) {
                img = NativeImage.read(is);
            }
            if (img == null) throw new IOException("Failed to read image: " + e.path);

            e.origWidth = img.getWidth();
            e.origHeight = img.getHeight();

            double ar = (double) e.origHeight / (double) e.origWidth;
            if (!layoutDirty) {
                avgAspectRatio = avgAspectRatio * 0.95 + ar * 0.05;
            }

            final NativeImage imgForMain = img;
            final ResourceLocation rl = new ResourceLocation("bignay", "screenshot_" + UUID.randomUUID());

            Minecraft.getInstance().execute(() -> {
                try {
                    DynamicTexture dt = new DynamicTexture(imgForMain);
                    Minecraft.getInstance().getTextureManager().register(rl, dt);
                    e.dynamicTexture = dt;
                    e.resource = rl;
                    e.loaded = true;
                    e.failed = false;
                } catch (Throwable ex) {
                    e.dynamicTexture = null;
                    e.resource = null;
                    e.loaded = false;
                    e.failed = true;
                    try {
                        imgForMain.close();
                    } catch (Throwable ignored) {
                    }
                } finally {
                    e.loading = false;
                    layoutDirty = true;
                    synchronized (queuedIndices) {
                        queuedIndices.clear(index);
                    }
                }
            });
        } catch (Throwable ex) {
            e.loading = false;
            e.failed = true;
            synchronized (queuedIndices) {
                queuedIndices.clear(index);
            }
            if (img != null) {
                try {
                    img.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    private void unloadFar(int keepStart, int keepEnd) {
        TextureManager tm = Minecraft.getInstance().getTextureManager();
        for (int i = 0; i < entries.size(); i++) {
            if (i < keepStart || i >= keepEnd) {
                Entry e = entries.get(i);
                if (e == fullscreenEntry) continue;
                if (e.resource != null || e.dynamicTexture != null || e.loaded || e.loading) {
                    e.unload(tm);
                    synchronized (queuedIndices) {
                        queuedIndices.clear(i);
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int step = rowHeights.isEmpty() ? thumbWidth + gap : rowHeights.get(0);
        scrollY -= (int) (delta * step);
        scrollY = Mth.clamp(scrollY, 0, maxScrollY);
        lastScrollTime = System.currentTimeMillis();

        schedulePrefetch(scrollY);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (fullscreen) {
            fullscreen = false;
            fullscreenEntry = null;
            return true;
        }

        if (mouseX < panelX || mouseX > panelX + panelW || mouseY < panelY || mouseY > panelY + panelH) {

            if (!isMouseOverButton(mouseX, mouseY)) {
                selectedIndex = -1;
                lastClickIndex = -1;
                lastClickTime = 0;
            }

            return super.mouseClicked(mouseX, mouseY, button);
        }

        int idx = 0;
        boolean clickedOnAny = false;
        for (int row = 0; row < rowHeights.size(); row++) {
            for (int col = 0; col < columns; col++) {
                if (idx >= entries.size()) break;
                int x = panelX + gap + col * (thumbWidth + gap);
                int y = panelY + rowY.get(row) - scrollY;
                int rowH = rowHeights.get(row);
                int imageH = rowH - captionHeight - 2;
                if (mouseX >= x && mouseX <= x + thumbWidth && mouseY >= y && mouseY <= y + imageH) {
                    clickedOnAny = true;
                    long now = System.currentTimeMillis();

                    if (lastClickIndex == idx && now - lastClickTime <= DOUBLE_CLICK_MS) {
                        Entry e = entries.get(idx);
                        fullscreen = true;
                        fullscreenEntry = e;

                        if ((!e.loaded && !e.loading) || e.failed) {
                            synchronized (queuedIndices) {
                                if (!queuedIndices.get(idx) && !e.loading && !e.loaded) {
                                    queuedIndices.set(idx);
                                    e.loading = true;
                                    final int finalIdx = idx;
                                    loader.submit(() -> loadEntry(finalIdx));
                                }
                            }
                        }

                        lastClickIndex = -1;
                        lastClickTime = 0;
                        selectedIndex = idx;
                        updateButtons();
                    } else {
                        selectedIndex = idx;
                        lastClickIndex = idx;
                        lastClickTime = now;
                        updateButtons();
                    }

                    return true;
                }
                idx++;
            }
        }

        if (!clickedOnAny) {
            selectedIndex = -1;
            lastClickIndex = -1;
            lastClickTime = 0;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderBackground(gui);

        gui.drawCenteredString(this.font, Component.translatable("screenshotsViewer.title"), this.width / 2, 10, 0xFFFFFF);

        if (layoutDirty && (System.currentTimeMillis() - lastScrollTime) > SCROLL_GRACE_MS) {
            recalcLayoutPreserveAnchor();
            layoutDirty = false;
        }

        if (fullscreen && fullscreenEntry != null && fullscreenEntry.resource != null && fullscreenEntry.origWidth > 0 && fullscreenEntry.origHeight > 0 && fullscreenEntry.loaded) {
            int imgWidth = fullscreenEntry.origWidth;
            int imgHeight = fullscreenEntry.origHeight;

            int padding = gap * 3;
            int maxW = this.width - padding * 2;
            int maxH = this.height - padding * 2;

            double scale = Math.min((double) maxW / imgWidth, (double) maxH / imgHeight);

            int drawW = (int) (imgWidth * scale);
            int drawH = (int) (imgHeight * scale);

            int offsetX = (this.width - drawW) / 2;
            int offsetY = (this.height - drawH) / 2;

            RenderSystem.setShaderTexture(0, fullscreenEntry.resource);
            gui.blit(fullscreenEntry.resource, offsetX, offsetY, 0, 0, drawW, drawH, drawW, drawH);

            gui.drawCenteredString(this.font, fullscreenEntry.path.getFileName().toString(), this.width / 2, offsetY + drawH + 4, 0xFFFFFF);
            super.render(gui, mouseX, mouseY, partialTick);
            return;
        } else if (fullscreen && fullscreenEntry != null) {
            int padding = gap * 3;
            int areaW = this.width - padding * 2;
            int areaH = this.height - padding * 2;
            int boxW = Math.min(areaW, areaH);
            int boxH = boxW;
            int offsetX = (this.width - boxW) / 2;
            int offsetY = (this.height - boxH) / 2;
            gui.fill(offsetX, offsetY, offsetX + boxW, offsetY + boxH, 0xFF333333);
            gui.drawCenteredString(this.font, fullscreenEntry.loading ? "loading..." : "no image", this.width / 2, offsetY + boxH / 2 - 6, 0xFFFFFF);
            gui.drawCenteredString(this.font, fullscreenEntry.path.getFileName().toString(), this.width / 2, offsetY + boxH + 4, 0xFFFFFF);
            super.render(gui, mouseX, mouseY, partialTick);
            return;
        }

        gui.fill(0, panelY - 3, this.width, panelY, DARK_BAR_COLOR);
        gui.fill(0, panelY + panelH, this.width, panelY + panelH + 3, DARK_BAR_COLOR);

        gui.fill(0, panelY, this.width, panelY + panelH, 0x88000000);

        if (rowHeights.isEmpty()) recalcLayout();

        gui.enableScissor(panelX, panelY, panelX + panelW, panelY + panelH);

        int totalRows = rowHeights.size();
        int idx = 0;
        for (int row = 0; row < totalRows; row++) {
            int y = panelY + rowY.get(row) - scrollY;
            int rowH = rowHeights.get(row);
            for (int col = 0; col < columns; col++) {
                if (idx >= entries.size()) break;
                int x = panelX + gap + col * (thumbWidth + gap);
                Entry e = entries.get(idx);
                renderThumbnailSafe(gui, e, x, y, rowH, idx);
                idx++;
            }
        }

        gui.disableScissor();

        int total = list == null ? entries.size() : list.size();
        int totalTextY = panelY + contentHeight - gap - this.font.lineHeight - scrollY + 12;

        if (totalTextY + this.font.lineHeight >= panelY && totalTextY <= panelY + panelH) {
            gui.drawCenteredString(this.font, "Screenshots: " + total, this.width / 2, totalTextY, 0xFFFFFF);
        }


        super.render(gui, mouseX, mouseY, partialTick);
    }

    private String ellipsize(String text, int maxWidth) {
        var font = this.font;

        if (font.width(text) <= maxWidth) {
            return text;
        }

        String dots = "...";
        int dotsWidth = font.width(dots);

        String cut = font.plainSubstrByWidth(text, maxWidth - dotsWidth);
        return cut + dots;
    }


    private void renderThumbnailSafe(GuiGraphics pose, Entry e, int x, int y, int rowHeight, int index) {
        int imageH = rowHeight - captionHeight - 2;
        boolean selected = (index == selectedIndex);

        if (e.resource != null && e.loaded && e.origWidth > 0 && e.origHeight > 0) {
            double scale = (double) thumbWidth / e.origWidth;
            int drawW = thumbWidth;
            int drawH = (int) (e.origHeight * scale);

            int offsetY = y + Math.max(0, (imageH - drawH) / 2);

            RenderSystem.setShaderTexture(0, e.resource);
            pose.blit(e.resource, x, offsetY, 0, 0, drawW, drawH, drawW, drawH);
        } else {
            int estH = Math.min((int) (thumbWidth * avgAspectRatio), imageH);
            int offsetY = y + Math.max(0, (imageH - estH) / 2);
            drawPlaceholder(pose, x, offsetY, thumbWidth, estH, e.loading);
        }

        if (selected) {
            int drawW = thumbWidth;
            int drawH = e.resource != null && e.loaded ? (int) (e.origHeight * ((double) thumbWidth / e.origWidth)) : Math.min((int) (thumbWidth * avgAspectRatio), imageH);
            int offsetY = y + Math.max(0, (imageH - drawH) / 2);

            int bx1 = x - SELECT_BORDER_THICKNESS;
            int by1 = offsetY - SELECT_BORDER_THICKNESS;
            int bx2 = x + drawW + SELECT_BORDER_THICKNESS;
            int by2 = offsetY + drawH + SELECT_BORDER_THICKNESS;

            pose.fill(bx1, by1, bx2, by1 + SELECT_BORDER_THICKNESS, SELECT_BORDER_COLOR);
            pose.fill(bx1, by2 - SELECT_BORDER_THICKNESS, bx2, by2, SELECT_BORDER_COLOR);
            pose.fill(bx1, by1, bx1 + SELECT_BORDER_THICKNESS, by2, SELECT_BORDER_COLOR);
            pose.fill(bx2 - SELECT_BORDER_THICKNESS, by1, bx2, by2, SELECT_BORDER_COLOR);
        }

        String name = e.path.getFileName().toString();
        pose.drawString(this.font, ellipsize(name, thumbWidth), x, y + rowHeight - captionHeight, 0xCCCCCC);
    }

    private void drawPlaceholder(GuiGraphics pose, int x, int y, int w, int h, boolean loading) {
        pose.fill(x, y, x + w, y + h, 0xFF333333);
        pose.drawCenteredString(this.font, Component.translatable("screenshotsViewer.loading" + (loading ? "Image" : "Error")), x + w / 2, y + h / 2 - 4, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        if (key == 256) {
            if (fullscreen) {
                fullscreen = false;
                fullscreenEntry = null;
            } else {
                Minecraft.getInstance().setScreen(null);
            }
            return true;
        }
        return super.keyPressed(key, scancode, modifiers);
    }

    private boolean isMouseOverButton(double mouseX, double mouseY) {
        for (var w : this.renderables) {
            if (w instanceof Button b) {
                if (mouseX >= b.getX() && mouseX <= b.getX() + b.getWidth()
                        && mouseY >= b.getY() && mouseY <= b.getY() + b.getHeight()) {
                    return true;
                }
            }
        }
        return false;
    }


    private void updateButtons() {
        openButton.active = selectedIndex != -1;
    }
}
