package com.wang.demo4any.utils;

import java.io.File;

import androidx.annotation.NonNull;

public class StorageUtils {

    @NonNull
    public static File getRecordStorage() {
        File recordDir = AppUtils.getApp().getExternalFilesDir("record");
        if (recordDir == null) {
            recordDir = AppUtils.getApp().getFilesDir();
        }
        return recordDir;
    }
}
