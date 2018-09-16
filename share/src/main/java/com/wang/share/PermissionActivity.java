package com.wang.share;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.wang.share.utils.PermissionUtils;

public class PermissionActivity extends Activity {

    public boolean isExternalStoragePermissionGranted() {
        return PermissionUtils.isExternalStoragePermissionGranted(this);
    }

    public void requestExternalStoragePermission() {
        PermissionUtils.requestExternalStoragePermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.REQUEST_CODE_EXTERNAL_STORAGE:
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
//        PermissionUtils.showExplanationDialog(this);
    }
}
