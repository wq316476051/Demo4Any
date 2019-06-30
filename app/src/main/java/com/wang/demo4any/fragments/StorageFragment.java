package com.wang.demo4any.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StorageFragment extends Fragment {

    private static final String TAG = "StorageFragment";

    public static final String TITLE = "Storage";

    public static StorageFragment newInstance() {
        return new StorageFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            StructStat stat = Os.stat(Environment.getExternalStorageDirectory().getAbsolutePath());
            Log.d(TAG, "onCreate: ");
        } catch (ErrnoException e) {
            e.printStackTrace();
        }

        try {
            Path path = Paths.get("sdcard/");
            Log.d(TAG, "onActivityCreated: path = " + path);
            Path realPath = path.toRealPath();
            Log.d(TAG, "onActivityCreated: realPath = " + realPath);

            boolean sameFile = Files.isSameFile(path, realPath);
            Log.d(TAG, "onActivityCreated: sameFile = " + sameFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
