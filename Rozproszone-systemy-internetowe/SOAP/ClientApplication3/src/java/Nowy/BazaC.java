/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nowy;

import java.io.Serializable;
import service.Czytelnik;

/**
 *
 * @author dami
 */
public class BazaC implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String imie;
    private String nazwisko;
    
    public BazaC()
    {
        this.id=0;
        this.imie="";
        this.nazwisko="";
    }
    
    public BazaC(Czytelnik c)
    {
        this.id=c.getIdCzytelnik();
        this.imie=c.getImie();
        this.nazwisko=c.getNazwisko();
    }
    public Czytelnik getC()
    {
        Czytelnik c= new Czytelnik();
        c.setIdCzytelnik(this.id);
        c.setImie(this.imie);
        c.setNazwisko(this.nazwisko);
        return c;
    }
    public void setC(Czytelnik c)
    {        
        this.id=c.getIdCzytelnik();
        this.imie=c.getImie();
        this.nazwisko=c.getNazwisko();
    }    
}
