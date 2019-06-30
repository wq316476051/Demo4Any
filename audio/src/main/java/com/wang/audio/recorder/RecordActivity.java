package com.wang.audio.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.wang.audio.R;
import com.wang.audio.recorder.AbsRecorder;
import com.wang.audio.recorder.MyMediaRecorder;

public class RecordActivity extends Activity {

    private static final String TAG = "RecordActivity";

    private AbsRecorder mRecorder;

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
        mRecorder = new MyMediaRecorder();
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
            case AbsRecorder.UNINITIALIZED:
                break;
            case AbsRecorder.INITIALIZED:
                mBtnStart.setEnabled(true);
                break;
            case AbsRecorder.RECORDING:
                mBtnPause.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
            case AbsRecorder.PAUSED:
                mBtnStart.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
            case AbsRecorder.STOPPED:
                mBtnStart.setEnabled(true);
                break;
            default:
                Log.d(TAG, "handleRecordStateChange: default");
                break;
        }
    }

    private void handleConnectStateChange(int state) {
        switch (state) {
            case AbsRecorder.CONNECTED:
                break;
            case AbsRecorder.DISCONNECTED:
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
