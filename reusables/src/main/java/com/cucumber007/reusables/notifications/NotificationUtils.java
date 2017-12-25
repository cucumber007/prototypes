package com.cucumber007.reusables.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;


import com.cucumber007.reusables.R;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Calendar;


public class NotificationUtils {

    public static final String NOTIFICATION_CHANNEL_ID = "notification_channel";
    public static final String KEY_TEST_TIME = "test_time";

    //Times
    public static final long MINUTE = 60 * 1000;
    public static final long HOUR_MILLIS = 60 * 60 * 1000;
    public static final long FULL_DAY_MILLIS = 24 * 60 * 60 * 1000;

    //Config
    public static final long TIME_ALARM_INTERVAL = FULL_DAY_MILLIS;

    public static void sendNotification(Context context, int notificationId, String notificationTitle, String notificationText,
                                        @DrawableRes int notificationIcon, Class activityClass) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {

            Intent notificationIntent = new Intent(context, activityClass);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        NotificationUtils.NOTIFICATION_CHANNEL_ID,
                        "Channel", android.app.NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);

            }

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context, NotificationUtils.NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(notificationId, mNotifyBuilder.build());

        }
    }

    public static void setTimeRepeatingAlarm(Context context, PendingIntent pendingIntent, LocalTime localTime, long interval) {
        setTimeRepeatingAlarm(context, pendingIntent, localTime.getHour(), localTime.getMinute(), interval);
    }

    public static void setTimeAlarm(Context context, PendingIntent pendingIntent, LocalTime localTime) {
        setTimeAlarm(context, pendingIntent, localTime.getHour(), localTime.getMinute());
    }

    public static void setTimeAlarm(Context context, PendingIntent pendingIntent, int hours, int minutes) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            alarmMgr.cancel(pendingIntent);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public static void setTimeRepeatingAlarm(Context context, PendingIntent pendingIntent, int hours, int minutes, long interval) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            alarmMgr.cancel(pendingIntent);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            long timeToSet = calendar.getTimeInMillis();
            if (LocalDateTime.now().toLocalTime().isAfter(LocalTime.of(hours, minutes))) {
                timeToSet += interval;
            }
            //LogUtil.logDebug("****", LocalDateTime.ofEpochSecond(timeToSet/1000,0, OffsetDateTime.now().getOffset()));
            alarmMgr.setRepeating(AlarmManager.RTC, timeToSet, interval, pendingIntent);
        }
    }

    public static long getLocalDateTimeDelta(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return Math.abs(ChronoUnit.MILLIS.between(localDateTime1, localDateTime2));
    }

    public static long getLocalTimeDelta(LocalTime localTime1, LocalTime localTime2) {
        return Math.abs(ChronoUnit.MILLIS.between(localTime1, localTime2));
    }

}
