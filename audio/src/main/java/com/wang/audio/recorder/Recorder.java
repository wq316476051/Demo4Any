package com.wang.audio.recorder;

public interface Recorder {

    void startRecording();
    void pause();
    void resume();
    void stop();
    int getMaxAmplitude();

    boolean isInitialized();
    boolean isRecording();
    boolean isPaused();
    boolean isStopped();
}
