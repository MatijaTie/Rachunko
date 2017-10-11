package com.example.matija.myapplication.Events;

/**
 * Created by matija on 05.10.17..
 */

public class ChooseImageEvent {
    private int img;

    public ChooseImageEvent(int img){
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
