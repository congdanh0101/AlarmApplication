package com.example.alarm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alarm.DB.AlarmRepository;
import com.example.alarm.model.Alarm;

import java.util.List;

public class CreateAlarmVM extends AndroidViewModel {

    private AlarmRepository alarmRepository;

    public CreateAlarmVM(@NonNull Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
    }
    public void updateAlarm(Alarm alarm){
        alarmRepository.updateAlarm(alarm);
    }
    public void insertAlarm(Alarm alarm){
        alarmRepository.insertAlarm(alarm);
    }

}
