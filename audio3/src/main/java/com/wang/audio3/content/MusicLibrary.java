package com.wang.audio3.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.wang.audio3.AudioApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MusicLibrary {

    private static final String TAG = "MusicLibrary";
    private static final TreeMap<String, MediaMetadataCompat> sMusic = new TreeMap<>();

    static {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = AudioApplication.getContext().getContentResolver().query(uri, null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            Log.e(TAG, "getMediaMetadatas: id=" + id + "; album=" + album);
            sMusic.put(id, new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                    .build());
        }
    }

    public static List<MediaBrowserCompat.MediaItem> getMediaItems() {
        List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();
        for (MediaMetadataCompat metadata : sMusic.values()) {
            result.add(new MediaBrowserCompat.MediaItem(metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }
        return result;
    }
}
