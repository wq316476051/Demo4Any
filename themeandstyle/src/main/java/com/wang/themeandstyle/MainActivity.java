package com.wang.themeandstyle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Title");
        setTitleColor(Color.RED);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_dim_behind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyDialogActivity.class));
            }
        });
    }


}
