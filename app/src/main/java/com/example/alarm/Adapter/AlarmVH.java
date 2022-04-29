package com.example.alarm.Adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.R;
import com.example.alarm.databinding.ItemAlarmBinding;
import com.example.alarm.model.Alarm;
import com.example.alarm.util.DayUtil;
import com.example.alarm.util.OnToggleAlarmListener;

public class AlarmVH extends RecyclerView.ViewHolder {

    private TextView tv_alarmTime, tv_alarmRecurringDays, tv_alarmTitle, tv_alarmDay;
    private ImageView iv_alarmRecurring;
    private Switch sw_isAlarmStarted;
    private ImageButton ib_deleteAlarm;
    private View itemView;

    public AlarmVH(@NonNull ItemAlarmBinding binding) {
        super(binding.getRoot());
        tv_alarmTime = binding.itemAlarmTime;
        tv_alarmDay = binding.itemAlarmDay;
        tv_alarmRecurringDays = binding.itemAlarmRecurringDays;
        tv_alarmTitle = binding.itemAlarmTitle;
        iv_alarmRecurring = binding.itemAlarmRecurring;
        sw_isAlarmStarted = binding.itemAlarmStarted;
        ib_deleteAlarm = binding.itemAlarmRecurringDelete;
        this.itemView = binding.getRoot();
    }

    public void bind(Alarm alarm, OnToggleAlarmListener listener) {
        String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());
        tv_alarmTime.setText(alarmText);
        sw_isAlarmStarted.setChecked(alarm.isStarted());

        if (alarm.isRecurring()) {
            iv_alarmRecurring.setImageResource(R.drawable.ic_alarm_repeat);
            tv_alarmRecurringDays.setText(alarm.getRecurringDaysText());
        } else {
            iv_alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            tv_alarmRecurringDays.setText("Once off");
        }

        if (!alarm.getTitle().isEmpty()) tv_alarmTitle.setText(alarm.getTitle());
        else tv_alarmTitle.setText("My Alarm");

        if(alarm.isRecurring())tv_alarmDay.setText(alarm.getRecurringDaysText());
        else tv_alarmDay.setText(DayUtil.getDay(alarm.getHour(),alarm.getMinute()));

        sw_isAlarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isShown() || compoundButton.isPressed())
                    listener.onToggle(alarm);
            }
        });

        ib_deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(alarm);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(alarm,view);
            }
        });
    }

}
