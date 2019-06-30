package com.wang.demo4any.utils;

import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {
    }

    public static void showShort(String text) {
        Toast.makeText(AppUtils.getApp(), text, Toast.LENGTH_SHORT).show();
    }
}
