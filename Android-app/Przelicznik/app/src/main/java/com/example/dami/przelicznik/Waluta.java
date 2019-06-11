package com.example.dami.przelicznik;

/**
 * Created by dami on 2016-04-27.
 */
public class Waluta {
    private String nazwa_waluty="";
    private int przelicznik=0;
    private String kod_waluty="";
    private double kurs_sredni=0;

    public int index=-1;

    public Waluta(){}
    public Waluta(String nazwa_waluty ,int przelicznik , String kod_waluty ,double kurs_sredni){
        this.nazwa_waluty=nazwa_waluty;
        this.przelicznik=przelicznik;
        this.kod_waluty=kod_waluty;
        this.kurs_sredni=kurs_sredni;
    }

    public void setNazwa(String nazwa)
    {this.nazwa_waluty=nazwa;}

    public void setPrzelicznik(int przelicz)
    {this.przelicznik=przelicz;}

    public void setKod_waluty(String kod)
    {this.kod_waluty=kod;}

    public void setKurs_sredni(double kurs)
    {this.kurs_sredni=kurs;}

    public String getNazwa()
    {return this.nazwa_waluty;}

    public int getPrzelicznik()
    {return this.przelicznik;}

    public String getKod_waluty()
    {return this.kod_waluty;}

    public double getkurs_sredni()
    {return this.kurs_sredni;}

    //pomocnicza metoda do wypisywania
    public String pisz()
    {
        String p=nazwa_waluty+" , "+przelicznik+" , "+kod_waluty+" , "+kurs_sredni;
        return p;
    }

}
