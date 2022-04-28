package com.example.alarm.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityHelper extends SQLiteOpenHelper {

    public static final String tableName = "Cities.db";
    public static final int version = 1;

    public CityHelper(Context context){
        super(context,tableName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String command = "CREATE TABLE Cities (Name TEXT PRIMARY KEY," + "TimeZone TEXT," + "Subscribed INTEGER)";
        sqLiteDatabase.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cities");
        onCreate(sqLiteDatabase);
    }
}
