package com.wang.audio2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.audio2.R;
import com.wang.audio2.client.MediaBrowserHelper;
import com.wang.audio2.service.PlayerService;
import com.wang.audio2.service.content.MusicLibrary;

import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ImageView mAlbumArt;
    private TextView mTitleTextView;
    private TextView mArtistTextView;
    private ImageView mMediaControlsImage;
//    private MediaSeekBar mSeekBarAudio;

    private MediaBrowserHelper mMediaBrowserHelper;
    private boolean mIsPlaying;
    private boolean mIsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        mTitleTextView = findViewById(R.id.song_title);
        mArtistTextView = findViewById(R.id.song_artist);
        mAlbumArt = findViewById(R.id.album_art);
        mMediaControlsImage = findViewById(R.id.media_controls);
//        mSeekBarAudio = findViewById(R.id.seekbar_audio);

        findViewById(R.id.button_previous).setOnClickListener(mClickListener);
        findViewById(R.id.button_play).setOnClickListener(mClickListener);
        findViewById(R.id.button_next).setOnClickListener(mClickListener);

        mMediaBrowserHelper = new MediaBrowserHelper(this, PlayerService.class) {

            @Override
            public void onConnected(MediaControllerCompat mediaController) {
                super.onConnected(mediaController);
                // TODO: 2018/9/1 SeekBar
                mIsConnected = true;
            }

            @Override
            public void onChildrenLoaded(String parentId, List<MediaBrowserCompat.MediaItem> children) {
                super.onChildrenLoaded(parentId, children);
                Log.e(TAG, "onChildrenLoaded: ");
                MediaControllerCompat mediaController = getMediaController();

                for (MediaBrowserCompat.MediaItem mediaItem : children) {
                    mediaController.addQueueItem(mediaItem.getDescription());
                    Log.d(TAG, "onChildrenLoaded: mediaItem = " + mediaItem);
                }

                mediaController.getTransportControls().prepare();
            }
        };

//        mMediaBrowserHelper.getMediaController().getMetadata(); // 初始数据
//        mMediaBrowserHelper.getMediaController().getPlaybackState(); // 初始状态
//        mMediaBrowserHelper.registerCallback(mMediaControllerCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
        mMediaBrowserHelper.registerCallback(mMediaControllerCallback);
        mMediaBrowserHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
        mMediaBrowserHelper.unregisterCallback(mMediaControllerCallback);
        mMediaBrowserHelper.onStop();
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:
                    mMediaBrowserHelper.getTransportControls().skipToPrevious();
                    break;
                case R.id.button_play:
                    Log.e(TAG, "onClick: mIsPlaying = " + mIsPlaying);
                    if (mIsPlaying) {
                        mMediaBrowserHelper.getTransportControls().pause();
                    } else {
                        mMediaBrowserHelper.getTransportControls().play();
                    }
                    break;
                case R.id.button_next:
                    mMediaBrowserHelper.getTransportControls().skipToNext();
                    break;
            }
        }
    };

    private MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        
        private static final String TAG = "MediaControllerCallback";

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat playbackState) {
            super.onPlaybackStateChanged(playbackState);
            mIsPlaying = playbackState != null &&
                    playbackState.getState() == PlaybackStateCompat.STATE_PLAYING;
            Log.e(TAG, "onPlaybackStateChanged: mIsPlaying = " + mIsPlaying);
            mMediaControlsImage.setPressed(mIsPlaying);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            Log.e(TAG, "onMetadataChanged: ");
            if (mediaMetadata == null) {
                return;
            }
            mTitleTextView.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            mArtistTextView.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            mAlbumArt.setImageBitmap(MusicLibrary.getAlbumBitmap(getApplicationContext(), mediaMetadata.getDescription().getMediaId()));
        }

        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            super.onQueueChanged(queue);
        }
    };
}
