package com.example.alarm;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarm.Service.AlarmService;
import com.example.alarm.ViewModel.AlarmListVM;
import com.example.alarm.databinding.ActivityRingBinding;
import com.example.alarm.model.Alarm;

import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {

    private AlarmListVM listVM;
    private ActivityRingBinding binding;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            );
        }

        listVM = new ViewModelProvider(this).get(AlarmListVM.class);
        Bundle bundle = getIntent().getBundleExtra(getString(R.string.bundle_alarm_obj));
        if (bundle != null)
            alarm = (Alarm) bundle.getSerializable(getString(R.string.arg_alarm_obj));
        binding.activityRingSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoozeAlarm();
            }
        });
        binding.activityRingDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAlarm();
            }
        });
        animateClock();


    }

    private void dismissAlarm() {
        if (alarm != null) {
            System.out.println(alarm.getHour()+":"+alarm.getMinute());
            alarm.setStarted(false);
            alarm.cancelAlarm(getBaseContext());
            listVM.updateAlarm(alarm);
        }
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        finish();
    }

    private void animateClock() {
        ObjectAnimator rotateAnimation =
                ObjectAnimator.ofFloat(
                        binding.activityRingClock,
                        "rotation",
                        0f, 30f, 0f, -30f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }

    private void snoozeAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 5);

        if (alarm != null) {
            alarm.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            alarm.setMinute(calendar.get(Calendar.MINUTE));
            alarm.setTitle("Snooze " + alarm.getTitle());
        } else {
            alarm = new Alarm(
                    new Random().nextInt(Integer.MAX_VALUE),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    "Snooze",
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    RingtoneManager.getActualDefaultRingtoneUri(getBaseContext(),
                            RingtoneManager.TYPE_ALARM).toString(),
                    false
            );
        }
        alarm.schedule(getApplicationContext());

        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            setShowWhenLocked(false);
            setTurnScreenOn(false);
        }else{
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            );
        }
    }
}