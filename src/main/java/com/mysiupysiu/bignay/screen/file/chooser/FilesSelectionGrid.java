package com.mysiupysiu.bignay.screen.file.chooser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class FilesSelectionGrid extends ObjectSelectionList<FilesSelectionGrid.RowEntry> {

    private final AbstractFileChooserScreen rootScreen;
    private List<File> content = List.of();
    private File selectedFile = null;
    private Path path;
    private Consumer<File> onPathUpdate;

    private static final int CELL_SIZE = 50;
    private static final int CELL_GAP = 6;
    private static final float ICON_SCALE = 2.5F;

    private long lastClickTime = 0;
    private File lastClickedFile = null;
    private static final long DOUBLE_CLICK_MS = 350;
    private boolean draggingScrollbar = false;
    private int columns = 6;

    public FilesSelectionGrid(int width, int height, int top, int bottom, AbstractFileChooserScreen screen) {
        super(Minecraft.getInstance(), width, height, top, bottom, CELL_SIZE + CELL_GAP + 5);
        this.rootScreen = screen;
    }

    @Override
    protected int getScrollbarPosition() {
        int gridWidth = this.columns * CELL_SIZE + (this.columns - 1) * CELL_GAP;
        int startX = this.getLeft() + (this.width - gridWidth) / 2;
        return startX + gridWidth + 10;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mx = (int) Math.floor(mouseX);
        int my = (int) Math.floor(mouseY);

        int viewTop = this.getTop();
        int viewBottom = this.getBottom();
        int viewLeft = this.getLeft();
        int viewRight = this.getRight() - 8;

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

                    boolean isDoubleClick = clicked.equals(lastClickedFile) && (now - lastClickTime) <= DOUBLE_CLICK_MS;

                    this.lastClickTime = now;
                    this.lastClickedFile = clicked;

                    if (isDoubleClick) {
                        this.path = selectedFile.toPath();
                        if (clicked.isDirectory()) {
                            this.rootScreen.setPath(path);
                            this.setContent(rootScreen.getEntries(clicked));
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
        if (super.mouseDragged(mouseY, mouseY, button, dx, dy)) {
            return true;
        } else if (button == 0 && this.draggingScrollbar) {
            if (mouseY < (double)this.y0) {
                this.setScrollAmount(0.0D);
            } else if (mouseY > (double)this.y1) {
                this.setScrollAmount(this.getMaxScroll());
            } else {
                double d0 = Math.max(1, this.getMaxScroll());
                int i = this.y1 - this.y0;
                int j = Mth.clamp((int)((float)(i * i) / (float)this.getMaxPosition()), 32, i - 8);
                double d1 = Math.max(1.0D, d0 / (double)(i - j));
                this.setScrollAmount(this.getScrollAmount() + dy * d1);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingScrollbar = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public class RowEntry extends ObjectSelectionList.Entry<RowEntry> {
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
            int startX = FilesSelectionGrid.this.getRowLeft() + (FilesSelectionGrid.this.getRowWidth() - gridWidth) / 2;

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);

                int drawX = startX + i * (CELL_SIZE + CELL_GAP);
                int drawY = y + 4;

                if (Objects.equals(file, FilesSelectionGrid.this.selectedFile)) {
                    g.fill(drawX - 2, drawY - 2, drawX + CELL_SIZE + 2, drawY + CELL_SIZE + 4, 0x40FFFFFF);
                }

                ItemStack icon = file.isDirectory() ? new ItemStack(Items.CHEST) : new ItemStack(Items.PAPER);

                var pose = g.pose();
                pose.pushPose();
                double iconCenterX = drawX + CELL_SIZE / 2.0;
                double iconCenterY = drawY + CELL_SIZE / 2.0 - 8.0;
                pose.translate(iconCenterX, iconCenterY, 200);
                pose.scale(ICON_SCALE, ICON_SCALE, ICON_SCALE);
                g.renderItem(icon, -8, -8);
                g.renderItemDecorations(Minecraft.getInstance().font, icon, -8, -8);
                pose.popPose();

                String name = file.getName();
                String cut = FilesSelectionGrid.this.trimToWidth(name, CELL_SIZE - 4);
                int textWidth = Minecraft.getInstance().font.width(cut);
                int textX = drawX + (CELL_SIZE - textWidth) / 2;
                int textY = drawY + CELL_SIZE - Minecraft.getInstance().font.lineHeight + 2;

                g.drawString(Minecraft.getInstance().font, Component.literal(cut), textX, textY, 0xFFFFFFFF, false);
            }
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }
    }

    public File getSelectedFile() {
        return this.selectedFile;
    }

    private String trimToWidth(String s, int maxWidth) {
        if (Minecraft.getInstance().font.width(s) <= maxWidth) return s;
        String ellipsis = "...";
        int avail = maxWidth - Minecraft.getInstance().font.width(ellipsis);
        if (avail <= 0) return s.substring(0, Math.max(0, Math.min(s.length(), 3))) + ellipsis;
        while (!s.isEmpty() && Minecraft.getInstance().font.width(s) > avail) {
            s = s.substring(0, s.length() - 1);
        }
        return s + ellipsis;
    }


    public void setPath(Path path) {
        this.path = path;
        if (this.onPathUpdate != null) this.onPathUpdate.accept(this.path.toFile());
    }

    public Path getPath() {
        return this.path;
    }

    public void setOnPathUpdate(Consumer<File> c) {
        this.onPathUpdate = c;
    }

    public void setContent(List<File> files) {
        this.content = files == null ? List.of() : List.copyOf(files);
        this.reload();
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
