package com.wang.demo4any.fragments;

import android.os.Bundle;

import com.wang.demo4any.jni.NativeWrapper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 静态申请、动态申请
 */
public class JniFragment extends Fragment {

    private static final String TAG = "JniFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NativeWrapper wrapper = new NativeWrapper(); //
    }
}
