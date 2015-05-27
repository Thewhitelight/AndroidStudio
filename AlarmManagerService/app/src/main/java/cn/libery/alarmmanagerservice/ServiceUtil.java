package cn.libery.alarmmanagerservice;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by SZQ on 2015/5/27.
 */
public class ServiceUtil {


    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager
                .getRunningServices(Constants.RECEIVER_SERVICE_COUNT);

        if (null == serviceInfos || serviceInfos.size() < 1) {
            return false;
        }

        for (int i = 0; i < serviceInfos.size(); i++) {
            if (serviceInfos.get(i).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            }
        }
        Log.i("ServiceUtil", className + " isRunning =  " + isRunning);
        return isRunning;
    }

    public static void invokeAlarmService(Context context) {
        Log.i("ServiceUtil", "invokeTimerPOIService wac called..");
        PendingIntent alarmSender = null;
        Intent startIntent = new Intent(context, AlarmService.class);
        startIntent.setAction(Constants.RECEIVER_ACTION);
        try {
            alarmSender = PendingIntent.getService(context, 0, startIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (Exception e) {
            Log.i("ServiceUtil", "failed to start " + e.toString());
        }
        AlarmManager am = (AlarmManager) context
                .getSystemService(Activity.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                Constants.TIME, alarmSender);
    }

    public static void cancleAlarmManager(Context context) {
        Log.i("ServiceUtil", "cancleAlarmManager to start ");
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(Constants.RECEIVER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context
                .getSystemService(Activity.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }

}
