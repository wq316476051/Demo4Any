package com.wang.audio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    protected boolean isPermissionsGranted() {
        boolean allGranted = true;
        for (String permission : PERMISSIONS) {
            allGranted = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
            if (!allGranted) {
                break;
            }
        }
        return allGranted;
    }

    protected void requestPermissions() {
        requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
    }

    protected void onPermissionsGranted() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            boolean allMatch = true;
            for (int result : grantResults) {
                allMatch = result == PackageManager.PERMISSION_GRANTED;
                if (!allMatch) {
                    break;
                }
            }
            if (allMatch) {
                onPermissionsGranted();
            }
        }
    }
}
