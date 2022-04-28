package com.example.alarm.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.alarm.model.Alarm;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Alarm> alarms);

    @Query("DELETE FROM Alarm WHERE id = :id")
    void deleteAlarm(int id);

    @Query("SELECT * FROM Alarm")
    LiveData<List<Alarm>> getAllAlarm();

    @Query("SELECT * FROM Alarm WHERE id = :id")
    LiveData<Alarm> getAlarm(int id);

}
