
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class Pack {
    
    BufferedImage image=null;//obraz
    String msg="";//informacja
    String msg2="";//wiadomosc
    
    BufferedImage image2=null;//obraz odczytywany
    int typ=-1;//typ odczytywany ,kolorowy,szary,czarno-bialy
    
    
    Pack(){}
    Pack(BufferedImage image,String msg)
    {
        this.image=image;
        this.msg=msg;
    }
    
    Pack(String msg,String msg2)
    {
        this.msg=msg;
        this.msg2=msg2;
    }
    
    Pack(BufferedImage image2,int typ,String msg)
    {
        this.image2=image2;
        this.typ=typ;
        this.msg=msg;
    }
    
    Pack(BufferedImage im1,BufferedImage im2 ,String msg)
    {
        this.image=im1;
        this.image2=im2;
        this.msg=msg;
    }
}
