
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
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
public class DCT {
    
    private int N=8;
    private double[] C = null;
    
    ///////////
    private ImageConfig ic = new ImageConfig();
    private Bit b = new Bit();
    
    DCT()
    {
        C = new double[N];
        for(int i=1;i<N;i++)
            C[i]=1;
        C[0]=1/Math.sqrt(2);
    }
    
    private double[][] tab2p1=null;
    //w metodzie pobieram blok i stosuje transformate kosinusowo
    public double[][] BlockChange(double[][] tab)
    {
        if(!Check(tab)){return null;}
        
        if(tab2p1==null)
        {tab2p1=new double[N][N];}
        else if(!(tab2p1.length==N && tab2p1[0].length==N))
        {tab2p1=new double[N][N];}
        //double[][] tab2=new double[N][N];
        
        double p1=2/((double)N);
        double p2=2*((double)N);
        
        for(int u=0;u<N;u++)
        for(int v=0;v<N;v++)  
        {        
            double sum=0;
            
            for(int x=0;x<N;x++)
            for(int y=0;y<N;y++)
            {
                double q1=Math.cos(((2*x+1)/p2)*u*Math.PI);
                double q2=Math.cos(((2*y+1)/p2)*v*Math.PI);
                                
                sum+=tab[x][y]*q1*q2;
            }                        
            tab2p1[u][v]=p1*C[u]*C[v]*sum;            
        }
              
        return tab2p1;
    }  
    
    
    private double[][] tab2p2=null;
    //w metodzie pobieram blok i stosuje transformate kosinusowo odwrotna
    public double[][] BlockRetrieve(double[][] tab)
    {
        if(!Check(tab)){return null;}               
        
        if(tab2p2==null)
        {tab2p2=new double[N][N];}
        else if(!(tab2p2.length==N && tab2p2[0].length==N))
        {tab2p2=new double[N][N];}
        //double[][] tab2=new double[N][N];
        
        double p1=2/((double)N);
        double p2=2*((double)N);
        
        for(int x=0;x<N;x++)
        for(int y=0;y<N;y++)  
        {               
            double sum=0;
            
            for(int u=0;u<N;u++)
            for(int v=0;v<N;v++)
            {
                double q1=Math.cos(((2*x+1)/p2)*u*Math.PI);
                double q2=Math.cos(((2*y+1)/p2)*v*Math.PI);
                                
                sum+=tab[u][v]*C[u]*C[v]*q1*q2;
            }                        
            tab2p2[x][y]=p1*sum;            
        }
        
        return tab2p2;
    }
        
    //sprawdzam poprawnosc bloku
    private boolean Check(double[][] tab)
    {
        if(tab.length!=N)
        {return false;}else{
            for(int i=0;i<tab.length;i++)if(tab[i].length!=N){return false;}
        }
        return true;
    }
    
    private int[][] tab2p3=null;
    //zamieniam wartosci w bloku na calkowite
    public int[][] DoubleToInt(double[][] tab)
    {
        if(!Check(tab)){return null;}
        
        if(tab2p3==null)
        {tab2p3=new int[N][N];}
        else if(!(tab2p3.length==N && tab2p3[0].length==N))
        {tab2p3=new int[N][N];}
        //int[][] tab2 = new int[N][N];
        
        double c;
        for(int i=0;i<N;i++)
        for(int j=0;j<N;j++)
        {
            tab2p3[i][j]=DoubleRound(tab[i][j]);
        }
        return tab2p3;
    }
    
    public int DoubleRound(double nr)
    {
        if((nr%1)>0.5){return(((int)nr)+1);}else{return((int)nr);}
    }
    
    /////////////////////////////////////////////////////////
    public double[] getColor(int r,int g,int b)
    {
        double y,cb,cr;
        y=0.299*r+0.587*g+0.114*b;//y - iluminacja 
        cb=-0.1687*r-0.3313*g+0.5*b;//cb stopien niebieskoci
        cr=0.5*r-0.4187*g-0.0813*b;//sr stopien czerwonosci
        double[] tab = {y,cb,cr};
        //System.out.println("r: "+r+" | g: "+g+" | b: "+b+"  ||  "+"y: "+y+" | cb: "+cb+" | cr: "+cr);
        return tab;
    }
    
    public int[] retrieveColor(double[] col)
    {
        double y=col[0],cb=col[1],cr=col[2];        
        int r,g,b;
        r=DoubleRound(y+1.402*cr);
        g=DoubleRound(y-0.34413*cb-0.71414*cr);
        b=DoubleRound(y+1.772*cb);       
        
        if(r>255){r=255;} if(r<0){r=0;}
        if(g>255){g=255;} if(g<0){g=0;}
        if(b>255){b=255;} if(b<0){b=0;}
               
        int[] tab={r,g,b};
        //System.out.println("r: "+r+" | g: "+g+" | b: "+b);
        return tab;
    }
    
    
    ///////////////////////
        
    /*public int[][] ImageToTable(BufferedImage im)
    {        
        int h = im.getHeight(),w = im.getWidth();
        int[][] tab =new int[h][w];
                
        for(int i=0;i<h;i++)
        for(int j=0;j<w;j++)
        {
            int[] c0 = ic.getImageColor(im, j,i);
            int s=(c0[0]+c0[1]+c0[2])/3;
            if(s<128)
            {tab[i][j]=0;}
            else
            {tab[i][j]=1;}
        }
        
        return tab;
    }
    
    
    public BufferedImage TableToImage(int[][] tab)
    {
        int h = tab.length,w = tab[0].length;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        for(int i=0;i<h;i++)
        for(int j=0;j<w;j++)
        {
            Color c;            
            if(tab[i][j]==0)
            {c = new Color(0,0,0);}
            else
            {c = new Color(255,255,255);}
            
            image.setRGB(j,i, c.getRGB());
        }        
        return image;
    }*/
    
    
    //////////////////////////////////////////////////////////////
    
    //ukrywam obraz im2 w im
    public BufferedImage WriteToImage(BufferedImage im,BufferedImage im2,double zag)
    {
        //zmienna zag oznacza stopien zagnierzdzenia po przez wartosci dotanie np 20,40 ,..
        if(zag<0){zag=Math.abs(zag);}
        
        BufferedImage image = ic.deepCopy(im);
        //////////
        int h1=im.getHeight(),w1=im.getWidth();        
        int h2=im2.getHeight(),w2=im2.getWidth();//do obrazu wpisywanego
        
        int h1_2=h1/N,w1_2=w1/N;
        int h2_2=h2*N,w2_2=w2*N;
        int h2_3=h1/h2_2,w2_3=w1/w2_2;//wielkosc zestawow blokow
        
        //jesli nie ma mozliwosc wpisac choc 1 obrazu to wychodze
        if(h2_3==0 || w2_3==0){
            System.out.println("h2_3: "+h2_3+" | w2_3: "+w2_3);
            return null;
        }
                
        System.out.println("h1: "+h1+" | w1: "+w1);        
        System.out.println("h1_2: "+h1_2+" | w1_2: "+w1_2+" | h1_2*w1_2: "+h1_2*w1_2);
        
        System.out.println("obraz: "+h2+" x "+w2);
        System.out.println("potrzebne punkty dla wpisywanego obrazu: h2_2:"+h2_2+" | w2_2:"+w2_2);
        
        System.out.println("h2_3: "+h2_3+" | w2_3: "+w2_3+" | h2_3*w2_3: "+h2_3*w2_3);
        System.out.println("h2_3*h2_2: "+h2_3*h2_2+" | w2_3*w2_2: "+w2_3*w2_2+" | (h2_3*h2_2)*(w2_3*w2_2): "+(h2_3*h2_2)*(w2_3*w2_2));
        System.out.println("w2: "+w2+" | h2: "+h2);
        
        //przechowuje: komurke w bloku(5 i 6),zestw blokow(1 i 2),pozycje bloku w zestawie(3 i 4)
        int[][][][][][] blocks=new int[h2_3][w2_3][h2][w2][N][N];
        
        System.out.println("1: "+blocks.length+" | 2: "+blocks[0].length+" | 3: "+blocks[0][0].length+" | 4: "+blocks[0][0][0].length+" | 5: "+blocks[0][0][0][0].length+" | 6: "+blocks[0][0][0][0][0].length);
        
        //najpierw przechodze po zestawach
        for(int h=0;h<h2_3;h++)
        for(int w=0;w<w2_3;w++)
        {
            //potem przechodze po 1 zestawie o liczbie blokow takiej samej jak wpisywany obraz
        for(int i1=0;i1<h2;i1++)
        for(int i2=0;i2<w2;i2++)
        {
            //na koniec przechodze po bloku
            for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                blocks[h][w][i1][i2][i][j] = im.getRGB(w*w2*N+i2*N+j,h*h2*N+i1*N+i);
            }
        }        
        }        
        
        ////////////////////////////////////
        
        
        double[][] tab = new double[N][N];
        
        double[][] blockPom = new double[N][N];
        double[][] block0 = new double[N][N];//na poczatku gdy przypisuje orginalne wartosci po DCT
        
        //najpierw przechodze po kazdym zestawie
        for(int i1=0;i1<h2;i1++)
        for(int i2=0;i2<w2;i2++)
        {
            //na poczatek pobieram bit 0 lub 1 oznaczjaca barwe ukrytego obrazu
            int kp=0;
            int[] col0 = ic.getImageColor(im2.getRGB(i2,i1));
            double sr0=(col0[0]+col0[1]+col0[2])/3;
            if(sr0<128)
            {kp=0;}
            else
            {kp=1;}
            
            
        //nastepnie przechodze po zestawach i w kazdym zestawie koduje ten sam zestaw bitow
        for(int h=0;h<h2_3;h++)
        for(int w=0;w<w2_3;w++)
        {
            //nastepnie pobieram blok z kazdego takiego zestawu
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {
                int[] col = ic.getImageColor(blocks[h][w][i1][i2][i][j]);
                double[] il = getColor(col[0],col[1],col[2]);  
                tab[i][j]=il[0];
            }
            
            //
            //wykonuje DCT dla bloku
            double[][] block2 = BlockChange(tab);
            
            
            //ponizej proboje zmienic liczbe wystopien wartosci o znakach ujemnych/dodatnich
            //po przez lekko ,losowo zmiane kolorow
            int roz=0;        
            ///
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {block0[i][j]=block2[i][j];}
            
            /////////////////////////////////////////////////////////////////////////////////////
            int c1=0;
            while(true)
            {
            //szukam liczbe wystopien wartosci dodatnich/ujemnych
                double sum=0;
                
            double p1=0,p2=0;
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            if(!(i==0 && j==0))
            {
                /*if(block0[i][j]>0)
                {p1++;}
                else
                {p2++;}*/
                
                sum+=block0[i][j];
            }
            
            
            //double size1=p2/(p1+p2);//dodatnie
            //double kp2=0.6;//
            
            //zmienna oznaczajoca mniej wiecej jak mocno mam zapiac znak wodny
            //oczywiscie czym wiekrza ta wartosc tym widoczniejsze sa zmiany w obrazie
            //double kp3=60;//zmieniam na atrybut metody 'zag'
            
            if(kp==1)            
            {if(sum>zag)
            {
                roz=0;
                block2=block0;break;
            }}
            else
            {if(sum<(-1)*zag)
            {
                roz=0;
                block2=block0;break;
            }}
            
                        
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {
                int c2=0;
                while(true)
                {
                double p=((int)(Math.random()*(roz*2+1)))-roz;//losuje liczby <-roz,roz>   
                double p0 = tab[i][j]+p;
                
                if(p0 >=0 && p0<=255)
                {
                    blockPom[i][j]=p0;
                    break;
                }else{
                    c2++;
                    if(c2>20 && roz<255){roz++;}
                }
                }                
            }
            //
            block0 = BlockChange(blockPom);
            
            c1++;
            if(c1>20 && roz<255){roz++;c1=0;}
            }
            /////////////////////////////////////////////////////////////////////////////////////            
            //////////////////wykonuje IDCT
            int[][] block3 = this.DoubleToInt(this.BlockRetrieve(block2));
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {
                int[] col = ic.getImageColor(blocks[h][w][i1][i2][i][j]);
                double[] il = getColor(col[0],col[1],col[2]);             
                il[0]=block3[i][j];
                int[] col2 = retrieveColor(il);            
                Color c = new Color(col2[0],col2[1],col2[2]);
                image.setRGB(w*w2*N+i2*N+j,h*h2*N+i1*N+i, c.getRGB());
            }
            
        }            
            System.out.println("i1: "+i1+" | i2: "+i2);
        }
                
        
        System.out.println("end");        
        
        return image;
    }
    
    //odczytuje obraz
    public BufferedImage ReadFromImage(BufferedImage im,int height,int width)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        //////////
        int h1=im.getHeight(),w1=im.getWidth();        
        int h2=height,w2=width;//do obrazu wpisywanego
        
        int h1_2=h1/N,w1_2=w1/N;
        int h2_2=h2*N,w2_2=w2*N;
        int h2_3=h1/h2_2,w2_3=w1/w2_2;//wielkosc zestawow blokow
        
        //jesli nie ma mozliwosc wpisac choc 1 obrazu to wychodze
        if(h2_3==0 || w2_3==0){
            System.out.println("h2_3: "+h2_3+" | w2_3: "+w2_3);
            return null;
        }
        
        int[][][][][][] blocks=new int[h2_3][w2_3][h2][w2][N][N];
        
        ////////////////////////////////////////////
        System.out.println("1: "+blocks.length+" | 2: "+blocks[0].length+" | 3: "+blocks[0][0].length+" | 4: "+blocks[0][0][0].length+" | 5: "+blocks[0][0][0][0].length+" | 6: "+blocks[0][0][0][0][0].length);
        
        //najpierw przechodze po zestawach
        for(int h=0;h<h2_3;h++)
        for(int w=0;w<w2_3;w++)
        {
            //potem przechodze po 1 zestawie o liczbie blokow takiej samej jak wpisywany obraz
        for(int i1=0;i1<h2;i1++)
        for(int i2=0;i2<w2;i2++)
        {
            //na koniec przechodze po bloku
            for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                blocks[h][w][i1][i2][i][j] = im.getRGB(w*w2*N+i2*N+j,h*h2*N+i1*N+i);
            }
        }        
        }
        
        ////////////////////////////////////////////////////
        
        
        double[][] tab = new double[N][N];
        double q2 = h2_3*w2_3;
        
        for(int i1=0;i1<h2;i1++)
        for(int i2=0;i2<w2;i2++)
        {        
            double q1=0;
            
        for(int h=0;h<h2_3;h++)
        for(int w=0;w<w2_3;w++)
        {
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {
                int[] col = ic.getImageColor(blocks[h][w][i1][i2][i][j]);
                double[] il = getColor(col[0],col[1],col[2]);  
                tab[i][j]=il[0];
            }
        
            double sum=0;
        
        double[][] block2 = BlockChange(tab);
        double p1=0,p2=0;
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        if(!(i==0 && j==0))
        {
            /*if(block2[i][j]>0)
            {p1++;}
            else
            {p2++;}*/
            sum+=block2[i][j];
        }
        //double size1=p2/(p1+p2);//dodatnie
        
        //////////////////////////////////////
        if(sum>0)
        {
            q1++;//jesli liczba dodatnich wartosci transformaty jest wiekrza to odczytuje bit 1
        }
        }
        
        
        Color c;
        double q3=q1/q2;
        if(q3<=0.5)
        {c = new Color(0,0,0);}
        else
        {c = new Color(255,255,255);}
        
        image.setRGB(i2,i1, c.getRGB());
        
        System.out.println("i1: "+i1+" | i2: "+i2+" | q1: "+q1+" | q2: "+q2+" | q3: "+q3);
        }
                
        return image;
    }
    
    
    
    public static void main(String args[]) {
     
        try{
            DCT c = new DCT();
            //////////////////////////////
            File plik1 = new File("panda.bmp");
            BufferedImage image0 = ImageIO.read(plik1);
            
            BufferedImage imagePom = ImageIO.read(new File("some.bmp"));
            //BufferedImage image2 = c.ic.deepCopy(image);
            
            //szerokosc / wysokosc            
            BufferedImage image = c.ic.resizeImage(c.ic.deepCopy(image0),1024,768);
            
            int size=8;
            //BufferedImage image2 = c.ic.resizeImage(c.ic.deepCopy(image0), size,size);
            
            double zag=100;
            BufferedImage image3 = c.WriteToImage(image,imagePom,zag); 
            
            
            //ImageIO.write(image2, "BMP", new File("cos.bmp"));
            /////////////
            //tutaj wykonuje kompresje jpg
            
            ImageIO.write(image, "BMP", new File("obraz orginalny!.bmp"));
            ImageIO.write(image3, "BMP", new File("obraz zmieniony!.bmp"));
            
            for(int st=100;st>0;st-=5)
            {
            //BufferedImage image3p = c.ic.resizeImage(c.ic.deepCopy(image3), image3.getWidth(),image3.getHeight());
            //c.ic.saveImageAsJPEG(image3p, st, "obraz2-"+st+".jpg");
            c.ic.saveImageAsJPEG(image3, st, "obraz2.jpg");
            File plik2 = new File("obraz2.jpg");
            BufferedImage image4 = ImageIO.read(plik2);
            
            //BufferedImage image4 = image3;
            /////////////
            //new ImageJFrame(image3);
            
            //BufferedImage image5 = c.ReadFromImage(image3, image2.getHeight(), image2.getWidth());
            BufferedImage image5 = c.ReadFromImage(image4, imagePom.getHeight(), imagePom.getWidth());
            
            BufferedImage image6 = c.ic.resizeImage(image5, image5.getWidth(),image5.getHeight());
            
            ImageIO.write(image6, "BMP", new File("odczytany-obraz-"+st+".bmp"));
            }
            /*new ImageJFrame(imagePom,"zapisywany obraz");
            new ImageJFrame(image5,"odczytany obraz");
            
            new ImageJFrame(image,"obraz1 bez kompresji");
            new ImageJFrame(image4,"obraz2 z kompresja");*/
            
            
            //////////////
            /*BufferedImage im0 = c.ic.ImageToBlackAndWhite(image2);
            new ImageJFrame(im0);*/
            
            /*int[][] tab = c.ImageToTable(image2);
            BufferedImage im4 = c.TableToImage(tab);
            new ImageJFrame(im4);*/
            /////////////
            
            //new ImageJFrame(image);
            
        }catch(Exception e){System.out.println("error: "+e);}
    }
}
