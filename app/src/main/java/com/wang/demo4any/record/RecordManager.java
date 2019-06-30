package com.wang.demo4any.record;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class RecordManager implements DefaultLifecycleObserver {

    private static final String TAG = "RecordManager";

    public static final int STATE_UNDEFINED = -1;

    public static final int STATE_IDLE = 0;

    public static final int STATE_RECORDING = 1;

    public static final int STATE_PAUSED = 2;

    private FragmentActivity mActivity;

    private IRecordService mService;

    private final Object mLock = new Object();

    private List<OnRecordStateChangeListener> mListeners = new ArrayList<>();

    private IRecordCallback mRecordCallback = new IRecordCallback.Stub() {
        @Override
        public void onStateChanged(int state) throws RemoteException {
            synchronized (mLock) {
                for (OnRecordStateChangeListener listener : mListeners) {
                    listener.onStateChanged(state);
                }
            }
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mService = IRecordService.Stub.asInterface(service);
            try {
                mService.registerCallback(mRecordCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    public RecordManager(@NonNull FragmentActivity activity) {
        mActivity = activity;
        mActivity.getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onCreate: ");
        Intent intent = new Intent(mActivity, RecordService.class);
        mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onDestroy: ");
        mActivity.unbindService(mServiceConnection);
    }

    public void addRecordStateChangeListener(OnRecordStateChangeListener listener) {
        synchronized (mLock) {
            mListeners.add(listener);
        }
    }

    public void removeRecordStateChangeListener(OnRecordStateChangeListener listener) {
        synchronized (mLock) {
            mListeners.remove(listener);
        }
    }

    public void startRecording() {
        if (mService != null) {
            try {
                mService.startRecording();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (mService != null) {
            try {
                mService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        if (mService != null) {
            try {
                mService.resume();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRecording() {
        if (mService != null) {
            try {
                mService.stopRecording();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAmplitude() {
        if (mService != null) {
            try {
                return mService.getAmplitude();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public int getState() {
        if (mService != null) {
            try {
                return mService.getState();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return STATE_UNDEFINED;
    }

    public interface OnRecordStateChangeListener {
        void onStateChanged(int state);
    }
}
