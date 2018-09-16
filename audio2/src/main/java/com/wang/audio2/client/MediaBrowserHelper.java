package com.wang.audio2.client;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MediaBrowserHelper {

    private static final String TAG = "MediaBrowserHelper";

    private Context mContext;
    private Class<? extends MediaBrowserServiceCompat> mMediaBrowserServiceClass;

    private MediaBrowserCompat mMediaBrowser;
    private MediaControllerCompat mMediaController;
    private final List<MediaControllerCompat.Callback> mCallbackList = new ArrayList<>();

    public MediaBrowserHelper(Context context, Class<? extends MediaBrowserServiceCompat> serviceClass) {
        mContext = context.getApplicationContext();
        mMediaBrowserServiceClass = serviceClass;
    }

    public void onStart() {
        if (mMediaBrowser == null) {
            mMediaBrowser = new MediaBrowserCompat(mContext,
                    new ComponentName(mContext, mMediaBrowserServiceClass),
                    mConnectionCallback, null);
            mMediaBrowser.connect();
        }
        Log.e(TAG, "onStart: Creating MediaBrowser, and connecting");
    }

    public void onStop() {
        if (mMediaController != null) {
            mMediaController.unregisterCallback(mMediaControllerCallback);
            mMediaController = null;
        }
        if (mMediaBrowser != null && mMediaBrowser.isConnected()) {
            mMediaBrowser.disconnect();
            mMediaBrowser = null;
        }
        resetState();
        Log.e(TAG, "onStop: Releasing MediaController, Disconnecting from MediaBrowser");
    }

    private void resetState() {
        performOnAllCallbacks(new CallbackCommand() {
            @Override
            public void perform(@NonNull MediaControllerCompat.Callback callback) {
                callback.onPlaybackStateChanged(null);
            }
        });
    }

    public void onConnected(MediaControllerCompat mediaController) {

    }

    public void onChildrenLoaded(String parentId, List<MediaBrowserCompat.MediaItem> children) {

    }

    public void registerCallback(MediaControllerCompat.Callback callback) {
        Log.e(TAG, "registerCallback: ");
        if (callback != null) {
            mCallbackList.add(callback);

            if (mMediaController != null) {
                MediaMetadataCompat metadata = mMediaController.getMetadata();
                if (metadata != null) {
                    Log.e(TAG, "registerCallback: metadata = " + metadata);
                    callback.onMetadataChanged(metadata);
                }

                PlaybackStateCompat playbackState = mMediaController.getPlaybackState();
                if (playbackState != null) {
                    callback.onPlaybackStateChanged(playbackState);
                }
            }
        }
    }

    public void unregisterCallback(MediaControllerCompat.Callback callback) {
        if (callback != null) {
            mCallbackList.remove(callback);
            if (mMediaController != null) {
                mMediaController.unregisterCallback(callback);
            }
        }
    }

    public MediaControllerCompat getMediaController() {
        return mMediaController;
    }

    public MediaControllerCompat.TransportControls getTransportControls() {
        if (mMediaController == null) {
            Log.d(TAG, "getTransportControls: MediaController is null!");
            throw new IllegalStateException("MediaController is null!");
        }
        return mMediaController.getTransportControls();
    }

    private MediaBrowserCompat.ConnectionCallback mConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            super.onConnected();
            Log.e(TAG, "onConnected: ");
            try {
                mMediaController = new MediaControllerCompat(mContext, mMediaBrowser.getSessionToken());
                mMediaController.registerCallback(mMediaControllerCallback);

                mMediaControllerCallback.onMetadataChanged(mMediaController.getMetadata());
                mMediaControllerCallback.onPlaybackStateChanged(mMediaController.getPlaybackState());

                MediaBrowserHelper.this.onConnected(mMediaController);
            } catch (RemoteException e) {
                Log.e(TAG, String.format("onConnected: Problem: $s", e.toString()));
                throw new RuntimeException(e);
            }
            Log.e(TAG, "onConnected: " + mMediaBrowser.getRoot());
            mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mSubscriptionCallback);
        }
    };

    private MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback = new MediaBrowserCompat.SubscriptionCallback() {
        @Override
        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
            super.onChildrenLoaded(parentId, children);
            Log.e(TAG, "onChildrenLoaded: " + children);
            MediaBrowserHelper.this.onChildrenLoaded(parentId, children);
        }
    };

    private MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onMetadataChanged(final MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            Log.e(TAG, "onMetadataChanged: " + metadata);
            performOnAllCallbacks(new CallbackCommand() {
                @Override
                public void perform(@NonNull MediaControllerCompat.Callback callback) {
                    callback.onMetadataChanged(metadata);
                }
            });
        }

        @Override
        public void onPlaybackStateChanged(final PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            Log.e(TAG, "onPlaybackStateChanged: ");
            performOnAllCallbacks(new CallbackCommand() {
                @Override
                public void perform(@NonNull MediaControllerCompat.Callback callback) {
                    callback.onPlaybackStateChanged(state);
                }
            });
        }
    };

    private void performOnAllCallbacks(CallbackCommand command) {
        for (MediaControllerCompat.Callback callback : mCallbackList) {
            if (callback != null) {
                command.perform(callback);
            }
        }
    }

    private interface CallbackCommand {
        void perform(@NonNull MediaControllerCompat.Callback callback);
    }
}
