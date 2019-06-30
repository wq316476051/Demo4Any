package com.wang.audio.recorder;

import android.media.MediaRecorder;

import com.wang.audio.RecordFile;
import com.wang.audio.Storage;

import java.io.File;

public class MyMediaRecorder extends AbsRecorder {

    private MediaRecorder mMediaRecorder;

    public MyMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setOutputFile(
                new File(Storage.getInstance().getDirectory(), RecordFile.createFilename("m4a")).getPath());
        setRecordState(AbsRecorder.INITIALIZED, true);
    }

    @Override
    public void startRecording() {
        if (isInitialized()) {
            mMediaRecorder.start();
            setRecordState(AbsRecorder.RECORDING, true);
        }
    }

    @Override
    public void pause() {
        if (isRecording()) {
            mMediaRecorder.pause();
            setRecordState(AbsRecorder.PAUSED, true);
        }
    }

    @Override
    public void resume() {
        if (isPaused()) {
            mMediaRecorder.resume();
            setRecordState(AbsRecorder.RECORDING, true);
        }
    }

    @Override
    public void stop() {
        if (isRecording() || isPaused()) {
            mMediaRecorder.stop();
            setRecordState(AbsRecorder.STOPPED, true);
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
