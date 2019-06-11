
import java.awt.Color;
import java.awt.image.BufferedImage;
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
public class Bit {
    
    Bit(){}
    
    
    public int bitP(int liczba,int bit,int b)
    {
        int nr=liczba;
        
        if(b==0){
            nr = bit0(liczba,bit);
        }else if(b==1){
            nr = bit1(liczba,bit);
        }        
        return nr;
    }
    
    public int bit0(int liczba,int bit)
    {
        if(bit<1){bit=1;}
        //ustawiam 0 pod bit o podanym numerze
        int p = 1 << (bit-1);
        return (liczba | p) ^ p;
    }
    public int bit1(int liczba,int bit)
    {
        if(bit<1){bit=1;}
        //ustawiam 1 pod bit o podanym numerze
        int p = 1 << (bit-1);
        return (liczba | p);
    }
    
    public int[] ToBit(int liczba,int N)//liczba i liczba bitow
    {
        //w metodzie zamieniam liczbe na tablice bitow o dlugosci do N
        int p = 1 << N;
        
        if(liczba>=p)
        {
            System.err.println(liczba+" >= "+p);
            return null;
        }
        
        int[] tab = new int[N];
        
        int pom=liczba;
        int k=N-1;
        while(pom>0)
        {
            int pom2=pom%2;
            tab[k]=pom2;
            pom=pom >> 1;
            
            k--;
        }
        
        return tab;
    }
    
    public int ToInt(int[] tab)
    {
        //zamieniam tablice bitow na liczbe
        if(tab==null){return -1;}
        int p = 0;
        
        for(int i=0;i<tab.length;i++)
        {
            p+=tab[i];
            if(i!=tab.length-1)
            p= p << 1;
        }
        
        return p;
    }
    
    public byte getBit(int liczba,int N)//metoda zwraca bit z podanej liczby na pozycji N
    {
        byte nr=(byte)((liczba >> (N-1))%2);        
        return nr;
    }
    
    public void toList(List<Byte> list,int[] tab)
    {
        //metoda dodaje tablice do listy
        for(int i=0;i<tab.length;i++)
            list.add((byte)tab[i]);
    }
    
    ///////////////////////////////////////////////////////////////
    //ponizej zamieniam obaraz na bity i odwrotnie
    
    public List<Byte> ImageToByte(BufferedImage image,int typ)
    {
        List<Byte> list = new ArrayList<>();
        try{
            int height = image.getHeight();
            int width = image.getWidth();
            
            int rgb;
            int red,green,blue;
            
            
            for(int h=0;h<height;h++)        
            for(int w=0;w<width;w++)
            {
                rgb = image.getRGB(w,h);
                red = (rgb >> 16 ) & 0x000000FF;
                green = (rgb >> 8 ) & 0x000000FF;
                blue = (rgb) & 0x000000FF;
                
                
                if(typ==0)//tzn kolorowy
                {
                    this.toList(list, this.ToBit(red, 8));
                    this.toList(list, this.ToBit(green, 8));
                    this.toList(list, this.ToBit(blue, 8));                    
                }else if(typ==1)//tzn szary
                {
                    int grey = (red+green+blue)/3;
                    this.toList(list, this.ToBit(grey, 8));
                }else if(typ==2)//tzn czarno-bialy
                {
                    int grey = (red+green+blue)/3;
                    if(grey<128)
                    list.add((byte)0);
                    else
                    list.add((byte)1);
                }
                
            }
            
        }catch(Exception e){System.out.println("error-ImageToByte: "+e);}
        
        return list;
    }
    
    public BufferedImage ByteToImage(List<Byte> list,int typ,int height,int width)
    {
        // Create a buffered image with transparency
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        try{
            System.out.println();
            int size2=0;
        
            if(typ==0)
            {size2=24;}
            else if(typ==1)
            {size2=8;}
            else if(typ==2)
            {size2=1;}
            
            int red=0,green=0,blue=0;
            int w=0,h=0;
            
            int[] tab=new int[8];
            int max=height*width*size2;
            
            if(list.size()<max)
            {}
            
            System.out.println("<-------------- ByteToImage --------------->");
            System.out.println("typ: "+typ+" | list-size: "+list.size()+" | list-size/8: "+((double)list.size())/8+" | list-size/24: "+((double)list.size())/24);
            
            System.out.println("height: "+height+" | width: "+width);
            System.out.println("list+size: "+list.size()+" | height2: "+((double)list.size())/width+" | width2: "+((double)list.size())/height);
            
            System.out.println("<------------------------------------------>");
            
                        
            int q=0;
            while(h<height)
            {                
                if(typ==0)//tzn kolorowy
                {
                    for(int j=0;j<3;j++)
                    {
                    for(int i=0;i<8;i++)
                    {
                        tab[i]=list.get(q);
                        q++;
                    }
                    
                    if(j==0)
                    {red=this.ToInt(tab);}
                    else if(j==1)
                    {green=this.ToInt(tab);}
                    else if(j==2)
                    {blue=this.ToInt(tab);}                    
                    }
                    
                    Color c = new Color(red,green,blue);
                    image.setRGB(w,h, c.getRGB());
                    
                }else if(typ==1)//tzn szary
                {
                    for(int i=0;i<8;i++)
                    {
                        tab[i]=list.get(q);
                        q++;
                    }
                    red=this.ToInt(tab);
                    green=red;
                    blue=red;
                    Color c = new Color(red,green,blue);
                    image.setRGB(w,h, c.getRGB());
                    
                }else if(typ==2)//tzn czarno-bialy
                {
                    int k = list.get(q);
                    q++;
                    
                    Color c;
                    if(k==0)
                    {c = new Color(0,0,0);}
                    else
                    {c = new Color(255,255,255);}
                    image.setRGB(w,h, c.getRGB());
                }
                                
                w++;
                if(w==width)//to na koncu petli
                {h++;w=0;}                
            }
            
            
        }catch(Exception e){System.out.println("error-ByteToImage: "+e);}
        
        return image;
    }
}
