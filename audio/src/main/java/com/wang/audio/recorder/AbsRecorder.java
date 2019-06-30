package com.wang.audio.recorder;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class AbsRecorder implements Recorder {

    public static final int DISCONNECTED = 0;
    public static final int CONNECTED = 1;

    public static final int UNINITIALIZED = -1;
    public static final int INITIALIZED = 0;
    public static final int RECORDING = 1;
    public static final int PAUSED = 2;
    public static final int STOPPED = 3;

    private int mRecordState = UNINITIALIZED;
    private int mConnectState = DISCONNECTED;

    private ListenerInfo mListenerInfo = new ListenerInfo();

    @Override
    public boolean isInitialized() {
        return mRecordState == INITIALIZED;
    }

    @Override
    public boolean isRecording() {
        return mRecordState == RECORDING;
    }

    @Override
    public boolean isPaused() {
        return mRecordState == PAUSED;
    }

    @Override
    public boolean isStopped() {
        return mRecordState == STOPPED;
    }
    /************************
     * ConnectChange
     ************************/
    public void setOnConnectChangeListener(OnConnectChangeListener listener) {
        mListenerInfo.mConnectChangeListener = listener;
    }

    protected void setConnectState(@RecordState int state) {
        setConnectState(state, false);
    }

    protected void setConnectState(@RecordState int state, boolean notify) {
        mConnectState = state;
        if (notify) {
            notifyConnectState();
        }
    }

    private void notifyConnectState() {
        if (mListenerInfo.mConnectChangeListener != null) {
            mListenerInfo.mConnectChangeListener.onConnectChangeListener(mConnectState);
        }
    }

    /************************
     * RecordState
     ************************/
    public void setOnRecordStateChangeListener(OnRecordStateChangeListener listener) {
        mListenerInfo.mRecordStateChangeListener = listener;
        setConnectState(mRecordState);
        notifyRecordState();
    }

    protected void setRecordState(@RecordState int state) {
        setRecordState(state, false);
    }

    protected void setRecordState(@RecordState int state, boolean notify) {
        mRecordState = state;
        if (notify) {
            notifyRecordState();
        }
    }

    protected void notifyRecordState() {
        if (mListenerInfo.mRecordStateChangeListener != null) {
            mListenerInfo.mRecordStateChangeListener.onRecordStateChangeListener(mRecordState);
        }
    }

    /************************
    * Listeners
    ************************/
    private class ListenerInfo {
        public OnConnectChangeListener mConnectChangeListener;
        public OnRecordStateChangeListener mRecordStateChangeListener;
    }

    public interface OnConnectChangeListener {
        void onConnectChangeListener(@ConnectState int state);
    }

    public interface OnRecordStateChangeListener {
        void onRecordStateChangeListener(@RecordState int state);
    }

    @IntDef({DISCONNECTED, CONNECTED})
    @Retention(RetentionPolicy.SOURCE)
    @interface ConnectState {}

    @IntDef({UNINITIALIZED, INITIALIZED, RECORDING, PAUSED, STOPPED})
    @Retention(RetentionPolicy.SOURCE)
    @interface RecordState {}
}
