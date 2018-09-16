package com.wang.audio2.service.players;

import android.support.v4.media.session.PlaybackStateCompat;

public abstract class PlaybackInfoListener {
    public abstract void onPlaybackStateChange(PlaybackStateCompat state);
    public void onPlaybackCompleted() {

    }
}
