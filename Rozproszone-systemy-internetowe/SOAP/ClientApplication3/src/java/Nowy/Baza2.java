/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nowy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import service.Czytelnik;
import service.Książka;
import service.Wypożyczenie;

/**
 *
 * @author dami
 */
public class Baza2 {        
    private final String sk="E:\\ksiazki10.dat";
    private final String sc="E:\\czytelnicy10.dat";
    private final String sw="E:\\wypozyczenia10.dat";
    
    
    public Baza2(){}
    
    //tutaj obiekty typu książka ,czytelnik,wypożyczenie nie sa serializowane
    //twoże nowe klasy BazaK,BazaC,BazaW by przechowywac dane
    
    public String k_zapisz(List<Książka> lk)
    {
        
            try{
            ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(sk));
            
            for(int i=0;i<lk.size();i++)
            {
                wy1.writeObject(new BazaK(lk.get(i)));
            }           
            wy1.close();
            }catch(Exception e){return "błąd: "+e;}
            return "";
    }
    public String c_zapisz(List<Czytelnik> lc)
    {
            //jesli nie bedzie serializować tej bazy to zrobic wlasny obiekt to zapisywania
            //moze tez wystopic problem z integerm to ,bedzie trzeba to rozbierac na czesci i żutować na int
            try{
            ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(sc));
            
            for(int i=0;i<lc.size();i++)
            {
                wy1.writeObject(new BazaC(lc.get(i)));
            }          
            wy1.close();
            }catch(Exception e){return "błąd: "+e;}
            return "";
    }
    public String w_zapisz(List<Wypożyczenie> lw)
    {
        
            try{
            ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(sw));
            for(int i=0;i<lw.size();i++)
            {
                wy1.writeObject(new BazaW(lw.get(i)));
            }          
            wy1.close();
            }catch(Exception e){return "błąd: "+e;}
            return " "+lw.size();
    }
    
    public List<Książka> k_wczytaj()
    {
        List<Książka> pom1 = new ArrayList<Książka>();        
        try{
            ObjectInputStream we1 = new ObjectInputStream(new FileInputStream(sk));
            try{
                while(true){
                BazaK pom = (BazaK)we1.readObject();
                pom1.add(pom.getK());
                }
            }catch(Exception e){}
            
            we1.close();
        }catch(Exception e){}
        return pom1;
    }
    
    public List<Czytelnik> c_wczytaj()
    {
        List<Czytelnik> pom1 = new ArrayList<Czytelnik>();        
        try{
            ObjectInputStream we1 = new ObjectInputStream(new FileInputStream(sc));
            
            try{
                while(true){
                BazaC pom = (BazaC)we1.readObject();
                pom1.add(pom.getC());
                }
            }catch(Exception e){}
            
            we1.close();
        }catch(Exception e){}
        return pom1;
    }
    
    public List<Wypożyczenie> w_wczytaj()
    {
        List<Wypożyczenie> pom1 = new ArrayList<Wypożyczenie>();        
        try{
            ObjectInputStream we1 = new ObjectInputStream(new FileInputStream(sw));
            try{
                while(true){
                BazaW pom = (BazaW)we1.readObject();
                pom1.add(pom.getW());
                }
            }catch(Exception e){}
            
            we1.close();
        }catch(Exception e){}
        return pom1;
    }
    
    
    public String Wczytaj_nowa() {
        
        
        try{
        List<Książka> lk = new ArrayList<Książka>();
        List<Czytelnik> lc = new ArrayList<Czytelnik>();
        List<Wypożyczenie> lw = new ArrayList<Wypożyczenie>();
        
        
        Czytelnik b1= new Czytelnik();b1.setIdCzytelnik(1);b1.setImie("Tomek");b1.setNazwisko("Cebula");lc.add(b1);
        Czytelnik b2= new Czytelnik();b2.setIdCzytelnik(2);b2.setImie("Jan");b2.setNazwisko("Kowalski");lc.add(b2);
        Czytelnik b3= new Czytelnik();b3.setIdCzytelnik(3);b3.setImie("Romek");b3.setNazwisko("Lebowski");lc.add(b3);
        Czytelnik b4= new Czytelnik();b4.setIdCzytelnik(4);b4.setImie("Miachal");b4.setNazwisko("Woodyjowksi");lc.add(b4);
        Czytelnik b5= new Czytelnik();b5.setIdCzytelnik(5);b5.setImie("Jarek");b5.setNazwisko("Majewski");lc.add(b5);
        
        Książka c1=new Książka();c1.setIdKsiążka(1);c1.setTytuł("Kordian");c1.setAutor("Juliusz Slowacki");lk.add(c1);
        Książka c2=new Książka();c2.setIdKsiążka(2);c2.setTytuł("Lalka");c2.setAutor("Boleslaw Prus");lk.add(c2);
        Książka c3=new Książka();c3.setIdKsiążka(3);c3.setTytuł("Rewizja");c3.setAutor("Remigiusz Mroz");lk.add(c3);
        Książka c4=new Książka();c4.setIdKsiążka(4);c4.setTytuł("Idiota");c4.setAutor("Fiodor Dostojewski");lk.add(c4);
        Książka c5=new Książka();c5.setIdKsiążka(5);c5.setTytuł("Przeprawa");c5.setAutor("Samar Yazbek");lk.add(c5);
        Książka c6=new Książka();c6.setIdKsiążka(6);c6.setTytuł("Potop");c6.setAutor("Hendryk Sienkiewicz");lk.add(c6);
        Książka c7=new Książka();c7.setIdKsiążka(7);c7.setTytuł("Metro 2033");c7.setAutor("Dmitrij Gluchowski");lk.add(c7);
        Książka c8=new Książka();c8.setIdKsiążka(8);c8.setTytuł("Wilki");c8.setAutor("Adam Wajrak");lk.add(c8);
        Książka c9=new Książka();c9.setIdKsiążka(9);c9.setTytuł("Hobbit");c9.setAutor("John Ronald Reuel Tolkien");lk.add(c9);
        Książka c10=new Książka();c10.setIdKsiążka(10);c10.setTytuł("Życie Pi");c10.setAutor("Yann Martel");lk.add(c10);
        
        
        Calendar CC=Calendar.getInstance(),CC2=Calendar.getInstance();
        Wypożyczenie d1=new Wypożyczenie();d1.setIdWypożyczenie(1);d1.setIdCzytelnika(1);d1.setIdKsiążka(1);
        int[] p1={2016,03,16};CC.set(Calendar.YEAR, p1[0]);CC.set(Calendar.MONTH, p1[1]-1);CC.set(Calendar.DAY_OF_MONTH, p1[2]);
        int[] q1={2016,03,31};CC2.set(Calendar.YEAR, q1[0]);CC2.set(Calendar.MONTH, q1[1]-1);CC2.set(Calendar.DAY_OF_MONTH, q1[2]);
        java.sql.Date DDp1 = new java.sql.Date(CC.getTimeInMillis()),DDq1 = new java.sql.Date(CC2.getTimeInMillis());//2016-03-31
        d1.setStatus("zwrócona");d1.setDataWypożyczenia(DDp1.toString());d1.setTerminZwrotu(DDq1.toString());lw.add(d1);
        
        Calendar CC3=Calendar.getInstance(),CC4=Calendar.getInstance();
        Wypożyczenie d2=new Wypożyczenie();d2.setIdWypożyczenie(2);d2.setIdCzytelnika(3);d2.setIdKsiążka(5);
        int[] p2={2016,04,01};CC3.set(Calendar.YEAR, p2[0]);CC3.set(Calendar.MONTH, p2[1]-1);CC3.set(Calendar.DAY_OF_MONTH, p2[2]);
        int[] q2={2016,04,15};CC4.set(Calendar.YEAR, q2[0]);CC4.set(Calendar.MONTH, q2[1]-1);CC4.set(Calendar.DAY_OF_MONTH, q2[2]);
        java.sql.Date DDp2 = new java.sql.Date(CC3.getTimeInMillis()),DDq2 = new java.sql.Date(CC4.getTimeInMillis());//2016-03-31
        d2.setStatus("wypożyczona");d2.setDataWypożyczenia(DDp2.toString());d2.setTerminZwrotu(DDq2.toString());lw.add(d2);
        
        Calendar CC5=Calendar.getInstance(),CC6=Calendar.getInstance();
        Wypożyczenie d3=new Wypożyczenie();d3.setIdWypożyczenie(3);d3.setIdCzytelnika(3);d3.setIdKsiążka(7);
        int[] p3={2016,03,18};CC5.set(Calendar.YEAR, p3[0]);CC5.set(Calendar.MONTH, p3[1]-1);CC5.set(Calendar.DAY_OF_MONTH, p3[2]);
        int[] q3={2016,04,01};CC6.set(Calendar.YEAR, q3[0]);CC6.set(Calendar.MONTH, q3[1]-1);CC6.set(Calendar.DAY_OF_MONTH, q3[2]);
        java.sql.Date DDp3 = new java.sql.Date(CC5.getTimeInMillis()),DDq3 = new java.sql.Date(CC6.getTimeInMillis());//2016-03-31
        d3.setStatus("przetrzymana");d3.setDataWypożyczenia(DDp3.toString());d3.setTerminZwrotu(DDq3.toString());lw.add(d3);
        
        
        //i zapisuje baze
        k_zapisz(lk);
        c_zapisz(lc);
        w_zapisz(lw);
        
        }catch(Exception e){return "błąd: "+e;}
        return "wczytano nowe dane do tablic: ";
        
        }
}
