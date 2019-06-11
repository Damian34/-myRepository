
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
public class zPom {
    
    public void Rand(int nr)
    {
        List<Integer> list = new ArrayList<>();
        
        while(list.size()<nr)
        {
            int n=-1;
            while(true)
            {
                int k=0;
                n=((int)(Math.random()*nr)+1);
                for(int i=0;i<list.size();i++)
                if(list.get(i)==n)
                {break;}else{k++;}
                
                if(k==list.size())
                {
                    list.add(n);
                    break;
                }
            }            
        }
        System.out.println(list.toString().replace("[","").replace("]","").replace(" ",""));
    }
    
    
    
    public static void main(String args[]) {
        
        
        try{
            File plik1 = new File("panda.bmp");
            BufferedImage image0 = ImageIO.read(plik1);
            ImageConfig ic = new ImageConfig();
            DCT d = new DCT();
            
            int w=0,h=0;
            int rgb = image0.getRGB(w, h);
            
            int[] cp = ic.getImageColor(rgb);
            
            double[] il = d.getColor(cp[0],cp[1],cp[2]);
            
            System.out.println("grey: "+il[0]);
            System.out.println("reszta barw: "+il[1]+" | "+il[2]);
            
            
            System.out.println("r: "+cp[0]+" | g: "+cp[1]+" | b: "+cp[2]);
            
            System.out.println("Height: "+image0.getHeight()+" | Width: "+image0.getWidth());
            
            
            
        }catch(Exception e){System.out.println("blad: "+e);}
        
    }
}
