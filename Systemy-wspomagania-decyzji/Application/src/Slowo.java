/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class Slowo implements Comparable<Slowo>{
    private String slowo;
    private double liczba;
    
    public Slowo(String slowo)
    {
        this.slowo=slowo;
    }
    public Slowo(String slowo ,double liczba)
    {
        this.slowo=slowo;
        this.liczba=liczba;
    }
    public Slowo(double liczba ,String slowo)
    {
        this.slowo=slowo;
        this.liczba=liczba;
    }
    
    
    public String getSlowo()
    {
        return this.slowo;
    }
    public double getLiczba()
    {
        return this.liczba;
    }
    
    @Override
    public String toString() {
        return ""+slowo;
    }
    

    @Override
    public int compareTo(Slowo o) {
        int porownane = slowo.compareTo(o.slowo);
 
        if(porownane == 0) {
            return slowo.compareTo(o.slowo);
        }
        else {
            return porownane;
        }
    }
}
