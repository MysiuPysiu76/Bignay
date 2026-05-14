package com.mysiupysiu.bignay.client.screen.file.chooser;

import com.mysiupysiu.bignay.config.BignayConfig;
import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FilesSelectionGrid extends ObjectSelectionList<FilesSelectionGrid.RowEntry> {

    private static final int CELL_SIZE = 50;
    private static final int CELL_GAP = 6;
    private static final float ICON_SCALE = 2.5F;
    private static final long DOUBLE_CLICK_MS = 350;

    private final AbstractFileChooserScreen rootScreen;
    private List<File> content = List.of();
    private File selectedFile = null;
    private Path path;
    private Consumer<File> onPathUpdate;

    private long lastClickTime = 0;
    private File lastClickedFile = null;
    private boolean draggingScrollbar = false;
    private int columns = BignayConfig.files.columns.get();

    public FilesSelectionGrid(int width, int height, int top, AbstractFileChooserScreen screen) {
        super(Minecraft.getInstance(), width, height, top, CELL_SIZE + CELL_GAP + 5);
        this.rootScreen = screen;
    }

    @Override
    protected int getScrollbarPosition() {
        int gridWidth = this.columns * CELL_SIZE + (this.columns - 1) * CELL_GAP;
        int startX = this.getX() + (this.width - gridWidth) / 2;
        return startX + gridWidth + 10;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mx = (int) Math.floor(mouseX);
        int my = (int) Math.floor(mouseY);

        int viewTop = this.getY();
        int viewBottom = this.getBottom();
        int viewLeft = this.getX();
        int viewRight = this.getRight();

        if (mx < viewLeft || mx > viewRight || my < viewTop || my > viewBottom) {
            return false;
        }

        int gridWidth = this.columns * CELL_SIZE + (this.columns - 1) * CELL_GAP;
        int startX = this.getRowLeft() + (this.getRowWidth() - gridWidth) / 2;

        List<RowEntry> rows = this.children();
        if (rows.isEmpty()) return false;

        long now = System.currentTimeMillis();

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            int rowTop = this.getRowTop(rowIndex);
            int rowBottom = rowTop + this.itemHeight;

            if (my < rowTop || my > rowBottom) continue;

            RowEntry row = rows.get(rowIndex);
            List<File> files = row.getFiles();

            for (int col = 0; col < files.size(); col++) {
                int drawX = startX + col * (CELL_SIZE + CELL_GAP);
                int drawY = rowTop + 4;
                int drawRight = drawX + CELL_SIZE;
                int drawBottom = drawY + CELL_SIZE;

                if (mx >= drawX && mx <= drawRight && my >= drawY && my <= drawBottom) {
                    File clicked = files.get(col);
                    this.selectedFile = clicked;

                    boolean isDoubleClick = clicked.equals(this.lastClickedFile) && (now - this.lastClickTime) <= DOUBLE_CLICK_MS;
                    this.lastClickTime = now;
                    this.lastClickedFile = clicked;

                    if (isDoubleClick) {
                        this.path = this.selectedFile.toPath();
                        if (clicked.isDirectory()) {
                            this.rootScreen.setPath(this.path);
                            this.setContent(this.rootScreen.getEntries(clicked));
                        } else {
                            this.rootScreen.fileConfirm();
                        }
                    }
                    return true;
                }
            }
        }

        int scrollbarX = getScrollbarPosition();
        if (mx >= scrollbarX && mx <= scrollbarX + 6) {
            this.draggingScrollbar = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (super.mouseDragged(mouseX, mouseY, button, dx, dy)) {
            return true;
        } else if (button == 0 && this.draggingScrollbar) {
            if (mouseY < (double) this.getY()) {
                this.setScrollAmount(0.0D);
            } else if (mouseY > (double) this.getBottom()) {
                this.setScrollAmount(this.getMaxScroll());
            } else {
                double maxScroll = Math.max(1, this.getMaxScroll());
                int height = this.height;
                int j = Mth.clamp((int) ((float) (height * height) / (float) this.getMaxPosition()), 32, height - 8);
                double d1 = Math.max(1.0D, maxScroll / (double) (height - j));
                this.setScrollAmount(this.getScrollAmount() + dy * d1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setSelected(RowEntry entry) {
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.draggingScrollbar = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.content == null || this.content.isEmpty()) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == 258 && this.selectedFile == null) { // TAB
            this.selectFirstIfNone();
            this.setScrollAmount(0.0D);
            return true;
        }

        int itemsPerRow = Math.max(1, this.columns);
        int currentIndex = this.selectedFile != null ? this.content.indexOf(this.selectedFile) : 0;
        if (currentIndex < 0) currentIndex = 0;

        int oldIndex = currentIndex;
        boolean moved = false;
        boolean shift = (modifiers & 1) != 0;

        switch (keyCode) {
            case 265 -> {
                currentIndex = Math.max(0, currentIndex - itemsPerRow);
                moved = true;
            } // UP
            case 264 -> {
                currentIndex = Math.min(this.content.size() - 1, currentIndex + itemsPerRow);
                moved = true;
            } // DOWN
            case 263 -> {
                currentIndex = Math.max(0, currentIndex - 1);
                moved = true;
            } // LEFT
            case 262 -> {
                currentIndex = Math.min(this.content.size() - 1, currentIndex + 1);
                moved = true;
            } // RIGHT
            case 258 -> { // TAB
                currentIndex = shift ? (currentIndex - 1 + this.content.size()) % this.content.size() : (currentIndex + 1) % this.content.size();
                moved = true;
            }
            case 257, 335 -> { // ENTER
                File sel = this.selectedFile != null ? this.selectedFile : (this.content.isEmpty() ? null : this.content.get(0));
                if (sel != null) {
                    if (sel.isDirectory()) {
                        this.path = sel.toPath();
                        this.rootScreen.setPath(this.path);
                        this.setContent(this.rootScreen.getEntries(sel));
                    } else {
                        this.rootScreen.fileConfirm();
                    }
                }
                return true;
            }
        }

        if (moved && currentIndex != oldIndex) {
            this.selectedFile = this.content.get(currentIndex);
            ensureSelectedVisible();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setContent(List<File> files) {
        this.content = files == null ? List.of() : List.copyOf(files);
        this.reload();
        this.selectedFile = null;
        this.setScrollAmount(0.0D);
    }

    @Override
    protected void renderList(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        boolean wasFocused = this.isFocused();
        this.setFocused(false);
        super.renderList(g, mouseX, mouseY, partialTicks);

        if (this.content == null || this.content.isEmpty()) {
            var font = Minecraft.getInstance().font;
            int centerX = this.getX() + this.width / 2;
            int centerY = this.getY() + this.height / 2;
            g.drawCenteredString(font, Component.translatable("fileChooser.emptyFolder"), centerX, centerY, 0xFFFFFF);
        }
        this.setFocused(wasFocused);
    }

    public class RowEntry extends Entry<RowEntry> {
        private final List<File> files;

        public RowEntry(List<File> files) {
            this.files = files;
        }

        public List<File> getFiles() {
            return this.files;
        }

        @Override
        public void render(GuiGraphics g, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks) {
            int gridWidth = columns * CELL_SIZE + (columns - 1) * CELL_GAP;
            int startX = FilesSelectionGrid.this.getX() + (FilesSelectionGrid.this.width - gridWidth) / 2;

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                int drawX = startX + i * (CELL_SIZE + CELL_GAP);
                int drawY = y + 4;

                if (Objects.equals(file, FilesSelectionGrid.this.selectedFile)) {
                    g.fill(drawX - 2, drawY - 2, drawX + CELL_SIZE + 2, drawY + CELL_SIZE + 4, 0x40FFFFFF);
                }

                ItemStack icon = file.isDirectory() ? new ItemStack(Items.CHEST) :
                        (FileUtils.getFileType(file).equals(FileType.ZIP) ? new ItemStack(Items.BOOK) : new ItemStack(Items.PAPER));

                var pose = g.pose();
                pose.pushPose();
                double iconCenterX = drawX + CELL_SIZE / 2.0;
                double iconCenterY = drawY + CELL_SIZE / 2.0 - 8.0;
                pose.translate(iconCenterX, iconCenterY, 200);
                pose.scale(ICON_SCALE, ICON_SCALE, ICON_SCALE);
                g.renderItem(icon, -8, -8);
                g.renderItemDecorations(Minecraft.getInstance().font, icon, -8, -8);
                pose.popPose();

                String cut = FilesSelectionGrid.this.trimToWidth(file.getName(), CELL_SIZE - 4);
                int textX = drawX + (CELL_SIZE - Minecraft.getInstance().font.width(cut)) / 2;
                int textY = drawY + CELL_SIZE - Minecraft.getInstance().font.lineHeight + 2;

                g.drawString(Minecraft.getInstance().font, cut, textX, textY, 0xFFFFFFFF, false);
            }
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }
    }

    public void selectFirstIfNone() {
        if (this.selectedFile == null && this.content != null && !this.content.isEmpty()) {
            this.selectedFile = this.content.get(0);
            ensureSelectedVisible();
        }
    }

    private void ensureSelectedVisible() {
        if (this.selectedFile == null) return;
        int index = this.content.indexOf(this.selectedFile);
        if (index < 0) return;

        int itemsPerRow = Math.max(1, this.columns);
        int row = index / itemsPerRow;

        int targetTop = row * this.itemHeight;
        int targetBottom = targetTop + this.itemHeight;
        double visibleTop = this.getScrollAmount();
        double visibleBottom = visibleTop + this.height;

        if (targetTop < visibleTop) {
            this.setScrollAmount(targetTop);
        } else if (targetBottom > visibleBottom) {
            this.setScrollAmount(targetBottom - this.height);
        }
    }

    public File getSelectedFile() {
        return this.selectedFile == null ? (this.path != null ? this.path.toFile() : null) : this.selectedFile;
    }

    private String trimToWidth(String s, int maxWidth) {
        if (Minecraft.getInstance().font.width(s) <= maxWidth) return s;
        String ellipsis = "...";
        int avail = maxWidth - Minecraft.getInstance().font.width(ellipsis);
        while (!s.isEmpty() && Minecraft.getInstance().font.width(s) > avail) {
            s = s.substring(0, s.length() - 1);
        }
        return s + ellipsis;
    }

    public void setPath(Path path) {
        this.path = path;
        if (this.onPathUpdate != null) this.onPathUpdate.accept(this.path.toFile());
        if (rootScreen.isRequireDirectory()) this.selectedFile = path.toFile();
    }

    public Path getPath() {
        return this.path;
    }

    public void setOnPathUpdate(Consumer<File> c) {
        this.onPathUpdate = c;
    }

    public void setColumns(int columns) {
        this.columns = Math.max(1, columns);
        this.reload();
    }

    private void reload() {
        this.clearEntries();
        if (content.isEmpty()) return;

        List<File> row = new ArrayList<>(this.columns);
        for (File f : this.content) {
            row.add(f);
            if (row.size() == this.columns) {
                addEntry(new RowEntry(new ArrayList<>(row)));
                row.clear();
            }
        }
        if (!row.isEmpty()) addEntry(new RowEntry(new ArrayList<>(row)));
    }
}
