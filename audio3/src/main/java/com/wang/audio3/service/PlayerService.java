package com.wang.audio3.service;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.wang.audio3.AudioApplication;
import com.wang.audio3.content.MusicLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PlayerService extends MediaBrowserServiceCompat {

    private static final String TAG = "PlayerService";
    private static final String CLIENT_PACKAGE = "com.wang.audio3";
    private static final String ROOT_ID = "root";
    private static final int SESSION_FLAGS =
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
            | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            | MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS;

    private MediaSessionCompat mMediaSession;
    private MediaSessionCompat.Callback mMediaSessionCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaSession = new MediaSessionCompat(this, TAG);
        mMediaSessionCallback = new MediaSessionCallback();
        mMediaSession.setCallback(mMediaSessionCallback);
        mMediaSession.setFlags(SESSION_FLAGS);
        setSessionToken(mMediaSession.getSessionToken());
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        Log.e(TAG, "onGetRoot: clientPackageName=" + clientPackageName
                + "; clientUid=" + clientUid + "; rootHints=" + rootHints);
        if (CLIENT_PACKAGE.equals(clientPackageName)) {
            return new BrowserRoot(ROOT_ID, null);
        }
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.e(TAG, "onLoadChildren: parentId=" + parentId);
        result.detach();
        new Task(result).execute();
    }

    private class Task extends AsyncTask<Void, Void, List<MediaBrowserCompat.MediaItem>> {
        private Result<List<MediaBrowserCompat.MediaItem>> mResult;
        public Task(Result<List<MediaBrowserCompat.MediaItem>> result) {
            mResult = result;
        }

        @Override
        protected List<MediaBrowserCompat.MediaItem> doInBackground(Void... voids) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            TreeMap<String, MediaMetadataCompat> music = new TreeMap<>();
            while (cursor != null && cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                Log.e(TAG, "getMediaMetadatas: id=" + id + "; album=" + album);
                music.put(id, new MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                        .build());
            }
            List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
            for (MediaMetadataCompat metadata : music.values()) {
                result.add(new MediaBrowserCompat.MediaItem(metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<MediaBrowserCompat.MediaItem> mediaItems) {
            super.onPostExecute(mediaItems);
            mResult.sendResult(mediaItems);
        }
    }

    /**
     * 接受客户端的命令
     */
    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStop() {

        }
    }
}
