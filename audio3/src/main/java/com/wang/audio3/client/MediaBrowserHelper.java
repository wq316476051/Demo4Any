package com.wang.audio3.client;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.wang.audio3.service.PlayerService;

import java.util.List;

public abstract class MediaBrowserHelper {

    private static final String TAG = "MediaBrowserHelper";

    private Context mContext;
    private Class<? extends MediaBrowserServiceCompat> mMediaBrowserServiceClass;
    private MediaBrowserCompat mMediaBrowser;
    private MediaControllerCompat mMediaController;
    private MediaControllerCompat.Callback mMediaControllerCallback;
    
    public MediaBrowserHelper(Context context, Class<? extends MediaBrowserServiceCompat> mediaBrowserServiceClass) {
        mContext = context.getApplicationContext();
        mMediaBrowserServiceClass = mediaBrowserServiceClass;
        mMediaControllerCallback = new MediaControllerCallback();
    }

    public void onStart() {
        if (mMediaBrowser == null) {
            ComponentName serviceComponent = new ComponentName(mContext, mMediaBrowserServiceClass);
            MediaBrowserCompat.ConnectionCallback callback = new MediaConnectionCallback();
            Bundle rootHints = null;
            mMediaBrowser = new MediaBrowserCompat(mContext, serviceComponent, callback, rootHints);
        }
        mMediaBrowser.connect();
        Log.e(TAG, "onStart: Create MediaBrowser and connecting");
    }

    public void onStop() {
        if (mMediaControllerCallback != null) {
            mMediaController.unregisterCallback(mMediaControllerCallback);
            mMediaController = null;
        }
        if (mMediaBrowser != null && mMediaBrowser.isConnected()) {
            mMediaBrowser.disconnect();
        }
        Log.e(TAG, "onStop: Releasing MediaController; Disconnecting from MediaBrowser");
    }

    public MediaControllerCompat getMediaController() {
        if (mMediaController == null) {
            throw new IllegalStateException("MediaController is null");
        }
        return mMediaController;
    }

    public MediaControllerCompat.TransportControls getTransportControls() {
        if (mMediaController == null) {
            Log.e(TAG, "getTransportControls: MediaController is null");
            throw new IllegalStateException("MediaController is null");
        }
        return mMediaController.getTransportControls();
    }

    public MediaMetadataCompat getMediaMetadata() {
        if (mMediaController != null) {
            return mMediaController.getMetadata();
        }
        return null;
    }

    public PlaybackStateCompat getPlaybackState() {
        if (mMediaController != null) {
            return mMediaController.getPlaybackState();
        }
        return null;
    }

    private class MediaConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private static final String TAG = "MediaConnectionCallback";

        @Override
        public void onConnected() {
            Log.e(TAG, "onConnected: ");
            try {
                mMediaController = new MediaControllerCompat(mContext, mMediaBrowser.getSessionToken());
                mMediaController.registerCallback(mMediaControllerCallback);

                // Sync existing MediaSession state to the UI.
                mMediaControllerCallback.onMetadataChanged(mMediaController.getMetadata());
                mMediaControllerCallback.onPlaybackStateChanged(mMediaController.getPlaybackState());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            String parentId = mMediaBrowser.getRoot();
            MediaBrowserCompat.SubscriptionCallback callback = new MediaBrowserSubscriptionCallback();
            mMediaBrowser.subscribe(parentId, callback);

            MediaBrowserHelper.this.onConnected();
        }

        @Override
        public void onConnectionSuspended() { // suspend: 暂停, 延缓
            Log.e(TAG, "onConnectionSuspended: ");
        }

        @Override
        public void onConnectionFailed() { // 如果 MediaBrowserService#onGetRoot() 返回 null
            Log.e(TAG, "onConnectionFailed: ");
            MediaBrowserHelper.this.onConnectionFailed();
        }
    }

    private class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback {
        private final String TAG = MediaBrowserSubscriptionCallback.class.getSimpleName();
        @Override
        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
            Log.e(TAG, "onChildrenLoaded: ");
            MediaBrowserHelper.this.onChildrenLoaded(parentId, children);
        }
    }

    private class MediaControllerCallback extends MediaControllerCompat.Callback {
        private static final String TAG = "MediaControllerCallback";
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            Log.e(TAG, "onPlaybackStateChanged: state=" + state);
            if (state != null) {
                MediaBrowserHelper.this.onPlaybackStateChanged(state);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            Log.e(TAG, "onMetadataChanged: metadata=" + metadata);
            if (metadata != null) {
                MediaBrowserHelper.this.onMetadataChanged(metadata);
            }
        }
    }

    protected abstract void onConnected();
    protected abstract void onConnectionFailed();
    protected abstract void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children);
    protected abstract void onPlaybackStateChanged(PlaybackStateCompat state);
    protected abstract void onMetadataChanged(MediaMetadataCompat metadata);
}
