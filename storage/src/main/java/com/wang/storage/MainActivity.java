package com.wang.storage;

import android.app.Activity;
import android.content.ComponentName;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

//        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
//        File[] externalFilesDirs = getExternalFilesDirs(Environment.DIRECTORY_MUSIC);
//        Log.d(TAG, "onCreate: externalFilesDir = " + externalFilesDir);
//
//        File externalCacheDir = getExternalCacheDir();
//        File[] externalCacheDirs = getExternalCacheDirs();
//        Log.d(TAG, "onCreate: externalCacheDir = " + externalCacheDir);
//
//        File[] externalMediaDirs = getExternalMediaDirs();
//        for (File dir : externalMediaDirs) {
//            Log.d(TAG, "onCreate: dir = " + dir);
//        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .commit();

        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void scanFile() {
        MediaScanner scanner = new MediaScanner(this);
        scanner.setCallback(new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
        scanner.scanFile(new File(""), "");
        scanner.release();

        MediaScannerConnection.scanFile(this, new String[] {"path"}, new String[] {"mimeType"},
                new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
    }
}
