
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class ImageConfig {
    
    ImageConfig(){}
    
    public BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    //metoda do zamiany obiektu Image na BufferedImage ,w celu wyswietlenia w jPanel
    public BufferedImage toBufferedImage(Image img)
    {
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0,img.getWidth(null), img.getHeight(null), null);
    //bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
    }
    
    
    public BufferedImage resizeImage(BufferedImage originalImage,int newWidth,int newHeight){
	BufferedImage resizedImage = new BufferedImage(newWidth,newHeight, Image.SCALE_REPLICATE);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, newWidth,newHeight, null);
	g.dispose();

	return resizedImage;
    }
    
    //zamieniam kolor obrazu na szary ,tzn aby dało się zapisać kolory w 8 bitach
    public BufferedImage ImageToGray(BufferedImage img)
    {
        BufferedImage im = null;
        try{
            im = this.deepCopy(img);
            int height = im.getHeight();
            int width = im.getWidth();
            
            int red,green,blue;
            int q;
            
            for(int h=0;h<height;h++)        
            for(int w=0;w<width;w++)
            {
                int[] col=(new ImageConfig()).getImageColor(im, w, h);
                red=col[0];green=col[1];blue=col[2];
                
                q=(red+green+blue)/3;
                
                Color c = new Color(q,q,q);
                im.setRGB(w,h, c.getRGB());
            }
            
        }catch(Exception e){System.out.println("error-ImageToGray: "+e);}
        
        return im;
    }
    
    //zamieniam kolor obrazu na czarno-bialy ,tzn aby dało się zapisać kolory w 2 bitach
    public BufferedImage ImageToBlackAndWhite(BufferedImage img)
    {
        BufferedImage im = null;
        try{
            im = this.deepCopy(img);
            int height = im.getHeight();
            int width = im.getWidth();
            
            int red,green,blue;
            int q;
            
            for(int h=0;h<height;h++)        
            for(int w=0;w<width;w++)
            {
                int[] col=(new ImageConfig()).getImageColor(im, w, h);
                red=col[0];green=col[1];blue=col[2];
                
                q=(red+green+blue)/3;
                
                Color c ;
                
                if(q<128)
                c = new Color(0,0,0);
                else
                c = new Color(255,255,255);
                
                im.setRGB(w,h, c.getRGB());
            }
            
        }catch(Exception e){System.out.println("error-ImageToBlackAndWhite: "+e);}
        
        return im;
    }
    
    
    
    //metoda zapisuje jako jpg i odczytuje z jakoscia procentowa od 0 do 100
    public void saveImageAsJPEG(BufferedImage image,double quality,String file)
    {
        if(image==null){return;}
        if(quality<0){quality=0;}
        if(quality>100){quality=100;}
        
        float q = ((float)quality)/100;
        
        ///
        try{
            OutputStream stream = new FileOutputStream(file);
            
            ImageWriter writer = null;
            Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
            if (iter.hasNext()) {
              writer = (ImageWriter) iter.next();
            }
            ImageOutputStream ios = ImageIO.createImageOutputStream(stream);
            writer.setOutput(ios);
            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwparam.setCompressionQuality(q);
            writer.write(null, new IIOImage(image, null, null), iwparam);
            ios.flush();
            
            stream.close();
            ios.close();
        }catch(Exception e){System.out.println("error-saveImageAsJPEG: "+e);}
    }
    
    
    //funkcja pobiera kolory z obrazka z podanego punktu
    public int[] getImageColor(BufferedImage im,int w ,int h)
    {
        int red,green,blue;
        int rgb = im.getRGB(w,h);
        //przesunienice o 8 czy 16 bitow ,bo na kazdy kolor jest przeznaczone 8 bitow
        //red,green,blue ,najstarsze bity sa od blue
        red = (rgb >> 16 ) & 0x000000FF;
        green = (rgb >> 8 ) & 0x000000FF;
        blue = (rgb) & 0x000000FF;        
        int col[]={red,green,blue};
        return col;
    }
    
    //funkcja pobiera kolory z obrazka z podanego rgb
    public int[] getImageColor(int rgb)
    {
        int red,green,blue;
        //przesunienice o 8 czy 16 bitow ,bo na kazdy kolor jest przeznaczone 8 bitow
        //red,green,blue ,najstarsze bity sa od blue
        red = (rgb >> 16 ) & 0x000000FF;
        green = (rgb >> 8 ) & 0x000000FF;
        blue = (rgb) & 0x000000FF;        
        int col[]={red,green,blue};
        return col;
    }
        
}
