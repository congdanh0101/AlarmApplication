package com.example.alarm.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alarm.R;

import java.util.Calendar;

public class AlarmFragment extends Fragment {


    private TimePicker timePicker;
    private Button btn_set, btn_stop;
    private TextView tv_alarm;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public static final String LOG_TAG = AlarmFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
//        btn_set = (Button) view.findViewById(R.id.btn_setAlarm);
//        btn_stop = (Button) view.findViewById(R.id.btn_stopAlarm);
//        tv_alarm = (TextView) view.findViewById(R.id.tv_alarm);
//        calendar = Calendar.getInstance();
//        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//        final Intent intent = new Intent(getContext(), AlarmReceiver.class);
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
//                        getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
////                sendBroadcast(intent);
//                tv_alarm.setText("Your alarm was set " + strHour + ":" + strMinute);
//            }
//        });
//
//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv_alarm.setText("Your alarm was stop");
//                alarmManager.cancel(pendingIntent);
//                intent.putExtra("extra", "off");
//                getActivity().sendBroadcast(intent);
//            }
//        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG,"Fragment destroy");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(LOG_TAG,"onCreate");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(LOG_TAG,"onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(LOG_TAG,"onStart" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(LOG_TAG,"onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(LOG_TAG,"onPause");
    }

}