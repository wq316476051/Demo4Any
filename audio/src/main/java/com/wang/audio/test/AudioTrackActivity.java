package com.wang.audio.test;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;

public class AudioTrackActivity extends Activity {

    private static final String TAG = "AudioTrackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAudioTrack();
    }

    private void initAudioTrack() {
        int sampleRateInHz = 44_100;
        int channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        int minBufferSize = AudioTrack.getMinBufferSize(AudioParams.SAMPLE_RATE, AudioParams.CHANNEL_CONFIG, AudioParams.AUDIO_FORMAT);
        Log.d(TAG, "initAudioTrack: minBufferSize = " + minBufferSize);


        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz, channelConfig, audioFormat, minBufferSize, AudioTrack.MODE_STREAM);
//        audioTrack.play();
//        audioTrack.write();
//        audioTrack.stop();
//        audioTrack.release();

        audioTrack = new AudioTrack(audioTrack.getStreamType(), audioTrack.getSampleRate(), audioTrack.getChannelConfiguration(), audioTrack.getAudioFormat(),
                audioTrack.getBufferSizeInFrames(), AudioTrack.MODE_STREAM);
    }


}
