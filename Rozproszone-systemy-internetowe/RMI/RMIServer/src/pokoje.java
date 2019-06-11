import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class pokoje extends UnicastRemoteObject implements RMI {
    public static List<Room> pokoje = new ArrayList<>();
    public static String sciezka="E:\\pokoj.dat";
    
    public pokoje() throws RemoteException, IOException{
        
            //otwieram swoj plik i wczytuje dane do pliku z obiektami
            
            /*ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(sciezka));
            List<String> pokoje1 = new ArrayList<>();
            List<Room> pokoje2 = new ArrayList<>();
            pokoj pok =new pokoj();
            StackRoom pom2;
            
            try {              
                File plik1 = new File("p0.txt");
                Scanner skaner1 = new Scanner(plik1);
                while (skaner1.hasNextLine()==true) {
                    String pom=skaner1.nextLine();
                    pokoje1.add(pom);

                } } catch (Exception e) {System.out.println("blad ?: "+e);}

                try{
                    for(int i=0;i<pokoje1.size();i++)
                    {
                    String[] tab=pokoje1.get(i).split(":",3);
                    switch (Integer.parseInt(tab[0])) {
                    case 1 : {Room nowy1=pok.getRoom("SmallRoom",Integer.parseInt(tab[1]),tab[2]);pokoje2.add(nowy1);}break;
                    case 2 : {Room nowy2=pok.getRoom("MediumRoom",Integer.parseInt(tab[1]),tab[2]);pokoje2.add(nowy2);}break;
                    case 3 : {Room nowy3=pok.getRoom("LargeRoom",Integer.parseInt(tab[1]),tab[2]);pokoje2.add(nowy3);}break;
                    }
                    }
                pom2=new StackRoom(pokoje2);
                wy1.writeObject(pom2);wy1.close();
                }catch (Exception e) {System.out.println("blad2 ?: "+e);}*/
                
                
        /////////////////////czytam z pliku z obiektem posiadajacym liste obiektow
            
            try{
            ObjectInputStream we = new ObjectInputStream(new FileInputStream(sciezka));
            StackRoom pom3 = (StackRoom) we.readObject();
            List<Room> pokoje3 = pom3.pokoje;
            pokoje=pokoje3;//<-- zapisyja do aktualnego obieku pokoje
            
            for(int i=0;i<pokoje3.size();i++)
            {
                if(!pokoje3.get(i).getosoba().equals(" "))
                System.out.println(pokoje3.get(i).getnr()+" "+pokoje3.get(i).getcena()+" "+pokoje3.get(i).getosoba()+"     "+pokoje3.get(i).pisz());
                else
                System.out.println(pokoje3.get(i).getnr()+" "+pokoje3.get(i).getcena()+" "+pokoje3.get(i).getosoba()+"          "+pokoje3.get(i).pisz());
            }
            we.close();
            }catch(Exception e){System.out.println("blad? :  "+e);}
            
    }
    

    @Override
    public List<String> get() throws RemoteException {
    List<String> lista = new ArrayList<String>();
    
             for(int i=0;i<pokoje.size();i++)
             {
                 lista.add((i+1)+". "+pokoje.get(i).getnr()+" "+pokoje.get(i).getcena()+" "+pokoje.get(i).getosoba());
             }


     return lista;
    }
    
    @Override
    public List<String> wolne() throws RemoteException {
    List<String> lista = new ArrayList<String>();
    
             for(int i=0;i<pokoje.size();i++)
             {
             if(pokoje.get(i).getosoba().equals(" "))
             {lista.add((i+1)+". "+pokoje.get(i).getnr()+" "+pokoje.get(i).getcena()+" "+pokoje.get(i).getosoba());}
             }
             
     return lista;
    }

    @Override
    public List<String> zajete() throws RemoteException {
    List<String> lista = new ArrayList<String>();
    
        for(int i=0;i<pokoje.size();i++)
             {
             if(!pokoje.get(i).getosoba().equals(" "))
             {lista.add((i+1)+". "+pokoje.get(i).getnr()+" "+pokoje.get(i).getcena()+" "+pokoje.get(i).getosoba());}
             }
             

     return lista;
    }
    
    @Override
    public void zwolnij(int numer) throws RemoteException //numer liczy od 1
    {       int n2=numer-1;
    
            try{pokoje.get(n2).setosoba(" ");
            actual();
            }catch (Exception e) {}
    }
    
   @Override
    public String wynajmij(int numer,String osoba) throws RemoteException //numer liczy od 1 
    {
       int n2=numer-1;
        
            try{
            if(pokoje.get(n2).getosoba().equals(" "))
            {
                pokoje.get(n2).setosoba(osoba);
                actual();
            }else{return "pokoj jest aktualnie zajety";}
            }catch (Exception e) {}
            
        
        return "";
    }
   
   
  public void actual() throws FileNotFoundException, IOException 
   {     
       try{
       ObjectOutputStream wy1 = new ObjectOutputStream(new FileOutputStream(sciezka));
       wy1.writeObject(new StackRoom(pokoje));
       }catch (Exception e) {}
       
   }

    @Override
    public int tab() throws RemoteException 
    {
        return pokoje.size();
    }

}
