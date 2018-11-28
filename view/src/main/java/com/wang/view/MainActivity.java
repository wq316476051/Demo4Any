package com.wang.view;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.util.Property;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.wang.view.listview.ListViewActivity;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity_view";
    private ListView mListView;
    private Button mButton;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn_list_view);
        Log.d(TAG, "onCreate: " + mButton.getRootView()); // DecorView
        Rect clipBounds = mButton.getClipBounds();
        Log.d(TAG, "onCreate: " + clipBounds);
        mButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ListViewActivity.class));
        });
        CheckBox cb = findViewById(R.id.cb);
        findViewById(R.id.relative).setOnClickListener(view -> {
            cb.setChecked(!cb.isChecked());
        });
        cb.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            Log.d(TAG, "onCreate: setOnCheckedChangeListener = " + isChecked);
        });

        mListView = findViewById(R.id.list_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
