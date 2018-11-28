package com.wang.demo4any;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private TextView mTvName;
    private Button mBtnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvName = findViewById(R.id.tv_name);
        mBtnClick = findViewById(R.id.btn_click);

        mBtnClick.setOnClickListener(view -> {
            File[] files = Environment.getExternalStorageDirectory().listFiles();
            for (File file : files) {
                Log.d(TAG, "onCreate: file.name = " + file.getName());
            }
        });

        takeScreenShortcut();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void takeScreenShortcut() {
        int width = 0;
        int height = 0;
        int format = 0;
        int maxImages = 1;
        ImageReader imageReader = ImageReader.newInstance(width, height, format, maxImages);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireLatestImage();
                if (image != null) {
                    Image.Plane[] planes = image.getPlanes();
                    if (planes.length > 0) {
                        Image.Plane plane = planes[0];

                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        bitmap.copyPixelsFromBuffer(plane.getBuffer());

                        // saveBitmap

                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                    }

                }
            }
        }, getBackgroundHandler());
    }

    private Handler getBackgroundHandler() {
        HandlerThread thread = new HandlerThread("take-screen-shot");
        thread.start();
        return new Handler(thread.getLooper());
    }
}
