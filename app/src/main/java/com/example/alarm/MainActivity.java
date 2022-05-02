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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

}
