package com.wang.audio.player.players.audio;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;

import com.wang.audio.player.players.AbsPlayer;

public class AudioTrackWrapper extends AbsPlayer {

    public AudioTrackWrapper(Context context) {
        super(context);
    }

    @Override
    public void playFromMedia(MediaMetadataCompat metadata) {

    }

    @Override
    public MediaMetadataCompat getCurrentMedia() {
        return null;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    protected void onPlay() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public void seekTo(long position) {

    }

    @Override
    public void setVolume(float volume) {

    }
}
