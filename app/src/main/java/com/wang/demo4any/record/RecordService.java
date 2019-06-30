package com.wang.demo4any.record;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wang.demo4any.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import androidx.annotation.Nullable;

public class RecordService extends Service {

    private static final String TAG = "RecordService";

    private BinderService mBinderService;

    private IRecordCallback mRecordCallback;

    private MediaRecorder mRecorder;

    private int mState = RecordManager.STATE_IDLE;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        mBinderService = new BinderService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderService;
    }

    private void wrapException(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
            mRecorder.release();
            mState = RecordManager.STATE_IDLE;
            notifyState();
        }
    }

    private int wrapExceptionWithResult(Callable<Integer> callable, int defValue) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            mRecorder.release();
            mState = RecordManager.STATE_IDLE;
            notifyState();
        }
        return defValue;
    }

    private void notifyState() {
        if (mRecordCallback != null) {
            try {
                mRecordCallback.onStateChanged(mState);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class BinderService extends IRecordService.Stub {
        @Override
        public void registerCallback(IRecordCallback callback) throws RemoteException {
            mRecordCallback = callback;
            mRecordCallback.onStateChanged(mState);
        }

        @Override
        public void unregisterCallback() throws RemoteException {
            mRecordCallback = null;
        }

        @Override
        public void startRecording() throws RemoteException {
            File record = new File(RecordStorage.getStorageDirectory(),
                    new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(new Date()) + ".m4a");
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFile(record);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioChannels(2);
            mRecorder.setAudioSamplingRate(48000);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            wrapException(() -> {
                mRecorder.start();
                mState = RecordManager.STATE_RECORDING;
                notifyState();
            });
        }

        @Override
        public void pause() throws RemoteException {
            wrapException(() -> {
                mRecorder.pause();
                mState = RecordManager.STATE_PAUSED;
                notifyState();
            });
        }

        @Override
        public void resume() throws RemoteException {
            wrapException(() -> {
                mRecorder.resume();
                mState = RecordManager.STATE_RECORDING;
                notifyState();
            });
        }

        @Override
        public void stopRecording() throws RemoteException {
            wrapException(() -> {
                mRecorder.stop();
                mRecorder.release();
                mState = RecordManager.STATE_IDLE;
                notifyState();
            });
        }

        @Override
        public int getAmplitude() throws RemoteException {
            return wrapExceptionWithResult(() -> mRecorder.getMaxAmplitude(), -1);
        }

        @Override
        public int getState() throws RemoteException {
            return mState;
        }
    }

    private class NotificationHelper {

    }
}
