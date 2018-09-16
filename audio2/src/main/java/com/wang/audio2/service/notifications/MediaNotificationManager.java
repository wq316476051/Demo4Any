package com.wang.audio2.service.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.wang.audio2.R;
import com.wang.audio2.service.PlayerService;
import com.wang.audio2.service.content.MusicLibrary;
import com.wang.audio2.ui.MainActivity;

public class MediaNotificationManager {

    private static final String TAG = MediaNotificationManager.class.getSimpleName();

    public static final int NOTIFICATION_ID = 412;

    private static final String CHANNEL_ID = "com.example.android.musicplayer.channel";
    private static final int REQUEST_CODE = 501;

    private final PlayerService mPlayerService;
    private final NotificationCompat.Action mPlayAction;
    private final NotificationCompat.Action mPauseAction;
    private final NotificationCompat.Action mNextAction;
    private final NotificationCompat.Action mPrevAction;
    private final NotificationManager mNotificationManager;

    public MediaNotificationManager(PlayerService service) {
        mPlayerService = service;
        mNotificationManager = (NotificationManager) mPlayerService.getSystemService(Context.NOTIFICATION_SERVICE);
        mPlayAction = new NotificationCompat.Action(
                R.drawable.ic_play_arrow_white_24dp,
                mPlayerService.getString(R.string.label_play),
                MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_PLAY)
        );
        mPauseAction = new NotificationCompat.Action(
                R.drawable.ic_pause_white_24dp,
                mPlayerService.getString(R.string.label_pause),
                MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_PAUSE)
        );
        mNextAction = new NotificationCompat.Action(
                R.drawable.ic_skip_next_white_24dp,
                mPlayerService.getString(R.string.label_next),
                MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)
        );
        mPrevAction = new NotificationCompat.Action(
                R.drawable.ic_skip_previous_white_24dp,
                mPlayerService.getString(R.string.label_previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
        );

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        mNotificationManager.cancelAll();
    }
    
    public void onDestory() {
        Log.e(TAG, "onDestory: ");
    }

    public NotificationManager getNotificationManager() {
        return mNotificationManager;
    }
    
    public Notification getNotification(MediaMetadataCompat metadata, PlaybackStateCompat state, MediaSessionCompat.Token token) {
        boolean isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;
        MediaDescriptionCompat description = metadata.getDescription();
        NotificationCompat.Builder builder = buildNotification(state, token, isPlaying, description);
        return builder.build();
    }

    private NotificationCompat.Builder buildNotification(PlaybackStateCompat state, MediaSessionCompat.Token token, boolean isPlaying,
                                                         MediaDescriptionCompat description) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "MediaSession", NotificationManager.IMPORTANCE_LOW);
                channel.setDescription("MediaSession and MediaPlayer");
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(mPlayerService, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(mPlayerService);
        }
        builder.setStyle(
                new MediaStyle().setMediaSession(token)
                        .setShowActionsInCompactView(0, 1, 2)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_STOP)));
        builder.setColor(ContextCompat.getColor(mPlayerService, R.color.notification_bg))
                .setSmallIcon(R.drawable.ic_stat_image_audiotrack)
                .setContentIntent(createContentIntent())
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setLargeIcon(MusicLibrary.getAlbumBitmap(mPlayerService, description.getMediaId()))
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mPlayerService, PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0) {
            builder.addAction(mPrevAction);
        }
        builder.addAction(isPlaying ? mPauseAction : mPlayAction);
        if ((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0) {
            builder.addAction(mNextAction);
        }
        return builder;
    }

    private PendingIntent createContentIntent() {
        Intent openUI = new Intent(mPlayerService, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(
                mPlayerService, REQUEST_CODE, openUI, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
