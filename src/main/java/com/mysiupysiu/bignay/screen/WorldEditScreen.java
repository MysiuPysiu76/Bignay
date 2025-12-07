package com.mysiupysiu.bignay.screen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.worldselection.*;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.ContentValidationException;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

import static net.minecraft.network.chat.Component.translatable;

public class WorldEditScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private EditBox worldNameEdit;

    public WorldEditScreen(EditWorldScreen screen) throws Exception {
        super(translatable("selectWorld.edit.title"));
        this.levelAccess = getLevelAccess(screen);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        this.worldNameEdit = new EditBox(this.font, centerX - 100, 35, 200, 20, Component.literal("World name"));
        this.worldNameEdit.setValue(this.levelAccess.getSummary().getLevelName());
        this.addRenderableWidget(this.worldNameEdit);

        int columnWidth = 150;

        int startX_Left = centerX - columnWidth - 5;
        int startX_Right = centerX + 5;
        int startY = this.height / 4 + 5;

        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Left, startY, 150, 20).build());
        this.addRenderableWidget(getOpenWorldFolderButton().bounds(startX_Right, startY, 150, 20).build());

        this.addRenderableWidget(getResetIconButton().bounds(startX_Left,startY + 24, 150, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Right, startY + 24, 150, 20).build());

        this.addRenderableWidget(getMakeBackupButton().bounds(startX_Left, startY + 48, 150, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Right, startY + 48, 150, 20).build());

        this.addRenderableWidget(getOptimizeWorldButton().bounds(startX_Left, startY + 72, 150, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Right, startY + 72, 150, 20).build());

        this.addRenderableWidget(getRecreateButton().bounds(startX_Left, startY + 96, 150, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Right, startY + 96, 150, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal(""), b -> {}).bounds(startX_Left, startY + 120, 150, 20).build());
        this.addRenderableWidget(getExportButton().bounds(startX_Right, startY + 120, 150, 20).build());

        int bottomY = this.height / 4 + 149;
        Button saveButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.edit.save"), b -> onSave()).bounds(centerX - 135, bottomY, 130, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, b -> onClose()).bounds(centerX + 5, bottomY, 130, 20).build());

        this.worldNameEdit.setResponder(s -> {
            saveButton.active = !s.isBlank();
        });
    }

    private void onSave() {
        try {
            String newName = this.worldNameEdit.getValue().trim();
            if (!newName.isEmpty()) {
                this.levelAccess.renameLevel(newName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.onClose();
    }

    @Override
    public void onClose() {
        try {
            this.levelAccess.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Minecraft.getInstance().setScreen(new SelectWorldScreen(null));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        graphics.drawString(this.font, translatable("selectWorld.enterName"), this.width / 2 - 100, 23, 10526880);

        this.worldNameEdit.render(graphics, mouseX, mouseY, delta);

        super.render(graphics, mouseX, mouseY, delta);
    }

    private LevelStorageSource.LevelStorageAccess getLevelAccess(EditWorldScreen screen) throws Exception {
        Field field = EditWorldScreen.class.getDeclaredField("levelAccess");
        field.setAccessible(true);
        return (LevelStorageSource.LevelStorageAccess) field.get(screen);
    }

    private Button.Builder getOpenWorldFolderButton() {
        return Button.builder(Component.translatable("selectWorld.edit.openFolder"), btn ->
                Util.getPlatform().openFile(this.levelAccess.getLevelPath(LevelResource.ROOT).toFile()));
    }

    private Button.Builder getResetIconButton() {
        return Button.builder(Component.translatable("selectWorld.edit.resetIcon"), btn ->
                this.levelAccess.getIconFile().ifPresent((path) ->
                    FileUtils.deleteQuietly(path.toFile())));
    }

    private Button.Builder getMakeBackupButton() {
        return Button.builder(Component.translatable("selectWorld.edit.backup"), btn ->
                makeBackupAndShowToast(this.levelAccess));
    }

    private Button.Builder getOptimizeWorldButton() {
        return Button.builder(Component.translatable("selectWorld.edit.optimize"), btn -> {
            this.minecraft.setScreen(new BackupConfirmScreen(this, (p_280911_, p_280912_) -> {
                if (p_280911_) {
                    makeBackupAndShowToast(this.levelAccess);
                }

                this.minecraft.setScreen(OptimizeWorldScreen.create(this.minecraft, b -> {
                    try {
                        levelAccess.close();
                    } catch (IOException ex) {
                        LoggerFactory.getLogger(WorldSelectionList.class).error("Failed to unlock level: ", ex);
                    }
                    this.minecraft.setScreen(this);
                }, this.minecraft.getFixerUpper(), this.levelAccess, p_280912_));
            }, Component.translatable("optimizeWorld.confirm.title"), Component.translatable("optimizeWorld.confirm.description"), true));
        });
    }

    private Button.Builder getRecreateButton() {
        return Button.builder(Component.translatable("selectWorld.recreate"), b -> {
            Minecraft mc = Minecraft.getInstance();

            try {
                Pair<LevelSettings, WorldCreationContext> pair = mc.createWorldOpenFlows().recreateWorldData(levelAccess);
                LevelSettings levelSettings = pair.getFirst();
                WorldCreationContext worldContext = pair.getSecond();

                Path path = CreateWorldScreen.createTempDataPackDirFromExistingWorld(levelAccess.getLevelPath(LevelResource.DATAPACK_DIR), mc);

                if (worldContext.options().isOldCustomizedWorld()) {
                    mc.setScreen(new ConfirmScreen((confirm) -> {
                        if (confirm) {
                            mc.setScreen(CreateWorldScreen.createFromExisting(mc, this, levelSettings, worldContext, path));
                        } else {
                            mc.setScreen(this);
                        }
                    },
                            Component.translatable("selectWorld.recreate.customized.title"),
                            Component.translatable("selectWorld.recreate.customized.text"),
                            CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
                } else {
                    mc.setScreen(CreateWorldScreen.createFromExisting(mc, this, levelSettings, worldContext, path));
                }

            } catch (ContentValidationException e) {
                mc.setScreen(new SymlinkWarningScreen(this));
            } catch (Exception e) {
                e.printStackTrace();
                mc.setScreen(new AlertScreen(() -> mc.setScreen(this),
                        Component.translatable("selectWorld.recreate.error.title"),
                        Component.translatable("selectWorld.recreate.error.text")));
            }
        });
    }

    private Button.Builder getExportButton() {
        return Button.builder(Component.translatable("selectWorld.edit.export"), btn ->
                Minecraft.getInstance().setScreen(new WorldExportScreen(this, levelAccess)));
    }

    private boolean makeBackupAndShowToast(LevelStorageSource.LevelStorageAccess access) {
        try {
            long backupSize = access.makeWorldBackup();
            Component title = Component.translatable("selectWorld.edit.backupCreated", access.getLevelId());
            Component size = Component.translatable("selectWorld.edit.backupSize", Mth.ceil(backupSize / 1048576.0D));

            showToast(title, size);
            return true;
        } catch (IOException e) {
            Component title = Component.translatable("selectWorld.edit.backupFailed");
            Component msg = Component.literal(e.getMessage());

            showToast(title, msg);
            return false;
        }
    }

    private void showToast(Component title, Component message) {
        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, title, message));
    }
}
