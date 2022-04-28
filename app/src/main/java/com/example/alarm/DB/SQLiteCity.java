package com.example.alarm.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alarm.DAO.ICityDAO;
import com.example.alarm.model.City;

import java.util.ArrayList;

public class SQLiteCity implements ICityDAO {
    public static final String LOG_TAG = SQLiteCity.class.getSimpleName();

    private Context context;

    public SQLiteCity(Context context) {
        this.context = context;
    }

    @Override
    public void saveCity(String name) {
        CityHelper helper = new CityHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "UPDATE Cities SET Subscribed = 1 WHERE Name = '" + name + "'";
        db.execSQL(query);
        Log.d(LOG_TAG, name + " SAVED");
    }

    @Override
    public void deleteCity(String name) {
        CityHelper helper = new CityHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "UPDATE Cities SET Subscribed = 0 WHERE Name = '" + name + "'";
        db.execSQL(query);
        Log.d(LOG_TAG, name + " DELETED");
    }

    @Override
    public ArrayList<City> load(boolean selectedOnly) {
        CityHelper helper = new CityHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c;
        if (selectedOnly)
            c = db.rawQuery("SELECT * FROM Cities WHERE Subscribed = 1", null);
        else
            c = db.rawQuery("SELECT * FROM Cities", null);

        ArrayList<City> temp = new ArrayList<>();
        while (c.moveToNext()) {
            City read = new City(c.getString(0), c.getString(1));
            int subscribed = c.getInt(2);
            if (subscribed == 1)
                read.setSubscribed(true);
            else
                read.setSubscribed(false);
            temp.add(read);
        }
        return temp;
    }

    @Override
    public boolean isDbEmpty() {
        CityHelper helper = new CityHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cities", null);
        if (c.getCount() == 0) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    @Override
    public void fillDb(ArrayList<City> cities) {
        CityHelper helper = new CityHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 0; i < cities.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("Name", cities.get(i).name);
            values.put("TimeZone", cities.get(i).timezone);
            values.put("Subscribed", cities.get(i).returnSubscribed());
            db.insertWithOnConflict("Cities",null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
}
