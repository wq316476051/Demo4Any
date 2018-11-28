package com.wang.window;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

public class BaseActivity extends Activity {

    protected boolean checkWindowAlertPermission() {
        return checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
                == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestWindowAlertPermission() {
        requestPermissions(new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW}, 1248);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1248) {

        }
    }
}
