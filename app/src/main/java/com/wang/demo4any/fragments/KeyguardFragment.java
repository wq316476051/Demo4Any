package com.wang.demo4any.fragments;

import android.app.KeyguardManager;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class KeyguardFragment extends Fragment {

    private static final String TAG = "KeyguardFragment";

    public void test(){
        KeyguardManager keyguardManager = getActivity().getSystemService(KeyguardManager.class);
        boolean deviceLocked = keyguardManager.isDeviceLocked();
        boolean deviceSecure = keyguardManager.isDeviceSecure();
        boolean keyguardLocked = keyguardManager.isKeyguardLocked();
        boolean keyguardSecure = keyguardManager.isKeyguardSecure();

        Log.d(TAG, "onClick: deviceLocked = " + deviceLocked);
        Log.d(TAG, "onClick: deviceSecure = " + deviceSecure);
        Log.d(TAG, "onClick: keyguardLocked = " + keyguardLocked);
        Log.d(TAG, "onClick: keyguardSecure = " + keyguardSecure);

        getActivity().setShowWhenLocked(true);
        getActivity().setTurnScreenOn(true);
        Log.d(TAG, "onClick: keyguardManager.isKeyguardLocked() = " + keyguardManager.isKeyguardLocked());
        keyguardManager.requestDismissKeyguard(getActivity(), new KeyguardManager.KeyguardDismissCallback() {
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
}
