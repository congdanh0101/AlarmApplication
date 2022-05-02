package com.example.alarm.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.alarm.DAO.AlarmDAO;
import com.example.alarm.model.Alarm;

import java.util.List;

public class AlarmRepository {
    private AlarmDAO alarmDAO;
    private LiveData<List<Alarm>> listLiveData;

    public AlarmRepository(Application application) {
        AlarmDB db = AlarmDB.getInstance(application);
        alarmDAO = db.alarmDAO();
        listLiveData = alarmDAO.getAllAlarms();
    }

    public void insertAlarm(Alarm alarm) {
        AlarmDB.dbWriteExecutors.execute(() -> {
            alarmDAO.insertAlarm(alarm);
        });
    }

    public void updateAlarm(Alarm alarm) {
        AlarmDB.dbWriteExecutors.execute(() -> {
            alarmDAO.updateAlarm(alarm);
        });
    }

    public void deleteAlarm(int id) {
        AlarmDB.dbWriteExecutors.execute(() -> {
            alarmDAO.deleteAlarm(id);
        });
    }

    public LiveData<List<Alarm>> getListLiveData() {
        return listLiveData;
    }
}
