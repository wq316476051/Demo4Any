package com.wang.demo4any.record;

import com.wang.demo4any.utils.AppUtils;

import java.io.File;
import java.util.Optional;

import androidx.annotation.NonNull;

public class RecordStorage {

    private static final String SUB_DIR = "records";

    private RecordStorage() {
    }

    @NonNull
    public static File getStorageDirectory() {
        return getExternalStorageDirectory().orElse(getInternalStorageDirectory());
    }

    private static File getInternalStorageDirectory() {
        return new File(AppUtils.getApp().getFilesDir(), SUB_DIR);
    }

    private static Optional<File> getExternalStorageDirectory() {
        return Optional.ofNullable(AppUtils.getApp().getExternalFilesDir(SUB_DIR));
    }
}
