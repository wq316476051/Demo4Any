package com.wang.demo4any.share;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

public class PermissionUtils {

    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 1248;

    private PermissionUtils() {/**/}

    public static boolean hasExternalStoragePermission(Context context) {
        return context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestExternalStoragePermission(Activity activity) {
        activity.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE);
    }

    public static void gotoPackagePermission(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    public static void showExplanationDialog(final Activity activity){
        if (!activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle("申请权限")
                    .setMessage("我们需要这个权限干...")
                    .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gotoPackagePermission(activity);
                        }
                    });
            builder.show();
        }
    }
}
