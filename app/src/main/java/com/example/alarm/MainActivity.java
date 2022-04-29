package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alarm.Alarm.AlarmFragment;
import com.example.alarm.Alarm.AlarmFragmentOfficial;
import com.example.alarm.Stopwatch.StopwatchFragment;
import com.example.alarm.Timer.TimerFragment;
import com.example.alarm.Worldclock.WorldclockFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AlarmFragmentOfficial alarmFragmentOfficial = new AlarmFragmentOfficial();
    AlarmFragment alarmFragment = new AlarmFragment();
    TimerFragment timerFragment = new TimerFragment();
    StopwatchFragment stopwatchFragment = new StopwatchFragment();
    WorldclockFragment worldclockFragment = new WorldclockFragment();
    //    View alarmFragment, timerFragment;
    private TimePicker timePicker;
    private Button btn_set, btn_stop;
    private TextView tv_alarm;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        timePicker = (TimePicker) findViewById(R.id.timePicker);
//        btn_set = (Button) findViewById(R.id.btn_setAlarm);
//        btn_stop = (Button) findViewById(R.id.btn_stopAlarm);
//        tv_alarm = (TextView) findViewById(R.id.tv_alarm);
//        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragmentOfficial).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_alarm:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, alarmFragmentOfficial).commit();
                        return true;
                    case R.id.navigation_stopwatch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stopwatchFragment).commit();
                        return true;

                    case R.id.navigation_timer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, timerFragment).commit();
                        return true;

                    case R.id.navigation_worldclock:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, worldclockFragment).commit();
                        return true;
                }

                return false;
            }
        });


//        final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//
//        btn_set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
//                calendar.set(Calendar.MINUTE, timePicker.getMinute());
//                calendar.set(Calendar.SECOND, 0);
//                calendar.set(Calendar.MILLISECOND, 0);
//
//                int hour = timePicker.getHour();
//                int minute = timePicker.getMinute();
//
//                String strHour = String.valueOf(hour);
//                String strMinute = String.valueOf(minute);
//
//                if (hour < 10) strHour = "0" + String.valueOf(hour);
//                if (minute < 10) strMinute = "0" + String.valueOf(minute);
//
//                intent.putExtra("extra", "on");
//
//                pendingIntent = PendingIntent.getBroadcast(
//                        MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
////                sendBroadcast(intent);
//                tv_alarm.setText("Your alarm was set " + strHour + ":" + strMinute);
//
//            }
//        });
//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv_alarm.setText("Your alarm was stop");
//                alarmManager.cancel(pendingIntent);
//                intent.putExtra("extra", "off");
//                sendBroadcast(intent);
//            }
//        });
    }

}
