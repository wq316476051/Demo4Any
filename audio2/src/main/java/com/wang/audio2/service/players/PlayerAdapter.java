package com.wang.audio2.service.players;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v4.media.MediaMetadataCompat;

public abstract class PlayerAdapter {

    private static final float MEDIA_VOLUME_DEFAULT = 1.0f;
    private static final float MEDIA_VOLUME_DUCK = 0.2f;

    private static final IntentFilter AUDIO_NOISY_INTENT_FILTER =
            new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private boolean mAudioNoisyReceiverRegistered = false;
    private final BroadcastReceiver mAudioNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                if (isPlaying()) {
                    pause();
                }
            }
        }
    };

    private final Context mApplicationContext;
    private final AudioManager mAudioManager;
    private final AudioFocusHelper mAudioFocusHelper;

    private boolean mPlayOnAudioFocus = false;

    public PlayerAdapter(Context context) {
        mApplicationContext = context.getApplicationContext();
        mAudioManager = (AudioManager) mApplicationContext.getSystemService(Context.AUDIO_SERVICE);
        mAudioFocusHelper = new AudioFocusHelper();
    }

    public abstract void playFromMedia(MediaMetadataCompat metadata);
    public abstract MediaMetadataCompat getCurrentMedia();
    public abstract boolean isPlaying();
    public abstract void setVolume(float mediaVolumeDefault);
    public abstract void seekTo(long position);

    public final void play() {
        if (mAudioFocusHelper.requestAudioFocus()) {
            registerAudioNoisyReceiver();
            onPlay();
        }
    }

    protected abstract void onPlay();

    public final void pause() {
        if (!mPlayOnAudioFocus) {
            mAudioFocusHelper.abandonAudioFocus();
        }

        unregisterAudioNoisyReceiver();
        onPause();
    }

    protected abstract void onPause();

    public final void stop() {
        mAudioFocusHelper.abandonAudioFocus();
        unregisterAudioNoisyReceiver();
        onStop();
    }

    protected abstract void onStop();

    private void registerAudioNoisyReceiver() {
        if (!mAudioNoisyReceiverRegistered) {
            mApplicationContext.registerReceiver(mAudioNoisyReceiver, AUDIO_NOISY_INTENT_FILTER);
            mAudioNoisyReceiverRegistered = true;
        }
    }

    private void unregisterAudioNoisyReceiver() {
        if (mAudioNoisyReceiverRegistered) {
            mApplicationContext.unregisterReceiver(mAudioNoisyReceiver);
            mAudioNoisyReceiverRegistered = false;
        }
    }

    private class AudioFocusHelper {

        private boolean requestAudioFocus() {
            int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            return result == AudioManager.AUDIOFOCUS_GAIN;
        }

        private void abandonAudioFocus() {
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }

        private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener
                = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (mPlayOnAudioFocus && !isPlaying()) {
                            play();
                        } else if (isPlaying()) {
                            setVolume(MEDIA_VOLUME_DEFAULT);
                        }
                        mPlayOnAudioFocus = false;
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        setVolume(MEDIA_VOLUME_DUCK);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (isPlaying()) {
                            mPlayOnAudioFocus = true;
                            pause();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        mAudioManager.abandonAudioFocus(this);
                        mPlayOnAudioFocus = false;
                        stop();
                        break;
                }
            }
        };
    }
}