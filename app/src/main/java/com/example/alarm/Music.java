package com.example.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Music extends Service {

    public static final String LOG_TAG = Music.class.getSimpleName();
    MediaPlayer mediaPlayer;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG,"IN music");
        String str_value = intent.getExtras().getString("extra");
        Log.e(LOG_TAG,str_value);

        if(str_value.equals("on"))id = 1;
        else if(str_value.equals("off"))id =0;

        System.out.println(id);


        if(id ==1){
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            id = 0;
        }else if(id ==0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        return START_NOT_STICKY;
    }
}
