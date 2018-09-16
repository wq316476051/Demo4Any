package com.wang.share;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.wang.share.utils.FileUtils;
import com.wang.share.utils.ShareHelper;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends PermissionActivity implements View.OnClickListener {

    private Button mBtnShareText, mBtnShareFile, mBtnShareMultiFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnShareText = findViewById(R.id.btn_share_text);
        mBtnShareFile = findViewById(R.id.btn_share_file);
        mBtnShareMultiFiles = findViewById(R.id.btn_share_multi_files);

        mBtnShareText.setOnClickListener(this);
        mBtnShareFile.setOnClickListener(this);
        mBtnShareMultiFiles.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_text:
                ShareHelper.create(this)
                        .setTitle("分享文本...")
                        .shareText("This is the message!");
                break;
            case R.id.btn_share_file:
                if (!isExternalStoragePermissionGranted()) {
                    requestExternalStoragePermission();
                }
                String filePath = Environment.getExternalStorageDirectory() + File.pathSeparator +
                        "recording38792151526322525.3gpp";
                Uri uri = FileUtils.getAudioUri(this, filePath);

                ShareHelper.create(this)
                        .setTitle("分享文件...")
                        .setMimeType(ShareHelper.TYPE_AUDIO)
                        .shareFile(uri);
                break;
            case R.id.btn_share_multi_files:
                if (!isExternalStoragePermissionGranted()) {
                    requestExternalStoragePermission();
                }
                String filePath0 = Environment.getExternalStorageDirectory() + File.pathSeparator +
                        "recording38792151526322525.3gpp";
                Uri uri0 = FileUtils.getAudioUri(this, filePath0);
                String filePath1 = Environment.getExternalStorageDirectory() + File.pathSeparator +
                        "recording423259449768923467.3gpp";
                Uri uri1 = FileUtils.getAudioUri(this, filePath1);

                ArrayList<Uri> shareUris = new ArrayList<>();
                shareUris.add(uri0);
                shareUris.add(uri1);
                ShareHelper.create(this)
                        .setTitle("分享文件...")
                        .setMimeType(ShareHelper.TYPE_AUDIO)
                        .shareFiles(shareUris);
                break;
        }
    }
}
