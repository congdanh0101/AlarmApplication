package com.example.alarm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.alarm.R;
import com.example.alarm.RingActivity;
import com.example.alarm.model.Alarm;

public class AlarmService extends Service {

    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    Alarm alarm;
    Uri ringtone;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(this.getBaseContext(), RingtoneManager.TYPE_ALARM);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getBundleExtra(getString(R.string.arg_alarm_obj));
        if (bundle != null) {
            alarm = (Alarm) bundle.getSerializable(getString(R.string.arg_alarm_obj));

            Intent notificationIntent = new Intent(this, RingActivity.class);
            notificationIntent.putExtra(getString(R.string.bundle_alarm_obj), bundle);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            String alarmTitle = getString(R.string.alarm_title);

            if(alarm!=null){
                alarmTitle = alarm.getTitle();
                try{
                    mediaPlayer.setDataSource(this.getBaseContext(),Uri.parse(alarm.getTone()));
                    mediaPlayer.prepareAsync();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                try{
                    mediaPlayer.setDataSource(this.getBaseContext(),ringtone);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String channelName = "Alarm Background Service";
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = null;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = new NotificationChannel(CHANNEL_ID,channelName,
                        NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            Notification notification  = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle("Ring ring ... ring ring ...")
                    .setContentText(alarmTitle)
                    .setSmallIcon(R.drawable.ic_alarm_black)
                    .setSound(null)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setFullScreenIntent(pendingIntent,true)
                    .build();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

            if(alarm.isVibrate()){
                long [] patterns = {0,100,1000};
                vibrator.vibrate(patterns,0);
            }
            startForeground(1,notification);
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
