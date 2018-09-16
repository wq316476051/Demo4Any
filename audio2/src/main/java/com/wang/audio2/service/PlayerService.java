package com.wang.audio2.service;

import android.accessibilityservice.GestureDescription;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.wang.audio2.service.content.MusicLibrary;
import com.wang.audio2.service.notifications.MediaNotificationManager;
import com.wang.audio2.service.players.MediaPlayerAdapter;
import com.wang.audio2.service.players.PlaybackInfoListener;
import com.wang.audio2.service.players.PlayerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlayerService extends MediaBrowserServiceCompat {

    private static final String TAG = "PlayerService";

    private MediaSessionCompat mMediaSession;
    private MediaNotificationManager mMediaNotificationManager;
    private PlayerAdapter mPlayerAdapter;
    private boolean mServiceInStartedState;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        mMediaSession = new MediaSessionCompat(this, "PlayerService");
        mMediaSession.setCallback(mCallback);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        setSessionToken(mMediaSession.getSessionToken());

        mMediaNotificationManager = new MediaNotificationManager(this);
        mPlayerAdapter = new MediaPlayerAdapter(this, mPlaybackInfoListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: " + intent.getAction());
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand:");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(TAG, "onTaskRemoved: ");
        //stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        mMediaNotificationManager.onDestory();
        mPlayerAdapter.stop();
        mMediaSession.release();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String s, int i, @Nullable Bundle bundle) {
        Log.e(TAG, "onGetRoot: ");
        return new BrowserRoot("root", null);
    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(MusicLibrary.getMediaItems());
        Log.e(TAG, "onLoadChildren: " + result);
    }

    private MediaSessionCompat.Callback mCallback = new MediaSessionCompat.Callback() {

        private List<MediaSessionCompat.QueueItem> mPlayList = new ArrayList<>();
        private int mQueueIndex = -1;
        private MediaMetadataCompat mMediaMetadata;

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            super.onAddQueueItem(description);
            Log.e(TAG, "onAddQueueItem: ");
            mPlayList.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
            mMediaSession.setQueue(mPlayList);
        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            super.onRemoveQueueItem(description);
            Log.e(TAG, "onRemoveQueueItem: ");
            mPlayList.remove(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
            mMediaSession.setQueue(mPlayList);
        }

        @Override
        public void onPrepare() {
            super.onPrepare();
            Log.e(TAG, "onPrepare: ");
            if (mQueueIndex < 0 && mPlayList.isEmpty()) {
                return;
            }
            String mediaId = mPlayList.get(mQueueIndex).getDescription().getMediaId();
            mMediaMetadata = MusicLibrary.getMetadata(PlayerService.this, mediaId);
            Log.e(TAG, "onPrepare: MediaSession.setMetadata() = " + mMediaMetadata.getDescription());
            mMediaSession.setMetadata(mMediaMetadata);
        }

        @Override
        public void onPlay() {
            super.onPlay();
            Log.e(TAG, "onPlay: ");
            if (!isReadyToPlay()) {
                return;
            }

            if (mMediaMetadata == null) {
                onPrepare();
            }

            mPlayerAdapter.playFromMedia(mMediaMetadata);
            Log.d(TAG, "onPlayFromMediaId: MediaSession active");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.e(TAG, "onPause: ");
            mPlayerAdapter.pause();
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.e(TAG, "onStop: ");
            mMediaSession.setActive(false);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            Log.e(TAG, "onSkipToPrevious: ");
            if (mPlayList.size() ==  0) {
                return;
            }
            mQueueIndex = (++mQueueIndex % mPlayList.size());
            mMediaMetadata = null;
            onPlay();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            Log.e(TAG, "onSkipToNext: ");
            mQueueIndex = mQueueIndex > 0 ? mQueueIndex -1 : mPlayList.size() -1;
            mMediaMetadata = null;
            onPlay();
        }

        @Override
        public void onSeekTo(long pos) {
            Log.e(TAG, "onSeekTo: ");
            super.onSeekTo(pos);
            mPlayerAdapter.seekTo(pos);
        }

        private boolean isReadyToPlay() {
            return (!mPlayList.isEmpty());
        }

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            // 接收到监听事件
            Log.e(TAG, "onMediaButtonEvent: ");
            return super.onMediaButtonEvent(mediaButtonEvent);
        }
    };

    /**
     * 处理“前台服务”和“通知”
     */
    private PlaybackInfoListener mPlaybackInfoListener = new PlaybackInfoListener() {

        private ServiceManager mServiceManager = new ServiceManager();

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            mMediaSession.setPlaybackState(state);

            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.moveServiceToStartedState(state);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.updateNotificationForPause(state);
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mServiceManager.moveServiceOutOfStartedState(state);
                    break;
            }
        }

        class ServiceManager {
            private void moveServiceToStartedState(PlaybackStateCompat state) {
                Log.e(TAG, "moveServiceToStartedState: ");
                Notification notification = mMediaNotificationManager.getNotification(mPlayerAdapter.getCurrentMedia(),
                        state, getSessionToken());
                if (!mServiceInStartedState) {
                    ContextCompat.startForegroundService(PlayerService.this,
                            new Intent(PlayerService.this, PlayerService.class));
                    mServiceInStartedState = true;
                }
                startForeground(MediaNotificationManager.NOTIFICATION_ID, notification);
            }

            private void updateNotificationForPause(PlaybackStateCompat state) {
                Log.e(TAG, "updateNotificationForPause: ");
                stopForeground(false);
                Notification notification = mMediaNotificationManager.getNotification(
                        mPlayerAdapter.getCurrentMedia(), state, getSessionToken());
                mMediaNotificationManager.getNotificationManager()
                        .notify(MediaNotificationManager.NOTIFICATION_ID, notification);
            }

            private void moveServiceOutOfStartedState(PlaybackStateCompat state) {
                Log.e(TAG, "moveServiceOutOfStartedState: ");
                stopForeground(true);
                stopSelf();
                mServiceInStartedState = false;
            }
        }
    };
}
