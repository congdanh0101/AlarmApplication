package com.example.alarm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo private int hour;
    @ColumnInfo private int minute;
    @ColumnInfo private boolean mon;
    @ColumnInfo private boolean tue;
    @ColumnInfo private boolean wed;
    @ColumnInfo private boolean thu;
    @ColumnInfo private boolean fri;
    @ColumnInfo private boolean sat;
    @ColumnInfo private boolean sun;
    @ColumnInfo private boolean start;

    public Alarm(){}
    public Alarm(int id,int hour,int minute,boolean mon, boolean tue,boolean wed, boolean thu, boolean fri, boolean sat, boolean sun, boolean start){
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.mon = mon;
        this.thu = thu;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.start = start;
    }

    public Alarm(int hour,int minute,boolean mon, boolean tue,boolean wed, boolean thu, boolean fri, boolean sat, boolean sun, boolean start){
        this.hour = hour;
        this.minute = minute;
        this.mon = mon;
        this.thu = thu;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
