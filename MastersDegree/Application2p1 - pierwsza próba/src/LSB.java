
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class LSB {
    
    private int maxBit=7;
    private int znaki=8;//liczba bitow kotre poswiecam na zakodowanie znaku ,normalnie powinno być 8
    
    private int maxBitLenth=24;//maxymalna liczba bitow poswiecona na zapisanie dlugosci wiadomosci
    
    private Bit b = new Bit();
    private ImageConfig ic=new ImageConfig();
    LSB(){}
    
    
    //dopisuje informacje do zdjecia
    private BufferedImage Dopis(BufferedImage image,List<Byte> list0)
    {
        ///
        int height = image.getHeight();
        int width = image.getWidth();
                
        int max=maxBit*3*height*width;//maxymalna liczba bitow do użycia
        
        int bit=list0.size()+maxBitLenth;//zapisuje wiadomosc i na poczatku dlugosc wiadomosci
                
        if(bit>max)//jesli wiadomosc jest zbyt duza to nie zapisje wiadomosci
        {
            return null;
        }
        
        List<Byte> list = new ArrayList<>();
        
        b.toList(list, b.ToBit((int)(list0.size()), maxBitLenth));//dopisuje dlugosc na poczatku wiadomosci
        
        for(int i=0;i<list0.size();i++)
            list.add(list0.get(i));
        
        int red,green,blue;
        
        int n=0;
        
        for(int bi=1;bi<=maxBit;bi++)        
        for(int rg=0;rg<3;rg++)        
        for(int h=0;h<height;h++)        
        for(int w=0;w<width;w++)
        {
            int[] col=ic.getImageColor(image, w, h);
            red=col[0];green=col[1];blue=col[2];
            
            //umieszczam wiadomosc
            if(rg==0)
            {
                red=b.bitP(red,bi,list.get(n));
            }else if(rg==1)
            {
                green=b.bitP(green,bi,list.get(n));
            }else if(rg==2)
            {
                blue=b.bitP(blue,bi,list.get(n));
            }
            
            Color c = new Color(red,green,blue);
            
            image.setRGB(w,h, c.getRGB());
            n++;
            
            if(n>=list.size())
            {
                return image;
            }
        }
        
        
        return null;
    }
        
        
    //odczytuje liste bitow z zdjecia
    //size to liczba bitow ktore chce odczytac ,jak size<0 to odczytuje rozmiar z wiadomosci
    private List<Byte> Odczyt(BufferedImage im,int size)
    {
        //int z = this.znaki;
        //String msg="";//raport
        //String msg2="";//odczytana wiadomosc
        BufferedImage image = (new ImageConfig()).deepCopy(im);
        
        int height = image.getHeight();
        int width = image.getWidth();
        
        int red,green,blue;
                
        //zapisuje wiadomosc na liscie
        List<Byte> list = new ArrayList<>();
        
        int n = -1;
        int k = 0;
                
        //zaczynam odczytywac bity
        for(int bi=1;bi<=maxBit;bi++)        
        for(int rg=0;rg<3;rg++)        
        for(int h=0;h<height;h++)        
        for(int w=0;w<width;w++)
        {
            int[] col=ic.getImageColor(image, w, h);
            red=col[0];green=col[1];blue=col[2];
            
            //odczytuje kolejne bity
            if(rg==0)
            {
                list.add(b.getBit(red, bi));
            }else if(rg==1)
            {
                list.add(b.getBit(green, bi));
            }else if(rg==2)
            {
                list.add(b.getBit(blue, bi));
            }
            k++;
            
            if(k==maxBitLenth)
            {
                int[] tab = new int[maxBitLenth];
                for(int i=0;i<list.size();i++)
                    tab[i]=list.get(i);
                int zn = b.ToInt(tab);
                
                //jesli size jest dodatnie to je przepisuje ,w przeciwnym przypadku pobieram odczytane
                if(size<0){n=zn;}else{n=size;}
                
                int max=maxBit*3*height*width;//maxymalna liczba bitow do użycia
                if((n+maxBitLenth)>max)//jesli liczba bitow do odczytu jest za duza to nic nie zwracam
                {return null;}
                
                
                System.out.println("liczba: "+k+" | rozmiar: "+n+" | rozmiar2: "+zn+" | "+list);
                list.clear();
            }
            
            if(n!=-1)
            if(k>=(n+maxBitLenth))
            {
                //System.out.println("k="+k+"| n="+n+" | list: "+list);
                return list;
            }
            
        }
        
        return null;
    }
    
    
    
    //w metodze dodaje text do obrazu
    public Pack DodajText(BufferedImage im ,String text)//tutaj koduje wiadomosc text
    {
        int z = this.znaki;
        String msg="";
        BufferedImage image = (new ImageConfig()).deepCopy(im);  
        
        //teraz robie liste do zapisania
        //w wiadomosci nie dodaje poczatkowej dlugosci ,wielkosc zapisanej informacji zapisuje 
        //w metodzie Dopis
                
        //sprawdzam czy w teksie nie ma znakow ktorych nie da sie zamienić na ustalony ciąg bitów
        int znaki=0;
        int mxc = 0;
        int mx = (int)Math.pow(2, z);
        for(int i=0;i<text.length();i++)
        if((int)text.charAt(i) >= mx)
        {
            znaki++;
            if((int)text.charAt(i)>mxc){mxc=(int)text.charAt(i);}
        }
        if(znaki>0)
        {
            msg="w tekscie są niedozwolone znaki ,maksymalna wartosc ANSI "+mxc;
            image=null;
            return new Pack(image,msg);
        }
        
        
        ///teraz przygotowuje wiadomość do zapisu
        List<Byte> list = new ArrayList<>();
                
        for(int i=0;i<text.length();i++)//koduje text do ciogu bitow ktory potem zapisuje
        {
            b.toList(list, b.ToBit((int)text.charAt(i), z));
        }
        b.toList(list, b.ToBit('\0', z));
        
        ///
        int height = image.getHeight();
        int width = image.getWidth();
        
        //dopisuje wiadomosc do obrazu
        image = this.Dopis(image, list);
        
        if(image==null)
        {
            System.out.println("jest blad??");
            
            int size=list.size()+maxBitLenth+8;//ukrywam jeszcze dlugosc,znak '\0'
            int max=maxBit*3*height*width;//maxymalna liczba bitow do użycia
            msg="wiadomość jest zbyt duża ,potrzeba ukryć "+size+" bitów ,a dostepnych jest "+max;            
            return new Pack(image,msg);
        }
        else
        {
            msg="zapisano wiadomosc";
            return new Pack(image,msg);
        }
        
        //return null;
    }
    
    //w metodze odczytuje text z obrazu
    public Pack OdczytTextu(BufferedImage im)
    {
        List<Byte> list = this.Odczyt(im,-1);
        
        System.out.println("list-size: "+list.size());
        if(list!=null)
        {
            String ss="";
            int[] tab = new int[znaki];
            char zn=0;
            
            for(int i=0;i<list.size();i++)
            {
                int p =i%znaki;
                tab[i%znaki]=list.get(i);
                
                if(p==znaki-1)
                {
                    zn = (char)b.ToInt(tab);
                    if(zn!='\0')
                    ss+=zn;
                }
            }
            //dopisuje znak konca lini '\0' by potwierdzic zakonczenie tekstu
            if(zn!='\0'){return new Pack("Brak zapisanej wiadomości","");}
            
            System.out.println(ss);
            
            return new Pack("Odczytano wiadomość",ss);
        }
        
        return null;
    }

    
    ///////////////////////////////////////////////////////////
    //w metodzie ukrywam obraz im2 w im ,wg typ np jest to kolorowy,szary,czarno-bialy
    public Pack DodajObraz(BufferedImage im ,BufferedImage im2,int typ)
    {
        int z = this.znaki;
        String msg="";
        BufferedImage image = (new ImageConfig()).deepCopy(im);  
        
        int max = maxBit*3*im.getHeight()*im.getWidth();
        int size=0;
        
        int h1=im2.getHeight(),w1=im2.getWidth();
        
        if(typ==0)
        {size = h1*w1*24;}
        else if(typ==1)
        {size = h1*w1*8;}
        else if(typ==2)
        {size = h1*w1*1;}
        
        size+=maxBitLenth+2+16+16;//zapisuje jeszcze dlugosc w bitach , typ 2,wysokosc 8,szerokosc 8 bitow
        
        System.out.println("max: "+max+" | size: "+size);
        
        if(size>max)
        {
            msg="obraz zajmuje za dużo ,potrzeba ukryć "+size+" bitów ,a dostepnych jest "+max;
            return new Pack(image,msg);
        }
        
        ///teraz przygotowuje wiadomość do zapisu
        List<Byte> list = new ArrayList<>();
        
        b.toList(list, b.ToBit(typ, 2));//dopisuje na poczatek typ
        b.toList(list, b.ToBit(im2.getHeight(), 16));//dopisuje wysokosc
        b.toList(list, b.ToBit(im2.getWidth(), 16));//dopisuje szerokosc
        
        List<Byte> l0 = b.ImageToByte(im2, typ);
        for(int i=0;i<l0.size();i++)
            list.add(l0.get(i));
        
        //b.toList(list, b.ToBit('\0', 8));
        
        System.out.println("list-size: "+list.size()+" | l0-size: "+l0.size());
        
        //dopisuje wiadomosc do obrazu
        image = this.Dopis(image, list);
        
        if(image==null)
        {
            msg="nie udało się ukryć obrazu";
            return new Pack(image,msg);
        }
        else
        {
            msg="ukryto obraz";
            return new Pack(image,msg);
        }
        
        //return null;
    }
    
    //w metodzie odczytuje ukryte zdjecie
    public Pack OdczytObrazu(BufferedImage im,int Height,int Width)
    {
        String msg="";
        List<Byte> list = this.Odczyt(im,-1);
        
        System.out.println("list-size: "+list.size());
        if(list!=null)
        {
            int typ=-1,h1=-1,w1=-1;
            int[] pom1=new int[2];
            for(int i=0;i<pom1.length;i++)
            {pom1[i]=list.get(0);list.remove(0);}            
            typ=b.ToInt(pom1);
                        
            int[] pom2=new int[16];
            for(int i=0;i<pom2.length;i++)
            {pom2[i]=list.get(0);list.remove(0);}
            h1=b.ToInt(pom2);
            
            for(int i=0;i<pom2.length;i++)
            {pom2[i]=list.get(0);list.remove(0);}
            w1=b.ToInt(pom2);
            
            
            System.out.println("typ: "+typ+" | wysokosc: "+h1+" | szerokosc: "+w1);
            
            BufferedImage image = null;
            if(Height<0 || Width<0)
            {
                image = b.ByteToImage(list, typ, h1, w1);
                msg="Odczytano obraz";
                return new Pack(image,typ,msg);
            }
            else
            {
                image = b.ByteToImage(list, typ, Height, Width);
                msg="Odczytano obraz";
                return new Pack(image,typ,msg);
            }
            
        }
        
        return null;
    }
    
    
    public Pack TestWritingImageOnJPG(BufferedImage image,BufferedImage image2,double st,int typ)
    {
        String FileName="obraz.jpg";
        ImageConfig ic = new ImageConfig();
        String msg="";
        
        Pack pc = this.DodajObraz(image, image2, typ);
        BufferedImage im=pc.image;
        
        List<Byte> l0 = b.ImageToByte(image2, typ);//bity ktore dopisuje do obrazu 1
        
        //zapisuje obraz z kompresja jpg
        ic.saveImageAsJPEG(im, st, FileName);
        //teraz odczytuje ten obraz i odczytuje ukryty obraz
        
        BufferedImage im2=null;
        
        try{
        File plik1 = new File(FileName);
        im2 = ImageIO.read(plik1);
        //
        //im2=im;//+to aby sprawdzic czy poprawnie dziala,powinno wtedy dac 100%
        }catch(Exception e){System.out.println("error-TestWritingImageOnJPG: "+e);}
        
        
        int s1 = 2+16+16;//zapisane sa jeszcze typ 2,wysokosc 8,szerokosc 8 bitow
        int size = l0.size()+s1;
        List<Byte> l1 = this.Odczyt(im2, size);
        
        //usuwam pierwsze 3 informacje ,typ,szerokosc,wysokosc i podaje je z orginalu
        for(int i=0;i<s1;i++)
            l1.remove(0);
        
        System.out.println("l0-size: "+l0.size()+" | l1-size: "+l1.size());
        
        //zamieniam liste bitow w obraz po kompresji stratnej jpg
        BufferedImage im3=b.ByteToImage(l1, typ, image2.getHeight(), image2.getWidth());
        
        
        /////////////////////
        int k=0;
        double k2=0;    
        
        for(int i=0;i<l0.size();i++)
        if(l0.get(i)==l1.get(i))
        {k++;}
        k2=(((double)k)/l0.size())*100;
        
        msg="podobienstwo bitów obrazów po kompresji JPG: "+k2+" %";
        System.out.println(msg);
        
        return new Pack(im2,im3,msg);
    }
    
    
}
