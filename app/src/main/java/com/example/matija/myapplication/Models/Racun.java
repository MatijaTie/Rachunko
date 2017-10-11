package com.example.matija.myapplication.Models;

/**
 * Created by matija on 07.08.17..
 */

public class Racun {
    private int cijena;
    private String naziv, datum, kategorija;

    public int getCijena() {
        return cijena;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getDatum() {
        return datum;
    }

    public String getKategorija() {
        return kategorija;
    }


    public Racun(int cijena, String naziv, String datum, String kategorija){
        this.cijena = cijena;
        this.naziv = naziv;
        this.datum = datum;
        this.kategorija = kategorija;
    }
}
