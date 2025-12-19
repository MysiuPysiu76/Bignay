package com.mysiupysiu.bignay.screen;

import com.mojang.datafixers.util.Pair;
import com.mysiupysiu.bignay.screen.file.chooser.FileChooserScreen;
import com.mysiupysiu.bignay.utils.BackupEntry;
import com.mysiupysiu.bignay.utils.Backups;
import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.world.WorldRestorer;
import com.mysiupysiu.bignay.utils.world.WorldDuplicator;
import com.mysiupysiu.bignay.utils.world.WorldInfoReader;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public class WorldEditScreen extends Screen {

    private final LevelStorageSource.LevelStorageAccess levelAccess;
    private EditBox worldNameEdit;

    public WorldEditScreen(EditWorldScreen screen) throws Exception {
        super(Component.translatable("selectWorld.edit.title"));
        this.levelAccess = getLevelAccess(screen);
    }

    public WorldEditScreen(LevelStorageSource.LevelStorageAccess levelAccess) {
        super(Component.translatable("selectWorld.edit.title"));
        this.levelAccess = levelAccess;
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

        this.addRenderableWidget(getInformationButton().bounds(startX_Left, startY, 150, 20).build());
        this.addRenderableWidget(getOpenWorldFolderButton().bounds(startX_Right, startY, 150, 20).build());

        this.addRenderableWidget(getResetIconButton().bounds(startX_Left,startY + 24, 150, 20).build());
        this.addRenderableWidget(getSetIconButton().bounds(startX_Right, startY + 24, 150, 20).build());

        this.addRenderableWidget(getMakeBackupButton().bounds(startX_Left, startY + 48, 150, 20).build());
        this.addRenderableWidget(getMenageBackupsButton().bounds(startX_Right, startY + 48, 150, 20).build());

        this.addRenderableWidget(getOptimizeWorldButton().bounds(startX_Left, startY + 72, 150, 20).build());
        this.addRenderableWidget(getPruneWorldButton().bounds(startX_Right, startY + 72, 150, 20).build());

        this.addRenderableWidget(getRecreateButton().bounds(startX_Left, startY + 96, 150, 20).build());
        this.addRenderableWidget(getDuplicateButton().bounds(startX_Right, startY + 96, 150, 20).build());

        this.addRenderableWidget(getDeleteButton().bounds(startX_Left, startY + 120, 150, 20).build());
        this.addRenderableWidget(getExportButton().bounds(startX_Right, startY + 120, 150, 20).build());

        int bottomY = this.height / 4 + 149;
        Button saveButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.edit.save"), b -> onSave()).bounds(centerX - 135, bottomY, 130, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, b -> onClose()).bounds(centerX + 5, bottomY, 130, 20).build());

        this.worldNameEdit.setResponder(s -> saveButton.active = !s.isBlank());
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
            levelAccess.close();
            Minecraft.getInstance().setScreen(new SelectWorldScreen(null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        graphics.drawString(this.font, Component.translatable("selectWorld.enterName"), this.width / 2 - 100, 23, 10526880);

        this.worldNameEdit.render(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);
    }

    private LevelStorageSource.LevelStorageAccess getLevelAccess(EditWorldScreen screen) throws Exception {
        Field field = EditWorldScreen.class.getDeclaredField("levelAccess");
        field.setAccessible(true);
        return (LevelStorageSource.LevelStorageAccess) field.get(screen);
    }

    private Button.Builder getInformationButton() {
        return Button.builder(Component.translatable("selectWorld.info"), btn ->
                Minecraft.getInstance().setScreen(new WorldInfoScreen(this.levelAccess)));
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

    private Button.Builder getSetIconButton() {
        return Button.builder(Component.translatable("selectWorld.setIcon"), btn -> {
            FileChooserScreen fileChooser = new FileChooserScreen();
            fileChooser.setPreviousScreen(this);
            fileChooser.addFilter(FileType.PNG);
            fileChooser.setOnConfirm(f -> {
                try {
                    File worldDir = this.levelAccess.getLevelPath(LevelResource.ROOT).toFile().getCanonicalFile();
                    Path oldIcon = new File(worldDir, "icon.png").toPath();
                    Files.deleteIfExists(oldIcon);
                    Files.copy(f.toPath(), oldIcon);
                    Minecraft.getInstance().setScreen(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fileChooser.setAdditionalFilter(f -> {
                try {
                    BufferedImage img = ImageIO.read(f);
                    if (img.getHeight() == 64 && img.getWidth() == 64) return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            });
            fileChooser.setFilterText(Component.translatable("fileChooser.filter.image64"));
            Minecraft.getInstance().setScreen(fileChooser);
        });
    }

    private Button.Builder getMakeBackupButton() {
        return Button.builder(Component.translatable("selectWorld.edit.backup"), btn ->
                makeBackupAndShowToast(this.levelAccess));
    }

    private Button.Builder getMenageBackupsButton() {
        return Button.builder(Component.translatable("selectWorld.backup.manage"), btn -> {
            File backups = new File(this.minecraft.gameDirectory, "backups");
            BackupEntry be = Backups.getLatestBackupForWorld(new WorldInfoReader(this.levelAccess).getWorldUUID()).get();
            WorldRestorer restorer = new WorldRestorer(this.levelAccess, new File(backups, be.file()));
            this.minecraft.setScreen(new ConfirmScreen(is -> {
               if (is) {
                   Minecraft.getInstance().setScreen(new OperationWithProgressScreen(Component.translatable("selectWorld.backup.restoring"), restorer));
               } else {
                   this.minecraft.setScreen(this);
               }
           }, Component.translatable("selectWorld.backup.restore"), Component.translatable("selectWorld.backup.restoreWorld", new WorldInfoReader(this.levelAccess).getWorldName(),  Instant.ofEpochMilli(be.created()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), be.version())));
        });
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

    private Button.Builder getPruneWorldButton() {
        return Button.builder(Component.translatable("selectWorld.edit.prune"), btn -> {
            this.minecraft.setScreen(new ConfirmScreen(confirm -> {
                if (confirm) {
                    this.minecraft.setScreen(new WorldPruneScreen(this.levelAccess, this));
                } else {
                    this.minecraft.setScreen(this);
                }
            },
                    Component.translatable("selectWorld.edit.prune.question"),
                    Component.translatable("selectWorld.edit.prune.description"),
                    CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
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

    private Button.Builder getDuplicateButton() {
        return Button.builder(Component.translatable("selectWorld.duplicate"), btn ->
                Minecraft.getInstance().setScreen(new OperationWithProgressScreen(Component.translatable("selectWorld.duplicate"), new WorldDuplicator(this.levelAccess))));
    }

    private Button.Builder getDeleteButton() {
        return Button.builder(Component.translatable("selectWorld.delete"), btn -> {
            this.minecraft.setScreen(new ConfirmScreen(is -> {
                try {
                    if (is) {
                        this.minecraft.setScreen(new ProgressScreen(true));
                        this.levelAccess.deleteLevel();
                    } else {
                        this.levelAccess.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                this.minecraft.setScreen(new SelectWorldScreen(null));
            }, Component.translatable("selectWorld.deleteQuestion"), Component.translatable("selectWorld.deleteWarning", this.levelAccess.getSummary().getLevelName()), Component.translatable("selectWorld.deleteButton"), CommonComponents.GUI_CANCEL));
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

            registerBackup();
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

    private void registerBackup() {
        File backupFile = getBackupFile();

        try {
            Backups.addBackup(new BackupEntry(
                    UUID.randomUUID(), new WorldInfoReader(this.levelAccess).getWorldUUID(),
                    backupFile.getName(), Files.getLastModifiedTime(backupFile.toPath()).toMillis(),
                    new WorldInfoReader(this.levelAccess).getWorldVersion(), backupFile.length()
            ));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private long getCreationTime(Path path) {
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            return attr.creationTime().toMillis();
        } catch (IOException e) {
            return 0;
        }
    }
    
    private File getBackupFile() {
        try {
            File folder = new File(System.getProperty("user.dir"), "backups");

            Optional<Path> latest = Files.list(folder.toPath())
                    .filter(Files::isRegularFile)
                    .max(Comparator.comparingLong(this::getCreationTime));

            return latest.get().toFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
