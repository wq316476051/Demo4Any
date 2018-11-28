package com.wang.unittest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv = findViewById(R.id.tv_second_name);

        if (getIntent().hasExtra("name")) {
            tv.setText(getIntent().getStringExtra("name"));
        }
    }
}