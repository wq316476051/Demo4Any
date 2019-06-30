package com.wang.demo4any.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProviderFragment extends Fragment {

    private static final String TAG = "ProviderFragment";


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] databases = getActivity().databaseList();
        Arrays.stream(databases).forEach(name -> {
            File databasePath = getActivity().getDatabasePath(name);
            Log.d(TAG, "onCreate: name = " + name);
            Log.d(TAG, "onCreate: databasePath = " + databasePath);
            boolean delete = getActivity().deleteDatabase(name);
            Log.d(TAG, "onCreate: delete = " + delete);

            /*
                test.db
                test2.db
            */
        });

//        findViewById(R.id.btn_get_version).setOnClickListener(view -> {
//            DatabaseHelper helper = new DatabaseHelper(this);
//        });
//
//        findViewById(R.id.btn_click).setOnClickListener(view -> {
//            DatabaseHelper helper = new DatabaseHelper(this);
//            SQLiteDatabase database = helper.getReadableDatabase();
//            int version = database.getVersion();
//            Log.d(TAG, "onCreate: version = " + version);
//            database.close();
//        });
    }
}
