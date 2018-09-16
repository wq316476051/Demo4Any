package com.wang.runtimepermission.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wang.runtimepermission.R;

public class TestActivity extends PermissionActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        findViewById(R.id.btn_external).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_external:
                if (isExternalStoragePermissionGranted()) {
                    // TODO: Do work
                } else {
                    requestExternalStoragePermission();
                }
                break;
        }
    }

    @Override
    protected void onExternalStoragePermissionGranted() {
        // TODO: Do work
    }

    @Override
    protected void onExternalStoragePermissionDenied() {
        // TODO: Do something, maybe a dialog
        PermissionHelper.showExplanationDialog(this);
    }
}
