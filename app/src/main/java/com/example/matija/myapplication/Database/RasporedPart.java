package com.example.matija.myapplication.Database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by matija on 22.08.17..
 */

@Table(database = RacunkoDatabase.class)
public class RasporedPart extends BaseModel implements Serializable {
    @Column
    int fontColor;
    @Column
    int backroundColor;
    @Column
    String title;
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    public RasporedPart(int fontColor, int backroundColor, String title){
        this.fontColor = fontColor;
        this.backroundColor = backroundColor;
        this.title = title;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getBackroundColor() {
        return backroundColor;
    }

    public void setBackroundColor(int backroundColor) {
        this.backroundColor = backroundColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RasporedPart(){}
}
