package com.wang.demo4any;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
    }
}
