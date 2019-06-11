/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.List;

/**
 *
 * @author dami
 */
public class Punkt4 {
    public String text;
    public List<Książka> lk;
    public List<Czytelnik> lc;
    public List<Wypożyczenie> lw;
    
    
    public Punkt4(){}
    public Punkt4(String t)
    {
        this.text=t;
        this.lc=null;
        this.lk=null;
        this.lw=null;
    }
    public Punkt4(String t,List<Książka> k,List<Czytelnik> c,List<Wypożyczenie> w)
    {        
        this.text=t;
        this.lc=c;
        this.lk=k;
        this.lw=w;
    }
    
}
