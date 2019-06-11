/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nowy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import service.Książka;

/**
 *
 * @author dami
 */
public class BazaK implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String tytul;
    private String autor;    
    
    public BazaK()
    { 
        this.id=0;
        this.tytul="";
        this.autor="";
    }
    public BazaK(Książka k)
    {
        this.id=k.getIdKsiążka();
        this.tytul=k.getTytuł();
        this.autor=k.getAutor();
    }
    public Książka getK()
    {
        Książka k =  new Książka();
        k.setIdKsiążka(this.id);
        k.setTytuł(this.tytul);
        k.setAutor(this.autor);
        return k;
    }
    public void setK(Książka k)
    {
        this.id=k.getIdKsiążka();
        this.tytul=k.getTytuł();
        this.autor=k.getAutor();
    }
}
