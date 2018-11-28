package com.wang.storage;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

/**
 * For example:
 *
 * MediaScanner scanner = new MediaScanner(this);
 * scanner.setCallback(new MediaScanner.Callback() {
 *     @Override
 *     public void onScanCompleted(String path, Uri uri) {
 *     }
 * });
 * scanner.scanFile(new File("file-path"), "mimeType");
 * scanner.release();
 */
public class MediaScanner {

    private static final String TAG = "MediaScanner";

    private File mFile;
    private String mMimeType;
    private MediaScannerConnection mConn;
    private MediaScannerConnection.OnScanCompletedListener mCompletedListener;

    public MediaScanner(Context context) {
        mConn = new MediaScannerConnection(context, mClient);
    }

    public void setCallback(MediaScannerConnection.OnScanCompletedListener listener) {
        mCompletedListener = listener;
    }

    public void scanFile(@NonNull File file, @Nullable String mimeType) {
        mFile = file;
        mMimeType = mimeType;
        if (!mConn.isConnected()) {
            mConn.connect();
        }
    }

    public void release() {
        mCompletedListener = null;
        if (mConn.isConnected()) {
            mConn.disconnect();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

    private MediaScannerConnection.MediaScannerConnectionClient mClient
            = new MediaScannerConnection.MediaScannerConnectionClient() {
        @Override
        public void onMediaScannerConnected() {
            Log.d(TAG, "onMediaScannerConnected: ");
            if (mFile != null && mFile.exists() && mFile.isFile()) {
                mConn.scanFile(mFile.getAbsolutePath(), mMimeType);
            }
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            Log.d(TAG, "onScanCompleted: ");
            if (mCompletedListener != null) {
                mCompletedListener.onScanCompleted(path, uri);
            }
        }
    };
}
