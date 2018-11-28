package com.wang.window;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Window window = getWindow();

        View decorView = window.getDecorView();

        int flags = window.getAttributes().flags;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        window.getNavigationBarColor();
        window.getNavigationBarDividerColor();
        window.setNavigationBarColor(Color.RED);
        window.setNavigationBarDividerColor(Color.BLUE);

        window.getStatusBarColor();
        window.setStatusBarColor(Color.RED);

        window.getWindowManager();

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        EditText editText2 = (EditText) findViewById(R.id.et_enter);
//        editText2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(MainActivity.this, "你点了软键盘回车按钮",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

}
