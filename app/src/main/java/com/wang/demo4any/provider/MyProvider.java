package com.wang.demo4any.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyProvider extends ContentProvider {
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int IMAGES_MEDIA = 1;
    private static final int IMAGES_MEDIA_ID = 2;
    private static final int AUDIO_MEDIA = 3;
    private static final int AUDIO_MEDIA_ID = 4;
    private static final int VIDEO_MEDIA = 5;
    private static final int VIDEO_MEDIA_ID = 6;
    static {
        URI_MATCHER.addURI("media", "images", IMAGES_MEDIA);
        URI_MATCHER.addURI("media", "images/#", IMAGES_MEDIA_ID);
        URI_MATCHER.addURI("media", "audio", AUDIO_MEDIA);
        URI_MATCHER.addURI("media", "audio/#", AUDIO_MEDIA_ID);
        URI_MATCHER.addURI("media", "video", VIDEO_MEDIA);
        URI_MATCHER.addURI("media", "video/#", VIDEO_MEDIA_ID);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    
    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("table");
        qb.appendWhere("_id = " + 3);
        return null;
    }

    
    @Override
    public String getType(Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
