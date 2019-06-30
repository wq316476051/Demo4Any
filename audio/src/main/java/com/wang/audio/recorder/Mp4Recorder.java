package com.wang.audio.recorder;

import android.media.AudioRecord;

public class Mp4Recorder extends AbsRecorder {

    private AudioRecord mAudioRecord;

    @Override
    public void startRecording() {
        if (mAudioRecord == null) {
            mAudioRecord = new AudioRecord.Builder()
                    .build();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public int getMaxAmplitude() {
        return 0;
    }
}
