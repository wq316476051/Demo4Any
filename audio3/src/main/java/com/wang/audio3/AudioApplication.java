package com.wang.audio3;

import android.app.Application;
import android.content.Context;

public class AudioApplication extends Application {

    private static Context sContext;
    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
