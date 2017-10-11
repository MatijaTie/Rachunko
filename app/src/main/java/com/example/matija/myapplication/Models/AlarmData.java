package com.example.matija.myapplication.Models;

import android.net.Uri;

import com.example.matija.myapplication.Database.RacunkoDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by matija on 27.09.17..
 */
@Table(database = RacunkoDatabase.class)
public class AlarmData extends BaseModel implements Serializable{
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    private String calendarDate;

    @Column
    private String calendarTime;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private int img;

    public AlarmData(int img, String title, String text, String calendarDate, String calendarTime){
        this.img = img;
        this.title = title;
        this.text = text;
        this.calendarDate = calendarDate;
        this.calendarTime = calendarTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public void setCalendarTime(String calendarTime) {
        this.calendarTime = calendarTime;
    }

    public String getString(){
        SimpleDateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm", Locale.getDefault());
        String parseText = calendarDate+" "+calendarTime;
        return  parseText;
    }

    public long getTimeInMilis(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String parseText = calendarDate+" "+calendarTime;
        try {
            calendar.setTime(format.parse(parseText));
        }catch (ParseException e){
            e.printStackTrace();
        }
            return calendar.getTimeInMillis();
    }
    public AlarmData(){}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tile) {
        title = tile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public String getCalendarTime() {
        return calendarTime;
    }
}
