package com.example.matija.myapplication.Database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by matija on 09.08.17..
 */

@Table(database = RacunkoDatabase.class)
public class DataTable extends BaseModel implements Serializable {

    public DataTable(){};
    public DataTable(double cijena, String naziv, String datum, String kategorija, UsersTable user){
        this.cijena = cijena;
        this.naziv = naziv;
        this.datum = datum;
        this.kategorija = kategorija;
        this.user = user;
    }

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    double cijena;


    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setUser(UsersTable user) {
        this.user = user;
    }

    @Column
    String naziv;

    @Column
    String kategorija;

    @Column
    String datum;


    @Column
    @ForeignKey
    UsersTable user;

    public int getId() {
        return id;
    }

    public double getCijena() {
        return cijena;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getKategorija() {
        return kategorija;
    }

    public String getDatum() {
        return datum;
    }

    public UsersTable getUser() {
        return user;
    }
}
