package com.wang.demo4any;

import android.app.Application;
import android.content.Context;

import com.wang.demo4any.utils.AppUtils;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.setApp(this);
    }
}
