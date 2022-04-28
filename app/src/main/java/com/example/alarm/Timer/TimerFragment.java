package com.example.alarm.Timer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarm.R;


public class TimerFragment extends Fragment {

    TextView tv_timer;
    Button btn_timer_start, btn_timer_reset;
    private static final long START_TIME_IN_MILIS=6000;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMilis = START_TIME_IN_MILIS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv_timer = view.findViewById(R.id.timer);
        btn_timer_reset = view.findViewById(R.id.btn_timer_reset);
        btn_timer_start = view.findViewById(R.id.btn_timer_start);

        btn_timer_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning) pauseTimer();
                else startTimer();
            }
        });

        btn_timer_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
    }

    public void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning =false;
        btn_timer_start.setText("start");
        btn_timer_reset.setVisibility(View.VISIBLE);
    }
    public void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMilis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMilis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
//                Intent intent = new Intent(getContext(),TimerReceiver.class);
                MediaPlayer mp = new MediaPlayer();
                mp = MediaPlayer.create(getContext(),R.raw.ip6);
                mp.start();
                Log.e("Timer","Finished");
                mTimerRunning = false;
                btn_timer_start.setText("Start");
                btn_timer_start.setVisibility(View.INVISIBLE);
                btn_timer_reset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning=true;
        btn_timer_start.setText("Pause");
        btn_timer_reset.setVisibility(View.VISIBLE);
    }
    public void resetTimer(){
        mTimeLeftInMilis = START_TIME_IN_MILIS;
        pauseTimer();
        updateCountDownText();
        btn_timer_reset.setVisibility(View.INVISIBLE);
        btn_timer_start.setVisibility(View.VISIBLE);
    }
    public void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMilis/1000) /60;
        int seconds = (int) (mTimeLeftInMilis/1000)%60;
        String sTimeLeftFormatted = String.format("%02d:%02d",minutes,seconds);
        tv_timer.setText(sTimeLeftFormatted);
    }
}