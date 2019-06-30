package com.wang.demo4any.fragments;

import android.media.MediaScannerConnection;
import android.net.Uri;

import com.wang.demo4any.media.MediaScanner;

import java.io.File;

import androidx.fragment.app.Fragment;

public class MediaFragment extends Fragment {



    private void scanFile() {
        // 1. 发广播

        // 2. 绑定扫描
        MediaScannerConnection.scanFile(getActivity(), new String[]{"path"}, new String[]{"mimeType"},
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

        // 3. 封装（绑定扫描）
        MediaScanner scanner = new MediaScanner(getActivity());
        scanner.setCallback(new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
        scanner.scanFile(new File(""), "");
        scanner.release();
    }
}
