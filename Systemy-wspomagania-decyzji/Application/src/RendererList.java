
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
class RenderList extends DefaultListCellRenderer {
    private Color c2=null;
    private int liczba;//liczba colorow ktore generuje
    
        public RenderList(){}
        
        public RenderList(int ile)
        {
            this.liczba=ile;
        }
        
        public RenderList(Color c)
        {
            this.c2=c;
        }
        
        
        public List<Color> colory()
        {
            //wyznaczam liste kolorow
            List<Color> col = new ArrayList<>();
            
            int licz=3*2;//losuje 6 kolorow co przejscie od 0 do 255 ,czy od min do max wartosci koloru
            
            int p0=(int)Math.ceil(((double)liczba)/licz);
            
            
            //w przeciwnym przypadku zwruci biały kolor tzn będzie 0,0,0
            //a i tak wybieram potem kolory
            if(p0==0){p0=1;}
            
            double max = 230;//maksymalna wartosc koloru
            double min = 0;//minimalna wartosc koloru
            
            //double p1=max/(p0+1);
            
            double p1=(max-min)/p0;
            //double p1=max/p0;
            
            //System.out.println("p1="+p1+" , max="+max+" , p0="+p0);
            
            /*for(int i=(int)p1;i<max;i+=p1)
            {
                col.add(new Color(i,0,0));
                col.add(new Color(0,i,0));
                col.add(new Color(0,0,i));
            }*/
            
            for(int i=(int)p1;i<=max;i+=p1)
            {
                col.add(new Color(i,0,0));
                col.add(new Color(0,i,0));
                col.add(new Color(0,0,i));
                
                //////////////jeszcze kilka zestawien kolorow
                //lepiej by bylo wiecej jak mniej
                //col.add(new Color(i,i,i));//ten jest zbyt bliska bialej barwie
                /*col.add(new Color(i,i,0));
                col.add(new Color(i,0,i));
                col.add(new Color(0,i,i));*/
                
                col.add(new Color(i,i,0));
                col.add(new Color(i,0,i));
                col.add(new Color(0,i,i));
            }
            
            return col;
        }
        
        public Color zmien(Color col)
        {
            
            if(col.getBlue()<=130 && col.getGreen()<=130 && col.getRed()<=130)
            {return Color.white;}
            else
            {return Color.black;}
            
        }
        
        
    
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            
            //List<Color> h=colory();
            try{
            c.setBackground( colory().get(index) );
            c.setForeground(zmien(colory().get(index)));
            }catch(Exception e){
            c.setBackground( Color.white );
            System.out.println("blad z kolorem");
            }
            //for(int i=0;i<h.size();i++)
            
                
            /*if ( index % 2 == 0 ) {
                if(c==null)
                c.setBackground( Color.yellow );
                else
                c.setBackground( c2 );
            }
            else {
                c.setBackground( Color.white );
            }*/
            return c;
        }
        
    }