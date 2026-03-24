package com.mysiupysiu.bignay.mixin;

import com.mysiupysiu.bignay.screen.file.InvalidFileScreen;
import com.mysiupysiu.bignay.screen.file.chooser.FileChooserScreen;
import com.mysiupysiu.bignay.screen.world.WorldImportScreen;
import com.mysiupysiu.bignay.utils.FileType;
import com.mysiupysiu.bignay.utils.world.WorldImporter;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public abstract class SelectWorldScreenMixin extends Screen {

    @Shadow
    private Button copyButton;

    protected SelectWorldScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void replaceRecreateWithImport(CallbackInfo ci) {
        if (this.copyButton == null) return;
        this.removeWidget(this.copyButton);

        this.addRenderableWidget(Button.builder(Component.translatable("importWorld.import"), b -> {
            FileChooserScreen fileChooser = new FileChooserScreen();
            fileChooser.addFilter(FileType.ZIP);
            fileChooser.setFilterText(Component.translatable("fileChooser.filter.zip"));
            fileChooser.setOnConfirm(file -> {
                if (WorldImporter.isValidWorld(file)) {
                    this.minecraft.setScreen(new WorldImportScreen(file));
                } else {
                    this.minecraft.setScreen(new InvalidFileScreen(fileChooser));
                }
            });
            fileChooser.setPreviousScreen(this);
            this.minecraft.setScreen(fileChooser);
        }).bounds(this.width / 2 + 4, this.height - 28, 72, 20).build());
    }
}
