package com.example.dami.przelicznik;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.sql.Date;

/**
 * Created by dami on 2016-04-27.
 */
public class Paczka implements Serializable{
    private static final long serialVersionUID = 1L;
    private Date data;
    private List<Waluta> lista;
    private int okno1=-1;
    private int okno2=-1;
    private int stan=0;

    public Paczka()
    {
        data = null;
        lista = new ArrayList<Waluta>();
    }
    public Paczka(Date d,List<Waluta> l)
    {
        this.data=d;
        this.lista=l;
    }
    public void setData(Date d)
    {this.data=d;}

    public void setlista(List<Waluta> l)
    {this.lista=l;}

    public void setokno1(int o)
    {this.okno1=o;}

    public void setokno2(int o)
    {this.okno2=o;}

    public void setStan(int s)
    {this.stan=s;}

    public Date getData()
    {return this.data;}

    public List<Waluta> getlista()
    {
        List<Waluta> list = new ArrayList<Waluta>();
        for(int i=0;i<this.lista.size();i++)
        {list.add(this.lista.get(i));}
        return list;
    }

    public int getokno1()
    {return this.okno1;}

    public int getokno2()
    {return this.okno2;}

    public int getStan()
    {return this.stan;}

    //metoda zamienia obiekt paczki na String[] by moc ja przeniesc miedzy aktywnosciami
    public String[] toTable()//Paczka p)
    {
        int rozmiar=lista.size()*4+4;

        String[] tab=new String[rozmiar];
        tab[0]=data.toString();
        tab[1]=okno1+"";
        tab[2]=okno2+"";
        tab[3]=stan+"";
        List<Waluta> list = lista;
        int j=0;
        for(int i=4;i<rozmiar;i=i+4)
        {
            tab[i]=list.get(j).getNazwa();
            tab[i+1]=list.get(j).getKod_waluty();
            tab[i+2]=list.get(j).getkurs_sredni()+"";
            tab[i+3]=list.get(j).getPrzelicznik()+"";
            j++;
        }

        return tab;
    }

    //tworzy obiekt na podstawie tablicy
    public Paczka(String[] tab)
    {
        this.data=zmien(tab[0]);
        this.okno1=Integer.parseInt(tab[1]);
        this.okno2=Integer.parseInt(tab[2]);
        this.stan=Integer.parseInt(tab[3]);
        List<Waluta> list = new ArrayList<Waluta>();
        for(int i=4;i<tab.length;i=i+4)
        {
            String nazwa=tab[i];
            String kod=tab[i+1];
            double sr=Double.parseDouble(tab[i+2]);
            int prze=Integer.parseInt(tab[i+3]);
            Waluta w=new Waluta(nazwa,prze,kod,sr);
            list.add(w);
        }
        this.lista=list;
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
}
