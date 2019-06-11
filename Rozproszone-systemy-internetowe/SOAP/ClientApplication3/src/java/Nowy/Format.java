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
import java.util.List;
import service.Czytelnik;
import service.Książka;
import service.Wypożyczenie;

/**
 *
 * @author dami
 */
public class Format {
    private final String scieżka="E:\\text.txt";
    
        
    //zapis do pliku
    public void zapis(List<String> text)
    {
        try{
            ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(scieżka));
            Text pom=new Text(text);
            wy1.writeObject(pom);
            wy1.close();
            
        }catch(Exception e){}
        
    }
    //czytanie z pliku
    public List<String> czytaj()
    {
        List<String> pom1 = new ArrayList<String>();
        try{
            ObjectInputStream we1 = new ObjectInputStream(new FileInputStream(scieżka));
            Text pom2=(Text)we1.readObject();
            pom1=pom2.get();
            we1.close();
        }catch(Exception e){}
        
        return pom1;
    }
    /////////////////////////////////////////////////////////////////////
    //szyfrowanie i deszyfrowanie
    
    public String szyfr(String text)
    {
        char[] znaki ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','Ó','ó','Ą','ą','Ć','ć','Ę','ę','Ń','ń','Ś','ś','Ź','ź','Ż','ż'};
        char[] szyfr={'u','C','V','p','6','A','k','L','Ę','T','N','d','Q','H','s','Ó','ą','4','x','e','X','0','w','D','Ń','l','t','r','ż','z','ę','O','G','Ą','7','Ź','a','W','J','ś','S','c','n','b','F','ć','ń','h','R','q','Ć','P','ź','m','y','M','9','B','E','U','1','o','Z','I','ó','v','K','5','Y','Ż','2','j','f','g','8','i','3','Ś'};
    
        String pom="";        
        //kodowanie tylko polskie znaki i cyfry
        for(int i=0;i<text.length();i++)
        {
        int z=0;
        for(int j=0;j<znaki.length;j++)
        if(text.charAt(i)==znaki[j])
        {
            z=1;
            pom=pom+(char)szyfr[j];
        }
        if(z==0){pom=pom+text.charAt(i);}
        } 
        
        return pom;
    }
    public String dszyfr(String text)
    {
        char[] znaki ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','Ó','ó','Ą','ą','Ć','ć','Ę','ę','Ń','ń','Ś','ś','Ź','ź','Ż','ż'};
        char[] szyfr={'u','C','V','p','6','A','k','L','Ę','T','N','d','Q','H','s','Ó','ą','4','x','e','X','0','w','D','Ń','l','t','r','ż','z','ę','O','G','Ą','7','Ź','a','W','J','ś','S','c','n','b','F','ć','ń','h','R','q','Ć','P','ź','m','y','M','9','B','E','U','1','o','Z','I','ó','v','K','5','Y','Ż','2','j','f','g','8','i','3','Ś'};
    
        String pom="";
        //kodowanie tylko polskie znaki i cyfry
        for(int i=0;i<text.length();i++)
        {
        int z=0;
        for(int j=0;j<szyfr.length;j++)
        if(text.charAt(i)==szyfr[j])
        {
            z=1;
            pom=pom+(char)znaki[j];
        }
        if(z==0){pom=pom+text.charAt(i);}
        } 
        
        return pom;
    }
    
    //i szyfrowanie liczb
    public int szyfr(int numer)
    {
        char[] znaki ={'0','1','2','3','4','5','6','7','8','9'};
        char[] szyfr ={'6','4','0','7','9','1','8','2','5','3'};
        String text=numer+"";
        String pom="";
        for(int i=0;i<text.length();i++)
        {
        int z=0;
        for(int j=0;j<znaki.length;j++)
        if(text.charAt(i)==znaki[j])
        {
            z=1;
            pom=pom+(char)szyfr[j];
        }
        if(z==0){pom=pom+text.charAt(i);}
        } 
        
        return Integer.parseInt(pom);
    }
    
    public int dszyfr(int numer)
    {
        char[] znaki ={'0','1','2','3','4','5','6','7','8','9'};
        char[] szyfr ={'6','4','0','7','9','1','8','2','5','3'};
        String text=numer+"";
        String pom="";
                
        for(int i=0;i<text.length();i++)
        {
        int z=0;
        for(int j=0;j<szyfr.length;j++)
        if(text.charAt(i)==szyfr[j])
        {
            z=1;
            pom=pom+(char)znaki[j];
        }
        if(z==0){pom=pom+text.charAt(i);}
        } 
        
        return Integer.parseInt(pom);
    }
    ////////////////////////////
    
    //szyfruje obiekty
    public Książka szyfrK(Książka k)
    {
        Książka k2 = new Książka();
        k2.setIdKsiążka(szyfr(k.getIdKsiążka()));
        k2.setAutor(szyfr(k.getAutor()));
        k2.setTytuł(szyfr(k.getTytuł()));
        return k2;
    }
    public Czytelnik szyfrC(Czytelnik c)
    {
        Czytelnik c2 = new Czytelnik();
        c2.setIdCzytelnik(szyfr(c.getIdCzytelnik()));
        c2.setImie(szyfr(c.getImie()));
        c2.setNazwisko(szyfr(c.getNazwisko()));        
        return c2;
    }
    public Wypożyczenie szyfrW(Wypożyczenie w)
    {
        Wypożyczenie w2=new Wypożyczenie();
        w2.setIdWypożyczenie(szyfr(w.getIdWypożyczenie()));
        w2.setIdKsiążka(szyfr(w.getIdKsiążka()));
        w2.setIdCzytelnika(szyfr(w.getIdCzytelnika()));
        w2.setStatus(szyfr(w.getStatus()));
        w2.setDataWypożyczenia(szyfr(w.getDataWypożyczenia()));
        w2.setTerminZwrotu(szyfr(w.getTerminZwrotu()));
        return w2;
    }
    
    //deszyfruje obiekty
    public Książka dszyfrK(Książka k)
    {
        Książka k2 = new Książka();
        k2.setIdKsiążka(dszyfr(k.getIdKsiążka()));
        k2.setAutor(dszyfr(k.getAutor()));
        k2.setTytuł(dszyfr(k.getTytuł()));
        return k2;
    }
    public Czytelnik dszyfrC(Czytelnik c)
    {
        Czytelnik c2 = new Czytelnik();
        c2.setIdCzytelnik(dszyfr(c.getIdCzytelnik()));
        c2.setImie(dszyfr(c.getImie()));
        c2.setNazwisko(dszyfr(c.getNazwisko()));        
        return c2;
    }
    public Wypożyczenie dszyfrW(Wypożyczenie w)
    {
        Wypożyczenie w2=new Wypożyczenie();
        w2.setIdWypożyczenie(dszyfr(w.getIdWypożyczenie()));
        w2.setIdKsiążka(dszyfr(w.getIdKsiążka()));
        w2.setIdCzytelnika(dszyfr(w.getIdCzytelnika()));
        w2.setStatus(dszyfr(w.getStatus()));
        w2.setDataWypożyczenia(dszyfr(w.getDataWypożyczenia()));
        w2.setTerminZwrotu(dszyfr(w.getTerminZwrotu()));
        return w2;
    }
    
    //szyfruje listy obiektów
    public List<Książka> szyfrK2(List<Książka> k)
    {
        List<Książka> k2= new ArrayList<Książka>();
        for(int i=0;i<k.size();i++)
        {
            k2.add(szyfrK(k.get(i)));
        }        
        return k2;
    }
    
    public List<Czytelnik> szyfrC2(List<Czytelnik> c)
    {
        List<Czytelnik> c2= new ArrayList<Czytelnik>();
        for(int i=0;i<c.size();i++)
        {
            c2.add(szyfrC(c.get(i)));
        }        
        return c2;
    }
    
    public List<Wypożyczenie> szyfrW2(List<Wypożyczenie> w)
    {
        List<Wypożyczenie> w2= new ArrayList<Wypożyczenie>();
        for(int i=0;i<w.size();i++)
        {
            w2.add(szyfrW(w.get(i)));
        }        
        return w2;
    }
    
    //deszyfruje listy obiektów
    
    public List<Książka> dszyfrK2(List<Książka> k)
    {
        List<Książka> k2= new ArrayList<Książka>();
        for(int i=0;i<k.size();i++)
        {
            k2.add(dszyfrK(k.get(i)));
        }        
        return k2;
    }
    
    public List<Czytelnik> dszyfrC2(List<Czytelnik> c)
    {
        List<Czytelnik> c2= new ArrayList<Czytelnik>();
        for(int i=0;i<c.size();i++)
        {
            c2.add(dszyfrC(c.get(i)));
        }        
        return c2;
    }
    
    public List<Wypożyczenie> dszyfrW2(List<Wypożyczenie> w)
    {
        List<Wypożyczenie> w2= new ArrayList<Wypożyczenie>();
        for(int i=0;i<w.size();i++)
        {
            w2.add(dszyfrW(w.get(i)));
        }        
        return w2;
    }
}
