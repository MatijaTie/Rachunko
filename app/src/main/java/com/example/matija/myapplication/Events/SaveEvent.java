package com.example.matija.myapplication.Events;

/**
 * Created by matija on 26.09.17..
 */

public class SaveEvent {
    String regex;

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {

        return regex;
    }

    public SaveEvent(String regex){
        this.regex = regex;
    }
}
