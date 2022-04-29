package com.example.alarm.util;

import android.os.Build;
import android.widget.TimePicker;

public final class TimePickerUtil {
    public static int getTimePickerHour(TimePicker timePicker){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            return timePicker.getHour();
        else
            return timePicker.getCurrentHour();
    }
    public static int getTimePickerMinute(TimePicker timePicker){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            return timePicker.getMinute();
        else
            return timePicker.getCurrentMinute();
    }
}
