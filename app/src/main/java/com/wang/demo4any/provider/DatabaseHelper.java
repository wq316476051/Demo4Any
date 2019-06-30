package com.wang.demo4any.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String NAME = "test2.db";

    private static final int VERSION = 1;
    private static final int VERSION_2 = 2;

    private String mName;
    private int mVersion;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION_2);
        mName = NAME;
        mVersion = VERSION_2;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: db = " + db);
        Log.d(TAG, "onCreate: version = " + db.getVersion());
        updateDatabase(db, 0, VERSION_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: db = " + db);
        Log.d(TAG, "onUpgrade: oldVersion = " + oldVersion);
        Log.d(TAG, "onUpgrade: newVersion = " + newVersion);
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < VERSION) {
            db.execSQL("CREATE TABLE records (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)");
        }
        if (oldVersion < VERSION_2) {

        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Log.d(TAG, "onConfigure: version = " + db.getVersion());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen: version = " + db.getVersion());
    }
}
