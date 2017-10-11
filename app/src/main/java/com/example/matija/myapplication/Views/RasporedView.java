package com.example.matija.myapplication.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.matija.myapplication.Database.RasporedPart;
import com.example.matija.myapplication.R;

import java.io.Serializable;

/**
 * Created by matija on 22.08.17..
 */

public class RasporedView extends TextView implements Serializable{
    RasporedPart rasporedPart;
    int day;
    int size;
    int position;
    int rasporedPartId;

    public int getRasporedPartId() {
        return rasporedPartId;
    }

    public void setRasporedPartId(int rasporedPartId) {
        this.rasporedPartId = rasporedPartId;
    }

    public int getPosition() {
        return position;
    }

    public RasporedView(Context context, int day, int size) {
        super(context);
        rasporedPart = null;
        this.day = day;
        this.size = size;
        setGravity(Gravity.CENTER);
        //this.position = position;
    }

    public void setRasporedPart(RasporedPart part){
        this.rasporedPart = part;
        this.rasporedPartId = part.getId();
    }

    public int getDay() {
        return day;
    }

    public int getSize() {
        return size;
    }

    public void setView(){
        if(rasporedPart.getBackroundColor() == Color.WHITE){
            setBackgroundResource(R.drawable.frame_up_left);
        }else{
            setBackgroundColor(rasporedPart.getBackroundColor());
        }

        setTextColor(rasporedPart.getFontColor());
        setText(rasporedPart.getTitle());
    }
    public RasporedPart getRasporedPart(){
        return  rasporedPart;
    }




}
