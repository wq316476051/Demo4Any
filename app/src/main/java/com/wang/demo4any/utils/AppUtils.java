package com.wang.demo4any.utils;

import android.app.Application;
import android.os.Build;

import com.wang.demo4any.App;

public class AppUtils {

    private static App mApp;

    public static void setApp(App app) {
        mApp = app;
    }

    public static Application getApp() {
        return mApp;
    }

    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
