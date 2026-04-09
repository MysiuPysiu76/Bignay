package com.mysiupysiu.bignay.utils;

public interface OperationWithProgress {

    void setProgressScreen(ProgressListener progressListener);
    void execute();
    void cancel();
    void finish();
}
