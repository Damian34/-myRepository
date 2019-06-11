
import java.util.ArrayList;
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
public class Tryby {
    //klasa zawiera tryby oceny i klasyfikacji metodą k-nn
    Metryki Metryka;
    
    public Tryby()
    {
        this.Metryka=new Metryki();
    }
    
    //zapamętuje miary dla trybu oceny ,a w trybie klasyfikacji działam przez wyrzucenie elmentu
    private List<List<Double>> miary = new ArrayList<>();
    
    public void uaktualnij_miary(Tabela kol, int klas, int typ)
    {
        //System.out.println(klas+"  "+typ);
        miary.clear();
        
        //zliczam listy miar
        for(int i=0;i<kol.ileWierszy;i++)
        {
            miary.add(ListaZMiarami(kol, i, klas, typ,null,i));
        }
        
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    
    private List<Double> ListaZMiarami(Tabela kol, List<String> X0_lista, int klas, int typ,List<List<Double>> cov0,int BezWiersza)
    {
        return ListaZMiarami(kol,X0_lista,klas,typ,cov0,BezWiersza,-1);
    }
    
    private List<Double> ListaZMiarami(Tabela kol, int X0, int klas, int typ,List<List<Double>> cov0,int BezWiersza)
    {
        return ListaZMiarami(kol,null,klas,typ,cov0,BezWiersza,X0);
    }
    
    private List<Double> ListaZMiarami(Tabela kol, List<String> X0_lista, int klas, int typ,List<List<Double>> cov0,int BezWiersza,int X0)
    {
        //metoda pobiera obiekt Kolumny i liste elementów zawierającą wiersz podany przez urzytkownika
        //typ oznacza którą metryką klasyfikuje 0-euklidesowa ,1-miejska ,2-nieskończoność ,3-Mahalanobisa
        //klas oznacza nr kolumny względem której klasyfikuje
        //metoda zwraca liste wartosci po pomierzaniu w podanej metryce
        List<Double> list = new ArrayList<>();
        
        if (klas < 0 || klas > kol.ileKolumn - 1) {
            return null;
        }//jeśli będzie błąd kolumną klasy to zwracam null
        if (typ < 0 || typ > 3) {
            return null;
        }//zwracam null jeśli typ jest nie poprawny

        
        if (typ == 0) {
            for (int i = 0; i < kol.ileWierszy; i++)//Eukl Miejska Infinity Mah
            {
                double p;
                if(X0_lista!=null)
                p = Metryka.Eukl(kol, X0_lista, i, klas);
                else
                p = Metryka.Eukl(kol, X0, i, klas);
                list.add(p);
            }
        }

        if (typ == 1) {
            for (int i = 0; i < kol.ileWierszy; i++) 
            {
                double p;
                if(X0_lista!=null)
                p = Metryka.Miejska(kol, X0_lista, i, klas);
                else
                p = Metryka.Miejska(kol, X0, i, klas);
                list.add(p);
            }
        }

        if (typ == 2) {
            for (int i = 0; i < kol.ileWierszy; i++) 
            {
                double p ;
                if(X0_lista!=null)
                p = Metryka.Infinity(kol, X0_lista, i, klas);
                else
                p = Metryka.Infinity(kol, X0, i, klas);
                list.add(p);
            }
        }

        if (typ == 3) 
        {
            List<List<Double>> cov = new ArrayList<>();
            if(cov0==null)//jesli nie podam macierzy covariancji to losuj nową
            cov = Metryka.Cov2(kol, klas ,BezWiersza);
            else
            cov = cov0;
            
            for (int i = 0; i < kol.ileWierszy; i++) 
            {
                
                double p;
                if(X0_lista!=null)
                p = Metryka.Mah(kol, X0_lista, i, klas, cov);
                else
                p = Metryka.Mah(kol, X0, i, klas, cov);
                list.add(p);
            }
        }

        return list;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // 2 metody klasyfikuj i oceniaj
    public String klasyfikuj(Tabela kol, List<String> X0_lista, int klas, int typ, int liczba)
    {        
        return klasyfikuj(kol,X0_lista,klas,typ,liczba,null,-1,-1,null);//nie ignoruje tutaj żadnego wiersza więc daje nr z poza zakresu
    }    
    
    //dla trybu oceny
    public String klasyfikuj(Tabela kol,int X0, int klas, int typ, int liczba ,List<List<Double>> cov ,int BezWiersza,List<Double> miary)
    {        
        return klasyfikuj(kol,null,klas,typ,liczba,cov,BezWiersza,X0,miary);//nie ignoruje tutaj żadnego wiersza więc daje nr z poza zakresu
    }  
    
    
    private String klasyfikuj(Tabela kol, List<String> X0_lista, int klas, int typ, int liczba ,List<List<Double>> cov ,int BezWiersza,int X0,List<Double> miary) 
    {
        //miary -lista z miarami którą moge podać z zewnątrz
        //metoda pobiera obiekt Kolumny i liste elementów zawierającą wiersz podany przez urzytkownika
        //typ oznacza którą metryką klasyfikuje 0-euklidesowa ,1-miejska ,2-nieskończoność ,3-Mahalanobisa
        //klas oznacza nr kolumny względem której klasyfikuje
        //liczba oznacza liczbe sąsiadów których biore pod uwage
        //metoda zwraca String oznaczający wybrany element

        if (liczba > kol.ileWierszy)//jesli liczba wierszy jest zbyt duża to zamieniam ją na maksymalną liczba
        liczba = kol.ileWierszy;
        
        if(liczba <= 0)//jesli liczba ==0 to algorytm by sie posypal
            liczba=1;

        List<Double> list = null;
        
        if(miary!=null)//jesli miary są podane
        {
            list=miary;
        }
        else
        {
        if(X0_lista!=null)
        list = ListaZMiarami(kol, X0_lista, klas, typ,cov,BezWiersza);//new ArrayList<>();
        else
        list = ListaZMiarami(kol, X0, klas, typ,cov,BezWiersza);
        }
        
        
        
        
        ///////////////////
        List<String> list2 = new ArrayList<>();//kol.Kolumny.get(klas);
        for(int i=0;i<kol.Kolumny.get(klas).size();i++)
            list2.add(kol.Kolumny.get(klas).get(i));
        
        
        List<Slowo> list3 = new ArrayList<>();

        int liczba_wierszy=kol.ileWierszy;//ta wartosc znajduje się w pętli ,dla warunku wyjścia
        
        if(BezWiersza>=0 && BezWiersza<kol.ileWierszy)//czyli typ to nie Mahalanobisa
        {
            list=this.wyrzuc(list, BezWiersza);//wyrzucam wiersz przez utworzenie nowej tablicy
            
            list2.remove(BezWiersza);
            liczba_wierszy=kol.ileWierszy-1;
        }
        ///////////////
        
        
        for (int i = 0; i < list2.size(); i++) {
            list3.add(new Slowo(list.get(i), list2.get(i)));
        }

        //sortuje liste wg wartosci ,tak by mieć liste kolejnych wartosci klasy i łatwo je pobierać
        for (int i = 0; i < list3.size(); i++) 
        {
            for (int j = 1; j < list3.size() - i; j++) 
            {
                if (list3.get(j - 1).getLiczba() > list3.get(j).getLiczba()) 
                {
                    Slowo pom = list3.get(j - 1);
                    list3.set(j - 1, list3.get(j));
                    list3.set(j, pom);
                }
            }
        }
        

        //teraz musze policzyć najbliższe elementy i zapisać w liscie obiektów słowo
        List<Slowo> list4 = new ArrayList<>();
        
        
        while(true){
        list4.clear();
        
        //robie liste klas ktore się nie powtarzają
        for (int i = 0; i < liczba; i++) {
                
        boolean z=false;
        
        
        for(int j=0;j<list4.size();j++)
        if(list3.get(i).getSlowo().equals(list4.get(j).getSlowo()))
        z=true;
        
        if(!z)
        list4.add(new Slowo(list3.get(i).getSlowo(),0));
        }
        
        for (int i = 0; i < list4.size(); i++)//zliczam ilosc wystopien danej klasy
        {
            for(int j=0;j<liczba;j++)
            if(list4.get(i).getSlowo().equals(list3.get(j).getSlowo()))
            {
                Slowo pom = new Slowo(list4.get(i).getSlowo(),list4.get(i).getLiczba()+1);
                list4.set(i, pom);
            }
        }
        
        
        double max=0;//maksymalna liczba elementow na liscie
        int ile=0;//ile razy wystopilą maksymalna liczba elementów
        int nr=0;
        
        for(int i=0;i<list4.size();i++)//sprawdzam jaka jest najwiekrza liczba elementów
        if(list4.get(i).getLiczba()>max)
        {
            max=list4.get(i).getLiczba();
            nr=i;
        }
        
        for(int i=0;i<list4.size();i++)
        if(list4.get(i).getLiczba()==max)
        {
            ile++;
        }   
        
        
                
        if(liczba==liczba_wierszy)//jeśli osiognołem max liczbe elementów to losuje i wychodze
        {
            int p = (int)Math.floor(Math.random()*ile);
            
            List<String> list0 = new ArrayList<>();
            
            for(int i=0;i<list4.size();i++)//robie liste elementów o najwiekrzej liczbie
            if(list4.get(i).getLiczba()==max)
            {
            list0.add(list4.get(i).getSlowo());
            }
        
            
            return list0.get(p);
            
        }
        
        if(ile>1){liczba++;}
        
        if(ile==1)
        {            
            return list4.get(nr).getSlowo();            
        }
        
        
        }
    }

    //metoda dla trybu klasyfikacji ,wyrzuca z listy element o podanym nr 
    private List<Double> wyrzuc(List<Double> miar,int nr)
    {
        List<Double> pom = new ArrayList<>();
        for(int i=0;i<miar.size();i++)
        if(i!=nr)
        {pom.add(miar.get(i));}
        
        return pom;
    }
    
        
    public List<Integer> oceniaj(Tabela kol, int klas, int typ, int liczba)
    {
        return oceniaj(kol,klas,typ,liczba,null,true,false);
    }
    
    
    public List<Integer> oceniaj(Tabela kol, int klas, int typ, int liczba,List<List<Double>> cov0,boolean aktualnij,boolean duzo)
    {        
        //duzo -zmienna oznacza czy wyswietlam dane do wykresu czy pojedyncze
        //kowariancje moge dodać z zewnątrz
        if(aktualnij)
        kol.Aktualizacja();
        
        List<String> list1 =new ArrayList<>();
        List<String> list2 =kol.Kolumny.get(klas);//kol.kolumna(klas);
        List<Integer> list3 =new ArrayList<>();
        
        List<List<Double>> cov=null;
        if(typ==3 && cov0==null)
        cov = Metryka.Cov2(kol , klas ,-1);//nie będe dostosowywać macierzy kowariancji do metody leave-one-out
        //tzn wyrzucać wartosci losowej by policzyć macierz cov ,poniewarz bardzo to zwielokrotnia
        //zapotrzebowanie na czas ,a dane w tabeli są podobne ,szczególnie przy dużych ilościach danych
        else
        cov=cov0;
        
        
        
        
        
        for(int i=0;i<kol.ileWierszy;i++)
        {            
            String s;
            if(duzo){
            if(typ==3)
            s = klasyfikuj(kol,i,klas,typ,liczba,cov,i,miary.get(i));  
            else
            s = klasyfikuj(kol,i,klas,typ,liczba,null,i,miary.get(i));                 
            }
            else
            {
            if(typ==3)
            s = klasyfikuj(kol,i,klas,typ,liczba,cov,i,null);  
            else
            s = klasyfikuj(kol,i,klas,typ,liczba,null,i,null); 
            }
            
            
            list1.add(s);
            
        }
        
        for(int i=0;i<kol.ileWierszy;i++)
        {
            if(list1.get(i).equals(list2.get(i)))
            list3.add(1);
            else
            list3.add(0);
            
            //System.out.println(list3.get(i)+" | "+list2.get(i)+" | "+list1.get(i));
        }
        /*
        int q=0;
        for(int i=0;i<list3.size();i++)
        if(list3.get(i)==1)
        q++;*/
        
        //System.out.println("ilosc poprawnych to: "+q+" na "+list3.size()+" ,więc jakość klasyfikacji = "+((double)q)/list3.size()+" ilosc_poprawny/wszystkie");
        
        
        return list3;
    }
}
