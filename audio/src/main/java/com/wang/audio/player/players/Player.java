package com.wang.audio.player.players;

import android.support.v4.media.MediaMetadataCompat;

public interface Player {

    void playFromMedia(MediaMetadataCompat metadata);
    void play();
    void pause();
    void stop();
    void seekTo(long position);

    MediaMetadataCompat getCurrentMedia();
}
