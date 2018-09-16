package com.wang.share.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * 注意: 没有检查参数, 请在传入前检查
 *
 * 分享文本:
 *      ShareHelper.create(this)
 *          .setTitle("分享文本...")
 *          .shareText("This is the message!");
 *
 * 分享单个文件:
 *      ShareHelper.create(this)
 *          .setTitle("分享文件...")
 *          .setMimeType(ShareHelper.TYPE_AUDIO)
 *          .shareFile(FileUtils.getAudioUri(this, "xxx.xxx"));
 *
 * 分享多个文件:
 *      ArrayList<Uri> shareUris = new ArrayList<>();
 *      shareUris.add(FileUtils.getAudioUri(this, "xxx.xxx"));
 *      shareUris.add(FileUtils.getAudioUri(this, "xxx.xxx"));
 *      ShareHelper.create(this)
 *          .setTitle("分享文件...")
 *          .setMimeType(ShareHelper.TYPE_AUDIO)
 *          .shareFiles(shareUris);
 */
public class ShareHelper {

    private static final String TAG = "ShareHelper";

    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_IMAGE = "image/*";
    public static final String TYPE_AUDIO = "audio/*";
    public static final String TYPE_VIDEO = "video/*";
    public static final String TYPE_FILE = "*/*";

    private Context context;
    private String mimeType = TYPE_FILE;
    private String title; // 分享选择窗口的标题

    private ShareHelper(Context context) {
        this.context = context;
    }

    public static ShareHelper create(Context context) {
        return new ShareHelper(context);
    }

    public ShareHelper setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public ShareHelper setTitle(String title) {
        this.title = title;
        return this;
    }

    public void shareText(String contentText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(TYPE_TEXT);
        shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);

        share(shareIntent);
    }

    public void shareFile(Uri shareFileUri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(mimeType);
        shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);

        share(shareIntent);
    }

    public void shareFiles(ArrayList<Uri> shareFileUris) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(mimeType);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, shareFileUris);

        share(shareIntent);
    }

    public void shareFileAndText(Uri shareFileUri, String contentText) {

    }

    private void checkParametersValid() {
        if (context == null) {
            throw new IllegalArgumentException("Activity can't be null!");
        }
    }

    private void share(Intent shareIntent) {
        shareIntent = Intent.createChooser(shareIntent, title);

        if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
            try {
                context.startActivity(shareIntent);
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }
}
