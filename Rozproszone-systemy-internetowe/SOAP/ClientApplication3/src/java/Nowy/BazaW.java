/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nowy;

import java.io.Serializable;
import service.Wypożyczenie;

/**
 *
 * @author dami
 */
public class BazaW implements Serializable{
    private static final long serialVersionUID = 1L;
    private int idw;
    private int idc;
    private int idk;
    private String status;
    private String start;
    private String end;
    
    public BazaW()
    {
        this.idw=1;
        this.idc=1;
        this.idk=1;
        this.status="c";
        this.start="p";
        this.end="k";
    }
    public BazaW(Wypożyczenie w)
    {
        this.idw=w.getIdWypożyczenie();
        this.idc=w.getIdCzytelnika();
        this.idk=w.getIdKsiążka();
        this.status=w.getStatus();
        this.start=w.getDataWypożyczenia();
        this.end=w.getTerminZwrotu();
    }
    public Wypożyczenie getW()
    {
        Wypożyczenie w=new Wypożyczenie();
        
        w.setIdWypożyczenie(this.idw);
        w.setIdCzytelnika(this.idc);
        w.setIdKsiążka(this.idk);
        w.setStatus(this.status);
        w.setDataWypożyczenia(this.start);
        w.setTerminZwrotu(this.end);
        
        return w;
    }
    
    public void setW(Wypożyczenie w)
    {
        this.idw=w.getIdWypożyczenie();
        this.idc=w.getIdCzytelnika();
        this.idk=w.getIdKsiążka();
        this.status=w.getStatus();
        this.start=w.getDataWypożyczenia();
        this.end=w.getTerminZwrotu();
    }
    
}
