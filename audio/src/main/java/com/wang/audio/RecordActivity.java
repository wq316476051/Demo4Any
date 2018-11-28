package com.wang.audio;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.wang.audio.recorder.BaseRecorder;
import com.wang.audio.recorder.MediaRecorderHelper;
import com.wang.audio.recorder.Recorder;

public class RecordActivity extends Activity {

    private static final String TAG = "RecordActivity";

    private BaseRecorder mRecorder;

    private Button mBtnStart, mBtnPause, mBtnResume, mBtnStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initViews();
        initData();
        setListeners();
    }

    private void initViews() {
        mBtnStart = findViewById(R.id.btn_start);
        mBtnPause = findViewById(R.id.btn_pause);
        mBtnResume = findViewById(R.id.btn_resume);
        mBtnStop = findViewById(R.id.btn_stop);
    }

    private void initData() {
        mRecorder = new MediaRecorderHelper();
        mRecorder.setOnConnectChangeListener(state -> {
            handleConnectStateChange(state);
        });
        mRecorder.setOnRecordStateChangeListener(state -> {
            handleRecordStateChange(state);
        });
    }

    private void setListeners() {
        mBtnStart.setOnClickListener(view -> {
            mRecorder.startRecording();
        });

        mBtnPause.setOnClickListener(view -> {
            mRecorder.pause();
        });

        mBtnResume.setOnClickListener(view -> {
            mRecorder.resume();
        });

        mBtnStop.setOnClickListener(view -> {
            mRecorder.stop();
        });
    }

    private void handleRecordStateChange(int state) {
        setButtonsEnabled(false);
        switch (state) {
            case BaseRecorder.UNINITIALIZED:
                break;
            case BaseRecorder.INITIALIZED:
                mBtnStart.setEnabled(true);
                break;
            case BaseRecorder.RECORDING:
                mBtnPause.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
            case BaseRecorder.PAUSED:
                mBtnStart.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
            case BaseRecorder.STOPPED:
                mBtnStart.setEnabled(true);
                break;
            default:
                Log.d(TAG, "handleRecordStateChange: default");
                break;
        }
    }

    private void handleConnectStateChange(int state) {
        switch (state) {
            case BaseRecorder.CONNECTED:
                break;
            case BaseRecorder.DISCONNECTED:
                break;
            default:
                Log.d(TAG, "handleConnectStateChange: default");
                break;
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        mBtnStart.setEnabled(enabled);
        mBtnPause.setEnabled(enabled);
        mBtnResume.setEnabled(enabled);
        mBtnStop.setEnabled(enabled);
    }
}
