package com.wang.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wang.view.listview.ListViewActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_list_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_view:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
        }
    }

    public void print(String msg) {
        Log.e(TAG, "print: msg = " + msg);
    }
}
