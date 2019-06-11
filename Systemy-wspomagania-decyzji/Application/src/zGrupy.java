
import java.util.ArrayList;
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
public class zGrupy {
    Metryki Metryka;
    
    zGrupy()
    {
        this.Metryka=new Metryki();
    }
    
    zGrupy(Tabela kol)
    {
        this.Metryka=new Metryki(kol);
    }
    public List<Integer> losuj(int k,int liczba)
    {
        //metoda do losowania wierszy
        //metoda losuje k liczb całkowitych nieujemnych od 0 do podanej liczby liczba
        List<Integer> list=new ArrayList<>();
        
        if(k<=0){k=1;}
        
        if(k>=liczba)//jesli k jest wiekrze niz liczba elementów na liscie
        {
            for(int i=0;i<k;i++)
            list.add(i);
            return list;
        }
        ////////////////////////////////////////////////
        
        for(int i=0;i<k;i++)
        {
            //System.out.println(lista.get(i));
            //////
            while(true)//wychodze dopiero gdy znajdzie niepowtarzajacy sie element
            {
                int p = (int)Math.floor(Math.random()*liczba);
                int z=0;
                for(int j=0;j<list.size();j++)
                if(list.get(j)==p)//jesli element znajduje sie juz na liscie list
                {
                    z=1;
                }
                if(z==0)
                {
                    list.add(p);
                    break;
                }
            }
            //////
        }
        Collections.sort(list);
        /////////////////////////////////////
                
        return list;
    }
    
    
    public int najmniej(List<Integer> wybrane ,List<Integer> klasy)
    {
        //przechodze po liscie klasy ,zliczam elementy i wybieram z niej ten element który 
        //powturzył sie mniej razy na liscie wybrane
        
        //metoda pobiera liste integerow i liczbe elementow od 0 do k-1
        //i liste intigerow aktualnie wybranych klas
        //i zwraca taki element ktory powturzyl sie najmnie razy w klasie ,a jest na pierwszej liscie
        
        //najpierw najlepiej pozliczac elementy na liscie klasy
        List<Punkt> list =new ArrayList<>();//pod x dam zmienna ,a pod y ilosc wystapien
        
        for(int i=0;i<klasy.size();i++)
        {
            int z=0;
            for(int j=0;j<list.size();j++)//przechodze i sprawdzam czy element wystąpił
            if(klasy.get(i)==list.get(j).x)
            {
                z=1;
            }
            
            if(z==0)//jesli nie wystąpił
            {
                Punkt p = new Punkt(klasy.get(i),1);
                list.add(p);
            }
            else
            {
                //w przciwnym wypadku znajduje ten element i zwiekszam y o 1
                for(int j=0;j<list.size();j++)//przechodze i sprawdzam czy element wystąpił
                if(klasy.get(i)==list.get(j).x)
                {
                    list.get(j).y++;break;
                }
            }
        }
        
        
        List<Punkt> list2=new ArrayList<>();
        //pierw przepisuje elementy ktore sa na obu listach
        //jesli element z wybrane nie wystepue na liscie lista to uznaje ze byl 0 razy
        for(int i=0;i<wybrane.size();i++)
        {
            int z=0;
            for(int j=0;j<list.size();j++)
            if(wybrane.get(i)==list.get(j).x)
            {
                list2.add(list.get(j));                
                z=1;break;
            }
            if(z==0)
            {
                list2.add(new Punkt(wybrane.get(i),0));
            }
            
        }
        //System.out.println("list2 size : "+list2.size());
        
        //szukam elementu ktory wystapil najmniej razy na liscie
        int min=(int)list2.get(0).y;
        int nr=0;
        for(int i=1;i<list2.size();i++)
        if(min > list2.get(i).y)
        {
            min=(int)list2.get(i).y;
            nr=i;
            //System.out.println("list: "+list2.get(nr).x+"  "+list2.get(nr).y);
        }
        
        //zwracam element ktory wystopil najmniej razy
        int zwrot = (int)list2.get(nr).x;
        
        return zwrot;
    }
    
    
    private List<Double> ListaZMiarami(Tabela kol ,List<String> X0_lista, int klas, int typ,List<List<Double>> cov0)
    {
        return ListaZMiarami(kol ,-1 ,X0_lista,klas,typ,cov0);
    }
    
    private List<Double> ListaZMiarami(Tabela kol ,int X0 , int klas, int typ,List<List<Double>> cov0)
    {
        return ListaZMiarami(kol ,X0 ,null,klas,typ,cov0);
    }
    
    private List<Double> ListaZMiarami(Tabela kol ,int X0 ,List<String> X0_lista, int klas, int typ,List<List<Double>> cov0)
    {
        //klasa - oznacza którą kolumne ignorować
        List<Double> list = new ArrayList<>();
        
        
        if (typ < 0 || typ > 3) {
            return null;
        }//zwracam null jeśli typ jest nie poprawny
        
        if(typ==0)
        {
            for (int i = 0; i < kol.ileWierszy; i++)//Eukl Miejska Infinity Mah
            {
                //tutaj i oznacza Y0 ,czyli licze odleglosc miedzy wierszami o numerach X0 i Y0
                double p;
                if(X0_lista==null)
                p = Metryka.Eukl(kol, X0, i, klas);
                else
                p = Metryka.Eukl(kol, X0_lista, i, klas);
                list.add(p);
            }
        }
        
        if (typ == 1) 
        {
            for (int i = 0; i < kol.ileWierszy; i++) 
            {
                //tutaj i oznacza Y0 ,czyli licze odleglosc miedzy wierszami o numerach X0 i Y0
                double p;
                if(X0_lista==null)
                p = Metryka.Miejska(kol, X0, i, klas);
                else
                p = Metryka.Miejska(kol, X0_lista, i, klas);
                list.add(p);
            }
        }

        if (typ == 2) 
        {
            for (int i = 0; i < kol.ileWierszy; i++) 
            {
                //tutaj i oznacza Y0 ,czyli licze odleglosc miedzy wierszami o numerach X0 i Y0
                double p;
                if(X0_lista==null)
                p = Metryka.Infinity(kol, X0, i, klas);
                else
                p = Metryka.Infinity(kol, X0_lista, i, klas);
                list.add(p);
            }
        }
        
        if (typ == 3) 
        {
            List<List<Double>> cov = new ArrayList<>();
            if(cov0==null)//jesli nie podam macierzy covariancji to losuj nową
            cov = Metryka.Cov2(kol, klas ,-1);
            else
            cov = cov0;
            
            ////////////
            /*for(int i=0;i<cov.size();i++)
            System.out.println(cov.get(i));*/
            ////////////
            
            for (int i = 0; i < kol.ileWierszy; i++) 
            {            
                double p;
                if(X0_lista==null)    
                p = Metryka.Mah(kol, X0, i, klas, cov);
                else
                p = Metryka.Mah(kol, X0_lista, i, klas, cov); 
                list.add(p);
            }
        }
        
        
        return list;
    }
    
    public List<Integer> grupuj(Tabela kol ,int k,int typ, int klas,Integer iteracje,boolean decyzyjna)
    {
        //powinna byc jeszcze maksymalna ilosc iteracji w przypadku zapetlenia
        
        //poczatkowe punkty skupien wyznaczam przez losowanie sposrod istniejacych
        //typ - typ metryki
        //k - liczba skupisk ,spośród liczby wierszy
        
        List<List<Double>> cov=null;
        
        if(typ==3)
        {cov=this.Metryka.Cov2(kol, klas, -1);}//bedzie pomijac wiersz o nr -1 ktorego i tak nie ma
            
        if(k>kol.ileWierszy)
        k=kol.ileWierszy;
        
        if(decyzyjna)
        {
            k=kol.zmniejsz2(kol.kolumna(klas)).size();
        }
        
        List<Integer> l0 = losuj(k,kol.ileWierszy);
        /*List<Integer> l0 ;
        if(!decyzyjna)
        l0= losuj(k,kol.ileWierszy);//losuje numery wierszy
        else
        {
            l0 = losujKlas(kol, klas);
            k=kol.zmniejsz2(kol.kolumna(klas)).size();
        }*/
        //List<Integer> l1 = kol.KolumnyBezZnakow(klas);//numery kolumn bez kolumn z nakami i bez kolumny klasy
        
        List<List<String>> centra = new ArrayList<>();//lista skupien
        List<List<Double>> odl = new ArrayList<>();//lista odleglosci
        List<Integer> klasy = new ArrayList<>();//lista nowych klas decyzyjnych
        
        //wyznaczam początkowe punkty skupien
        System.out.println("poczatkowe punkty skupien: ");
        for(int i=0;i<l0.size();i++)
        {
            centra.add(new ArrayList<>(kol.Wiersze.get(l0.get(i))));
            System.out.println(centra.get(i));
        }
        System.out.println();
        
        System.out.println("poczatkowe odleglosc od srodkow: ");
        //wyznaczam początkową liste odległości od punktów skupień
        for(int i=0;i<l0.size();i++)
        {
            List<Double> list0 = ListaZMiarami(kol ,l0.get(i),klas,typ,cov);
            odl.add(list0);
            System.out.println("wiersz "+l0.get(i)+" | "+list0);
        }
        System.out.println();
        System.out.println("jest "+odl.size()+" list ,i kazda ma po "+odl.get(0).size()+" elementow");
        
        //przypisuje nowe klasy ,dla najblizszych elementow
        //jednoczesnie klasy sa numerami oznaczajacymi skupienie na liscie miar list0
        for(int i=0;i<odl.get(0).size();i++)
        {
            //System.out.println("odl "+i+": "+odl.get(i));
            int m=0;
            double min = odl.get(0).get(i);
            for(int j=1;j<odl.size();j++)//<<-- odl.size()==l0.size()
            {
                if(min>odl.get(j).get(i))
                {
                    min=odl.get(j).get(i);
                    m=j;
                    //this.najmniej(klasy, klasy);
                    //pom.add(j);
                }
            }
            
            //zabezpiecza to przed brakiem przypisania klasy ,przez co posypaly by sie sumyw nastepnej iteracji
            ///przechodze jeszcze raz i sprawdzam ile razy wystąpił minimalny element
            List<Integer> pom = new ArrayList<>();
            for(int j=0;j<odl.size();j++)
            if(min==odl.get(j).get(i))
            {
                pom.add(j);
            }
            
            //jesli element wystopil wiecej jak raz, tzn ze istnieje kilka wartosci minimalnych
            if(pom.size()>1)//to przypisuje element do klasy ktora wystapila mniej razy
            {
                m=najmniej(pom,klasy);
                klasy.add(m);
                //System.out.println("pom size > 1 ,tzn pom size="+pom.size());
            }
            else
            {
                klasy.add(m);
            }
            
        }
        System.out.println(klasy);
        System.out.println();
        List<Integer> klas4 = new ArrayList<>(klasy);
        Collections.sort(klas4);
        System.out.println(klas4);
        
        //teraz powinna być pętla wykonujaca tą operacje az do spelnienia warunku koncowego
        //na poczatek wyznaczyc nowe srednie i nowe skupiska wg zbiorow wyznaczonych za pomocą listy klasy
        //potem wyliczyc nowe odleglosci od nowych skupisk
        //i wyznaczam nowa liste klasy
        //potem spradzam warunek stopu ,tzn czy klasy sie zmienily ,czy nie ,jak nie to wychodze
        //jesli nie to przepisuje klasy
    int b=0;
    //while(true)//petla sie wkonuje az warunek stopu zostanie spelniony
    //for(;b<iteracje;b++)
    while(true)
    {
        //wychodze warunkowo
        if(iteracje!=null)
        if(b>iteracje)
        {break;}
        b++;
        
        //wyznaczam nowe srodki
        System.out.println("wyznaczam nowe skupiska: ");
        for(int i=0;i<centra.size();i++)
        {
            List<Integer> list = new ArrayList<>();
            for(int j=0;j<klasy.size();j++)//pobieram numery dla nowych klas
            if(klasy.get(j)==i)
            list.add(j);
            System.out.println("elementy: "+list);
            
            
            List<Double> sumy = new ArrayList<>();//lista sum ,a potem srednich
            for(int j=0;j<kol.ileKolumn;j++){sumy.add(0.0);}
            //if(!kol.KolumnyBezZnakow.get(j))//tzn jest to kolumna bez znaków
            for(int j=0;j<list.size();j++)//przechodze po wierszach dla nowe klasy
            {
                for(int j2=0;j2<kol.ileKolumn;j2++)//przechode po kolumnach bez znaków
                if(!kol.KolumnyBezZnakow.get(j2))
                {
                    double pom=Double.valueOf(kol.Kolumny.get(j2).get(list.get(j)));
                    sumy.set(j2, sumy.get(j2)+pom);
                }
            }
            //wyznaczam srednie
            for(int j=0;j<sumy.size();j++)
            sumy.set(j, sumy.get(j)/list.size());
                
            System.out.println("sumy: "+sumy);

            //zapisuje nowe środki
            for(int j=0;j<kol.ileKolumn;j++)
            if(!kol.KolumnyBezZnakow.get(j))
            {
                centra.get(i).set(j, sumy.get(j)+"");
            }
        }
        ////////////////////
        //sprawdzam czy nie pominelem jakiegos centrum 
        //tzn czy nie wybralem mniejszej liczby centrow w kolejnym losowaniu
        for(int i=0;i<centra.size();i++)
        {
        for(int j=0;j<centra.get(i).size();j++)
        if(centra.get(i).get(j).equals("NaN"))
        {
            System.out.println(i+" | "+centra.get(i));
            return null;
        }
        }
        //////////////////
        
        
        System.out.println();
        
        for(int i=0;i<centra.size();i++)
        System.out.println(centra.get(i));
        System.out.println();
        
        ///////////////////////////////////////////// <<---??
        //wyznaczam nowe odleglosci od skupisk
        System.out.println("wyznaczam nowe odleglosci od skupisk: ");
        
        odl.clear();
        for(int i=0;i<centra.size();i++)
        {
            List<Double> list0 = ListaZMiarami(kol ,centra.get(i),klas,typ,cov);
            
            odl.add(list0);
            
            System.out.println("centra: "+centra.get(i)+" | odleglosci: "+list0);
        }
        System.out.println();
        
        ///////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////
        ///wyznaczam nowa liste klasy
        List<Integer> klasy2 = new ArrayList<>();
        
        //przechodze miedzy miarami odleglosci dla pojedynczego elementu od centrum
        for(int i=0;i<odl.get(0).size();i++)
        {
            int m=0;
            double min = odl.get(0).get(i);
            for(int j=1;j<odl.size();j++)//sprawdzam do ktorego centrum mu najblizej
            {
                if(min>odl.get(j).get(i))
                {
                    min=odl.get(j).get(i);
                    m=j;
                }
            }
            //klasy2.add(m);
            
            //zabezpiecza to przed brakiem przypisania klasy ,przez co posypaly by sie sumyw nastepnej iteracji
            ///przechodze jeszcze raz i sprawdzam ile razy wystąpił minimalny element
            List<Integer> pom = new ArrayList<>();
            for(int j=0;j<odl.size();j++)
            if(min==odl.get(j).get(i))
            {
                pom.add(j);
            }
            
            //
            //jesli element wystopil wiecej jak raz, tzn ze istnieje kilka wartosci minimalnych
            if(pom.size()>1)//to przypisuje element do klasy ktora wystapila mniej razy
            {
                m=najmniej(pom,klasy2);
                klasy2.add(m);
                //System.out.println("pom size > 1 ,tzn pom size="+pom.size());
            }
            else
            {
                klasy2.add(m);
            }
            
            
        }
        System.out.println(klasy2);
        System.out.println();
        
        /*
        List<Integer> klas5 = new ArrayList<>(klasy2);
        Collections.sort(klas5);
        System.out.println(klas5);*/
        ////////////////////////////////////////
        ///sprawdzam warunek stopu ,tzn czy klasy sie zmienily ,czy nie ,jak nie to wychodze
        //jesli nie to przepisuje klasy
        int z=0;
        for(int i=0;i<klasy.size();i++)
        if(klasy.get(i)==klasy2.get(i))
        {
            z++;
        }
        System.out.println(z);
        
        if(z==klasy.size())//to znaczy ze sie nie zmienily
        {
            System.out.println("klasy nie zmienily sie ,po "+(b+1)+" iteracjach");
            klasy=klasy2;
            break;
        }
        else//w przeciwnym przypadku przepisuje klasy2 na klasy i kontynuje
        {
            System.out.println("klasy zmienily sie");
            klasy=klasy2;
        }
        b++;
        
        
    }
    
    if(iteracje!=null)
    if(b==iteracje)
    {
    System.out.println("klasy nie zostaly przydzielone po ustalonych: "+iteracje+" iteraciach");
    }
        
        
    return klasy;
    }
    
    public int decyzyjne(Tabela tab,int kol)
    {
        //metoda pobiera Tabele ,i nr kolumny ,i zwraca liczbe elementów ktore się nie powtarzają
        
        List<String> list = tab.kolumna(kol);
                
        int klasy=tab.zmniejsz(list).size();
        
        return klasy;
    }
    
    
    private class Punkt4
    {
        List<String> list = null;
        int nr = 0 ;
        
        Punkt4(List<String> list ,int nr)
        {
            this.nr=nr;
            this.list=list;
        }
        
        Punkt4(Punkt4 pkt)
        {
            this.nr=pkt.nr;
            this.list=new ArrayList<>(pkt.list);
        }
        
    }
    
    public List<Integer> ZamienKlasy(Tabela tab,int klasa,int metryka,Integer ile_petli)
    {
        //metoda pobiera obiekt Tablica ,nr kolumny klas ,typ metryki 
        
        //grupuj(Tabela kol ,int k,int typ, int klas,int iteracje,boolean decyzyjna)
        //zmienna ile_petli oznacza jak dlugo mam szukac klas aby ,i aby wyjsc przy padku nie spelnienia warunku
        /*
        w metodzie wyznaczam nowe klasy i wczytuje byle klasy
        potem wyznaczam dla kazdego wektora klas decyzyjnych centra
        i przyporzatkowuje do siebie najblizsze centra
        na koniec wyznaczam macierz pomylek
        poniewasz dla obu wektorow klas sa te same punkty to macierz pomylek powinna byc wyznaczona optymalnie
        */
        
        
        List<String> list2 = tab.kolumna(klasa);///poczatkowe klasy
        List<String> list3 = tab.zmniejsz2(new ArrayList<>(list2));//istniejace klasy decyzyjne
                
        /////////////////////////////////
        List<Integer> list = grupuj(tab,10,metryka,klasa,ile_petli,true);///otrzymane klasy
        //////////////////////////////////
            
        System.out.println("\n\n\n\n\n\n\n\n");
        
        System.out.println("poczatkowe przyporzatkowanie klas : "+list2);
        System.out.println("wylosowane przyporzatkowanie klas : "+list);
        System.out.println("istniejace klasy decyzyjne : "+list3);
        
        //poczatkowe klasy zamienione na int'y
        List<Integer> list4 = new ArrayList<>();//pierwotne klasy decyzyjne zamienione w inty
        
        for(int i=0;i<list2.size();i++)
        {
        for(int j=0;j<list3.size();j++)
        if(list2.get(i).equals(list3.get(j)))
        {
            list4.add(j);
        }
        }
        System.out.println("poczatkowe klasy zamienione na int: "+list4);
        
        
        ///teraz wyznaczam centra dla obu klas
        
        List<List<Double>> centra1 = new ArrayList<>();//centra dla klasy pierwotnej
        List<List<Double>> centra2 = new ArrayList<>();//centra dla klasy wylosowanej
        
        System.out.println("klas jest: "+list3.size());
        
        for(int i=0;i<list3.size();i++)
        {
            //List<List<Double>> k1 = new ArrayList<>();//wyznaczam wartosci dla klasy o nr i
            
            //wyznaczam centra dla klas pierwotnych
            List<Double> sum = new ArrayList<>();
            for(int j=0;j<tab.ileKolumn;j++)
            sum.add(0.0);
            int liczba=0;
            
            for(int j=0;j<tab.ileWierszy;j++)
            if(list4.get(j)==i)//oznacza klasy poczatkowe
            {
                for(int m=0;m<tab.ileKolumn;m++)
                if(!tab.KolumnyBezZnakow.get(m))//przechodze po kolumnach bez znakow
                {
                    double m2 = Double.valueOf(tab.Kolumny.get(m).get(j));
                    sum.set(m, m2+sum.get(m));
                }
                liczba++;
            }
            //wyznaczam srednie
            for(int j=0;j<sum.size();j++)
            {
                sum.set(j, sum.get(j)/liczba);
            }
            centra1.add(sum);
            
            //odrazu wyznaczam centra dla klas wylosowanych
            
            List<Double> sum2 = new ArrayList<>();
            for(int j=0;j<tab.ileKolumn;j++)
            sum2.add(0.0);
            int liczba2=0;
            
            for(int j=0;j<tab.ileWierszy;j++)
            if(list.get(j)==i)//oznacza klasy wylosowane
            {
                for(int m=0;m<tab.ileKolumn;m++)
                if(!tab.KolumnyBezZnakow.get(m))//przechodze po kolumnach bez znakow
                {
                    double m2 = Double.valueOf(tab.Kolumny.get(m).get(j));
                    sum2.set(m, m2+sum2.get(m));
                }
                liczba2++;
            }
            //wyznaczam srednie
            for(int j=0;j<sum2.size();j++)
            {
                sum2.set(j, sum2.get(j)/liczba2);
            }
            centra2.add(sum2);
            
        }
        
        System.out.println("centra1 klasy pierwonej :");
        for(int i=0;i<centra1.size();i++)
        System.out.println(i+"|  "+centra1.get(i));
        
        System.out.println("\ncentra2 klasy wylosowanej :");
        for(int i=0;i<centra2.size();i++)
        System.out.println(i+"|  "+centra2.get(i));
        
        List<Punkt4> centra1p = new ArrayList<>();//centra dla klasy pierwotnej przypiszne
        List<Punkt4> centra2p = new ArrayList<>();//centra dla klasy wylosowanej przypisane
        
        List<Punkt> punkty = new ArrayList<>();//lista zwiera przypisania z klasy poczatkowej na wylosowane
        
        
        for(int i=0;i<centra1.size();i++)
        {
        List<String> c1 = new ArrayList<>();
        List<String> c2 = new ArrayList<>();
        
        for(int j=0;j<centra1.get(i).size();j++)
        {
            c1.add(centra1.get(i).get(j)+"");
            c2.add(centra2.get(i).get(j)+"");
        }
        
        ///przchowuje liste i numer klasy
        centra1p.add(new Punkt4(c1,i));
        centra2p.add(new Punkt4(c2,i));
        }
        ////////////////////////
        
        
        /////////////////////////////
        List<List<Double>> cov = null;
        if(metryka==3)
        cov = Metryka.Cov2(tab, klasa, -1);
         
        System.out.println();
        for(int i=0;i<centra1p.size();i++)
        //while(centra1p.size()>0)
        {
            //int i =0;
            /*for(int j=0;j<centra2p.size();j++)
            {System.out.print(centra2p.get(j).nr+" , ");}*/
            
            
            List<Double> nowe = new ArrayList<>();
            //int kl1=Integer.valueOf(centra1p.get(i).get(centra1p.get(i).size()-1));
            //int kl2=Integer.valueOf(centra2p.get(i).get(centra1p.get(i).size()-1));
            
            for(int j=0;j<centra2p.size();j++)
            {
               //double w = g.Metryka.Miara(tab, X0, Y0, 2,3,cov);
               //Miara(Tabela kol ,List<String> X0_lista,List<String> Y0_lista, int klas, int typ,List<List<Double>> cov0)
               double w = Metryka.Miara(tab, centra1p.get(i).list, centra2p.get(j).list, klasa, metryka, cov);
               nowe.add(w);
            }
            //teraz na liscie nowe szukam elementow najmniejszych
            
            double min =nowe.get(0);
            int nr=0;
            for(int j=1;j<nowe.size();j++)
            if(nowe.get(j)<min)
            {
                min=nowe.get(j);
                nr=j;
            }
            //System.out.println("\ncentra2p: "+centra2p.get(nr).list+"\ni: "+i+" nr:"+nr+" ,nr z centra2p: "+centra2p.get(nr).nr+" , min odl: "+min+"\n");
            
            punkty.add(new Punkt(centra1p.get(i).nr,centra2p.get(nr).nr));
            //centra1p.remove(i);
            centra2p.remove(nr);
            
            //punkty.add(new Punkt(i,nr));
        }
        
        
        for(int i=0;i<punkty.size();i++)
        {
            System.out.println(i+"|  "+punkty.get(i).x+"  "+punkty.get(i).y);
        }
        System.out.println("lista poczatkowa: "+list4+"\nlista losowana: "+list);//sa to kolejno klasy poczatkowe i wylosowwane
        
        
        ////////////////////////////////////////////
        //przechodze i przestwiam wg wylosowanej listy punktow i zamieniam miejscami co trzeba
        //zmieniam tylko wylosowwane klasy
        List<Integer> losowane=new ArrayList<>();//list);//wylosowane
            
        for(int i=0;i<list.size();i++)
        {
            losowane.add(-1);
        }
        
        for(int i=0;i<punkty.size();i++)
        {
            for(int j=0;j<list.size();j++)
            if(list.get(j)==punkty.get(i).y)
            {
                //list.set(j,(int)punkty.get(i).y);
                losowane.set(j, (int)punkty.get(i).x);
            }
        }
        list=losowane;//i przepisuje na koniec
        
        System.out.println("\nlist po zamianie losowanej: "+list);
        
        
        
        
        
        ////////////////////////////////
        /////// teraz wyznaczam macierz pomylek
        
        int[][] macierz_pomylek=new int[punkty.size()][punkty.size()];
        
        for(int i=0;i<list3.size();i++)
        for(int j=0;j<list3.size();j++)
        macierz_pomylek[i][j]=0;
        
        
        for(int i=0;i<list4.size();i++)//poczatkowe klasy
        {
        //list to wybrane klasy
        macierz_pomylek[list4.get(i)][list.get(i)]++;
        
        }
        
        System.out.println("\nna koniec: ");
        for(int i=0;i<macierz_pomylek.length;i++)
        {
        for(int j=0;j<macierz_pomylek.length;j++)
        {
            System.out.print(macierz_pomylek[i][j]+" , ");
        }System.out.println();
        }
        
        ///teraz powinienem wyznaczy ocene ,a w wyniku zwrucic 3 rzeczy ,liste wybranych klas,macierz pomylek
        //i blad klasyfikacji
        int sum=0;
        
        for(int i=0;i<macierz_pomylek.length;i++)
        sum=sum+macierz_pomylek[i][i];
        
        System.out.println("\nliczba poprawnie zaklasyfikowanych obiektow: "+sum+"\njakosc klasyfikacji: "+(double)sum/(double)tab.ileWierszy);
        
        
        return list;
    }
    
    public List<String> ZamienKlasy2(Tabela tab,int klasa,int metryka,Integer ile_petli)
    {
        //metoda zamienia otrzymane klasy w postaci intow na takie jakie sa pierwotnie
        List<Integer> list = ZamienKlasy(tab,klasa,metryka,ile_petli);
        
        List<String> list2 = tab.kolumna(klasa);///poczatkowe klasy
        List<String> list3 = tab.zmniejsz2(new ArrayList<>(list2));//istniejace klasy decyzyjne
         
        
        //w wyniku zwracam nazwy klas jakie byly na poczatkowo w klasie pierwotnej
        List<String> wynik=new ArrayList<>();
        
        for(int i=0;i<list.size();i++)
        {
        for(int j=0;j<list3.size();j++)
        if(list.get(i)==j)//dodaje elementy do listy wg nazw naglowkow
        {
            wynik.add(list3.get(j));
            break;
        }
        }        
        
        return wynik;
    }
    
    
    private class Klaster
    {
        List<String> centrum = new ArrayList<>();
        List<Punkt4> punkty = new ArrayList<>();
        double wysokosc=0;
        Klaster kl1=null;
        Klaster kl2=null;
        
        Klaster(Tabela tab,List<Punkt4> punkty)
        {
            //trzeba zapametywac punkt i nr wiersza by latwiej tym operowac
            //to sa klastry startowe
            //na podstawie tych punktow wyznaczam centrum
            //na poczatku wysokosc jest = 0 ,po loczeniu klastrow wysokosc to odleglosc miedzy 2 ostatnimi centrami
            
            this.centr(tab, punkty);//licze centrum
            this.wysokosc=0;//poniewarz nie locze zadnych punktow
            this.punkty=punkty;
            
        }
        
        private void centr(Tabela tab,List<Punkt4> punkty)
        {
            //wyznaczam nowe centrum z otrzymanej listy punktów
            this.centrum=new ArrayList<>();
            
            List<Double> sum =new ArrayList<>();
            for(int j=0;j<tab.ileKolumn;j++){sum.add(0.0);} 
            
            
            for(int i=0;i<punkty.size();i++)
            {            
            for(int j=0;j<tab.ileKolumn;j++)
            if(!tab.KolumnyBezZnakow.get(j))
            {
                double d=Double.valueOf(punkty.get(i).list.get(j));
                sum.set(j, sum.get(j)+d);
            }               
            }
            for(int i=0;i<sum.size();i++)
            sum.set(i, sum.get(i)/punkty.size());
            
            for(int i=0;i<sum.size();i++)
            this.centrum.add(sum.get(i)+"");
            
        }
        
        public List<Integer> INT()
        {
            //metoda zwraca liste intów
            List<Integer> nowy= new ArrayList<>();
            for(int i=0;i<punkty.size();i++)
            nowy.add(punkty.get(i).nr);
            return nowy;
        }
        
        public List<List<String>> STR()
        {
            //metoda zwraca liste stringow , liste punktow
            List<List<String>> nowy= new ArrayList<>();
            for(int i=0;i<punkty.size();i++)
            nowy.add(new ArrayList<>(punkty.get(i).list));
            return nowy;
        }
        
        Klaster(Tabela tab,Klaster klas1 ,Klaster klas2,int klasa,int metryka,List<List<Double>> cov,Metryki met)
        {
            //tutaj robie nowy klaster na podstawie wczesniejszych
            //na poczatek pobieram punkty z obu i robie z nich 1 zestaw
            List<String> pkty=new ArrayList<>();
            
            for(int i=0;i<klas1.punkty.size();i++)
            pkty.add(klas1.punkty.get(i).nr+"");
            
            for(int i=0;i<klas2.punkty.size();i++)
            pkty.add(klas2.punkty.get(i).nr+"");
            
            tab.zmniejsz(pkty);
            List<Integer> nowe = new ArrayList<>();
            for(int i=0;i<pkty.size();i++)
            nowe.add(Integer.valueOf(pkty.get(i)));
            
            //sortuje te punkty
            int zmiana = 1;
            while(zmiana>0)
            {
                zmiana=0;
                for(int i=0;i<nowe.size()-1;i++)
                if(nowe.get(i+1)<nowe.get(i))
                {
                    int pp=nowe.get(i+1);
                    nowe.set(i+1, nowe.get(i));
                    nowe.set(i, pp);
                    zmiana++;
                }
            }
            //i robie nowa liste punktow
            this.punkty=new ArrayList<>();
            for(int i=0;i<nowe.size();i++)
            {
                List<String> pk0 = new ArrayList<>(tab.Wiersze.get(nowe.get(i)));
                Punkt4 pk = new Punkt4(pk0,nowe.get(i));
                this.punkty.add(pk);
            }
            //teraz licze nowe centrum
            this.centr(tab, this.punkty);
            //teraz licze odleglosc miedzy centrami poprzednich punktow
            
            Double odl = met.Miara(tab, new ArrayList<>(klas1.centrum), new ArrayList<>(klas2.centrum), klasa, metryka, cov);
            
            this.wysokosc=odl;
            this.kl1=klas1;
            this.kl2=klas2;
        }
        
        Klaster(Klaster klas)
        {
            //tworze kopie klastru
            this.centrum=new ArrayList<>(klas.centrum);
            this.kl1=klas;
            this.kl2=null;
            this.wysokosc=klas.wysokosc;
            this.punkty=new ArrayList<>();
            
            for(int i=0;i<klas.punkty.size();i++)
            this.punkty.add(new Punkt4(klas.punkty.get(i)));
        }
        
    }
    
    public int klasteryzacja(Tabela tab,int klasa,int metryka,int wynik) 
    {
        //wyznaczam klastry najpierw robie liste klastrow poczatkowa
        //bede przechowywac wszystkie wyniki tzn wysztkie listy kastrow
        List<List<Klaster>> list = new ArrayList<>();
        
        
        List<Klaster> listk =  new ArrayList<>();//na poczatek kazda lista zwiera po 1 punkcie
        //na poczatek robie liste klastrow poczatkowa
        for(int i=0;i<tab.ileWierszy;i++)
        {
            List<Punkt4> nowy = new ArrayList<>();
            nowy.add(new Punkt4(tab.Wiersze.get(i),i));
            listk.add(new Klaster(tab,nowy));
        }
        list.add(listk);
        
        
        /////////////////////////////
        List<List<Double>> cov = null;
        if(metryka==3)
        cov = Metryka.Cov2(tab, klasa, -1);
        
        //teraz przechodze do momentu az zostanie 1 element na wczesniejszej liscie
        
        int p=1;
        //for(p<20;)
        while(true)
        {
            if(list.get(p-1).size()==1){break;}
            //pobieram wczesniejsza liste klastrow
            List<Klaster> listp = list.get(p-1);
            List<Klaster> nowy=new ArrayList<>();//nowa lista klastrow do zapisania
            
            //lista zawiera nr klastra ,bede z niej kasowac
            List<Integer> listc = new ArrayList<>();
            
            for(int i=0;i<listp.size();i++)
            listc.add(i);
            
            
            ////////////////////////////////////////////////////////////
            ///////miary zapisze do tablicy i policze raz
            double[][] odl = new double[listp.size()][listp.size()];
            for(int i=0;i<listc.size();i++)
            {
            for(int j=0;j<listc.size();j++)
            {
                double licz = this.Metryka.Miara(tab, listp.get(i).centrum, listp.get(j).centrum, klasa, metryka, cov);
                odl[i][j]=licz;
            }
            }
            
            while(true)
            {
            int ni=-1,ni2=-1;
            if(listc.size()==1)
            {
                //tzn zostal 1 element ,to go przepisuje
                int nrl = listc.get(0);
                Klaster kll=listp.get(nrl);
                nowy.add(kll);
                break;
            }
            if(listc.size()==0)//jesli lista listc jest pusta to wychodze
            {break;}
            ///
            /////////////////////////////////////////////////////
            //tutaj sprawdzam jakie numery pozostaly nie polaczone na liscie listc i je lacze ,jesli zostaly min 2
            for(int i=0;i<listc.size();i++)
            {
                double min= Double.MAX_VALUE;//tak szuka 2 najblizsze elementy
                for(int i2=0;i2<listc.size();i2++)
                if(i!=i2)//nie locze tego samego punktu z sobą
                {
                    //pobieram numery z listc
                    int nr1 = listc.get(i);
                    int nr2 = listc.get(i2);
                    //dolgelosci odczytuje tylko z macierzy ,a nr sa przechowywane w listc
                    double licz = odl[nr1][nr2];
                    
                    if(licz<min && licz!=0)
                    {
                        min=licz;
                        ni=i;ni2=i2;
                    }
                }
            }
            //////////////////////
            
            //potem wychodze locze te 2 punkty i usuwam je z listy listc
            //ni i ni2 to tylko aktualne pozycje ,poprawne sa na liscie listc ktory oznacza nr z listy klastrow
            if(ni!=-1 && ni2!=-1)
            {
                int nr1=listc.get(ni);
                int nr2=listc.get(ni2);
                //robie nowy punkt na podstawie 2 poprzednich najbliższych
                Klaster nn = new Klaster(tab,listp.get(nr1),listp.get(nr2),klasa,metryka,cov,this.Metryka);
                nowy.add(nn);//dodaje nowy klaster
                if(ni>ni2)//i kasuje poprzednie zaleznie od kolejnosci na liscie
                {
                    listc.remove(ni);
                    listc.remove(ni2);
                }
                else
                {
                    listc.remove(ni2);
                    listc.remove(ni);
                }
            }
            }
            
            p++;
            list.add(nowy);
        }
        /////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////tutaj wyznaczam optymalna liczb podzialu zbioru
        
        List<Integer> zbiory = new ArrayList<>();
        
        for(int i=0;i<list.size();i++)
        {
            for(int j=0;j<list.get(i).size();j++)
            {
                System.out.print(list.get(i).get(j).INT());
            }
            System.out.println();
            zbiory.add(list.get(i).size());
        }
        
        ////////////////////////////////////////
        //////////////////////////
        //sprawdzam w ktorym kroku srednia odleglosci miedzy wybranymi 2 centrami jest najwiekrza
        //mowiac odleglosci mam na mysli odleglosci miedzy kolejnymi centrami ,pomijam oczywiscie 1 krok
        //gdzie elementy maja odleglosc = 0
        
        //licze srednie odleglosci miedzy centrami dla karzdego kroku
        List<Double> zbior_sr=new ArrayList<>();
        zbior_sr.add(0.0);//dodaje 0 bo dla 1 elementu i tak by bylo 0 ,wic mimo wszystko nalezy to pominac
        for(int i=1;i<list.size();i++)
        {
            double pom=0;
            for(int j=0;j<list.get(i).size();j++)
            {
                pom+=list.get(i).get(j).wysokosc;
            }
            double sr=pom/list.get(i).size();
            zbior_sr.add(sr);
        }
        
        //wyznaczam maksymalna srednio odleglosc wzgledem srednich wyliczonych wyrzej
        double max=0;
        int nr=-1;
        for(int i=1;i<zbior_sr.size();i++)
        if(zbior_sr.get(i)>max)
        {
            max=zbior_sr.get(i);
            nr=i;
        }
        //wybieram punkt ktory ma odleglosc miedzy 2 wczesniejszymi punktami wiec nr2-1 tzn wczesniejszy podzial
        int opt_sr = list.get(nr-1).size();
        
        //////////////////jesli rozpietosc na liście zbior_sr < 1 ,bez 1 elemntu jakim jest 0 ,to zbiór bedzie 1 elementowy
        List<Double> pp1=new ArrayList<>(zbior_sr);
        pp1.remove(0);
        
        if(this.rozpietosc(pp1)<1)
        {
            opt_sr=1;
        }
        ///////////////////
        
        
        ////////////////////////
        //wyznaczam mediane dla kazdego wyniku sposrod odleglosci
        List<Double> zbrior_med=new ArrayList<>();
        zbrior_med.add(0.0);
        
        for(int i=1;i<list.size();i++)
        {
            List<Double> pom=new ArrayList<>();
            for(int j=0;j<list.get(i).size();j++)
            {
                pom.add(list.get(i).get(j).wysokosc);
            }
            double pom2=this.Mediana(pom);
            zbrior_med.add(pom2);
        }
        
        //maksymalna liczba zbiorow dla wyliczonych median
        int nr2=0;
        double max2=0;
        for(int i=1;i<zbrior_med.size();i++)
        if(zbrior_med.get(i)>max2)
        {
            max2=zbrior_med.get(i);
            nr2=i;
        }
        
        //wybieram punkt ktory ma odleglosc miedzy 2 wczesniejszymi punktami wiec nr2-1 tzn wczesniejszy podzial
        int opt_med = list.get(nr2-1).size();
        
        //////////////////jesli rozpietosc na liście zbrior_med < 1 ,bez 1 elemntu jakim jest 0 ,to zbiór bedzie 1 elementowy
        List<Double> pp2=new ArrayList<>(zbrior_med);
        pp2.remove(0);
        
        if(this.rozpietosc(pp2)<1)
        {
            opt_med=1;
        }
        ///////////////////
        
        System.out.println("\nzbiory maja kolejno liczebnosc: "+zbiory);
        
        System.out.println("gdzie srednie odleglosci wynosza kolejno: "+zbior_sr);
        System.out.println("optymalna liczebnosc zbiorow dla srenich: "+opt_sr);
        
        System.out.println("lista median sposrod odleglosci miedzy kolejnymi centrami dla kazdego kroku: "+zbrior_med);
        System.out.println("optymalna liczebnosc zbiorow dla median: "+opt_med);
        
        if(wynik==0)//jesli 0 to zwracam optymalna liczbe klas wzgledem median dla kazdego kroku
        return opt_med;
        if(wynik==1)//jesli 1 to zwracam optymalna liczbe klas wzgledem srednich dla kazdego kroku
        return opt_sr;
        
        return 0;
    }
    
    
    public double Mediana(List<Double> list)
    {
        //pobiera liste double i zwraca mediane
        //pierw sortuje ta liste
        int zmiana = 1;
        while(zmiana>0)
        {
        zmiana=0;
        for(int i=0;i<list.size()-1;i++)
        if(list.get(i+1)<list.get(i))
        {
            double pom=list.get(i+1);
            list.set(i+1,list.get(i));
            list.set(i, pom);
            zmiana++;
        }
        }
        
        double pom2;
        if(list.size()%2==0)
        {
            int nr = (list.size()/2)-1;
            int nr2 = (list.size()/2);
            pom2=(list.get(nr)+list.get(nr2))/2;
        }
        else
        {
            int nr=(list.size()/2);
            pom2=list.get(nr);
        }
        
        
        return pom2;
    }
    
    public double rozpietosc(List<Double> list)
    {
        double min=Double.MAX_VALUE,max=0;
        
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)<min){min=list.get(i);}
            if(list.get(i)>max){max=list.get(i);}
        }
        
        double wynik=max-min;
        
        return wynik;
    }
    
    
    
    public double odchlenie(List<Double> list)
    {
        double sr=0;
        
        for(int i=0;i<list.size();i++)
        {
            sr+=list.get(i);
        }
        sr=(sr/(double)list.size());//srednia
        
        double war=0;
        for(int i=0;i<list.size();i++)
        {
            double pom=(list.get(i)-sr)*(list.get(i)-sr);
            war=war+pom;            
        }
        war=(war/(double)list.size());//wariancja
        
        double odchylenie = Math.sqrt(war);//odchylenie
        
        return odchylenie;
    }
    
    public double srednia(List<Double> list)
    {
        double sr=0;
        
        for(int i=0;i<list.size();i++)
        {
            sr+=list.get(i);
        }
        sr=(sr/(double)list.size());
        
        return sr;
    }
    
    public double srodek(List<Double> list)
    {
        //wyznaczam środek przedzialu
        double min=Double.MAX_VALUE,max=0;
        
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)<min){min=list.get(i);}
            if(list.get(i)>max){max=list.get(i);}
        }
        
        double wynik=(min+max)/2;        
        
        return wynik;
    }
    
    
    
    /*
    blad w metodzi k srednich jest taki ze najk wybieram elementy i istnieje kilka elementow ktore zbytnio odbiegaja
    od zbioru danych i wybiore kilka takich elementow jako srodki
    
    */    
    public static void main(String [] args)
    {
        Tabela tab = new Tabela();
        //tab.naglowki=false;
        zGrupy g=new zGrupy(tab);
        
        int klas=278;
        
        g.klasteryzacja(tab, 2, 0,0);
        
        //grupuj(Tabela kol ,int k,int typ, int klas,Integer iteracje,boolean decyzyjna);
        
        //g.grupuj(tab, 2, 0, 278, null, false);
        
        //g.optymalnie(tab, 0, 2, null);
        
        ///g.ZamienKlasy(tab, 2, 0,null);
        
        
        /*
        List<String> X0= new ArrayList<>();
        List<String> Y0= new ArrayList<>();
        for(int i=0;i<tab.ileKolumn;i++)
        {
            X0.add(""+0);
            Y0.add(""+1);
        }
        
        List<List<Double>> cov =  g.Metryka.Cov2(tab,2,-1);
        
        //double w = g.Metryka.Miejska(tab, X0, Y0, 2);
        //double w = g.Metryka.Mah(tab, X0, Y0, 2,cov);
        double w = g.Metryka.Miara(tab, X0, Y0, 2,3,cov);
        
        System.out.println("wynik: "+w);*/
        
    }
}
