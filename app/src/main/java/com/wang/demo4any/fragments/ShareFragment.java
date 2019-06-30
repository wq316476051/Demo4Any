package com.wang.demo4any.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wang.demo4any.R;
import com.wang.demo4any.share.FileUtils;
import com.wang.demo4any.share.ShareHelper;
import com.wang.demo4any.utils.PermissionUtils;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ShareFragment";

    public static final String TITLE = "Share";

    private Button mBtnShareText;
    private Button mBtnShareFile;
    private Button mBtnShareMultiFiles;

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnShareText = view.findViewById(R.id.btn_share_text);
        mBtnShareFile = view.findViewById(R.id.btn_share_file);
        mBtnShareMultiFiles = view.findViewById(R.id.btn_share_multi_files);

        mBtnShareText.setOnClickListener(this);
        mBtnShareFile.setOnClickListener(this);
        mBtnShareMultiFiles.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_text:
                ShareHelper.create(getActivity())
                        .setTitle("分享文本...")
                        .shareText("This is the message!");
                break;
            case R.id.btn_share_file:
                if (!PermissionUtils.hasStoragePermission()) {
                    PermissionUtils.requestStoragePermission(getActivity());
                    return;
                }
                String filePath = Environment.getExternalStorageDirectory() + File.separator +
                        "recording38792151526322525.3gpp";
                Uri uri = FileUtils.getAudioUri(getActivity(), filePath);

                ShareHelper.create(getActivity())
                        .setTitle("分享文件...")
                        .setMimeType(ShareHelper.AUDIO_ANY)
                        .shareFile(uri);
                break;
            case R.id.btn_share_multi_files:
                if (!PermissionUtils.hasStoragePermission()) {
                    PermissionUtils.requestStoragePermission(getActivity());
                    return;
                }
                String filePath0 = Environment.getExternalStorageDirectory() + File.separator +
                        "recording38792151526322525.3gpp";
                Uri uri0 = FileUtils.getAudioUri(getActivity(), filePath0);
                String filePath1 = Environment.getExternalStorageDirectory() + File.separator +
                        "recording423259449768923467.3gpp";
                Uri uri1 = FileUtils.getAudioUri(getActivity(), filePath1);

                ArrayList<Uri> shareUris = new ArrayList<>();
                shareUris.add(uri0);
                shareUris.add(uri1);
                ShareHelper.create(getActivity())
                        .setTitle("分享文件...")
                        .setMimeType(ShareHelper.AUDIO_ANY)
                        .shareFiles(shareUris);
                break;
        }
    }
}
