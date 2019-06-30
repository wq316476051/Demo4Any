package com.wang.demo4any.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;

public class BroadcastFragment extends Fragment {

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            PendingResult pendingResult = goAsync();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock lock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tag:test");
            lock.acquire(TimeUnit.SECONDS.toMillis(20));
            CompletableFuture.runAsync(() -> {
                // TODO: 2019/6/21

                pendingResult.finish();
                lock.release();
            });
        }
    }
}
