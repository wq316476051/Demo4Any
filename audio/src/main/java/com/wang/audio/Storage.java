package com.wang.audio;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Storage {

    private static final String TAG = "Storage";
    private static final String PATH = "Wang";

    private static Storage sStorage;

    private Storage() {
    }

    public static Storage getInstance() {
        if (sStorage != null) {
            return sStorage;
        } else {
            return new Storage();
        }
    }

    public File getDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory(), PATH);
        if (dir.exists() && dir.isFile()) {
            boolean delete = dir.delete();
            Log.d(TAG, "getDirectory: delete = " + delete);
        }
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            Log.d(TAG, "getDirectory: mkdirs = " + mkdirs);
        }
        return dir;
    }
}
