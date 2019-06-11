
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
public class zDyskretny {
    //////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////dyskretny 2 ,dziele po prostu zbiór na podana liczbe klas
    //poczatek i koniec to minima i maxima
    public List<Double> dyskretny(List<Double> list,int liczba,int sposob)
    {
        //sposob = 0 to typu ze numeruje elementy od 0 do liczba-1 ,
        //sposob = 1 to typu ze numeruje elementy wg konca przedzialu
        //sposob = 2 to typu ze numeruje elementy wg liczebnosci
        //pobieram liste i liczbe na ile muszę ją podzielić
        
        if(liczba<=0){liczba=1;}
        if(liczba>list.size()){liczba=list.size();}
        
        double max=0,min=Double.MAX_VALUE;
        
        for(int i =0;i<list.size();i++)
        {
            if(list.get(i)>max){max=list.get(i);}
            if(list.get(i)<min){min=list.get(i);}
        }
        
        
        double odstep = (max - min)/liczba;
        
        //System.out.println("min="+min+" , max="+max+" , odstep="+odstep);
        
        ////////////////////dyskretyzuje liste
        ////////////////
        //int[] tab=new int[list.size()];
        List<Double> numery = new ArrayList<>();
        for(int i=0;i<list.size();i++){numery.add(-1.0);}
        
        List<Double> przedzialy = new ArrayList<>();//elementow tutaj jest w sumie = liczba+1
        
        List<Double> numery2 = new ArrayList<>();//numery do sposobu 2
        
        double pom=min;
        for(int i=0;i<liczba;i++)//tworze przedzialy
        {
            przedzialy.add(pom);
            pom=pom+odstep;
            
        }przedzialy.add(pom);
        
        //System.out.println(przedzialy);
        
        try{
            /*
        for(int i=0;i<przedzialy.size()-1;i++)//przechodze po przedzialach
        {
            
        }*/
        for(int i=0;i<list.size();i++)//przechodze po elementach
        {
            
            for(int j=0;j<przedzialy.size()-1;j++)//przechodze po przedzialach
            {
            if(j==0 && list.get(i)==min)//jesli jest to 1 element = min
            {
                numery.set(i, (double)j);//narazie tak
                //System.out.println("jest min "+i);
                break;
            }
            if(j==przedzialy.size()-2 && list.get(i)==max)//jesli jest to ostatni element = max
            {
                numery.set(i, (double)j);//narazie tak
                //System.out.println("jest max "+i);
                break;
            }
            if(list.get(i)>przedzialy.get(j) && list.get(i)<=przedzialy.get(j+1))//w pozostalych przypadkach
            {
                numery.set(i, (double)j);
                break;
            }
            
            
            }
            
        }    
        //System.out.println(numery);
            
        /////////////////////////////////////////////////
        ///tutaj jest wynik
        
        if(sposob==0){numery2=numery;}
        
        if(sposob==1)
        {
        for(int i=0;i<numery.size();i++)
        {
            numery2.add(przedzialy.get((int)numery.get(i).doubleValue()+1));
        }    
        }
        
        if(sposob==2)
        {
            List<Integer> l1 = new ArrayList<>();
            for(int i=0;i<liczba;i++)
            {
                int licz=0;
                for(int j=0;j<numery.size();j++)
                if(numery.get(j)==i)
                {
                    licz++;
                }
                l1.add(licz);
            }
            //System.out.println(l1);
            
        for(int i=0;i<numery.size();i++)
        {
            int p = l1.get((int)numery.get(i).doubleValue());
            numery2.add((double)p);
        }    
            
        }
        
        if(sposob==3)//jesli 3 to zwracam  kolejno liczbe elementow w z kazdego przedzialu
        {
            //List<Integer> l1 = new ArrayList<>();
            for(int i=0;i<liczba;i++)
            {
                int licz=0;
                for(int j=0;j<numery.size();j++)
                if(numery.get(j)==i)
                {
                    licz++;
                }
                numery2.add((double)licz);
            }
            //System.out.println(numery2);
            
            
        }
        
        }catch(Exception e){System.out.println("dyskretyzacja , blad: "+e);}
        
        
        //System.out.println(numery2);
        
        return numery2;
    }
    
    public List<String> dyskretny2(List<Double> list,int liczba,int sposob)
    {
        List<Double> list2 = dyskretny(list,liczba,sposob);
        List<String> list3 = new ArrayList<>();
        if(sposob==0)
        {
        
        for(int i=0;i<list2.size();i++)
        {
            list3.add((int)list2.get(i).doubleValue()+"");
        }
        
        }
        else
        {
            
        for(int i=0;i<list2.size();i++)
        {
            list3.add(list2.get(i).doubleValue()+"");
        }
            
        }
        
        
        return list3;
    }
    
    
    
    public static void main(String [] args)
    {
        zDyskretny d = new zDyskretny();
        Tabela tab = new Tabela();
        
        System.out.println(tab.Wartosci(0));
        System.out.println(d.dyskretny(tab.Wartosci(0), 3,2));
        
    }
    
    
    ////////////////////////////////
}
