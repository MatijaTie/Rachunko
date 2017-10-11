package com.example.matija.myapplication.Database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by matija on 09.08.17..
 */

@Table(database = RacunkoDatabase.class)
public class UsersTable extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    int partHeight;

    @Column
    String regex;

    @Column
    String name;




    @Column
    String password;



    public int getPartHeight() {
        return partHeight;
    }

    public void setPartHeight(int partHeight) {
        this.partHeight = partHeight;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getid(){
        return id;
    }
}
