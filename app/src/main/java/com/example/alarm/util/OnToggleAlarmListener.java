package com.example.alarm.util;

import android.view.View;

import com.example.alarm.model.Alarm;

public interface OnToggleAlarmListener {
    void onToggle(Alarm alarm);
    void onDelete(Alarm alarm);
    void onItemClick(Alarm alarm, View view);
}
