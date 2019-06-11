
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
public class DCT0 {
    
    private int N=8;
    private double[] C = null;
    
    ///////////
    private ImageConfig ic = new ImageConfig();
    private Bit b = new Bit();
    
    DCT0()
    {
        C = new double[N];
        for(int i=1;i<N;i++)
            C[i]=1;
        C[0]=1/Math.sqrt(2);
    }
        
    
    //w metodzie pobieram blok i stosuje transformate kosinusowo
    public double[][] BlockChange(double[][] tab)
    {
        if(!Check(tab)){return null;}
        
        double[][] tab2=new double[N][N];
        
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
            tab2[u][v]=p1*C[u]*C[v]*sum;            
        }
              
        return tab2;
    }  
    
        
    //w metodzie pobieram blok i stosuje transformate kosinusowo odwrotna
    public double[][] BlockRetrieve(double[][] tab)
    {
        if(!Check(tab)){return null;}               
        
        double[][] tab2=new double[N][N];
        
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
            tab2[x][y]=p1*sum;            
        }
        
        return tab2;
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
    
    //zamieniam wartosci w bloku na calkowite
    public int[][] DoubleToInt(double[][] tab)
    {
        if(!Check(tab)){return null;}
        int[][] tab2 = new int[N][N];
        
        double c;
        for(int i=0;i<N;i++)
        for(int j=0;j<N;j++)
        {
            tab2[i][j]=DoubleRound(tab[i][j]);
        }
        return tab2;
    }
    
    public int DoubleRound(double nr)
    {
        if((nr%1)>0.5){return(((int)nr)+1);}else{return((int)nr);}
    }
    //////////////////////////////////////////////////////////////
        
    /*
    tutaj chialem dzielic obraz na bloki ,zastosowac DCT,dopisywac do nich informacje,zastosowac IDCT
    
    tylko czy jest sposob na zapisanie informacji tak aby poźniej móc ją odzyskać
    bez koniecznosci porownywania danego obrazu z orginalnym
    
    tzn. jak moglby wygladac klucz/sposob ukrycia informacji ?
    
    tzn. jak zmodyfikować blok by zakodować np. bit 0 i 1 
    */
    public BufferedImage WriteToImage(BufferedImage im,List<Byte> msg)
    {
        BufferedImage image = ic.deepCopy(im);
        //////////
        int h1=im.getHeight(),w1=im.getWidth();
        int h2=h1/N,w2=w1/N;
        
        //List<int[][]> list = new ArrayList<>();
        int[][][][] blocks=new int[h2][w2][N][N];
        
        System.out.println("h1: "+h1+" | w1: "+w1);        
        System.out.println("h2: "+h2+" | w2: "+w2+" | h2*w2: "+h2*w2);
        
        for(int h=0;h<h2;h++)
        {
        for(int w=0;w<w2;w++)
        {
            for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                blocks[h][w][i][j] = im.getRGB(w*N+j,h*N+i);
            }            
        }
        }
        
        ////////////////////////////////////
        
        int k0=0;
        for(int h=0;h<h2;h++)
        {
        for(int w=0;w<w2;w++)
        {
        double[][] tab = new double[N][N];
        //pobieram z kazdego elementu bloku barwe
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        {
            /*int[] col = ic.getImageColor(blocks[h][w][i][j]);
            tab[i][j]=col[0];*/
            
            int[] col = ic.getImageColor(blocks[h][w][i][j]);
            double[] il = getColor(col[0],col[1],col[2]);  
            tab[i][j]=il[0];
        }
        
        //wykonuje DCT dla bloku
        double[][] block2 = BlockChange(tab);
        
        //ponizej proboje zmienic liczbe wystopien wartosci o znakach ujemnych/dodatnich
        //po przez lekko ,losowo zmiane kolorow
        double[][] blockPom = new double[N][N];
        int roz=0;        
        ///
        double[][] block0 = new double[N][N];//na poczatku przypisuje orginalne wartosci po DCT
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        {block0[i][j]=block2[i][j];}
        
        int c1=0;
        while(true)
        {
            //szukam liczbe wystopien wartosci dodatnich/ujemnych
            double p1=0,p2=0;
            for(int i=0;i<N;i++)        
            for(int j=0;j<N;j++)
            {
                if(block0[i][j]>0)
                {p1++;}
                else
                {p2++;}
            }
            double size1=p2/(p1+p2);//dodatnie
            //double size2=p1/(p1+p2);//ujemne
            
            if(k0<msg.size())
            {
                //System.out.println("k0: "+k0+" | msg-size: "+msg.size());
            }
            else
            {break;}
            
            if(msg.get(k0)==1)            
            {if(size1>0.5)
            {
                roz=0;
                block2=block0;break;
            }}
            else
            {if(size1<0.5)
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
        
        
        
        //////////////////wykonuje IDCT
        int[][] block3 = this.DoubleToInt(this.BlockRetrieve(block2));
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        {
            /*int[] col = ic.getImageColor(blocks[h][w][i][j]);
            col[0]=block3[i][j];
            Color c = new Color(col[0],col[1],col[2]);
            image.setRGB(w*N+j,h*N+i, c.getRGB());*/
            
            int[] col = ic.getImageColor(blocks[h][w][i][j]);
            double[] il = getColor(col[0],col[1],col[2]);             
            il[0]=block3[i][j];
            int[] col2 = retrieveColor(il);            
            Color c = new Color(col2[0],col2[1],col2[2]);
            image.setRGB(w*N+j,h*N+i, c.getRGB());
        }
        
        k0++;
        
        }
        }
        
        
        ///////////////////////
        /*for(int h=0;h<h2;h++)
        {
        for(int w=0;w<w2;w++)
        {
            //int[][] tab=new int[N][N];
            
            for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                //int rgb = im.getRGB(w*N+j,h*N+i);
                //blocks[h][w][i][j]=rgb;
                
                Color c = new Color(0,0,0);
                image.setRGB(w*N+j,h*N+i, c.getRGB());
            }            
        }
        }*/
        
        return image;
    }
    
    public List<Byte> ReadFromImage(BufferedImage im)
    {
        BufferedImage image = ic.deepCopy(im);
        //////////
        int h1=im.getHeight(),w1=im.getWidth();
        int h2=h1/N,w2=w1/N;
        
        //List<int[][]> list = new ArrayList<>();
        int[][][][] blocks=new int[h2][w2][N][N];
        
        /*System.out.println("h1: "+h1+" | w1: "+w1);        
        System.out.println("h2: "+h2+" | w2: "+w2+" | h2*w2: "+h2*w2);*/
        
        for(int h=0;h<h2;h++)
        {
        for(int w=0;w<w2;w++)
        {
            for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                blocks[h][w][i][j] = im.getRGB(w*N+j,h*N+i);
            }            
        }
        }
        
        ////////////////////////////////////
        
        List<Byte> list = new ArrayList<>();
        
        int k0=0;
        for(int h=0;h<h2;h++)
        {
        for(int w=0;w<w2;w++)
        {
        double[][] tab = new double[N][N];
        //pobieram z kazdego elementu bloku barwe
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        {
            /*int[] col = ic.getImageColor(blocks[h][w][i][j]);
            tab[i][j]=col[0];*/
            
            int[] col = ic.getImageColor(blocks[h][w][i][j]);
            double[] il = getColor(col[0],col[1],col[2]);  
            tab[i][j]=il[0];
        }        
        //wykonuje DCT dla bloku
        double[][] block2 = BlockChange(tab);
        
        //szukam liczbe wystopien wartosci dodatnich/ujemnych
        double p1=0,p2=0;
        for(int i=0;i<N;i++)        
        for(int j=0;j<N;j++)
        {
            if(block2[i][j]>0)
            {p1++;}
            else
            {p2++;}
        }
        double size1=p2/(p1+p2);//dodatnie
        //double size2=p1/(p1+p2);//ujemne
            
        if(size1>0.5)
        {
            list.add((byte)1);//jesli liczba dodatnich wartosci transformaty jest wiekrza to odczytuje bit 1
        }
        else{list.add((byte)0);}
        
        }
        }
        ////
        return list;
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
        
    
    
    //////////////////////////////////////////////////////    
    
    public static void main(String args[]) {
        
        
        /*double[][] tab = {
            {1,2,3,4,5,6,7,8},
            {9,10,11,12,13,14,15,16},
            {17,18,19,20,21,22,23,24},
            {25,26,27,28,29,30,31,32},
            {33,34,35,36,37,38,39,40},
            {41,42,43,44,45,46,47,48},
            {49,50,51,52,53,54,55,56},
            {57,58,59,60,61,62,63,64}
        };*/
        /*
        double[][] tab = {
            {186,198,199,190,182,177,182,197},
            {179,184,183,176,173,172,175,184},
            {188,182,180,178,174,172,171,166},
            {132,130,139,146,151,169,191,201},
            {131,134,137,140,139,139,139,138},
            {153,157,161,172,177,145,89,49},
            {190,178,192,196,120,43,39,47},
            {176,184,187,112,41,39,43,44},
        };*/
        
        
        DCT0 c = new DCT0();
        /*
        double[][] tab = new double[c.N][c.N];
        
        for(int i=0;i<tab.length;i++)
        for(int j=0;j<tab.length;j++)
        {tab[i][j]=128;}
        
        
        //or(int i=0;i<tab.length;i++)
        //for(int j=0;j<tab.length;j++){tab[i][j]+=(int)(Math.random()*100%65);}
        
        
        //////////////////
        System.out.println("poczatkowy blok");
        for(int i=0;i<tab.length;i++)
        {
            for(int j=0;j<tab[i].length;j++)
            {System.out.print(tab[i][j]+" , ");}
            System.out.println();
        }      
        ////////////////
        double[][] tab2 = c.BlockChange(tab);
        
        //tab2[7][7]=255;
                
        System.out.println("\nblok po DCT");
        for(int i=0;i<tab2.length;i++)
        {
            for(int j=0;j<tab2[i].length;j++)
            {
                //double c2=(double)((int)(tab2[i][j]*1000))/1000;
                //System.out.print(c2/1000+" , ");
                
                System.out.print(tab2[i][j]+" , ");
                //System.out.print(Math.log(Math.abs(tab2[i][j]))+" , ");                
            }
            System.out.println();
        }
        
        System.out.println("\n\n");
                
        
        System.out.println("blok po IDCT");
        double[][] tab3 = c.BlockRetrieve(tab2);
        for(int i=0;i<tab3.length;i++)
        {
            for(int j=0;j<tab3[i].length;j++)
            {
                double c2=(double)((int)(tab3[i][j]*1000))/1000;
                System.out.print(c2+" , ");
            }
            System.out.println();
        }
        
        
        
        System.out.println("\n\n");        
        
        System.out.println("blok po IDCT2");
        int[][] tab4 = c.DoubleToInt(tab3);
        for(int i=0;i<tab4.length;i++)
        {
            for(int j=0;j<tab4[i].length;j++)
            {
                System.out.print(tab4[i][j]+" , ");
            }
            System.out.println();
        }
        
        ////////////
        double[][] tab5 = new double[tab4.length][tab4.length];
        for(int i=0;i<tab4.length;i++)
        for(int j=0;j<tab4.length;j++)
            tab5[i][j]=tab4[i][j];
            
        
        System.out.println("\nblok po DCT");
        double[][] tab6 = c.BlockChange(tab5);
        
        for(int i=0;i<tab6.length;i++)
        {
            for(int j=0;j<tab6[i].length;j++)
            {
                System.out.print(tab6[i][j]+" , ");
            }
            System.out.println();
        }
            
        System.out.println("\n\n");
        */
        
        /*double[] p = c.getColor(255, 255, 255);
        c.retrieveColor(p);*/
       
        
        //c.Rand(10);
        int t=1;
        
        BufferedImage image=null;        
        while(true)
        try{
            System.out.println("próba: "+t);t++;
            File plik1 = new File("panda.bmp");
            image = ImageIO.read(plik1);
            
            List<Byte> list = new ArrayList<>();
            
            String p = "msg1";
            for(int i=0;i<p.length();i++)
            c.b.toList(list, c.b.ToBit(p.charAt(i), 8));
            c.b.toList(list, c.b.ToBit('\0', 8));          
            System.out.println("lista dopisywana: "+list);

            
            BufferedImage image2 = c.WriteToImage(image,list);
            //
            System.out.println("kompresuje do jpg");
            c.ic.saveImageAsJPEG(image2, 100, "obraz2.jpg");
            File plik2 = new File("obraz2.jpg");
            BufferedImage image3 = ImageIO.read(plik2);
            
            //
            List<Byte> list2 = c.ReadFromImage(image3);
            String p2="";
            int size = list2.size()/8;
            int[] tab = new int[8];
            for(int i=0;i<size;i++)
            {
                for(int j=0;j<8;j++)
                {tab[j]=list2.get(i*8+j);}
                char c2 = (char)c.b.ToInt(tab);
                p2=p2+c2;
            }
            
            System.out.println("wpisano: "+p);
            System.out.println("odczytano: "+p2);
            
            System.out.println("lista odczytana: "+list2);
            
            double q0=0;
            for(int i=0;i<list.size();i++)
            if(list.get(i)==list2.get(i))
                q0++;
            System.out.println("podobienstwo: "+((q0/list.size())*100)+"%");
            
            
            //new ImageJFrame(image);
            //new ImageJFrame(image2);  
        }catch(Exception e){System.out.println("error: "+e);}
        
        
        
        
        
        
    }
}
