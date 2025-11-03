package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.WorldImporter;
import net.minecraft.network.chat.Component;

public class WorldImportProgressScreen extends AbstractProgressScreen {

    private final WorldImporter importer;

    public WorldImportProgressScreen(WorldImporter importer) {
        super(Component.translatable("importWorld.progress.title"));
        this.importer = importer;
        this.importer.setProgressListener(this);
    }

    @Override
    public void onCancel() {
        importer.cancel();
    }

    @Override
    protected void onAction() {
        importer.execute();
        finish();
    }
}
