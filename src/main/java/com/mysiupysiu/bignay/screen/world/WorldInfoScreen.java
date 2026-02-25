package com.mysiupysiu.bignay.screen.world;

import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;

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
        this.infoList = new WorldInfoList(this.minecraft, this.width, this.height, 40, this.height - 80, 18);

        this.addWidget(this.infoList);

        loadWorldInfo();

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, b -> this.minecraft.setScreen(new WorldEditScreen(this.levelAccess))).bounds(this.width / 2 - 130, this.height - 60, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.backToMenu"), b -> {
            try {
                this.levelAccess.close();
                this.minecraft.setScreen(new SelectWorldScreen(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).bounds(this.width / 2 + 10, this.height - 60, 120, 20).build());
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx);

        this.infoList.render(gfx, mouseX, mouseY, partialTicks);
        gfx.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    private void loadWorldInfo() {
        WorldInfoReader reader = new WorldInfoReader(this.levelAccess);
        this.infoList.refreshList();

        addInfoLine("player", "-");
        addInfoLine("time", reader.getPlayerTimePlayed());
        addInfoLine("mined", reader.getPlayerBlocksDestroyedCount());
        addInfoLine("mobsKilled", reader.getPlayerMobsKilledCount());
        addInfoLine("crafted", reader.getPlayerItemsCraftedCount());
        addInfoLine("deaths", reader.getPlayerDeathsCount());
        addInfoLine("distance", reader.getPlayerDistanceTraveled());
        addInfoLine("achievements", reader.getPlayerFinishedAdvancementsCount());
        addInfoLine("lastPlayed", formatDate(reader.getPlayerLastPlayed()));

        addInfoLine("world", "-");
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

    private void addInfoLine(String name, Object value) {
        if (value != null) {
            Component text = Component.translatable("selectWorld.info." + name, "§e" + value);
            this.infoList.addEntry(new WorldInfoEntry(text));
        }
    }

    private String formatDate(Long l) {
        if (l == null) return null;
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    class WorldInfoList extends ObjectSelectionList<WorldInfoEntry> {
        public WorldInfoList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
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
            return 300;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width / 2 + 150;
        }
    }

    class WorldInfoEntry extends ObjectSelectionList.Entry<WorldInfoEntry> {
        private final Component text;

        public WorldInfoEntry(Component text) {
            this.text = text;
        }

        @Override
        public void render(GuiGraphics gfx, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            gfx.drawString(WorldInfoScreen.this.font, this.text, left, top + 2, 0xFFFFFF, false);
        }

        @Override
        public Component getNarration() {
            return this.text;
        }
    }
}
