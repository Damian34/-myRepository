
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
public class zzDrzewo {
        
    public class wieszcholek{
        /*
        klasa pomocnicza do rysowania drzewa , zawiera ona wieszcholek nadrzedny ,nazwijmy ja super-wieszcholek
        , liste wieszcholek podrzednych nazwyjmy je pod-wieszcholkami
        ,liste wykorzystanych kryterow 
        ,ceche nadrzedne
        ,ceche aktualne
        ,indyfikator liscia typu boolen
        ,liste intigerow jako liste uzytych kryteriow
        ,liste list stringow jako liste kolumn ,gdzie kolumna oznacza kryterium
        ,nr kolumny oznaczajco klase (?)
        */
        public zzDrzewo app=new zzDrzewo();//to tylko dla metody do liczenia miary entropi
        public wieszcholek super_wieszcholek = null;
        public List<wieszcholek> pod_wieszcholek = null;//
        
        //jeszcze nr kolumn i powinny byc urzyte kolumny
        public Integer nadrzedny_atrybut = null;
        public Integer aktualny_atrybut = null;///<<------------jakis blad bo sie gdzies gubi
        public List<Integer> urzyte_atrybuty = null;//
        
        //np jest kilka cech w kolumnie
        public String nadrzedna_cecha = null;///cecha wyrazona numer kolumny
        public String aktualna_cecha = null;///
        
        public List<List<String>> aktualne_kolumny = null;
        
        public List<List<String>> kolumny = null;
        public Integer klasa = null;
        public boolean lisc = false;//mozna dodac na koniec co jest lisciem typu string
        public String lisc2 = null;//to co jest lisciem
        
        
        wieszcholek(List<List<String>> kolumny,int klasa,List<String> numery)
        {
            urzyte_atrybuty = new ArrayList<>();
            aktualne_kolumny = new ArrayList<>();
            pod_wieszcholek = new ArrayList<>();
            //tutaj tworze korzen
            //liste kolumn podaje za kazdym razem to samo i nie modyfikuje jej
            //klasa tzn kolumna gdzie jest klasa
            //traktuje dane za kazdym razem jako string ,wiec moge w nich ukryc ewentualnie numery
            this.kolumny=kolumny;
            
            /////dodaje do this.kolumny 1 kolumne oznaczajac pozycje
            //ta kolumna bedzie przekazywana do pozostalych galezi wiec nie trzeba jej nadpisywac
            //nalezy jednak pomijac za kazdym razem ostatnio kolumne przy przeszukiwaniu tej listy
            //i listy aktualnych
            
            this.kolumny.add(numery);
            //////////
            
            
            this.klasa=klasa;
            this.super_wieszcholek=null;//to jest korzen wiec nie ma niczego wyrzej
            this.nadrzedna_cecha=null;//w korzeniu nie ma cechy ,bo nic tu nie dzielilem
            this.nadrzedny_atrybut=null;//nie ma nadrzednego atrybutu
            
            this.aktualna_cecha=null;//korzenia nie dzielilem ,wiec nie byl dzielony wzgledem zadnej cechy
            
            this.lisc = false;//to tez nie jest tego nie dotyczy (chyba ze to zbior 1 elementowy)
            
            ////////// przepisuje wszystkie wiersze do korzenia
            for(int i=0;i<this.kolumny.size();i++)
            {
                this.aktualne_kolumny.add(new ArrayList<>(this.kolumny.get(i)));
            }
            
            /////////
            //nalezy tutaj wyznaczyc pod_wieszcholki ,aktualna cecha,zwiekrzyc urzyte kryteria o aktualne
            start();
        }
        
        
        private void start()//to co licze na start dla korzenia
        {
            //zakladam ze dane będą już zdeskrytezowane ,choc to jest nie istotne
            /*
            najpierw licze entropie dla calego zbioru ,potem 
            licze entropie wzgledem kazdego atrybutu ,dzieloc go na powtarzajcych sie wartosciach
            dla kazdego atrybutu biore te listy entropi i sumuje uwzgledniajac wagi
            nastepnie licze przyrost informacji i wybieram atrybut o najwiekrzym przyroscie
            tworze nowe wieszcholki ,uwzgledniam liscie dla tych gdzie miara entropi wzgledem klas = 0
            */
            
            ///////////
            System.out.println("licze entropie dla atrybutu klasy: ");
            // w tej metodzie wybieram co jest atrybutem aktualnym
            double entropia_klasy = this.app.entropia(this.aktualne_kolumny.get(this.klasa)); 
            
            punkt4 entropie2_zwrot = entropie2();//////////////
            
            List<Double> entropie2 = entropie2_zwrot.wyniki;//trzeba jednak dodac ze w przypadku gdy gdzies jest 0 to jest lisc
            
            //licze entropie w kazdej kategori i kazdym atrybucie i potem je sumuje dla kazdego atrybutu
            System.out.println(entropia_klasy+" | "+entropie2);
            
            //licze roznice wzgledem entropi atrybutu klasy ,a sumami entropi dla pozostalych atrybutow co przed chwilo policzylem
            double max=0;
            int max_i=0;
            for(int i=0;i<this.aktualne_kolumny.size()-1;i++)
            if(i!=this.klasa)//null to kolumna klasy
            if(!urzywane(i))//i jesli i nie jest kolumna urzywaną
            {
                double p = (double)entropia_klasy - (double)entropie2.get(i);//tzn przyrost informacji
                if(p>max)
                {
                    max=p;
                    max_i=i;
                }
            }
            System.out.println("najwiekrzy przyrost informacji jest dla :\n"+max_i+". = "+max);
            
            //wiec max_i bedzie atrybutem w korzeniu
            
            this.aktualny_atrybut=max_i;
            this.urzyte_atrybuty.add(max_i);//wiec go potem nie bede urzywac
            
            /////////
            //to teraz przechodze po elementach i twoze nowe wieszcholki
            
            List<String> konary = this.app.skrot(this.aktualne_kolumny.get(max_i));
            
            System.out.println("konary ,tzn atrybut wgledem ktorych dzielilem wyzej: \n"+max_i+". "+konary);
            
            //miary entropi dla konarow
            List<Double> entropie_k = entropie2_zwrot.entropie.get(max_i);
            
            //dla kzdego konaru mam nowe kolumny
            List<List<List<String>>> nowe = entropie2_zwrot.list.get(max_i);
                
            System.out.println("entropie_k :"+entropie_k);
            
            //wyznaczam podwieszcholki dla wybranych konarow
            for(int i=0;i<konary.size();i++)
            {
                //robie nowy wieszcholek dla kazdego konaru
                wieszcholek w = new wieszcholek();
                //teraz potrzebne sa miary entropi dla kazdego konaru
                
                List<List<String>> nowe_kolumny = nowe.get(i);
                //tworze nowy wieszcholek
                w.super_wieszcholek=this;
                //w.kolumny = nowe_kolumny;
                //
                w.aktualna_cecha = konary.get(i);
                
                //
                w.kolumny=this.kolumny;
                w.aktualne_kolumny= nowe_kolumny;
                
                ///////////////////////////////////////////
                w.klasa=this.klasa;
                w.nadrzedna_cecha=this.aktualna_cecha;
                w.nadrzedny_atrybut=max_i;
                //w.urzyte_atrybuty=new ArrayList<>(this.urzyte_atrybuty); 
                w.urzyte_atrybuty=this.urzyte_atrybuty; 
                
                w.aktualny_atrybut=max_i;//to tylko przy przepisaniu
                                                                             
                ///
                w.pod_wieszcholek=this.pod_wieszcholek;
                
                //jak jest lisc to przepisuje jeszcze to 
                if(entropie_k.get(i)==0)//w przypadku gdy bedzie tu lisc
                {
                    System.out.println(i+". "+"lisc");
                    w.lisc=true;//zapisuje ze jest tam lisc
                    //jesli jest tam lisc ,to po przez obliczono entropie tzn ze jest tam tylko 1 powtarzajacy sie element
                    
                    /////////////////////nalezy to sprawdzic bo nie dziala(ok jakos dziala)
                    List<String> ls =this.app.skrot(nowe_kolumny.get(this.klasa));
                    if(ls.size()==1)
                    {w.lisc2=ls.get(0);}
                    else//tutaj nie zajdzie
                    {w.lisc2=zwrot(nowe_kolumny.get(this.klasa));}//ale wybieram element o najwiekszej liczbie wystopien
                    
                    System.out.println("lisc: "+nowe_kolumny.get(this.klasa));
                    System.out.println("lisc2: "+this.app.skrot(nowe_kolumny.get(this.klasa)));
                    System.out.println("lisc3: "+w.lisc2);
                    //////                                     
                }                
                
                this.pod_wieszcholek.add(w);//zapisuje podwieszcholki
            }
                        
            //wypisuje
            for(int i=0;i<this.pod_wieszcholek.size();i++)
            {
                System.out.print(this.pod_wieszcholek.get(i).lisc+" ,");
            }System.out.println();
            System.out.println("urzyte atrybuty: "+this.urzyte_atrybuty);
                        
        }
        
        private String zwrot(List<String> list)
        {
            //metoda szuka elementu ktory wystepuje najwiecej razy na liscie
            List<String> listp = this.app.skrot(list);
            List<Integer> listI = new ArrayList<>();
            
            for(int k=0;k<listp.size();k++)
            {
                int licz=0;
                for(int i=0;i<list.size();i++)
                if(list.get(i).equals(listp.get(k)))
                {
                    licz++;
                }
                listI.add(licz);
            }
            
            int max=0,max_i=0;
            for(int i=0;i<listI.size();i++)
            if(listI.get(i)>max)
            {
                max=listI.get(i);
                max_i=i;
            }
            String wynik = listp.get(max_i);
            
            return wynik;
        }
        
        private punkt4 entropie2()
        {
            List<Double> wyniki = new ArrayList<>();
            List<List<List<List<String>>>> dzielone = new ArrayList<>();
            List<List<Double>> wyniki2 = new ArrayList<>();
            /*
            tutaj licze entropie w sposob taki ze pierw biore i dziele kazdy dostepny atrybut wg jego kategori
            potem licze dla kazdej kategori entropie wzgledem przyporzatkowanych do niej klas
            potem biore to sume sume i dodaje do listy
            i tak dla kazdego atrybutu
            na koniec zwracam liste wynikiów dla kazdego atrybutu
            */
            for(int t=0;t<this.aktualne_kolumny.size()-1;t++)
            if(t!=this.klasa)
            {                
                //pierw wyznaczam liste kategori dla atrybutu
                //this.app.skrot(this.aktualne_kolumny.get(this.aktualny_atrybut));
                List<String> kategorie = this.app.skrot(this.aktualne_kolumny.get(t));
                //////////
                
                List<List<List<String>>> podzielone = dziel(kategorie,t);
                
                //teraz licze dla kazdej kategori entropie
                List<Double> list= new ArrayList<>();
                for(int i=0;i<podzielone.size();i++)
                {
                    //dla kazdego podzialu wybieram klase i lcize wzgledem jej entropie
                    List<String> podzielone2 = podzielone.get(i).get(this.klasa);
                    double entropia_klasy = this.app.entropia(podzielone2);
                    
                    list.add(entropia_klasy);
                }
                
                double sum = 0;
                for(int i=0;i<list.size();i++)
                {
                    double calosc = this.aktualne_kolumny.get(0).size();
                    double waga = podzielone.get(i).get(this.klasa).size() / calosc;
                    sum = sum + waga*list.get(i);
                }
                
                wyniki.add(sum);
                wyniki2.add(list);
                dzielone.add(podzielone);
                
            }
            
            //nie moge za bardzo odrazu wybierac atrybutu na korzen bo bedzie trzeba sprawdzic gdzie sa liscie
            return new punkt4(dzielone,wyniki2,wyniki);
        }
        
        private boolean urzywane(int nr)
        {
            //metoda sprawdza czy na liscie atrybutow urzywanych jest podany numer
            for(int i=0;i<this.urzyte_atrybuty.size();i++)
            if(this.urzyte_atrybuty.get(i)==nr)
            {return true;}
            return false;
        }
        
        private List<List<List<String>>> dziel(List<String> cecha,int atrybut)//moze dzialac nie poprawnie
        {
            //bedzie wszystko ok dopuki aktualne_kolumny to nie null ,lub nie jest puste ,lub nie ma listy roznej dlugosci
            List<List<List<String>>> list = new ArrayList<>();
            
            //dziele wzgledem cechy
            for(int i=0;i<cecha.size();i++)
            {
                List<List<String>> list2 = new ArrayList<>();
                
                for(int j=0;j<this.aktualne_kolumny.size();j++)//robie nowe listy
                {
                    List<String> pom = new ArrayList<>();
                    list2.add(pom);
                }
                //////////////???
                
                for(int j=0;j<this.aktualne_kolumny.get(0).size();j++)
                if(aktualne_kolumny.get(atrybut).get(j).equals(cecha.get(i)))//1 pozycja oznacza kolumne ,2 wiersz ~                    
                {
                    for(int j2=0;j2<this.aktualne_kolumny.size();j2++)
                    {
                        String pom = this.aktualne_kolumny.get(j2).get(j);//j kolumna ,j2 wiesz
                        list2.get(j2).add(pom);
                    }
                }
                list.add(list2);
            }
            
            return list;
        }       
                
        wieszcholek(wieszcholek wiesz)
        {
            //tutaj dziele wieszcholek na kilka podwieszcholkow
            //jesli wyjdzie na to ze to lisc to przepisuje wszystko  ,tzn nadpisue to klase
            
            if(wiesz.lisc)//jesli jest lisc to przepisuje wszsytko(nie dziala najlpeiej ta opcja ,lepiej z zewnątrz)
            {
                //jesli to lisc to przepisuje wszystko
                System.out.println("\ntutaj jest lisc\n");
                przepisz(wiesz);
            }
            else//jesli liczba atrybutow osiognie maksymalno ilosc to wybieram na lisc element o najwiekrzej 
            //liczbie powturzen , ta opcja dziala
            if(wiesz.urzyte_atrybuty.size()==wiesz.kolumny.size()-2)
            {//zakladam ze klasa bedzie zawsze podana
                System.out.println("przepelnienie");
                przepisz(wiesz);
                przepelnienie();
            }
            else//jesli nic nie zaszlo to dziele dalej (w tej opcji moze byc jakos blad ewentualnie)
            {
                dziel(wiesz);
            }
        }
        
        private void przepelnienie()
        {
            this.lisc=true;
            //pierw pobieram liste klas
            List<String> list = this.aktualne_kolumny.get(this.klasa);
            String liscp = zwrot(list);
            
            this.lisc2=liscp;
            System.out.println("klasa: "+this.klasa+" | element na klase: "+liscp);
        }
        
        private void dziel(wieszcholek wiesz)
        {
            /*
            przepisuje niektore elementy z super wieszcholka
            i licze kolejne wieszcholki
            */
            
            this.pod_wieszcholek = new ArrayList<>();
            
            this.kolumny=wiesz.kolumny;
            this.klasa=wiesz.klasa;
            this.super_wieszcholek=wiesz.super_wieszcholek;
            
            this.nadrzedna_cecha=wiesz.nadrzedna_cecha;
            this.nadrzedny_atrybut=wiesz.nadrzedny_atrybut;
            
            //this.app=wiesz.app;
            this.lisc = false;
                                    
            this.aktualna_cecha = wiesz.aktualna_cecha ;
            
            //ja przepisuej z tego wyrzej to reszte tez przepisuje
            this.aktualne_kolumny=wiesz.aktualne_kolumny;
            this.urzyte_atrybuty= new ArrayList<>(wiesz.urzyte_atrybuty);
            //////
                        
            //trzeba wyznaczyc aktualne kolumny na podstawie tego co mam
            
            for(int i=0;i<this.aktualne_kolumny.size();i++)
            System.out.println(this.aktualne_kolumny.get(i));
            
            System.out.println("start: ");
            
            System.out.println("aktualna_cecha: "+this.aktualna_cecha);
            System.out.println("aktualny_atrybut: "+this.aktualny_atrybut);
            System.out.println("app: "+this.app);
            System.out.println("lisc: "+this.lisc);
            System.out.println("nadrzedna_cecha: "+this.nadrzedna_cecha);
            System.out.println("nadrzedny_atrybut: "+this.nadrzedny_atrybut);
            System.out.println("super_wieszcholek: "+this.super_wieszcholek);
            System.out.println("pod_wieszcholek: "+this.pod_wieszcholek);
            System.out.println("urzyte_atrybuty: "+this.urzyte_atrybuty);
            System.out.println("klasa: "+this.klasa);
            
            System.out.println("kolumny: ");
            for(int i=0;i<this.kolumny.size();i++)
            System.out.println(this.kolumny.get(i));
            
            System.out.println("aktualne_kolumny: ");
            for(int i=0;i<this.aktualne_kolumny.size();i++)
            System.out.println(this.aktualne_kolumny.get(i));
            
            System.out.println("\n\n\nlicze galez: ");
            
            start();//i to na koniec powinno dzielic w ten sam sposob co przy wyborze korzenia
        }            
        
        private void przepisz(wieszcholek w)
        {
            //przepisuje wszystko z podanego wieszcholka na ten
            //zakladam ze wieszcholek ten ma wszystko co trzeba
            
            this.super_wieszcholek=w.super_wieszcholek;
            this.klasa=w.klasa;
            this.aktualne_kolumny = new ArrayList<>();//tu trzeba skopiowac
            for(int i=0;i<w.aktualne_kolumny.size();i++)
                this.aktualne_kolumny.add(new ArrayList<>(w.aktualne_kolumny.get(i)));
            this.urzyte_atrybuty=new ArrayList<>(w.urzyte_atrybuty);//to lepiej skopiowac
            this.lisc=w.lisc;
            this.lisc2=w.lisc2;
            
            this.aktualna_cecha=w.aktualna_cecha;
            this.aktualny_atrybut=w.aktualny_atrybut;
            this.kolumny = w.kolumny;//to mozna przepisac
            this.nadrzedna_cecha=w.nadrzedna_cecha;
            this.nadrzedny_atrybut=w.nadrzedny_atrybut;
            this.pod_wieszcholek=w.pod_wieszcholek;            
        }      
                
        wieszcholek(){}//konstruktor ktory nic nie zmienia
            
        public List<String> Numery()
        {
            //metoda zwraca numery wierszy w ktorych znajdują sie elementy z aktualnej listy
            //numery powinny byc podane na poczatku
            List<String> wynik = this.aktualne_kolumny.get(this.aktualne_kolumny.size()-1);
            
            return wynik;
        }
        
        public List<Integer> Numery2()
        {
            //ta sama metoda co wyzej tylko ze zwraca inty ,jesli ich nie bylo to null
            List<String> list = this.aktualne_kolumny.get(this.aktualne_kolumny.size()-1);
            List<Integer> wynik = new ArrayList<>();
            
            try{
                for(int i=0;i<list.size();i++)
                wynik.add(Integer.valueOf(list.get(i)));
            }catch(Exception e){return null;}//zwracam null jak bedzie blad            
            return wynik;
        }        
        
        public String klasa()
        {
            //metoda zwraca klase na liscie aktualne_kolumny ktora najczesciej wystepuje w danym momencie
            List<String> list = this.aktualne_kolumny.get(this.klasa);
            String klas = this.zwrot(list);
            
            return klas;
        }
        
    }
    
    public class punkt4{
    public List<List<List<List<String>>>> list = new ArrayList<>();
    public List<List<Double>> entropie = new ArrayList<>();   
    public List<Double> wyniki = new ArrayList<>();    
        punkt4(){}        
        punkt4(List<List<List<List<String>>>> list,List<List<Double>> entropie,List<Double> wyniki){
            this.list=list;
            this.entropie=entropie;
            this.wyniki=wyniki;
        }
    }
    
    public double entropia(List<String> list)
    {
        //metoda zwraca miare entropi sum((a/b)log(a/b)) czy jakos tak
        //pierw grupujac elementy wg powtarzajacych sie
        
        //pierw policzyc ile jest elementow nie powtarzajacych sie        
        List<String> list2=this.skrot(list);
        
        List<Integer> list3 = new ArrayList<>();
        
        for(int i=0;i<list2.size();i++)
        {
            int licz=0;
            for(int j=0;j<list.size();j++)
            if(list.get(j).equals(list2.get(i)))
            {
                licz++;
            }
            list3.add(licz);
        }
        
        double sum=0;
        for(int i=0;i<list3.size();i++)
        {
            //od czego tutaj zalezy podstawa logarytmu??
            double x = list3.get(i)/(double)list.size();
            double log = Math.log(x)/Math.log(2);//log przy podstawie 2 z liczby x
            
            /*if(!Double.isInfinite(log))
            System.out.println(log);*/
            
            if(!Double.isInfinite(log))//jesli nie jest to infiniti to dodaje
            {
                sum+=x*log;                
            }
        }
        sum= -sum;
                
        return sum;
    }
    
    public List<String> skrot(List<String> list)
    {
        //metoda wyrzuca powtorki
        List<String> list2 = new ArrayList<>();
        
        for(int i=0;i<list.size();i++)
        {
            boolean jest = false;
            for(int j=0;j<list2.size();j++)
            if(list.get(i).equals(list2.get(j)))
            {
                jest=true;
                break;
            }
            
            if(!jest)
            {
                list2.add(list.get(i));
            }
            
        }
        ////////nie wiem czy to gdzies nie przeszkadza
        Collections.sort(list2);    
        
        return list2;
    }
    
    public List<List<String>> dyskretyzuj(Tabela tab ,int podzial)
    {
        zDyskretny ds = new zDyskretny();
        List<List<String>> list = new ArrayList<>();
        //metoda dyskretyzuje
        for(int i=0;i<tab.ileKolumn();i++)
        {
            if(!tab.sprawdz(i))//jesli nie ma znakow w i-tej kolumnie
            {
                list.add(ds.dyskretny2(tab.Wartosci(i), podzial,0));
            }
            else
            {
                list.add(tab.kolumna(i));
            }
            System.out.println(tab.Naglow().get(i)+" "+list.get(i));
        }
        
        return list;
    }
    
    
    public List<List<wieszcholek>> drzewo(Tabela tab ,int klasa,int podzial)
    {
        return drzewo(tab,klasa,podzial,-1);
    }    
    
    public List<List<wieszcholek>> drzewo(Tabela tab ,int klasa,int podzial,int BezWiersza)
    {
        //wspolczynnik BezWiersza powodue ominiecie wiersza o tej wartosci
        
        List<List<wieszcholek>> wie = new ArrayList<>();//<<-- to moge zwrucic
                        
        List<List<String>> list = dyskretyzuj(tab,podzial);                
                
        //przechodze po liscie "list" i usuwam wybrany wiersz
        
        if(BezWiersza>=0 && BezWiersza<tab.ileWierszy())
        for(int i=0;i<list.size();i++)
        {
            list.get(i).remove(BezWiersza);
        }
        //////////////////tutaj zaczyna sie rysowanie drzewa
        //lista numerow to jako oznaczenia ,na potem dla metody leave-one-out
        List<String> numery = new ArrayList<>();//dodatkowa kolumna do oznaczania poczatkowej pozycji
        for(int i=0;i<tab.ileWierszy();i++)//kolumna ta bedzie dolaczona do listy "list" i bedzie na końcu
        if(i!=BezWiersza)//pomijam ten wiersz
        {
            numery.add(""+i);
        }
        ///////////////////////////////////////////////////////////////////
        wieszcholek a = new wieszcholek(list,klasa,numery);//licze 1 wieszcholek ,"korzen"
        System.out.println("\n");
        
        System.out.println(numery.size());
        
        for(int i=0;i<list.size();i++)
        System.out.println(list.get(i).size());
        
        //
        List<wieszcholek> a_2 = new ArrayList<>();
        a_2.add(a);
        wie.add(a_2);
        
        System.out.println("\nklasy decyzyjne :\n"+a.klasa+" | "+a.kolumny.get(a.klasa));        
        System.out.println("\nlicze nowe wieszcholki: ");
        
        int k=1;
        //for(int k=1;k<10;k++)
        while(true)//powinno sie nie wywalac w przeciwnym wypadku jest jakis blad
        {
            List<wieszcholek> list2 = wie.get(k-1);//biore przed ostatnio liste wieszcholkow
            
            ////warunek wyjsca to ze wszedzie sa ustawione liscie
            int z=0;
            for(int i=0;i<list2.size();i++)
            if(!list2.get(i).lisc)//sprawdzam liczbe nie zakonczonych konarow
            {
                z++;
            }
            //jesli liczba takich konarow = 0 to znaczy ze na wszystkich sa liscie i wychodze
            if(z==0){
                //na koniec wypisuje troche informacji
                System.out.println("koniec petli: ");
                for(int i=0;i<list2.size();i++)
                {
                    System.out.print(list2.get(i).lisc2+" ,");
                }
                System.out.println("\nliczebnosci zbiorow: ");
                for(int i=0;i<list2.size();i++)
                {
                    System.out.print(list2.get(i).aktualne_kolumny.get(0).size()+" ,");
                }
                System.out.println("\nmaksymalna liczba elementow to: "+tab.ileWierszy());
                
                System.out.println("\nzbiory ,po przez indexy: ");
                for(int i=0;i<list2.size();i++)
                {
                    List<String> p = list2.get(i).Numery();
                    List<String> q = new ArrayList<>();
                    //System.out.print(list2.get(i).Numery()+" ,");
                    for(int j=0;j<p.size();j++)
                    {
                        q.add(tab.wiersz(Integer.valueOf(p.get(j))).get(klasa));
                    }
                    System.out.print(q+" ,");
                }
                System.out.println("\nmaksymalna liczba elementow to: "+tab.ileWierszy());
                           
                System.out.println("zbiory po przez numery po dyskretyzacji: ");
                for(int i=0;i<list2.size();i++)
                {
                    System.out.print(list2.get(i).aktualne_kolumny.get(list2.get(i).aktualne_kolumny.size()-1)+" ,");
                }
                System.out.println();
                
                
                break;
            }
            ///
            
            List<wieszcholek> nowa = new ArrayList<>();
            for(int n=0;n<list2.size();n++)
            {
                //teraz dla kazdego wieszcholka licze nowe wieszcholki
                wieszcholek p = list2.get(n);
                //wyznaczone nizej podwieszcholki podczepiam do p ,tak aby utworzyc dynamiczne drzewo
                List<wieszcholek> podwieszcholki = new ArrayList<>();
                
                if(!p.lisc)//jesli nie ma lisci
                {
                for(int i=0;i<p.pod_wieszcholek.size();i++)
                {
                    wieszcholek q = new wieszcholek(p.pod_wieszcholek.get(i));//tworze nowy na podstawie tego
                    //tzn bedzie podzielony
                    nowa.add(q);
                    podwieszcholki.add(q);
                }
                p.pod_wieszcholek=podwieszcholki;
                }
                else
                {
                    nowa.add(p);
                }
            }
            
            wie.add(nowa);
            k++;
        }
        
        
        ///////////////////////tutaj troche wypisuje
        System.out.println("aktualna_cecha: ");
            
        for(int k2=0;k2<wie.size();k2++)
        {
            System.out.print(k2+".: ");
            for(int i=0;i<wie.get(k2).size();i++)
            {
                wieszcholek p = wie.get(k2).get(i);
                System.out.print(p.aktualna_cecha+" ,");
            }            
            System.out.println();            
        }
        
        System.out.println("\naktualny_atrybut: ");
            
        for(int k2=0;k2<wie.size();k2++)
        {
            System.out.print(k2+".: ");
            for(int i=0;i<wie.get(k2).size();i++)
            {
                wieszcholek p = wie.get(k2).get(i);
                System.out.print(p.aktualny_atrybut+" ,");
            }            
            System.out.println();            
        }        
        
        System.out.println("\nlisc: ");
            
        for(int k2=0;k2<wie.size();k2++)
        {
            System.out.print(k2+".: ");
            for(int i=0;i<wie.get(k2).size();i++)
            {
                wieszcholek p = wie.get(k2).get(i);
                System.out.print(p.lisc+" ,");
            }            
            System.out.println();            
        }
        
        System.out.println("\nzbiory: ");
            
        for(int k2=0;k2<wie.size();k2++)
        {
            System.out.print(k2+".: ");
            for(int i=0;i<wie.get(k2).size();i++)
            {
                wieszcholek p = wie.get(k2).get(i);
                System.out.print(p.urzyte_atrybuty+" ,");
            }            
            System.out.println();            
        }
        
        System.out.println("\nliczebnosc zbiorow: ");
            
        for(int k2=0;k2<wie.size();k2++)
        {
            System.out.print(k2+".: ");
            for(int i=0;i<wie.get(k2).size();i++)
            {
                wieszcholek p = wie.get(k2).get(i);
                System.out.print(p.aktualne_kolumny.get(0).size()+" ,");
            }            
            System.out.println();            
        }
        
        //teraz moge cos zwrucic ,np drzewo w postaci listy list
        
        return wie;
    }
    /*
    teraz potrzeba 2 metody
    1 taka co wyznacza klasy w sposob normalny
    ,i 2 co korzysta z metody leave-one-out
    */
    
    public List<String> drzewo_normalne(Tabela tab ,int klasa,int podzial)
    {
        //metoda zwraca liste stringow ,tzn klas ulozonych przez drzewo decyzyjne
        
        List<List<wieszcholek>>  list = this.drzewo(tab, klasa, podzial, -1);
        /*
        teraz nalezy pobrac te dane z tej listy ,najlepiej z ostatniego wiersza 
        ulozyc wzgledem ostatniej kolumny i pobrac na koniec tylko kolumne klasy
        */
        List<wieszcholek> wie = list.get(list.size()-1);
        
        List<List<String>> elementy = new ArrayList<>();//zapisuje wszsytkie elementy na 1 liscie
        
        //najpierw dodaje puste listy do listy elementy
        for(int i=0;i<tab.ileKolumn()+1;i++)
        elementy.add(new ArrayList<>());
            
        for(int k=0;k<wie.size();k++)
        {
            List<List<String>> m = wie.get(k).aktualne_kolumny;
            
            //teraz dodaje do elementow
            for(int i=0;i<m.get(0).size();i++)//przechodze po wierszach
            {
                //m.get(i).get(i);
                //elementy.get(i).add(m.get(i));
                for(int j=0;j<m.size();j++)//przechodze po kolumnach
                {
                    //powinienem pobierac wylosowane klasy
                    String p = m.get(j).get(i);
                    if(j==klasa)//jesli to jest to atrybut klasy to wybieram klase z liscia
                    {
                        p = wie.get(k).lisc2;
                    }
                    elementy.get(j).add(p);
                }
            }
        }
        
        System.out.println("\nsprawdzam polozenie elementow po uporzatkowaniu prez drzewo: ");
                       
        //teraz sortuje wzgledem ostatniej kolumny        
        int zmiana = 1;        
        while(zmiana > 0)
        {
            zmiana=0;
            for(int i=0;i<elementy.get(0).size()-1;i++)
            {
            int a = Integer.valueOf(elementy.get(elementy.size()-1).get(i));
            int b = Integer.valueOf(elementy.get(elementy.size()-1).get(i+1));            
            if(a>b)//sortuje wzgledem ostatniej kolumny
            {
                for(int j=0;j<elementy.size();j++)
                {
                    String p1 = elementy.get(j).get(i);
                    String p2 = elementy.get(j).get(i+1);
                    elementy.get(j).set(i+1, p1);
                    elementy.get(j).set(i, p2);                    
                }
                zmiana++;
            }
            }
        }
        
        for(int i=0;i<elementy.size();i++)
        System.out.println(elementy.get(i));
        
        System.out.println("\n");
        
        List<String> wynik = elementy.get(klasa);
                
        return wynik;
    }
    //leave_one_out
    
    public List<String> drzewo_ocena(Tabela tab ,int klasa,int podzial)
    {
        /*
        metoda wyrzuca 1 element ,wyznacza drzewo "dynamiczne"
        potem szukam na tym drzewie gdzie powinen sie znalesc wyrzucony element
        z szukanego miejsca pobieram klase ,
        liste tych klas zwracam
        */
        
        List<List<String>> dysk = dyskretyzuj(tab,podzial); 
        //int k=4;
        String lisc = "";
        List<String> klasy = new ArrayList<>();
        
        for(int k=0;k<tab.ileWierszy();k++)
        {
            lisc = "";
            List<List<wieszcholek>> gg = this.drzewo(tab, klasa, podzial, k);
            
            List<String> wiersz = new ArrayList<>();//trzeba zdeskretyzowac wszystko ,po czym wziosc ten wiersz
            
            for(int i=0;i<dysk.size();i++)
            wiersz.add(dysk.get(i).get(k));
                
                
            wieszcholek korzen = gg.get(0).get(0);
            /*
            trzeba zrobic w sumie tak ze dla kadzego "i"
            wyznaczam drzewo ,potem biore korzen i zaczynam przechodzic po drzewie do puki nie trafie na lisc
            jak trafie na lisc przechodzac wg kryteriow to sprawdzam jaką powinien ten element posiadac klase
            */
            for(int p=0;p<tab.ileKolumn()+2;p++)
            {
                if(korzen.lisc)
                {
                    lisc = korzen.lisc2;
                    break;
                }
                //dane sa poukladane dynamicznie wiec przechodze tak o :
                
                
                //jesli w konarach nie ma danej liczby to cos zrob ,np losuj ?
                boolean sprawdz = true;                
                int atrybut = korzen.aktualny_atrybut;                
                
                for(int j=0;j<korzen.pod_wieszcholek.size();j++)
                {
                
                //ostatni element moze nie miec cechy z powodu braku rozgalezienia
                if(wiersz.get(atrybut).equals(korzen.pod_wieszcholek.get(j).aktualna_cecha))
                {
                    korzen=korzen.pod_wieszcholek.get(j);
                    sprawdz = false;
                    break;
                }
                
                }
                if(sprawdz)//to w razie jak by sie trafil koniec sciezki
                {
                    System.out.println("koniec sciezki!");
                    //w tym przypadku losuje juz bo nic nie pasuje
                    /*
                    double h1 = korzen.pod_wieszcholek.size();
                    h1=h1-0.0000000000001;
                    int h2 = (int)Math.floor(Math.random()*h1);
                    
                    korzen=korzen.pod_wieszcholek.get(h2);//wylosowany
                    */
                    ////////////////////////
                    //jesli nie ma innej sciezki to wybieram klase z posrod tego co mam 
                    
                    lisc = korzen.klasa();
                    break;
                }
            }
            
        klasy.add(lisc);
        }
        //System.out.println(klasy);
        
        return klasy;
    }
    
    public List<String> ocena(Tabela tab ,int klasa,int podzial)
    {
        /*
        w metodzie porownuje przydzielone klasy/wyniki uzyskane przy urzyciu metody leave one out
        z orginalnymi i zwracam
        */
        List<String> wyniki = new ArrayList<>();
        
        List<String> aktualne = tab.kolumna(klasa);
        List<String> wylosowane = drzewo_ocena(tab,klasa,podzial);
        
        double z=0;
        
        for(int i=0;i<tab.ileWierszy();i++)
        if(wylosowane.get(i).equals(aktualne.get(i)))
        {
            wyniki.add(""+1);//jesli sa takie same
            z++;
        }
        else
        {
            wyniki.add(""+0);
        }
        
        System.out.println("ocena klasyfikacji przy użyciu drzewa decyzyjnego: "+z/(double)tab.ileWierszy());
        
        return wyniki;
    }
    
    public double ocena2(List<String> list)
    {
        double z=0;
        for(int i=0;i<list.size();i++)
        if(list.get(i).equals("1"))
        {
            z++;
        }
        double wynik=z/(double)list.size();
        return wynik;
    }
    
    public double ocena3(Tabela tab ,int klasa,int podzial)
    {
        List<String> list = ocena(tab ,klasa,podzial);
        
        double z=0;
        for(int i=0;i<list.size();i++)
        if(list.get(i).equals("1"))
        {
            z++;
        }
        double wynik=z/(double)list.size();
        return wynik;
    }
    
    public static void main(String [] args)
    {
        /*
        miara entropi to miara nie porzatku ,
        tzn. ze czym wartosc blizza 0 to tym lepszy porzadek ?
        */
        zzDrzewo z = new zzDrzewo();
        Tabela tab = new Tabela();
        
        //System.out.println(tab.kolumna(3));
        
        double e = z.entropia(tab.kolumna(0));
        
        //for(int i=0;i<tab.ileKolumn();i++)
        //System.out.println(z.entropia(tab.kolumna(i)));
        
        int klasa=4;
        
        //z.drzewo(tab, klasa,3);
        //z.drzewo_normalne(tab, klasa,3);
        //z.drzewo_ocena(tab, klasa,2);
        
        //System.out.println(z.ocena(tab, klasa,5));
        System.out.println("*ocena: "+z.ocena3(tab, klasa,5));
        
        /*
        oceny
        1=0.0 <<--- dla zbioru 1 elementowego daje 0,nie to ze zle klasyfikuje ,tylko chyba sie gubi gdzies
        2=0.7866666666666666
        3=0.9666666666666667
        4=0.92
        5=0.9266666666666666
        */
        
        //System.out.println(z.skrot(tab.kolumna(4)));
        
        
        //System.out.println(a);
        
        //////////////////////////
        /*System.out.println();
        //wynik powinien miec 0.94 ?
        double x =(double)5/14;
        double x2 =(double)9/14;
        
        System.out.println(x+" | "+x2);
        
        double log = Math.log(x)/Math.log(2);
        double log2 = Math.log(x2)/Math.log(2);
        
        System.out.println(x*log+x2*log2);
        */
    }
}
