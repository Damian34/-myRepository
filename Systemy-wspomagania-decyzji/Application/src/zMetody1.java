
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class zMetody1 {
    
    public double Trafnosc(List<String> list1, List<String> list2)
    {
        //wspolczynnik trafnosci/skutecznosci?
        
        //metoda pobiera 2 listy stringow o tej samej dlugosci i sprawdza ile elementow jest podobnych
        //metoda nie zwraca uwagi na kolejnosc
        //zwraca w wyniku (iloraz poprawnie zaklasyfikowanych obiektów do liczby wszystkich obiektow)
        
        if(list1.size()!=list2.size()){return -1;}
        
        double max=list1.size();
        
        double licz=Czesc_wspolna(list1,list2);
        
        double wynik=licz/max;
        
        return wynik;
    }    
    
    public double Jaccard(List<String> list1, List<String> list2)
    {
        //miara Jaccard'a
        //A(suma)B=A+B-A(czesc_wsuplna)B
        double wsp=Czesc_wspolna(list1,list2);
        //double wsp=Czesc_wspolna2(list1,list2);
        
        double down=list1.size()+list2.size()-wsp;
        
        double wynik=wsp/down;
        
        return wynik;
    }
    
    public double Dice(List<String> list1, List<String> list2)
    {
        //współczynnik Dice / Sørensena
        //2*(A(czesc_wspulna)B)/(A+B) (,czy ?)   (A(czesc_wspulna)B)/(A+B)
        
        //double wsp=Czesc_wspolna2(list1,list2);
        double wsp=Czesc_wspolna(list1,list2);
        
        double down = list1.size()+list2.size();
        
        double wynik=(2*wsp)/down;        
        
        return wynik;
    }
    
    
    
    public double Czesc_wspolna(List<String> list1, List<String> list2)
    {
        //metoda liczbe elementów wspulnych dla obu macierzy
        //zwraca uwage na kolejności
        
        double licz=0;
        
        for(int i=0;i<list1.size();i++)
        if(list1.get(i).equals(list2.get(i)))
        {
            licz++;
        }
        
        return licz;
    }
    
    public double Czesc_wspolna2(List<String> list1, List<String> list2)
    {
        //metoda liczbe elementów wspulnych dla obu macierzy
        //nie zwraca uwagi na kolejnosc
        
        double licz=0;
        
        /*
        for(int i=0;i<list1.size();i++)
        if(list1.get(i).equals(list2.get(i)))
        {
            licz++;
        }*/
        List<String> list3 = new ArrayList<>(list2);
        
        for(int i=0;i<list1.size();i++)//przechodze po 1 liscie i szukam elementów w drugiej
        {
            for(int j=0;j<list3.size();j++)
            if(list1.get(i).equals(list3.get(j)))//jesli znajde identyczny element wychodze
            {
                licz++;
                list3.remove(j);//aby nie porownywac go ponownie
                break;
            }
            
        }
        //System.out.println("znalazlo "+licz+" elementow");
        
        return licz;
    }
    
    
    public static void main(String [] args)
    {
        zMetody1 met = new zMetody1();
        Tabela tab = new Tabela();
        
        List<String> l1 =tab.kolumna(2);
        List<String> l2 =tab.kolumna(2);
        
        System.out.println(met.Trafnosc(l1,l2));
        
        /*Map<String,Integer> c = new HashMap<String, Integer>();
        
        c.put("a1", 2);
        c.put("b2", 6);
        
        c.put("b2", 10);
        
        System.out.println(c.get("b2"));
        
        
        System.out.println(c);*/
        
    }
}
