package com.wang.demo4any.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.stream.Stream;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    private static final String TAG = "PermissionUtils";

    private static final int REQUEST_CODE_EXTERNAL_STORAGE = 100;

    private static final int REQUEST_CODE_RECORD_AUDIO = 101;

    private PermissionUtils() {
    }

    private static boolean hasPermission(String permission) {
        return AppUtils.getApp().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean hasPermissions(String[] permissions) {
        return Stream.of(permissions).allMatch(PermissionUtils::hasPermission);
    }

    private static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        activity.requestPermissions(permissions, requestCode);
    }

    public static boolean hasStoragePermission() {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void requestStoragePermission(Activity activity) {
        requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE_EXTERNAL_STORAGE);
    }

    public static boolean hasRecordAudioPermission() {
        return hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    public static void requestRecordAudioPermission(Activity activity) {
        requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_CODE_RECORD_AUDIO);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_EXTERNAL_STORAGE:
                break;
            case REQUEST_CODE_RECORD_AUDIO:
                break;
            default:
                break;
        }
    }

    public interface Response {
        void onPermissionsGranted();
        void onPermissionsDenied();
    }
}
