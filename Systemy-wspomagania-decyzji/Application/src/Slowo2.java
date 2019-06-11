
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
//klasa pomocnicza do posortowania
public class Slowo2 implements Comparable<Slowo2>{
    
    private double liczba1;
    private double liczba2;
    private String slowo;
    
    public Slowo2()
    {
        
    }
    public Slowo2(String slowo)
    {
        this.slowo=slowo;
    }
    public Slowo2(String slowo ,double liczba1 ,double liczba2)
    {
        this.slowo=slowo;
        this.liczba1=liczba1;
        this.liczba2=liczba2;
    }
    public String getSlowo()
    {
        return this.slowo;
    }
    public double getLiczba1()
    {
        return this.liczba1;
    }
    
    public double getLiczba2()
    {
        return this.liczba2;
    }
    
    //metoda sortujaca
    public List<Slowo2> sortuj(List<Slowo2> list)
    {
        Collections.sort(list);
        
        return list;
    }
    
    @Override
    public String toString() {
        return ""+liczba1;
    }    

    @Override
    public int compareTo(Slowo2 o) {
        int porownane = slowo.compareTo(o.slowo);
 
        if(porownane == 0) {
            return slowo.compareTo(o.slowo);
        }
        else {
            return porownane;
        }
    }
}
