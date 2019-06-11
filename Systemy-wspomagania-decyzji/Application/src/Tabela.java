
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
public class Tabela {
    private String sciezka="text.txt";
    private List<String> linie = new ArrayList<>();
    //private List<Double> linie2 = new ArrayList<>();
    public boolean naglowki = true; //oznacza czy sa nagłówki czy nie
    //true oznacza ze są i nalezy je ignorowac ,false ze nie ma i nie trzeba ich ignorowac
    
    //private boolean sort_typ = true;//tru znaczy że sortowanie jest alfabetyczne ,a false ze wg kolejnosci wystąpień
    /*private boolean typ0=false; //brak porzątkowania
    private boolean typ1=true; //porzątkowanie alfabetyczne
    private boolean typ2=false; //porzątkowanie wg kolejności wystąpień
    private boolean typ3=false; //porzątkowanie wg ilości wystąpień*/
    private int typ=1;//porzątkowanie wg: 0-brak ,1-alfabetyczne ,2-wg kolejności wystąpień,3-wg ilości wystąpień
    public String znak=";";//znak ktorym dziele w pliku
    
    
    public Tabela(){
        wczytaj();
    }
    
    public Tabela(boolean naglowki)
    {
        wczytaj();
        this.naglowki=naglowki;
    }
    
    public Tabela(boolean naglowki,int typ)
    {
        wczytaj();
        this.naglowki=naglowki;
        /*this.typ1=alfabetycznie;
        this.typ2=kolejnosc_wystopien;
        this.typ3=ilosc_wystopien;
        
        if(!(typ1 && typ2 && typ3))
        this.typ0=true;*/
        this.typ=typ;
    }
    
    public void setTyp(int typ) //metoda ustwia typ sortowania
    {
        this.typ=typ;
    }
    
    public List<String> getLinie()
    {
        return this.linie;
    }
    
    public List<List<String>> getLinie(int a)//zwraca liste ,list kolumn
    {
        List<List<String>> lin = new ArrayList<>();
        for(int i=0;i<ileKolumn();i++)
        {
            lin.add(kolumna(i));
        }
        //return this.linie;
        return lin;
    }
    
    public void setLinie(List<List<String>> linie2,int a)//zwraca liste ,list kolumn
    {
        //List<List<String>> lin = new ArrayList<>();
        linie.clear();
        for(int i=0;i<linie2.get(0).size();i++)
        {
            String pom="";
            for(int j=0;j<linie2.size();j++)
            {
                if(j!=linie2.size()-1)
                pom=pom+linie2.get(j).get(i)+znak;
                else
                pom=pom+linie2.get(j).get(i);
            }
            linie.add(pom);
        }
    }
    
    
    
    public void setLinie(List<String> linie)
    {
        this.linie=new ArrayList(linie);
    }
    
    public List<String> wczytaj()
    {
        //wczytuje plik
            try{
            File plik1 = new File(sciezka);
            Scanner skaner1 = new Scanner(plik1);
            
            while (skaner1.hasNextLine()==true) {
            String pom=skaner1.nextLine();
            if(pom.charAt(0)!='#')//ignoruje komentarze zaczynajace sie na #
            linie.add(pom);

            }             
            
            } catch (Exception e) {}       
                
        
        return linie;
    }
    
    public List<Double> Wartosci(int nr)//tutaj mozna dodac zmienna okreslajaco sposob sortowania
    {
        //przyjmuje nr kolumny ,i zwracam liste wartosci liczbowych wg pewnego rodzaju sortowania w przypadku
        // gdy w kolumnie sa znaki ,a w przypadku gdy sa same liczby nie zmienia ich
        List<Double> lin = new ArrayList<>();
        List<String> linie2 = kolumna(nr);
        
        try{
        if(!sprawdz(nr))//jeśli nie ma znaków
        {
                for(int i=0;i < linie.size();i++)
                {
                    lin.add(Double.valueOf(linie2.get(i)));
                }      
                //return lin;
        }
        else
        {            
                List<Integer> lin2 = rank(nr);
                for(int i=0;i<linie.size();i++)
                {
                    lin.add((double)lin2.get(i));
                }
                //return lin;
        }
        //if(lin.size()!=linie2.size()){return null;}//jesli bedzie rozna liczba wierszy
        }catch(Exception e){}
        
        //lin.add((double)linie2.size());
        return lin;
    }
    
    public int ileKolumn()
    {
        String[] p;
        
        if(linie.size()!=0)
        p = linie.get(0).split(znak);
        else
        return 0;
        
        return p.length;
    }
    
    public int ileWierszy()
    {
        if(naglowki){return linie.size()-1;}
        
        return linie.size();
    }
    
    public boolean sprawdz(int nr)//metoda sprawdza czy w danej kolumnie sa znaki
    {//zwraca false jesli nie ma znakowi true jesli sa
        List<String> kol = kolumna(nr);
        
        try{
            for(int i=0;i<kol.size();i++)
            Double.valueOf(kol.get(i));
        }catch(Exception e)
        {
            return true;//zwraca true jesli wystapi blad tzn jest znak
        }
        
        return false;
    }
    
    public double max(int nr)
    {
        try{
            
            List<String> k1=kolumna(nr);
            List<Double> k2 = new ArrayList<>();
            
            
            if(sprawdz(nr))//jeśli są znaki
            {
                k2=null;k2=Wartosci(nr);
            }
            else
            {
            for(int i=0;i<k1.size();i++)
            {k2.add(Double.valueOf(k1.get(i)));}
            }
            
            
            double b=k2.get(0);
            
            for(int i=1;i<k2.size();i++)
            if(Math.abs(k2.get(i))>b)
            {
                b=Math.abs(k2.get(i));
            }
            
            try{
            }catch(Exception e){return 1;}
            
            return b;
            
        }catch(Exception e){}
        
        return 0;
    }
    
    public int ileKomurek(int k)//ile jest komurek w k-tym wierszu
    {
        String[] p = linie.get(k).split(znak);
        
        return p.length;
    }
    
    public List<String> Naglow(){
        
        List<String> nag = new ArrayList<>();
        
        try{
        String[] p = linie.get(0).split(znak);
        
        if(naglowki)
        if(linie.size()>1)//jeśli jest wiecej niż 1 kolumna i w pozostałych kolumnach jest wiecej rekordów
        {
            int max=0;
            for(int i=1;i<linie.size();i++)
            if(ileKomurek(i)>max)
            {
                max=ileKomurek(i);
            }
            if(max > p.length)//jesli są nie oznaczone kolumny
            {
                for(int i=0;i<p.length;i++){nag.add(p[i]);}
                
                for(int i=p.length;i<max;i++){nag.add("kolumna"+(i+1));}
                
            }
            
            //jeśli wszystkie kolumny są oznaczone
            if(max == p.length)
            for(int i=0;i<p.length;i++){
            nag.add(p[i]);
            }
        }
        
        if(!naglowki)
        {
            int max=0;
            for(int i=0;i<linie.size();i++)
            if(ileKomurek(i)>max)
            {
                max=ileKomurek(i);
            }
            
            
            for(int i=0;i<max;i++){nag.add("kolumna"+(i+1));}
        }
        
        
        if(linie.size()==0){return nag;}
        
        }catch(Exception e){System.out.println("błąd: "+e);}
            
        
        return nag;
    }
    
    public List<String> uloz(int nr)
    {//metoda pobiera nr kolumny i układa wartości alfabetycznie i zwraca ulozono liste
        List<String> lista = kolumna(nr);
        
        Collections.sort(lista);
        
        return lista;
    }
    
    public List<String> uloz(List<String> lista)
    {//metoda pobiera liste i układa wartości alfabetycznie i zwraca ulozono liste
        
        Collections.sort(lista);
        
        return lista;
    }
    
    public List<Slowo> uloz2(int nr)
    {
        List<String> lista = kolumna(nr);
        List<Slowo> lista2 = new ArrayList<>();
                
        for(int i=0;i<lista.size();i++)
        {
            lista2.add(new Slowo(lista.get(i),i+1));
        }
        Collections.sort(lista2);
        
        return lista2;
    }
    
        
    public List<String> kolumna(int nr)
    {
        List<String> kol = new ArrayList<>();
        int i=0;
        if(nr>ileKolumn()-1){nr=ileKolumn()-1;}
        
        if(naglowki){i=1;}
        
        
        for(;i<linie.size();i++){
            String[] p = linie.get(i).split(znak);
            kol.add(p[nr]);
        }
        
        return kol;
    }
    
    public List<Integer> rank(int nr)    
    {//metoda pobiera nr kolumny i zwraca liste wartosci dla tej kolumny wg rankingu kolejnosci alfabetycznej
    //tak ze lista ta pasuje jedynie do listy nie ułożonej
        
        List<String> lista =kolumna(nr);
        List<Integer> lista2 =new ArrayList<>();
        
        if(typ==0)//lista bez porzatkowania 
        {
            for(int i=0;i<lista.size();i++)
            lista2.add(i+1);
        }
        if(typ==1)//sortowanie alfabetyczne
        {
            lista2=rank1(lista);
        }
        if(typ==2)//sortowanie wg kolejności wystąpienia
        {
            lista2=rank2(lista);
        }
        if(typ==3)//sortowanie wg liczebności
        {
            lista2=rank3(lista);
        }
            
        return lista2;//rank3();
    }
    
    private List<Integer> rank1(List<String> lista)  
    {
        //sortowanie alfabetyczne        
        
        List<Integer> l1 = new ArrayList<>();
        List<String> l2 = lista;//kolumna(nr);
        
        List<Slowo2> s1 = new ArrayList<>();
        int[] tab=new int[l2.size()];
        
        
        for(int i=0;i<l2.size();i++)//tworze liste nieuporzątkowaną
        {
            s1.add(new Slowo2(l2.get(i),i+1,0));
        }
        Slowo2 r1=new Slowo2();
        
        String e1="";
        int u=1;
        
        s1=r1.sortuj(s1);    
        for(int i=0;i<s1.size();i++)//posortowaną liste numeruje w ostanim elemencie obiektu Slowo2
        {
            Slowo2 pom= s1.get(i);
            if(i==0){e1=pom.getSlowo();}
            
            if(i!=0 && !(pom.getSlowo().equals(e1)))
            {
                e1=pom.getSlowo();u++;
            }
            
            s1.set(i,new Slowo2(pom.getSlowo(),pom.getLiczba1(),u));            
        }
        
        for(int i=0;i<s1.size()-1;i++)
        if(s1.get(i).getSlowo().equals(s1.get(i+1).getSlowo()))
        {
            Slowo2 pom=s1.get(i);
            Slowo2 pom2=s1.get(i+1);
            s1.set(i+1, new Slowo2(pom2.getSlowo(),pom2.getLiczba1(),pom.getLiczba2()));
        }
        
        /////////////////////sortuje do postaci początkowej
        for(int i=0;i<s1.size();i++)
        {
            for(int j=0;j<s1.size()-1;j++)
            if(s1.get(j).getLiczba1() > s1.get(j+1).getLiczba1())
            {
                Slowo2 pom = s1.get(j);
                s1.set(j, s1.get(j+1));
                s1.set(j+1, pom);
            }
        }
        
        /////
        for(int i=0;i<s1.size();i++)
        {
            l1.add((int)s1.get(i).getLiczba2());
        }
        
        return l1;
    }
    
    
    
    private List<Integer> rank2(List<String> lista) //poprawic bo to jest wg liczebnosci 
    {
        //sortowanie wg kolejności wystąpienia
        List<Integer> l1 = new ArrayList<>();
        List<String> l3 =lista;// kolumna(nr);
        int p=1,p2=0;
        
        for(int i=0;i<l3.size();i++)l1.add(0);
        
        
        for(int i=0;i<l3.size();i++)
        {
            String pom=l3.get(i);
            p2=0;
            
            for(int j=0;j<l3.size();j++)
            if(pom.equals(l3.get(j)) && l1.get(j)==0)
            {
                l1.set(j, p);p2++;
            }
            
            int max=0;
            for(int j=0;j<l1.size();j++)if(l1.get(j)>max){max=l1.get(j);}
            
            p=max+1;
        }
        
        return l1;
    }
    
    
    private List<Integer> rank3(List<String> lista)  
    {
        //sortowanie wg liczebności
        List<Integer> l0 = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        List<String> l3 = lista;//kolumna(nr);
        //List<String> l4 = kolumna(nr);
        int[] tab = new int[l3.size()];
        
        List<Slowo2> list = new ArrayList<>();
        
        for(int i=0;i<l3.size();i++)
        {
            int pom=0;
            for(int j=0;j<l3.size();j++)
            if(l3.get(i).equals(l3.get(j)))
            {
                pom++;
            }
            //l0.add(pom);
            
            list.add(new Slowo2(l3.get(i),pom,0));
        }
        
        int max=0,max2=0,pom=0,z=1;
        
        for(int i=0;i<list.size();i++)
        if(list.get(i).getLiczba1()>max){max=(int)list.get(i).getLiczba1();}
        
        for(int i=0;i<list.size();i++)
        {
            for(int j=0;j<list.size();j++)
            if((int)list.get(j).getLiczba1()==max)
            {
                list.set(j,new Slowo2(list.get(j).getSlowo(),list.get(j).getLiczba1(),z));
            }
                        
            max2=max;
            max=0;
            
            for(int j=0;j<list.size();j++)
            if(list.get(j).getLiczba1()>max && list.get(j).getLiczba1()<max2) 
            {max=(int)list.get(j).getLiczba1();}
            
            z++;            
        }
        
        for(int i=0;i<list.size();i++)
        l1.add((int)list.get(i).getLiczba2());
        
        return l1;
    }
    
    
    public List<String> zmniejsz(List<String> list)//metoda zmniejsza liste z powtorek
    {        
            Collections.sort(list);
            
            for(int i=0;i<list.size()-1;i++)
            if(list.get(i).equals(list.get(i+1)))
            {
                list.remove(i);i--;
            }
            
        
        return list;
    }
    
    //////////////////////
    public List<String> zmniejsz2(List<String> list)
    {        
        //metoda zmniejsza liste z powtorek ,ale nie układa danych
            /*Collections.sort(list);
            
            for(int i=0;i<list.size()-1;i++)
            if(list.get(i).equals(list.get(i+1)))
            {
                list.remove(i);i--;
            }*/
        
        List<String> list2 = new ArrayList<>();
            
        for(int i=0;i<list.size();i++)
        {
            boolean z = true;
            for(int j=0;j<list2.size();j++)
            if(list.get(i).equals(list2.get(j)))
            {z=false;}
            if(z)
            {
                list2.add(list.get(i));
            }
        }
        
        
        return list2;
    }
    
    ///////////////////////
    
    public List<String> wiersz(int numer)//pobiera numer wiersza i zwraca wiersz
    {
        List<String> list = new ArrayList<>();
        int k2=0;
        
        if(naglowki){k2=1;}
        
        int k=numer+k2;
        if(k>linie.size()-1){return null;}
        
        String[] s=linie.get(k).split(znak);
        
        for(int i=0;i<s.length;i++)
        {
            list.add(s[i]);
        }
        
        return list;        
    }
    
    public void dodajKolumne(List<String> list)
    {
        if(naglowki)//jesli sa naglowki to dodaje nazwe pseldo kolumny
        {
            linie.set(0, linie.get(0)+znak+"Kolumna"+(ileKolumn()+1));
        }
        int i=0,j=0;
        if(naglowki){i=1;}
        
        for(;i<linie.size();i++,j++)
        {
            linie.set(i, linie.get(i)+znak+list.get(j));
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    public List<String> KolumnaBezWiersza(int kol,int wiersz)
    {//zwracam liste stringa o podanym numerze kolumny ,i bez wiersza o podanym numerze
        List<String> list=Kolumny.get(kol);
        
        if(wiersz<0 || wiersz >list.size()-1)//jesli numer wiersza nie istnieje na liscie to zwroc odrazu
        {return list;}
        
        list.remove(wiersz);        
        
        return list;
    }
    
    public List<String> WierszBezKolumny(int wiersz,int kol)
    {
        //zwraca liste stringow o podanym wierszu bez pola z kolumny o podanym numerze
        
        List<Integer> nr ;
        
        if(kol>0 && kol<ileKolumn)
        nr = KolumnyBezZnakow2.get(kol);//numery kolumn ktore biore
        else
        nr = KolumnyBezZnakow(kol);//jesli są z poza zakresu to policzy odzielnie
        
        
        //List<String> list = wiersz(wiersz);
        //list.remove(kol);
        List<String> list = Wiersze.get(wiersz);
        List<String> list2 = new ArrayList<>();
        
        for(int i=0;i<nr.size();i++)
        {
            list2.add(list.get(nr.get(i)));
        }
        
        return list2;
    }
    
    public List<String> WierszBezKolumny(List<String> wiersz,int kol)
    {
        //zwraca liste stringow po pobraniu listy i usuwa komurke z kolumny o podanym numerze
        //wiersz.remove(kol);
        List<Integer> nr ;
        
        if(kol>0 && kol<ileKolumn)
        nr = KolumnyBezZnakow2.get(kol);//numery kolumn ktore biore
        else
        nr = KolumnyBezZnakow(kol);//jesli są z poza zakresu to policzy odzielnie
        
        List<String> list2 = new ArrayList<>();
        
        for(int i=0;i<nr.size();i++)
        {
            list2.add(wiersz.get(nr.get(i)));
        }        
        
        return list2;
    }
    
    public List<String> WierszBezKolumny2(int wiersz,int kol)
    {
        //zwraca liste stringow o podanym wierszu bez pola z kolumny o podanym numerze
        // i bez wszystkich kolumn z znakami
        List<String> list = Wiersze.get(wiersz);    //wiersz niesformatowany
        List<String> list2 = new ArrayList<>();//wiersz sformatowany
        
        List<Integer> liczby ;
        
        if(kol>0 && kol<ileKolumn)
        liczby = KolumnyBezZnakow2.get(kol);//numery kolumn ktore biore
        else
        liczby = KolumnyBezZnakow(kol);//jesli są z poza zakresu to policzy odzielnie
            
        for(int i=0;i<liczby.size();i++)
        {
            list2.add(list.get(liczby.get(i)));
        }
                        
        return list2;
    }
    
    public List<String> WierszBezKolumny2(List<String> wiersz,int kol)
    {
        //zwraca liste stringow po pobraniu listy i usuwa komurke z kolumny o podanym numerze
        // i bez wszystkich kolumn z znakami
        List<String> list = wiersz;    //wiersz niesformatowany
        List<String> list2 = new ArrayList<>();//wiersz sformatowany
        
        List<Integer> liczby ;
        
        if(kol>0 && kol<ileKolumn)
        liczby = KolumnyBezZnakow2.get(kol);//numery kolumn ktore biore
        else
        liczby = KolumnyBezZnakow(kol);//jesli są z poza zakresu to policzy odzielnie
        
        for(int i=0;i<liczby.size();i++)
        {
            list2.add(list.get(liczby.get(i)));
        }
                        
        return list2;
    }
    
    
    public List<List<Double>> KolumnyWskazane(int p ,int BezWiersza)//metoda pobiera p by zignorować wskazano kolumne
    {
        //parametr BezWiersza oznacza ignorowany wiersz z klasy Kolumny
        //metoda zwraca liste na ktorej są kolumny w postaci list
        //kolumny te nie zawierają znaków ,wiec odrazu je przeformatowuje na double
        List<List<Double>> kolumny = new ArrayList<>();
        
        List<Integer> liczby ;
        
        if(p>0 && p<ileKolumn)
        liczby = KolumnyBezZnakow2.get(p);//numery kolumn ktore biore
        else
        liczby = KolumnyBezZnakow(p);//jesli są z poza zakresu to policzy odzielnie
        
        
        for(int i=0;i<liczby.size();i++)//przechodze kolumnach
        {
            List<String> pom = Kolumny.get(liczby.get(i));
            List<Double> pom2 = new ArrayList<>();
            
            
            try{//przechdoze po wierszach
            for(int j=0;j<pom.size();j++)//zamieniam String na Double
            if(j!=BezWiersza)//tutaj ignoruje wiersz
            pom2.add(Double.valueOf(pom.get(j)));
            }catch(Exception e){return null;}//w przypadku błedu zwracam null
            
            kolumny.add(pom2);
        }
        
        
        return kolumny;
    }
    
    public List<Integer> KolumnyBezZnakow2()
    {
        //metoda zwraca numery kolumn w których nie ma znaków
        List<Integer> liczby=new ArrayList<>();        
        
        for(int i=0;i<ileKolumn;i++)
        if(!sprawdz(i))//jeśli w kolumnie nie ma znaków
        {
            liczby.add(i);
        }
        
        
        return liczby;
    }
    
    
    public List<Integer> KolumnyBezZnakow(int p)
    {
        //metoda zwraca numery kolumn w których nie ma znaków
        //zmienna p oznacza ktora kolumne mam dodatkowo wyrzucić z listy
        //jesli np p<0 wuwczas nie bede ignorowac żadnej dodatkowej kolumny 
        List<Integer> liczby=new ArrayList<>();        
        
        for(int i=0;i<ileKolumn;i++)
        if(!sprawdz2(i))//jeśli w kolumnie nie ma znaków
        if(i!=p)//jeśli nie jest to ignorowana kolumna
        {
            liczby.add(i);
        }
        
        
        return liczby;
    }
    
    public List<Boolean> KolumnyBezZnakow = new ArrayList<>();//zawiera liste o kolumnach z znakami
    private List<List<Integer>> KolumnyBezZnakow2 = new ArrayList<>();//zwiera gotowe listy z wyrzuconym wierszem
    public List<List<String>> Kolumny = new ArrayList<>();//zawiera liste kolumn
    public List<List<String>> Wiersze = new ArrayList<>();//zawiera liste wierszy
    public int ileKolumn=0;
    public int ileWierszy=0;
    
    public List<List<List<String>>> WierszBezKolumny2 = new ArrayList<>();//gotowa lista wierszy bez wiersza i bez kolumny
    //gotowe listy kolumny bez znaków , bez kolumny i wiersza
    
    
    public void Aktualizacja()//aktualizuje liste kolumn z znakami
    {
        KolumnyBezZnakow.clear();
        Kolumny.clear();
        Wiersze.clear();
        KolumnyBezZnakow2.clear();
        ileKolumn=this.ileKolumn();
        ileWierszy=this.ileWierszy();
        WierszBezKolumny2.clear();
        
        for(int i=0;i<ileKolumn;i++)//aktualizuje liste zawierająco informacje o kolumnach z znakami
        KolumnyBezZnakow.add(sprawdz(i));
        
        for(int i=0;i<ileKolumn;i++)//zapisuje liste kolumn
        Kolumny.add(kolumna(i));
        
        for(int i=0;i<ileWierszy;i++)//zapisuje liste wierszy
        Wiersze.add(wiersz(i));        
        
        for(int i=0;i<ileKolumn;i++)//zapisuje liste kolumn z wyrzuconymi wierszami
        KolumnyBezZnakow2.add(KolumnyBezZnakow(i));  
        
        for(int i=0;i<ileWierszy;i++)
        {
        List<List<String>> pom = new ArrayList<>();
        for(int j=0;j<ileKolumn;j++)
        {
            pom.add(this.WierszBezKolumny2(i, j));
        }
        WierszBezKolumny2.add(pom);
        }
                
    }
    
    
    public boolean sprawdz2(int nr)//metoda sprawdza czy w danej kolumnie sa znaki
    {//zwraca false jesli nie ma znakowi true jesli sa
        
        return KolumnyBezZnakow.get(nr);        
    }
}
