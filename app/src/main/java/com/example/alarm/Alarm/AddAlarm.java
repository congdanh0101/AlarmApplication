package com.example.alarm.Alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.alarm.R;

import java.util.Calendar;

import butterknife.BindView;

public class AddAlarm extends AppCompatActivity {
    @BindView(R.id.timePicker) TimePicker timePicker;
    

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public static final String LOG_TAG = AddAlarm.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
    }

    private void initUi(){
    }
}