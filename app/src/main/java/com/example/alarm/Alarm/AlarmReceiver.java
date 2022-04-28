package com.example.alarm.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.alarm.Music;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    public static final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(LOG_TAG,"In receiver");
        String str_value = intent.getExtras().getString("extra");
        Log.e(LOG_TAG,str_value);
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("extra",str_value);
        context.startService(myIntent);

    }
}
