package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class WorldInfoScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private final List<Component> lines = new LinkedList<>();

    private InfoScrollPanel scrollPanel;

    public WorldInfoScreen(LevelStorageSource.LevelStorageAccess levelAccess) {
        super(Component.translatable("selectWorld.info.title"));
        this.levelAccess = levelAccess;
        loadWorldInfo();
    }

    @Override
    protected void init() {
        this.scrollPanel = new InfoScrollPanel(20, 40, this.width - 40, this.height - 120, lines);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, b ->
            Minecraft.getInstance().setScreen(new WorldEditScreen(this.levelAccess))
        ).bounds(this.width / 2 - 130, this.height - 60, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.backToMenu"), b -> {
            try {
                levelAccess.close();
                Minecraft.getInstance().setScreen(new SelectWorldScreen(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).bounds(this.width / 2 + 10, this.height - 60, 120, 20).build());
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx);

        gfx.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        scrollPanel.render(gfx, mouseX, mouseY);

        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scrollPanel.mouseScrolled(mouseX, mouseY, delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void loadWorldInfo() {
        WorldInfoReader reader = new WorldInfoReader(this.levelAccess);
        lines.clear();

        addLine("player", "-");
        addLine("time", reader.getPlayerTimePlayed());
        addLine("mined", reader.getPlayerBlocksDestroyedCount());
        addLine("mobsKilled", reader.getPlayerMobsKilledCount());
        addLine("crafted", reader.getPlayerItemsCraftedCount());
        addLine("deaths", reader.getPlayerDeathsCount());
        addLine("distance", reader.getPlayerDistanceTraveled());
        addLine("achievements", reader.getPlayerFinishedAdvancementsCount());
        addLine("lastPlayed", Instant.ofEpochMilli(reader.getPlayerLastPlayed()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        addLine("world", "-");
        addLine("worldName", reader.getWorldName());
        addLine("seed", reader.getWorldSeed());
        addLine("version", reader.getWorldVersion());
        addLine("size", reader.getWorldSize());
        addLine("difficulty", reader.getWorldDifficulty());
        addLine("gamemode", reader.getWorldGameMode());
        addLine("day", reader.getWorldDay());
        addLine("cheats", reader.getWorldCheatsEnabled());
        addLine("hardcore", reader.getWorldHardcore());
    }

    private void addLine(String name, Object first, Object... other) {
        if (first != null) {
            this.lines.add(Component.translatable("selectWorld.info." + name, "§e" + first, other));
        }
    }

    public static class InfoScrollPanel {

        private static final int LINE_HEIGHT = 18;
        private static final int PADDING = 8;

        private final List<Component> lines;
        private final int x, y, width, height;
        private int scrollAmount = 0;

        public InfoScrollPanel(int x, int y, int width, int height, List<Component> lines) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.lines = lines;
        }

        public void render(GuiGraphics gfx, int mouseX, int mouseY) {
            gfx.fill(x, y, x + width, y + height, 0xAA000000);

            int totalHeight = lines.size() * LINE_HEIGHT + PADDING;
            int maxScroll = Math.max(0, totalHeight - height);
            scrollAmount = Mth.clamp(scrollAmount, 0, maxScroll);

            int startY = y - scrollAmount + PADDING + 2;

            gfx.enableScissor(x, y, x + width, y + height);

            for (Component c : lines) {
                gfx.drawString(Minecraft.getInstance().font, c, x + PADDING, startY, 0xFFFFFF, false);
                startY += LINE_HEIGHT;
            }

            gfx.disableScissor();

            if (totalHeight > height) {
                int barHeight = Math.max(10, (int)((float)height * height / totalHeight));
                int barY = y + (int)((float)scrollAmount / maxScroll * (height - barHeight));

                gfx.fill(x + width - 6, barY, x + width - 2, barY + barHeight, 0xFFFFFFFF);
            }
        }

        public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
            scrollAmount -= delta * 15;
            return true;
        }
    }
}
