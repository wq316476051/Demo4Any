package com.wang.audio.recorder;

import android.media.MediaRecorder;

public class MediaRecorderHelper extends BaseRecorder {

    private MediaRecorder mMediaRecorder;

    public MediaRecorderHelper() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setOutputFile("");
        setRecordState(BaseRecorder.INITIALIZED, true);
    }

    @Override
    public void startRecording() {
        if (isInitialized()) {
            mMediaRecorder.start();
            setRecordState(BaseRecorder.RECORDING, true);
        }
    }

    @Override
    public void pause() {
        if (isRecording()) {
            mMediaRecorder.pause();
            setRecordState(BaseRecorder.PAUSED, true);
        }
    }

    @Override
    public void resume() {
        if (isPaused()) {
            mMediaRecorder.resume();
            setRecordState(BaseRecorder.RECORDING, true);
        }
    }

    @Override
    public void stop() {
        if (isRecording() || isPaused()) {
            mMediaRecorder.stop();
            setRecordState(BaseRecorder.STOPPED, true);
        }
    }

    @Override
    public int getMaxAmplitude() {
        if (isRecording() || isPaused()) {
            mMediaRecorder.getMaxAmplitude();
        }
        return -1;
    }
}
