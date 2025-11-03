package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.WorldExporter;
import net.minecraft.network.chat.Component;

public class WorldExportProgressScreen extends AbstractProgressScreen {

    private final WorldExporter exporter;

    public WorldExportProgressScreen(WorldExporter exporter) {
        super(Component.translatable("exportWorld.progress.title"));
        this.exporter = exporter;
        this.exporter.setProgressListener(this);
    }

    @Override
    public void onCancel() {
        exporter.cancel();
    }

    @Override
    protected void onAction() {
        exporter.execute();
        finish();
    }
}
