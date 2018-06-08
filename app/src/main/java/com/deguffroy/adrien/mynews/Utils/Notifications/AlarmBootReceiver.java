package com.deguffroy.adrien.mynews.Utils.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.deguffroy.adrien.mynews.Controllers.NotificationsActivity;

/**
 * Created by Adrien Deguffroy on 08/06/2018.
 */

public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduleRepeatingRTCNotification(context, NotificationsActivity.NOTIFICATIONS_HOUR,NotificationsActivity.NOTIFICATIONS_MIN);
        }
    }
}