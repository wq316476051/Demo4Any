package com.wang.keyguard;

import android.app.Activity;
import android.app.KeyguardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Button mBtnLockScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_lock_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                boolean deviceLocked = keyguardManager.isDeviceLocked();
                boolean deviceSecure = keyguardManager.isDeviceSecure();
                boolean keyguardLocked = keyguardManager.isKeyguardLocked();
                boolean keyguardSecure = keyguardManager.isKeyguardSecure();

                Log.d(TAG, "onClick: deviceLocked = " + deviceLocked);
                Log.d(TAG, "onClick: deviceSecure = " + deviceSecure);
                Log.d(TAG, "onClick: keyguardLocked = " + keyguardLocked);
                Log.d(TAG, "onClick: keyguardSecure = " + keyguardSecure);

                setShowWhenLocked(true);
                setTurnScreenOn(true);
                Log.d(TAG, "onClick: keyguardManager.isKeyguardLocked() = " + keyguardManager.isKeyguardLocked());
                keyguardManager.requestDismissKeyguard(MainActivity.this, new KeyguardManager.KeyguardDismissCallback() {
                    @Override
                    public void onDismissError() {
                        Log.d(TAG, "onDismissError: ");
                    }

                    @Override
                    public void onDismissSucceeded() {
                        Log.d(TAG, "onDismissSucceeded: ");
                    }

                    @Override
                    public void onDismissCancelled() {
                        Log.d(TAG, "onDismissCancelled: ");
                    }
                });
            }
        });
    }
}
