package com.deguffroy.adrien.mynews.Utils.Notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Adrien Deguffroy on 08/06/2018.
 */

public class NotificationHelper {

    public static int ALARM_TYPE_RTC = 100;
    private static AlarmManager alarmManagerRTC;
    private static PendingIntent alarmIntentRTC;

    public static void scheduleRepeatingRTCNotification(Context context, String hour, String min) {
        //Setting intent to class where Alarm broadcast message will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);
        //Setting alarm pending intent
        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        alarmManagerRTC = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Setting alarm to wake up device every day for clock time.
        //AlarmManager.RTC_WAKEUP is responsible to wake up device for sure, which may not be good practice all the time.
        // Use this when you know what you're doing.
        //Use RTC when you don't need to wake up device, but want to deliver the notification whenever device is woke-up
        //We'll be using RTC.WAKEUP for demo purpose only

        // With setInexactRepeating(), you have to use one of the AlarmManager interval //
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManagerRTC.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 5, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntentRTC);
    }

    public static void cancelAlarmRTC() {
        if (alarmManagerRTC!= null) {
            alarmManagerRTC.cancel(alarmIntentRTC);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Enable boot receiver to persist alarms set for notifications across device reboots
     */
    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * Disable boot receiver when user cancels/opt-out from notifications
     */
    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
