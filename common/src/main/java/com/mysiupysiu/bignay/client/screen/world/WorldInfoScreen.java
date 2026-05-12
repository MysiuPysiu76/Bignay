package com.mysiupysiu.bignay.client.screen.world;

import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WorldInfoScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private WorldInfoList infoList;

    public WorldInfoScreen(LevelStorageSource.LevelStorageAccess levelAccess) {
        super(Component.translatable("selectWorld.info.title"));
        this.levelAccess = levelAccess;
    }

    @Override
    protected void init() {
        this.infoList = new WorldInfoList(this.width, this.height - 95);
        this.addRenderableWidget(this.infoList);

        this.loadWorldInfo();

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, b -> {
            try {
                this.minecraft.setScreen(EditWorldScreen.create(Minecraft.getInstance(), this.levelAccess, null));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).bounds(this.width / 2 - 125, this.height - 40, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.backToMenu"), b -> {
            try {
                this.levelAccess.close();
                this.minecraft.setScreen(new SelectWorldScreen(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).bounds(this.width / 2 + 5, this.height - 40, 120, 20).build());
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        super.renderBackground(gfx, mouseX, mouseY, delta);
        this.infoList.render(gfx, mouseX, mouseY, delta);
        gfx.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(gfx, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        if (key == 256) { // ESC
            try {
                this.levelAccess.close();
                this.minecraft.setScreen(new SelectWorldScreen(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return super.keyPressed(key, scancode, modifiers);
    }

    private void loadWorldInfo() {
        WorldInfoReader reader = new WorldInfoReader(this.levelAccess);
        this.infoList.refreshList();

        addSection("player");
        addInfoLine("time", reader.getPlayerTimePlayed());
        addInfoLine("mined", reader.getPlayerBlocksDestroyedCount());
        addInfoLine("mobsKilled", reader.getPlayerMobsKilledCount());
        addInfoLine("crafted", reader.getPlayerItemsCraftedCount());
        addInfoLine("deaths", reader.getPlayerDeathsCount());
        addInfoLine("distance", reader.getPlayerDistanceTraveled());
        addInfoLine("achievements", reader.getPlayerFinishedAdvancementsCount());
        addInfoLine("lastPlayed", formatDate(reader.getPlayerLastPlayed()));

        addSection("world");
        addInfoLine("worldName", reader.getWorldName());
        addInfoLine("createdDate", formatDate(reader.getWorldCreatedDate()));
        addInfoLine("seed", reader.getWorldSeed());
        addInfoLine("version", reader.getWorldVersion());
        addInfoLine("size", reader.getWorldSize());
        addInfoLine("difficulty", reader.getWorldDifficulty());
        addInfoLine("gamemode", reader.getWorldGameMode());
        addInfoLine("day", reader.getWorldDay());
        addInfoLine("cheats", reader.getWorldCheatsEnabled());
        addInfoLine("hardcore", reader.getWorldHardcore());
    }

    private void addSection(String key) {
        this.infoList.addEntry(new WorldInfoEntry(Component.translatable("selectWorld.info." + key).withStyle(s -> s.withBold(true).withColor(0xFFAA00))));
    }

    private void addInfoLine(String name, Object value) {
        if (value != null) {
            Component text = Component.translatable("selectWorld.info." + name, "§e" + value);
            this.infoList.addEntry(new WorldInfoEntry(text));
        }
    }

    private String formatDate(Long l) {
        if (l == null || l <= 0) return "-";
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    class WorldInfoList extends ObjectSelectionList<WorldInfoEntry> {

        public WorldInfoList(int w, int h) {
            super(Minecraft.getInstance(), w, h, 40, 18);
        }

        public void refreshList() {
            this.clearEntries();
        }

        @Override
        public int addEntry(WorldInfoEntry entry) {
            return super.addEntry(entry);
        }

        @Override
        public int getRowWidth() {
            return 280;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width / 2 + 145;
        }
    }

    class WorldInfoEntry extends ObjectSelectionList.Entry<WorldInfoEntry> {
        private final Component text;

        public WorldInfoEntry(Component text) {
            this.text = text;
        }

        @Override
        public void render(GuiGraphics gfx, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            gfx.drawString(WorldInfoScreen.this.font, this.text, left, top + 2, 0xFFFFFF);
        }

        @Override
        public @NotNull Component getNarration() {
            return this.text;
        }
    }
}
