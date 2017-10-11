package com.example.matija.myapplication.Events;

import com.example.matija.myapplication.Models.AlarmData;

/**
 * Created by matija on 04.10.17..
 */

public class NotificationTransferList {
    private AlarmData data;

    public NotificationTransferList(AlarmData data){
        this.data = data;

    }

    public AlarmData getData() {
        return data;
    }

    public void setData(AlarmData data) {
        this.data = data;
    }
}
