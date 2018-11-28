package com.wang.audio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.audio.client.MediaBrowserHelper;
import com.wang.audio.player.MusicService;
import com.wang.audio.player.contentcatalogs.MusicLibrary;
import com.wang.audio.ui.MediaSeekBar;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_record).setOnClickListener(view -> {
            startActivityInternal(RecordActivity.class);
        });

        findViewById(R.id.btn_play).setOnClickListener(view -> {
            startActivityInternal(PlaybackActivity.class);
        });
    }

    private void startActivityInternal(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
