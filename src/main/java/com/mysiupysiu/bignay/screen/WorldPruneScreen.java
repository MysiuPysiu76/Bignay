package com.mysiupysiu.bignay.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorldPruneScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private final Screen previous;
    private TimeUnitOption selectedUnit = TimeUnitOption.DAYS;
    private Component waring = Component.translatable("selectWorld.edit.prune.warning", 1, "DAYS");

    public WorldPruneScreen(LevelStorageSource.LevelStorageAccess levelAccess, Screen previous) {
        super(Component.translatable("selectWorld.edit.prune"));
        this.levelAccess = levelAccess;
        this.previous = previous;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        EditBox unitInput = new EditBox(this.font, centerX - 115, centerY - 50, 80, 20, Component.literal("1"));
        unitInput.setFilter(text -> text.matches("\\d*"));
        unitInput.setValue("1");
        this.addRenderableWidget(unitInput);

        CycleButton<TimeUnitOption> unitType = CycleButton.<TimeUnitOption>builder(option -> Component.translatable("timeUnit." + option.name().toLowerCase()))
                .withValues(TimeUnitOption.values())
                .withInitialValue(selectedUnit)
                .create(centerX + 25, centerY - 50, 110, 20,
                        Component.translatable("timeUnit.unit"), (button, value) -> {
                            selectedUnit = value;
                            updateWarning(unitInput.getValue(), value);
                });
        this.addRenderableWidget(unitType);

        Button processButton = Button.builder(CommonComponents.GUI_PROCEED, btn -> {
            try {
                File worldFile = this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile();

                Stream.of("/", "DIM1", "DIM-1").forEach(f -> {
                    try {
                        File root = new File(worldFile, f);
                        if (root.exists()) {
                            File region = new  File(root, "region");
                            if (region.exists()) {
                                Set<String> files = Files.walk(region.toPath())
                                        .filter(p -> isOlderThan(p, calculateTime(unitInput.getValue(), unitType.getValue())))
                                        .map(p -> p.getFileName().toString())
                                        .collect(Collectors.toSet());

                                files.forEach(file -> {
                                    Set.of("entities", "poi", "region").forEach(folder -> {
                                        File currentFile = new File(new File(root, folder), file);
                                        if (currentFile.exists()) currentFile.delete();
                                    });
                                });
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.minecraft.setScreen(previous);
        }).bounds(centerX - 135, centerY + 30, 120, 20).build();

        this.addRenderableWidget(processButton);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, b -> this.minecraft.setScreen(this.previous))
                .bounds(centerX + 15, centerY + 30, 130, 20).build());

        unitInput.setResponder(s -> {
            processButton.active = !s.isBlank();
            updateWarning(unitInput.getValue(), unitType.getValue());
        });

        super.init();
    }

    @Override
    public void render(GuiGraphics gui, int p_281550_, int p_282878_, float p_282465_) {
        this.renderBackground(gui);
        int centerX = this.width / 2;

        gui.drawCenteredString(this.font, this.title, centerX, 15, 16777215);
        gui.drawCenteredString(this.font, Component.translatable("selectWorld.edit.prune.settings"), centerX, 40, 16777215);
        gui.drawCenteredString(this.font, waring, centerX, this.height / 2, 10526880);

        super.render(gui, p_281550_, p_282878_, p_282465_);
    }

    private static boolean isOlderThan(Path path, Instant border) {
        try {
            FileTime lastModified = Files.getLastModifiedTime(path);
            return lastModified.toInstant().isBefore(border);
        } catch (IOException e) {
            return false;
        }
    }

    private Instant calculateTime(String unit, TimeUnitOption option) {
        return ZonedDateTime.now().minus(Integer.parseInt(unit), option.unit).toInstant();
    }

    private void updateWarning(String unit, TimeUnitOption option) {
        this.waring = Component.translatable("selectWorld.edit.prune.warning", unit, option);
    }

    private enum TimeUnitOption {
        MINUTES(ChronoUnit.MINUTES),
        HOURS(ChronoUnit.HOURS),
        DAYS(ChronoUnit.DAYS),
        WEEKS(ChronoUnit.WEEKS),
        MONTHS(ChronoUnit.MONTHS),
        YEARS(ChronoUnit.YEARS);

        public final ChronoUnit unit;

        TimeUnitOption(ChronoUnit unit) {
            this.unit = unit;
        }
    }
}
