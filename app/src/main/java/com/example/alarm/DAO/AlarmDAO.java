package com.example.alarm.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alarm.model.Alarm;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Alarm> alarms);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(Alarm alarm);

    @Query("DELETE FROM Alarm WHERE Id = :id")
    void deleteAlarm(int id);

    @Query("SELECT * FROM Alarm")
    Alarm getAllAlarm();

    @Query("DELETE FROM Alarm")
    void deleteAll();

    @Query("SELECT * FROM Alarm WHERE Id = :id")
    LiveData<Alarm> getAlarm(int id);

    @Query("SELECT * FROM Alarm ORDER BY Id ASC")
    LiveData<List<Alarm>> getAllAlarms();

    @Update
    void updateAlarm(Alarm alarm);

}
