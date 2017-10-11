package com.example.matija.myapplication.Events;

import com.example.matija.myapplication.Database.DataTable;

import java.util.List;

/**
 * Created by matija on 03.10.17..
 */

public class setMainList {
    List<DataTable> data;
    public setMainList(List<DataTable>data){
        this.data = data;
    }

    public List<DataTable> getData() {
        return data;
    }
}
