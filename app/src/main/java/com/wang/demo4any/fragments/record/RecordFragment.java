package com.wang.demo4any.fragments.record;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.demo4any.R;
import com.wang.demo4any.record.RecordManager;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class RecordFragment extends Fragment {

    private static final String TAG = "RecordFragment";

    private static final int MSG_AMPLITUDE = 100;

    private static final long MSG_AMPLITUDE_DELAY = TimeUnit.SECONDS.toMillis(1);

    private TextView mTvAmplitude;

    private Button mBtnStartPause;

    private Button mBtnStop;

    private RecordManager mRecordManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AMPLITUDE) {
                startPrintAmplitude();
            }
        }
    };

    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + getActivity());
        mRecordManager = new RecordManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvAmplitude = view.findViewById(R.id.tv_amplitude);
        mBtnStartPause = view.findViewById(R.id.btn_start_pause);
        mBtnStop = view.findViewById(R.id.btn_stop);

        mBtnStartPause.setOnClickListener(button -> {
            int state = mRecordManager.getState();
            switch (state) {
                case RecordManager.STATE_IDLE:
                    mRecordManager.startRecording();
                    break;
                case RecordManager.STATE_RECORDING:
                    mRecordManager.pause();
                    break;
                case RecordManager.STATE_PAUSED:
                    mRecordManager.resume();
                    break;
            }
        });

        mBtnStop.setOnClickListener(button -> {
            int state = mRecordManager.getState();
            if (state == RecordManager.STATE_RECORDING || state == RecordManager.STATE_PAUSED) {
                mRecordManager.stopRecording();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.setTitle(R.string.title_record);
        mRecordManager.addRecordStateChangeListener(this::updateUiOnState);
        updateUiOnState(mRecordManager.getState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void updateUiOnState(int state) {
        if (state != RecordManager.STATE_RECORDING) {
            stopPrintAmplitude();
        }
        switch (state) {
            case RecordManager.STATE_UNDEFINED:
                mBtnStartPause.setEnabled(false);
                mBtnStop.setEnabled(false);
                break;
            case RecordManager.STATE_IDLE:
                mBtnStartPause.setText("Start");
                mBtnStartPause.setEnabled(true);
                mBtnStop.setEnabled(false);
                break;
            case RecordManager.STATE_RECORDING:
                mBtnStartPause.setText("Pause");
                mBtnStartPause.setEnabled(true);
                mBtnStop.setEnabled(true);
                startPrintAmplitude();
                break;
            case RecordManager.STATE_PAUSED:
                mBtnStartPause.setText("Start");
                mBtnStartPause.setEnabled(true);
                mBtnStop.setEnabled(true);
                break;
        }
    }

    private void startPrintAmplitude() {
        int amplitude = mRecordManager.getAmplitude();
        mTvAmplitude.setText(String.valueOf(amplitude));
        mHandler.sendEmptyMessageDelayed(MSG_AMPLITUDE, MSG_AMPLITUDE_DELAY);
    }

    private void stopPrintAmplitude() {
        mHandler.removeMessages(MSG_AMPLITUDE);
    }
}
