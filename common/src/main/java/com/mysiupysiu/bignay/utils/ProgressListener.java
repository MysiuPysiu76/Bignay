package com.mysiupysiu.bignay.utils;

public interface ProgressListener {

    void onProgress(double progress);
    void onCancel();
    void onFinish();
}
