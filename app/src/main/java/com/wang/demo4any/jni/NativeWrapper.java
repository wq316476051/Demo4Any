package com.wang.demo4any.jni;

import android.util.Log;

public class NativeWrapper {

    private static final String TAG = "NativeWrapper";

    private int mNativeContext;

    static {
        System.loadLibrary("test_jni");
        native_init(); // JNIEnv.FindClass()
    }

    public NativeWrapper() {
        native_setup(); // 创建 native 对象
    }

    private void callback() {
        Log.d(TAG, "callback: ");
    }

    private native static void native_init();

    private native void native_setup();
}
