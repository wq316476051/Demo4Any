package com.wang.demo4any.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScreenFragment extends Fragment {

    private static final String TAG = "ScreenFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        screenSize();
    }

    private void screenSize() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        Log.d(TAG, "onCreate: widthPixels = " + widthPixels);
        Log.d(TAG, "onCreate: heightPixels = " + heightPixels);
        // 1080 * 1920
        // 1080 * 1794
        // 126 pixel

        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        float density = metrics.density;
        int densityDpi = metrics.densityDpi;
        Log.d(TAG, "onCreate: xdpi = " + xdpi);
        Log.d(TAG, "onCreate: ydpi = " + ydpi);
        Log.d(TAG, "onCreate: density = " + density);
        Log.d(TAG, "onCreate: density*160 = " + density * 160);
        Log.d(TAG, "onCreate: densityDpi = " + densityDpi);

        double screenWidth = widthPixels * 1.0/ densityDpi;
        double screenHeight = heightPixels * 1.0 / densityDpi;
        double screenSize = Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
        Log.d(TAG, "onCreate: screenWidth = " + screenWidth);
        Log.d(TAG, "onCreate: screenHeight = " + screenHeight);
        Log.d(TAG, "onCreate: screenSize = " + screenSize);

        float x = widthPixels / metrics.xdpi;
        float y = heightPixels/ metrics.ydpi;
        Log.d(TAG, "onCreate: x = " + x);
        Log.d(TAG, "onCreate: y = " + y);

        int stateBarHeight = getStateBarHeight(getActivity()); // 63
        int navigationBarHeight = getNavigationBarHeight(getContext());
        Log.d(TAG, "onCreate: stateBarHeight = " + stateBarHeight);
        Log.d(TAG, "onCreate: navigationBarHeight = " + navigationBarHeight); // 126
    }

    private static int getStateBarHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        //获取NavigationBar的高度
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }
}
