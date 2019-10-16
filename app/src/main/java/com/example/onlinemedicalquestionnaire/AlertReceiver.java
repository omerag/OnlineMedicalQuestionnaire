package com.example.onlinemedicalquestionnaire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification();
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationHelper.getManager().notify(1, notificationBuilder.build());
    }
}
