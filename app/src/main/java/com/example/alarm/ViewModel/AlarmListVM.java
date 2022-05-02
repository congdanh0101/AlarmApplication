package com.example.alarm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.alarm.DB.AlarmDB;
import com.example.alarm.DB.AlarmRepository;
import com.example.alarm.model.Alarm;

import java.util.List;

public class AlarmListVM extends AndroidViewModel {

    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> listLiveData;

    public AlarmListVM(@NonNull Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
        listLiveData = alarmRepository.getListLiveData();
    }

    public void updateAlarm(Alarm alarm){
        alarmRepository.updateAlarm(alarm);
    }
    public void deleteAlarm(int id){
        alarmRepository.deleteAlarm(id);
    }
    public LiveData<List<Alarm>> getListLiveData(){
        return alarmRepository.getListLiveData();
    }


}
