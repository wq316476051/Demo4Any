package com.wang.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + Log.getStackTraceString(new Throwable()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + Log.getStackTraceString(new Throwable()));
        TextView textView = new TextView(getContext());
        textView.setText("Fragment");
        return textView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " + Log.getStackTraceString(new Throwable()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + Log.getStackTraceString(new Throwable()));
    }
}
