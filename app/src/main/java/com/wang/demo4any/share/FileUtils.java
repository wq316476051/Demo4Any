package com.wang.demo4any.share;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class FileUtils {
    
    private static final String TAG = "FileUtils";

    private FileUtils() {}

    public static Uri getAudioUri(Context context, String filePath) {
        Log.d(TAG, "getAudioUri: filePath = " + filePath);
        if (filePath == null) {
            return null;
        }
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String _id = MediaStore.Audio.Media._ID;
        String data = MediaStore.Audio.Media.DATA;

        Uri uri = null;
        Cursor cursor = context.getContentResolver().query(baseUri, new String[]{ _id },
                data + "=?", new String[]{ filePath }, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(_id));
                uri = Uri.withAppendedPath(baseUri, String.valueOf(id));
                Log.d(TAG, "getAudioUri: uri (by query) = " + uri);
                return uri;
            }
            cursor.close();
        }

        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(data, filePath);
            uri = context.getContentResolver().insert(baseUri, values);
            Log.d(TAG, "getAudioUri: uri (by insert) = " + uri);
        }
        return uri;
    }


}
