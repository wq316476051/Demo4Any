package com.wang.demo4any.share;

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
 *          .setMimeType(ShareHelper.AUDIO_ANY)
 *          .shareFile(FileUtils.getAudioUri(this, "xxx.xxx"));
 *
 * 分享多个文件:
 *      ArrayList<Uri> shareUris = new ArrayList<>();
 *      shareUris.add(FileUtils.getAudioUri(this, "xxx.xxx"));
 *      shareUris.add(FileUtils.getAudioUri(this, "xxx.xxx"));
 *      ShareHelper.create(this)
 *          .setTitle("分享文件...")
 *          .setMimeType(ShareHelper.AUDIO_ANY)
 *          .shareFiles(shareUris);
 */
public class ShareHelper {

    private static final String TAG = "ShareHelper";

    public static final String TEXT_PLAIN = "text/plain";
    public static final String IMAGE_ANY = "image/*";
    public static final String AUDIO_ANY = "audio/*";
    public static final String VIDEO_ANY = "video/*";
    public static final String ANY_ANY = "*/*";

    private Context mContext;
    private String mMimeType = ANY_ANY;
    private String mTitle; // 分享选择窗口的标题

    private ShareHelper(Context context) {
        mContext = context;
    }

    public static ShareHelper create(Context context) {
        return new ShareHelper(context);
    }

    public ShareHelper setMimeType(String mimeType) {
        mMimeType = mimeType;
        return this;
    }

    public ShareHelper setTitle(String title) {
        mTitle = title;
        return this;
    }

    public void shareText(String contentText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(TEXT_PLAIN);
        shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);

        share(shareIntent);
    }

    public void shareFile(Uri shareFileUri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(mMimeType);
        shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);

        share(shareIntent);
    }

    public void shareFiles(ArrayList<Uri> shareFileUris) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType(mMimeType);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, shareFileUris);

        share(shareIntent);
    }

    private void share(Intent shareIntent) {
        Intent intent = Intent.createChooser(shareIntent, mTitle);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }
}
