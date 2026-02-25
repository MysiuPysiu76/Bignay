package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.OperationWithProgress;
import net.minecraft.network.chat.Component;

public class OperationWithProgressScreen extends AbstractProgressScreen {

    private final OperationWithProgress operation;

    public OperationWithProgressScreen(String title, OperationWithProgress operation) {
        super(Component.translatable(title));
        this.operation = operation;
        this.operation.setProgressScreen(this);
    }

    @Override
    protected void onAction() {
        this.operation.execute();
    }

    @Override
    public void onCancel() {
        operation.cancel();
        finish();
    }
}
