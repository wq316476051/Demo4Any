// IRecordService.aidl
package com.wang.demo4any.record;

import com.wang.demo4any.record.IRecordCallback;

// Declare any non-default types here with import statements

interface IRecordService {
    void registerCallback(IRecordCallback callback);
    void unregisterCallback();
    void startRecording();
    void pause();
    void resume();
    void stopRecording();
    int getAmplitude();
    int getState();
}
