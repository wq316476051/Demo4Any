package com.wang.runtimepermission.mine;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.wang.runtimepermission.MainActivity;

public class PermissionActivity extends Activity {

    public boolean isExternalStoragePermissionGranted() {
        return PermissionHelper.isExternalStoragePermissionGranted(this);
    }

    public void requestExternalStoragePermission() {
        PermissionHelper.requestExternalStoragePermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onExternalStoragePermissionGranted();
                } else {
                    onExternalStoragePermissionDenied();
                }
                break;
        }
    }

    protected void onExternalStoragePermissionGranted() {

    }
    protected void onExternalStoragePermissionDenied() {

    }
}
