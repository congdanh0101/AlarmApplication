package com.example.alarm.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alarm.R;


public class TimerFragment extends Fragment {

//    private static final long START_TIME_IN_MILIS = 6000;
//    TextView tv_timer;
//    Button btn_timer_start, btn_timer_reset;
//    private CountDownTimer mCountDownTimer;
//    private boolean mTimerRunning;
//    private long mTimeLeftInMilis = START_TIME_IN_MILIS;

    private NumberPicker np_hour,np_minute,np_second;
    private Button btn_start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        np_hour = view.findViewById(R.id.np_hour);
        np_minute = view.findViewById(R.id.np_minute);
        np_second = view.findViewById(R.id.np_second);
        btn_start = view.findViewById(R.id.btn_start_timer);
        setNumberPickerTimer();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, minute,second;
                long timeMillisSeconds = 0;

                hour = np_hour.getValue();
                minute = np_minute.getValue();
                second  =np_second.getValue();

                timeMillisSeconds = (hour*60*60) + (minute*60) + second;
                timeMillisSeconds = timeMillisSeconds*1000;

                if (timeMillisSeconds!=0){
                    Intent intent = new Intent(getActivity(),CountdownTimerAcitivity.class);
                    intent.putExtra(getContext().getString(R.string.time_millis_second),timeMillisSeconds);
                    startActivity(intent);
                }else
                    Toast.makeText(getActivity(),"Please pick a number",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNumberPickerTimer() {
        String[] hours = getResources().getStringArray(R.array.spinner_hour);
        String[] minutes = getResources().getStringArray(R.array.spinner_minutes_seconds);
        String[] seconds = getResources().getStringArray(R.array.spinner_minutes_seconds);

        np_hour.setMinValue(0);
        np_minute.setMinValue(0);
        np_second.setMinValue(0);

        np_hour.setMaxValue(hours.length-1);
        np_minute.setMaxValue(minutes.length-1);
        np_second.setMaxValue(seconds.length-1);

        np_hour.setDisplayedValues(hours);
        np_minute.setDisplayedValues(minutes);
        np_second.setDisplayedValues(seconds);
    }
}