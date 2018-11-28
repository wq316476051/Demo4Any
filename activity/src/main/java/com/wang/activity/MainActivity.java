package com.wang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Log.d(TAG, "attachBaseContext: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + Log.getStackTraceString(new Throwable()));
    }

    /***************************
     *
     ***************************/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + Log.getStackTraceString(new Throwable()));
        return super.onTouchEvent(event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        Log.d(TAG, "onApplyThemeResource: " + Log.getStackTraceString(new Throwable()));
    }
}
