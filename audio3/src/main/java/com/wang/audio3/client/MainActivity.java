package com.wang.audio3.client;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.wang.audio3.R;
import com.wang.audio3.client.MediaBrowserHelper;
import com.wang.audio3.service.PlayerService;

import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private MediaBrowserHelper mMediaBrowserHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("申请权限")
                        .setMessage("sfasfasf")
                        .setPositiveButton("queding", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        mMediaBrowserHelper = new MediaBrowserHelperWrapper(this, PlayerService.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        mMediaBrowserHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
        mMediaBrowserHelper.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    private class MediaBrowserHelperWrapper extends MediaBrowserHelper {

        public MediaBrowserHelperWrapper(Context context, Class<? extends MediaBrowserServiceCompat> mediaBrowserServiceClass) {
            super(context, mediaBrowserServiceClass);
        }

        @Override
        protected void onConnected() {
            MediaMetadataCompat mediaMetadata = getMediaMetadata();
            PlaybackStateCompat playbackState = getPlaybackState();
            if (mediaMetadata != null) {

            }
        }

        @Override
        protected void onConnectionFailed() {

        }

        @Override
        protected void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {

        }

        @Override
        protected void onPlaybackStateChanged(PlaybackStateCompat state) {

        }

        @Override
        protected void onMetadataChanged(MediaMetadataCompat metadata) {

        }
    }
}
