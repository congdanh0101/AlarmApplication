package com.example.alarm.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.alarm.DAO.AlarmDAO;
import com.example.alarm.model.Alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class},version = 1,exportSchema = false)
public abstract class AlarmDB extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbWriteExecutors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AlarmDB INSTANCE;

    static AlarmDB getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (AlarmDB.class){
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AlarmDB.class,"Alarm").build();
            }
        }
        return INSTANCE;
    }
    public abstract AlarmDAO alarmDAO();
}
