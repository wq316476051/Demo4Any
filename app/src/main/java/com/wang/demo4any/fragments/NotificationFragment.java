package com.wang.demo4any.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.demo4any.R;
import com.wang.demo4any.notification.NotificationService;
import com.wang.demo4any.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class NotificationFragment extends Fragment {

    private static final String TAG = "NotificationFragment";

    public static final String TITLE = "Notification";

    private static final String CHANNEL_ID = "notification:channel:id";

    private static final String CHANNEL_NAME = "notification:channel:name";

    private static final int NOTIFICATION_ID_NORMAL = 100;

    private static final int NOTIFICATION_ID_FOREGROUND = 101;

    private Handler mHandler = new Handler();

    public static Fragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppUtils.isAtLeastO()) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvSeconds = view.findViewById(R.id.tv_second);
        Button btnDecrement = view.findViewById(R.id.btn_decrement);
        Button btnIncrement = view.findViewById(R.id.btn_increment);
        Button btnNormal = view.findViewById(R.id.btn_normal);
        Button btnForeground = view.findViewById(R.id.btn_foreground);

        tvSeconds.setText(String.valueOf(getDelayInSeconds()));

        btnDecrement.setOnClickListener(button -> {
            try {
                Long delayInSeconds = Long.valueOf(tvSeconds.getText().toString());
                if (delayInSeconds <= 0) {
                    return;
                }
                --delayInSeconds;
                tvSeconds.setText(String.valueOf(delayInSeconds));
                setDelayInSeconds(delayInSeconds);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        btnIncrement.setOnClickListener(button -> {
            try {
                Long delayInSeconds = Long.valueOf(tvSeconds.getText().toString());
                ++delayInSeconds;
                tvSeconds.setText(String.valueOf(delayInSeconds));
                setDelayInSeconds(delayInSeconds);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        btnNormal.setOnClickListener(button -> {
            try {
                mHandler.postDelayed(() -> {
                    Notification.Builder builder;
                    if (AppUtils.isAtLeastO()) {
                        builder = new Notification.Builder(getActivity(), CHANNEL_ID);
                    } else {
                        builder = new Notification.Builder(getActivity());
                    }
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Normal title")
                            .setContentText("Normal message!!!");
                    getNotificationManager().notify(NOTIFICATION_ID_NORMAL, builder.build());
                }, TimeUnit.SECONDS.toMillis(Long.valueOf(tvSeconds.getText().toString())));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        btnForeground.setOnClickListener(button -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.startService(new Intent(activity, NotificationService.class));
            }
        });
    }

    private long getDelayInSeconds() {
        return getSharedPreferences().getLong("delay", 0);
    }

    private void setDelayInSeconds(long delayInSeconds) {
        getSharedPreferences().edit().putLong("delay", delayInSeconds).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return AppUtils.getApp().getSharedPreferences("notification", Context.MODE_PRIVATE);
    }

    private NotificationManager getNotificationManager() {
        return AppUtils.getApp().getSystemService(NotificationManager.class);
    }
}
