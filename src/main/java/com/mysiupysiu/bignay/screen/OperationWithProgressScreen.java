package com.mysiupysiu.bignay.screen;

import com.mysiupysiu.bignay.utils.OperationWithProgress;
import net.minecraft.network.chat.Component;

public class OperationWithProgressScreen extends AbstractProgressScreen {

    private final OperationWithProgress operation;

    protected OperationWithProgressScreen(Component component, OperationWithProgress operation) {
        super(component);
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
