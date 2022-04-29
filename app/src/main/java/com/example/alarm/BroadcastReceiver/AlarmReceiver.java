package com.example.alarm.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.alarm.Music;
import com.example.alarm.R;
import com.example.alarm.Service.AlarmService;
import com.example.alarm.Service.reScheduledAlarmService;
import com.example.alarm.model.Alarm;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    Alarm alarm;
    public static final String LOG_TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            String toastText = "Alarm Reboot";
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startRescheduleAlarmsService(context);
        }else{
            Bundle bundle = intent.getBundleExtra(context.getString(R.string.arg_alarm_obj));
            if(bundle!=null){
                alarm = (Alarm) bundle.getSerializable(context.getString(R.string.arg_alarm_obj));
                String toastText = "Alarm Received";
                Toast.makeText(context,toastText,Toast.LENGTH_SHORT).show();

                if(alarm!=null){
                    if(!alarm.isRecurring()){
                        startAlarmService(context,alarm);
                    }else{
                        if(isAlarmToday(alarm))startAlarmService(context,alarm);
                    }
                }
            }
        }

    }

    private boolean isAlarmToday(Alarm a) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        switch (today){
            case Calendar.MONDAY:
                return a.isMonday();
            case Calendar.TUESDAY:
                return a.isTuesday();
            case Calendar.WEDNESDAY:
                return a.isWednesday();
            case Calendar.THURSDAY:
                return a.isThursday();
            case Calendar.FRIDAY:
                return a.isFriday();
            case Calendar.SATURDAY:
                return a.isSaturday();
            case Calendar.SUNDAY:
                return a.isSunday();
        }
        return false;
    }

    private void startAlarmService(Context context, Alarm a) {
        Intent intentService=  new Intent(context, AlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.arg_alarm_obj),a);
        intentService.putExtra(context.getString(R.string.arg_alarm_obj),bundle);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intentService);
        else
            context.startService(intentService);
    }

    private void startRescheduleAlarmsService(Context context) {
        Intent intentService = new Intent(context, reScheduledAlarmService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intentService);
        else
            context.startService(intentService);
    }
}
