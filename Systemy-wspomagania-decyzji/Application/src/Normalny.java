
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class Normalny {    
    private String sciezka="Tablica_rozkladu_nromalnego.txt";
    private List<String> linie = new ArrayList<>();
    
    public Normalny()
    {
    }
    
    public List<String> wczytaj()
    {
        //wczytuje plik
            try{
            File plik1 = new File(sciezka);
            Scanner skaner1 = new Scanner(plik1);
            
            while (skaner1.hasNextLine()==true) {
            String pom=skaner1.nextLine();
            linie.add(pom);

            }             
            
            } catch (Exception e) {}       
                
        
        return linie;
    }
    private boolean sprawdz(String s)
    {
        for(int i=0;i<s.length();i++)
        if(s.charAt(i)=='.')
        {
            return true;
        }        
        return false;
    }
    
    
    public double znajdz(double zmienna)//metoda szuka wartosci w tablicy rozkłądu wartosci
    {
        List<String> lista = wczytaj();
        boolean dodatnia = true;
        //w pliku kolumy oznaczaja 0.00 ,0.01 ,0.02 , ..  , 0.09
        
        if(zmienna > 3.5 || zmienna < -3.5){return 1;}//zwracam 1 w przypadku gdy wartosci nie bedzie w tabelach
        
        if(zmienna > 0)
        {
            dodatnia=true;
        }
        else
        {
            dodatnia=false;
            zmienna=zmienna*(-1);
        }
        String zm=zmienna+"";
        
        
        try{              
        
        String a="";
        double nr=0;
        int nr2=0;
            
        if(!sprawdz(zm))
        {
            zm=zm+".00";
        }
        if(zm.length()<4)
        {
            zm=zm+"0";
        }
        nr=Double.valueOf(zm.charAt(0)+"."+zm.charAt(2));
          
        nr2=(int)(nr*10);//numer wiersza ktory biore
        String s=lista.get(nr2);
        String[] s2=s.split(" ");
        double s3=Double.valueOf(s2[0]);
          
        int nr3=0;//numer kolumny
        String zm2="0.0"+zm.charAt(3);
        nr3=(int)(100*Double.valueOf(zm2));
         
        //szukam wartosci w tabeli
        String b=lista.get(nr2);//wybieram wiersz
        String[] b2=b.split(" ");
        String b3=b2[nr3+1];//wybieram komurke
           
        double b4 = Double.valueOf(b3);
        
        
        if(dodatnia)
        { 
        return 1.0-b4;          
        }
        else
        {            
            return 1.0-b4;
        }
        
        }catch(Exception e){}
            
        
        return 0;
    }
}
