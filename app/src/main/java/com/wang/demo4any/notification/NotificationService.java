package com.wang.demo4any.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wang.demo4any.R;
import com.wang.demo4any.utils.AppUtils;

import androidx.annotation.Nullable;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";

    private static final String CHANNEL_ID = "notification:channel:id";

    private static final String CHANNEL_NAME = "notification:channel:name";

    private static final int NOTIFICATION_ID_FOREGROUND = 101;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID_FOREGROUND, createNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    private Notification createNotification() {
        Notification.Builder builder;
        if (AppUtils.isAtLeastO()) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Normal title")
                .setContentText("Normal message!!!");
        return null;
    }
}
