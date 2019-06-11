/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;

/**
 *
 * @author dami
 */
@WebService(serviceName = "Usluga")
@Stateless()
public class Usluga {
    

    /////////////////////////tu daje metody :

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksAvailable")
    public List<Książka> BooksAvailable(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste dostępnych książek
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Książka> pom = new ArrayList<Książka>();
        
        for(int i=0;i<lk.size();i++)
        {
        int z=0,z2=0;
        for(int j=0;j<lw.size();j++)
        {
            if((int)lk.get(i).getIdKsiążka()==(int)lw.get(j).getIdKsiążka() && !lw.get(j).getStatus().equals("zwrócona")){z=1;}
            if((int)lk.get(i).getIdKsiążka()==(int)lw.get(j).getIdKsiążka()){z2=1;}
        }
        if(z==0 || z2==0)//jesli książka nie jest wypożyczona lub nie jest na lście wypożyczonych
        {
            pom.add(lk.get(i));
        }
        }
        
        return szyfrK2(pom);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderAll")
    public List<Czytelnik> ReaderAll(@WebParam(name = "text") String text ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste czytelnikow zależnie od podanego textu ,np jesli podana jest liczba to traktuje to jako id
        
        text=dszyfr(text);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        String pom="";
        if(text!=null)
        pom=text.toLowerCase();
                
        List<Czytelnik> pom2 = new ArrayList<Czytelnik>();
        boolean n1;
        int a=0;
        
        try{
        a=Integer.parseInt(pom);
        
        n1=true;
        }catch(Exception e){n1=false;}
        
        if(n1==true)
        for(int i=0;i<lc.size();i++)//indexOf(pom)==-1 oznacza ze nie ma tam tego fragmentu
        if(((int)lc.get(i).getIdCzytelnik()==a) || (lc.get(i).getImie().toLowerCase().indexOf(pom)!=-1 || lc.get(i).getNazwisko().toLowerCase().indexOf(pom)!=-1))
        {
            pom2.add(lc.get(i));
        }
        
        if(n1==false)
        for(int i=0;i<lc.size();i++)//indexOf(pom)==-1 oznacza ze nie ma tam tego fragmentu
        if(lc.get(i).getImie().toLowerCase().indexOf(pom)!=-1 || lc.get(i).getNazwisko().toLowerCase().indexOf(pom)!=-1)
        {
            pom2.add(lc.get(i));
        }
        return szyfrC2(pom2);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksAll")
    public List<Książka> BooksAll(@WebParam(name = "text") String text ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste książek zależnie od podanego textu ,np jesli podana jest liczba to traktuje to jako id
        
        text=dszyfr(text);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        String pom="";
        if(text!=null)
        pom=text.toLowerCase();
                
        List<Książka> pom2 = new ArrayList<Książka>();
        boolean n1;
        int a=0;
        
        try{
        a=Integer.parseInt(pom);
        
        n1=true;
        }catch(Exception e){n1=false;}
        
        if(n1==true)
        for(int i=0;i<lk.size();i++)//indexOf(pom)==-1 oznacza ze nie ma tam tego fragmentu
        if(((int)lk.get(i).getIdKsiążka()==a) || (lk.get(i).getAutor().toLowerCase().indexOf(pom)!=-1 || lk.get(i).getTytuł().toLowerCase().indexOf(pom)!=-1))
        {
            pom2.add(lk.get(i));
        }
        
        if(n1==false)
        for(int i=0;i<lk.size();i++)//indexOf(pom)==-1 oznacza ze nie ma tam tego fragmentu
        if(lk.get(i).getAutor().toLowerCase().indexOf(pom)!=-1 || lk.get(i).getTytuł().toLowerCase().indexOf(pom)!=-1)
        {
            pom2.add(lk.get(i));
        }
        
        return szyfrK2(pom2);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksBorrowed")
    public List<Książka> BooksBorrowed(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste pożyczonych lub przetrzymanych książek
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Książka> pom = new ArrayList<Książka>();
        
        for(int i=0;i<lk.size();i++)
        {
        int z=0;
        for(int j=0;j<lw.size();j++)
        {
            if((int)lk.get(i).getIdKsiążka()==(int)lw.get(j).getIdKsiążka() && !lw.get(j).getStatus().equals("zwrócona")){z=1;}
        }
        
        if(z==1)//jesli książka nie jest zwrócona to z==1
        {
            pom.add(lk.get(i));
        }
        }
        
        return szyfrK2(pom);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksHeld")
    public List<Książka> BooksHeld(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste przetrzymanych książek
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Książka> pom = new ArrayList<Książka>();
                
        for(int i=0;i<lk.size();i++)
        {
        int z=0;
        for(int j=0;j<lw.size();j++)
        {
            if((int)lk.get(i).getIdKsiążka()==(int)lw.get(j).getIdKsiążka() && lw.get(j).getStatus().equals("przetrzymana")){z=1;}
        }
        if(z==1)//jesli książka nie jest zwrucona to z==1
        {
            pom.add(lk.get(i));
        }
        }
        
        return szyfrK2(pom);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderBorrower")
    public List<Punkt1> ReaderBorrower(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca liste czytelników i liczbe posiadanych przez nich książek
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        int z=0,z2=0;
        
        List<Punkt1> pom = new ArrayList<Punkt1>();
        
        try{
        for(int i=0;i<lc.size();i++)
        {
        z=0;z2=0;
        for(int j=0;j<lw.size();j++)
        {
            if((int)lc.get(i).getIdCzytelnik()==(int)lw.get(j).getIdCzytelnika() && !lw.get(j).getStatus().equals("zwrócona")){z=1;z2++;}

        }
        if(z==1)//jesli książka nie jest zwrucona to z==1
        {
            Punkt1 b = new Punkt1();
            b.czytelnik=szyfrC(lc.get(i));
            b.ilość=szyfr(z2);
            
            pom.add(b);
        }
        }
        }catch(Exception e){}
        
        return pom;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderAdd")
    public Punkt4 ReaderAdd(@WebParam(name = "imie") String imie, @WebParam(name = "nazwisko") String nazwisko ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //dodaje nowego czytelnika jeśli podano poprawne imie i nazwisko
        
        imie=dszyfr(imie);
        nazwisko=dszyfr(nazwisko);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        if(imie.equals(null) || nazwisko.equals(null)){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        int a=0;
        for(int i=0;i<lc.size();i++)if((int)lc.get(i).getIdCzytelnik()>a){a=lc.get(i).getIdCzytelnik();}
        
        if(!imie.equals("") && !nazwisko.equals(""))
        {
        try{
            Czytelnik pom=new Czytelnik();
            pom.setIdCzytelnik(a+1);
            pom.setImie(imie);
            pom.setNazwisko(nazwisko);
            lc.add(pom);
            
            return new Punkt4(szyfr("dodano czytelnika"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
        }catch(Exception e){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        }else{return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
    }

    
    //String BookAdd tytul autor

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookAdd")
    public Punkt4 BookAdd(@WebParam(name = "tytul") String tytul, @WebParam(name = "autor") String autor ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //dodaje nowo książke jeśli podano poprawny tytuł i autora
        
        tytul=dszyfr(tytul);
        autor=dszyfr(autor);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        if(tytul.equals(null) || autor.equals(null)){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        int a=0;
        for(int i=0;i<lk.size();i++)if((int)lk.get(i).getIdKsiążka()>a){a=lk.get(i).getIdKsiążka();}
        
        if(!tytul.equals("") && !autor.equals(""))
        {
        try{
            Książka pom=new Książka();
            pom.setIdKsiążka(a+1);
            pom.setTytuł(tytul);
            pom.setAutor(autor);
            lk.add(pom);
            
            return new Punkt4(szyfr("dodano książke"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
        }catch(Exception e){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        }else{return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
    }
    
    //String BookGive idk idc czas

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookGive")
    public Punkt4 BookGive(@WebParam(name = "idk") int idk, @WebParam(name = "idc") int idc, @WebParam(name = "czas") int czas,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        
        idk=dszyfr(idk);
        idc=dszyfr(idc);
        czas=dszyfr(czas);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        if(czas<0){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}//w przypadku podania czasu < 0 wychodze
                
        int z1=0,z2=0,z3=0;
        for(int i=0;i<lk.size();i++)if((int)lk.get(i).getIdKsiążka()==idk){z1=1;}
        if(z1==0){return new Punkt4(szyfr("błąd, nie ma takiej książki: "+idk),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        for(int i=0;i<lc.size();i++)if(lc.get(i).getIdCzytelnik()==idc){z2=1;}
        if(z2==0){return new Punkt4(szyfr("błąd, nie ma tekiej osoby: "+idc),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        //należy sprawdzić czy tą książke ktoś posiada
        for(int i=0;i<lw.size();i++)//jesli idksiążki nie jest w tym if'ie tzn ze nie jest pożyczona
        if((int)lw.get(i).getIdKsiążka()==idk  && !lw.get(i).getStatus().equals("zwrócona"))//jesli ta książka nie jest wypożyczona 
        {z3=1;}
        
        
        if(z3==0)//jesli tej książki nikt nie ma to z3==0
        {
            Wypożyczenie pom=new Wypożyczenie();
            int a=0;
            for(int i=0;i<lw.size();i++)if((int)lw.get(i).getIdWypożyczenie()>a){a=lw.get(i).getIdWypożyczenie();}
            
            Calendar c2=Calendar.getInstance();
            Calendar c3=Calendar.getInstance();
            
            c3.add(5, czas);
            
            java.sql.Date D2 = new java.sql.Date(c2.getTimeInMillis());//data aktualna
            java.sql.Date D3 = new java.sql.Date(c3.getTimeInMillis());//data przesunieta o liczbe dni
            
            pom.setIdWypożyczenie(a+1);
            pom.setIdKsiążka(idk);
            pom.setIdCzytelnika(idc);
            pom.setStatus("wypożyczona");//wypożyczona przetrzymana
            pom.setDataWypożyczenia(D2.toString());
            pom.setTerminZwrotu(D3.toString());
            
            
            List<Wypożyczenie> pom5 = new ArrayList<Wypożyczenie>();
            for(int i=0;i<lw.size();i++){pom5.add(lw.get(i));}
            pom5.add(pom);
            lw=pom5;
        }
        else{return new Punkt4(szyfr("błąd, tą książke ma aktualnie inna osoba"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        
        
        return new Punkt4(szyfr("wypożyczono książke"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "AllStatus")
    public List<Punkt2> AllStatus(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {//List<Punkt2>
        //podaje czytelnika i ksiązke którą wypożyczył lub oddał
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Punkt2> pom = new ArrayList<Punkt2>();
        
        try{
        for(int i=0;i<lw.size();i++)
        {
        
        
        Książka a=new Książka();
        Czytelnik b=new Czytelnik();
        
        for(int j=0;j<lk.size();j++)
        if((int)lw.get(i).getIdKsiążka()==(int)lk.get(j).getIdKsiążka())
        {a=lk.get(j);break;}        
        
        for(int j=0;j<lc.size();j++)
        if((int)lw.get(i).getIdCzytelnika()==(int)lc.get(j).getIdCzytelnik())
        {b=lc.get(j);break;}  
        
        if((int)lw.get(i).getIdCzytelnika()!=0 && (int)lw.get(i).getIdKsiążka()!=0){
        if(a!=null && b!=null)
        {
        Punkt2 c=new Punkt2();
        c.książka=szyfrK(a);
        c.czytelnik=szyfrC(b);
        c.wyp=szyfrW(lw.get(i));//pobieram wypożyczenie
        pom.add(c);
        }
        }
        
        
        //jesli indeksy na liscie wypożyczeń są równe 0 to nalezy je uwzglednic
        
        //jesli nie ma ksiązki to trzbea coś zwrucić
        if((int)lw.get(i).getIdKsiążka()==0 && (int)lw.get(i).getIdCzytelnika()!=0)
        {            
        Punkt2 c=new Punkt2();
        Książka k=new Książka();k.setTytuł(" ");k.setAutor(" ");k.setIdKsiążka(0);
        c.książka=szyfrK(k);
        c.czytelnik=szyfrC(b);
        c.wyp=szyfrW(lw.get(i));//pobieram wypożyczenie
        pom.add(c);
        }
        
        //jesli nie ma czytelnika to trzbea coś zwrucić
        if((int)lw.get(i).getIdCzytelnika()==0 && (int)lw.get(i).getIdKsiążka()!=0)
        {            
        Punkt2 c=new Punkt2();
        Czytelnik C=new Czytelnik();C.setImie(" ");C.setNazwisko(" ");C.setIdCzytelnik(0);
        c.książka=szyfrK(a);
        c.czytelnik=szyfrC(C);
        c.wyp=szyfrW(lw.get(i));//pobieram wypożyczenie
        pom.add(c);
        }
        
        //jesli nie ma ksiązki i czytelnika to trzbea coś zwrucić
        if((int)lw.get(i).getIdCzytelnika()==0 && (int)lw.get(i).getIdKsiążka()==0)
        {            
        Punkt2 c=new Punkt2();
        Książka k=new Książka();k.setTytuł(" ");k.setAutor(" ");k.setIdKsiążka(0);
        Czytelnik C=new Czytelnik();C.setImie(" ");C.setNazwisko(" ");C.setIdCzytelnik(0);
        c.książka=szyfrK(k);
        c.czytelnik=szyfrC(C);
        c.wyp=szyfrW(lw.get(i));//pobieram wypożyczenie
        pom.add(c);
        }
        
        }
        }catch(Exception e){}
                
        return pom;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookReturn")
    public Punkt4 BookReturn(@WebParam(name = "numer") int numer,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //pobieram numer z tablicy wypożyczenie zmienia status na zwrócona
        
        numer=dszyfr(numer);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        int z=0,z2=-1;
        for(int i=0;i<lw.size();i++)if((int)lw.get(i).getIdWypożyczenie()==numer){z=1;z2=i;}
        
        if(z==0){return new Punkt4(szyfr("błąd,brak wypożyczenia o tym numerze"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        //jeśli jest takie wypożyczenie to:
        Wypożyczenie p=lw.get(z2);
        String pom=p.getStatus();
        p.setStatus("zwrócona");
        lw.set(z2,p);
        
        return new Punkt4(szyfr("książka była "+pom),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "RemoveBook")
    public Punkt4 RemoveBook(@WebParam(name = "numer") int numer ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //podany jest numer książki
        //metoda usuwa książke o podanym numerze ,pod warunkiem że nie została wypożyczona
        //przy usunieciu książki ,należy jednocześnie zmienić id w tablicy wypożyczeń na 0
        
        numer=dszyfr(numer);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        int z=0,z2=0;
        int nr=-1;//nr na liscie ksiązek
        String st="";
        for(int i=0;i<lk.size();i++)if((int)lk.get(i).getIdKsiążka()==numer){nr=i;z=1;}
        if(z==0){return new Punkt4(szyfr("błąd,brak takiej książki"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        for(int i=0;i<lw.size();i++)//jesli jest takie wypozyczenie ze ksiazka jest wypozyczona / przetrzymana to z2==1
        if((int)lw.get(i).getIdKsiążka()==numer && !lw.get(i).getStatus().equals("zwrócona")){z2=1;st=lw.get(i).getStatus();}
        //jesli ta książka jest wypożyczona to wyjdź
        if(z2==1){return new Punkt4(szyfr("błąd,ta ksiązka jest "+st),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        
        //z2==0 jesli książka jest zwrócona lub nie ma jej na liscie wypożyczeń
        //nr książki
        //jesli książka jest ewentualnie zwrucona to w liscie wypożyczen pozmieniać jej id na 0 ,zeby sie nie odnosily do niczego
        for(int i=0;i<lw.size();i++)
        if((int)lw.get(i).getIdKsiążka()==numer)
        {
        Wypożyczenie a=lw.get(i);
        a.setIdKsiążka(0);
        lw.set(i,a);
        }
        //na koniec usuwam ksiązke o pobranym numerze (nr)
        lk.remove(nr);
        
        return new Punkt4(szyfr("książka zostałą usunięta"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
    }
 
    /**
     * Web service operation
     */
    @WebMethod(operationName = "RemoveReader")
    public Punkt4 RemoveReader(@WebParam(name = "numer") int numer ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //podany jest numer czytelnika
        //metoda usuwa czytelnika o podanym numerze ,pod warunkiem że nie posiada książki
        //przy usunieciu czytelnika ,należy jednocześnie zmienić id w tablicy wypożyczeń na 0
        
        numer=dszyfr(numer);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        int z=0,z2=0;
        int nr=-1;//nr na liscie ksiązek
        String st="";
        for(int i=0;i<lc.size();i++)if(lc.get(i).getIdCzytelnik()==numer){nr=i;z=1;}
        if(z==0){return new Punkt4(szyfr("błąd,brak takiej osoby"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
        for(int i=0;i<lw.size();i++)//jesli jest takia osoba ze ksiazka jest wypozyczona / przetrzymana to z2==1
        if((int)lw.get(i).getIdCzytelnika()==numer && !lw.get(i).getStatus().equals("zwrócona")){z2=1;st=lw.get(i).getStatus();}
        //jesli ta książka jest wypożyczona to wyjdź
        if(z2==1){return new Punkt4(szyfr("błąd,u tej osoby jest "+st+" książka"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
        
                
        //z2==0 jesli czytelnika ma zwrócona lub nie ma go na liscie wypożyczeń
        //nr czytelnika
        //jesli czytelnika ma ewentualnie zwrócone ksiązki to pozmieniać jego id na 0 ,zeby sie nie odnosily do niczego
        for(int i=0;i<lw.size();i++)
        if((int)lw.get(i).getIdCzytelnika()==numer)
        {
        Wypożyczenie a=lw.get(i);
        a.setIdCzytelnika(0);
        lw.set(i,a);
        }
        //na koniec usuwam czytelnika o pobranym numerze (nr)
        lc.remove(nr);
        
        return new Punkt4(szyfr("czytelnik został usunięty"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderStatus")
    public List<Punkt3> ReaderStatus(@WebParam(name = "numer") int numer ,@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {//List<Punkt3>
        //zwraca liste wypożyczen i książek dla danej podanej osoby
        
        numer=dszyfr(numer);
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Punkt3> pom = new ArrayList<Punkt3>();        
        
        for(int i=0;i<lw.size();i++)
        {
            if((int)lw.get(i).getIdCzytelnika()==numer)
            {
            Punkt3 c=new Punkt3();
            Wypożyczenie a=new Wypożyczenie();
            Książka b=new Książka();
            
            a=lw.get(i);
            //pobieram książke dla podanego numeru wypożyczenia
            for(int j=0;j<lk.size();j++)if((int)lw.get(i).getIdKsiążka()==(int)lk.get(j).getIdKsiążka()){b=lk.get(j);break;}
            
            c.książka=szyfrK(b);
            c.wypożyczenie=szyfrW(a);
            pom.add(c);
            }
        }
        
        return pom;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BorrowedStatus")
    public List<Punkt2> BorrowedStatus(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //zwraca czytelnika i wypożyczone przez jego książke ,pomija te o statusie zwrócona
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        List<Punkt2> pom = new ArrayList<Punkt2>();
        
        try{
        for(int i=0;i<lw.size();i++)
        if(!lw.get(i).getStatus().equals("zwrócona"))//jesli status jest rózny od zwrócona to dodaje do listy pom
        {
        Książka a=new Książka();
        Czytelnik b=new Czytelnik();
        for(int j=0;j<lk.size();j++)
        if((int)lw.get(i).getIdKsiążka()==(int)lk.get(j).getIdKsiążka())
        {a=lk.get(j);break;}
        for(int j=0;j<lc.size();j++)
        if((int)lw.get(i).getIdCzytelnika()==(int)lc.get(j).getIdCzytelnik())
        {b=lc.get(j);break;}
        if(a!=null && b!=null)
        {
        Punkt2 c=new Punkt2();
        c.książka=szyfrK(a);
        c.czytelnik=szyfrC(b);
        c.wyp=szyfrW(lw.get(i));//pobieram wypożyczenie
        pom.add(c);
        }        
        }
        }catch(Exception e){}
                
        return pom;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "UpdateAll")
    public Punkt4 UpdateAll(@WebParam(name = "lk") List<Książka> lk,@WebParam(name = "lc") List<Czytelnik> lc,@WebParam(name = "lw") List<Wypożyczenie> lw) {
        //metoda uaktualnia status wypożyczeń
        
        lk=dszyfrK2(lk);
        lc=dszyfrC2(lc);
        lw=dszyfrW2(lw);
        
        try{
        Calendar c1=Calendar.getInstance();
        c1.add(5,-1);//c1 zamieniam z daty dzisiejszej na wczorajszą
        Date DD1 = new Date(c1.getTimeInMillis());
        
        //jesli termin zwrotu jest po dzisiejszym dniu to zmień jego status na przetrzymana
        //jeśli nie jest zwrócona
        for(int i=0;i<lw.size();i++)        
        if(lw.get(i).getStatus().equals("wypożyczona"))//jesli książka jest wypożyczona        
        if(DD1.after(zmien(lw.get(i).getTerminZwrotu())))//jeśli dzisiejszy dzień jest po terminie
        {
            Wypożyczenie a=lw.get(i);
            a.setStatus("przetrzymana");
            lw.set(i,a);            
        }
        
        return new Punkt4("",szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));
        }catch(Exception e){return new Punkt4(szyfr("błąd"),szyfrK2(lk),szyfrC2(lc),szyfrW2(lw));}
    }
    
    
    
    private Date zmien(String data)
    {
        String p=data;//"2016-04-16";
        String[] p2=p.replace(" ","").split("-");
        int[] p3=new int[p2.length];
        for(int i=0;i<p2.length;i++){try{p3[i]=Integer.parseInt(p2[i]);}catch(Exception e){}}
        GregorianCalendar b = new GregorianCalendar(p3[0],p3[1]-1,p3[2]);
        Date b2 =new Date(b.getTimeInMillis());
        
        return b2;
    }
    
    //szyfrowanie tylko litery ,polskie litery i cyfry ,reszte pomijam
    
    private String szyfr(String text)
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
    private String dszyfr(String text)
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
    private int szyfr(int numer)
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
    
    private int dszyfr(int numer)
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
    private Książka szyfrK(Książka k)
    {
        Książka k2 = new Książka();
        k2.setIdKsiążka(szyfr(k.getIdKsiążka()));
        k2.setAutor(szyfr(k.getAutor()));
        k2.setTytuł(szyfr(k.getTytuł()));
        return k2;
    }
    private Czytelnik szyfrC(Czytelnik c)
    {
        Czytelnik c2 = new Czytelnik();
        c2.setIdCzytelnik(szyfr(c.getIdCzytelnik()));
        c2.setImie(szyfr(c.getImie()));
        c2.setNazwisko(szyfr(c.getNazwisko()));        
        return c2;
    }
    private Wypożyczenie szyfrW(Wypożyczenie w)
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
    private Książka dszyfrK(Książka k)
    {
        Książka k2 = new Książka();
        k2.setIdKsiążka(dszyfr(k.getIdKsiążka()));
        k2.setAutor(dszyfr(k.getAutor()));
        k2.setTytuł(dszyfr(k.getTytuł()));
        return k2;
    }
    private Czytelnik dszyfrC(Czytelnik c)
    {
        Czytelnik c2 = new Czytelnik();
        c2.setIdCzytelnik(dszyfr(c.getIdCzytelnik()));
        c2.setImie(dszyfr(c.getImie()));
        c2.setNazwisko(dszyfr(c.getNazwisko()));        
        return c2;
    }
    private Wypożyczenie dszyfrW(Wypożyczenie w)
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
    private List<Książka> szyfrK2(List<Książka> k)
    {
        List<Książka> k2= new ArrayList<Książka>();
        for(int i=0;i<k.size();i++)
        {
            k2.add(szyfrK(k.get(i)));
        }        
        return k2;
    }
    
    private List<Czytelnik> szyfrC2(List<Czytelnik> c)
    {
        List<Czytelnik> c2= new ArrayList<Czytelnik>();
        for(int i=0;i<c.size();i++)
        {
            c2.add(szyfrC(c.get(i)));
        }        
        return c2;
    }
    
    private List<Wypożyczenie> szyfrW2(List<Wypożyczenie> w)
    {
        List<Wypożyczenie> w2= new ArrayList<Wypożyczenie>();
        for(int i=0;i<w.size();i++)
        {
            w2.add(szyfrW(w.get(i)));
        }        
        return w2;
    }
    
    //deszyfruje listy obiektów
    
    private List<Książka> dszyfrK2(List<Książka> k)
    {
        List<Książka> k2= new ArrayList<Książka>();
        for(int i=0;i<k.size();i++)
        {
            k2.add(dszyfrK(k.get(i)));
        }        
        return k2;
    }
    
    private List<Czytelnik> dszyfrC2(List<Czytelnik> c)
    {
        List<Czytelnik> c2= new ArrayList<Czytelnik>();
        for(int i=0;i<c.size();i++)
        {
            c2.add(dszyfrC(c.get(i)));
        }        
        return c2;
    }
    
    private List<Wypożyczenie> dszyfrW2(List<Wypożyczenie> w)
    {
        List<Wypożyczenie> w2= new ArrayList<Wypożyczenie>();
        for(int i=0;i<w.size();i++)
        {
            w2.add(dszyfrW(w.get(i)));
        }        
        return w2;
    }
           
}
