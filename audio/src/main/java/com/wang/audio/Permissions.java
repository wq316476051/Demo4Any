package com.wang.audio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

public class Permissions {

    private Permissions() {}

    public static boolean isStorage(Context context) {
        return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStorage(Activity activity, int id) {
        activity.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, id);
    }
}
