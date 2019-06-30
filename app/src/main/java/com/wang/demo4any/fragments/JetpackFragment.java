package com.wang.demo4any.fragments;

import androidx.fragment.app.Fragment;

/*
databinding
lifecycle
viewmodel + livedata
navigation // Jetpack中新增的，当前无法使用，gradle 4.6。Paging也是一样
room
 */
public class JetpackFragment extends Fragment {

    private static final String TAG = "JetpackFragment";

    public static final String TITLE = "Jetpack";

    public static JetpackFragment newInstance() {
        return new JetpackFragment();
    }
}
