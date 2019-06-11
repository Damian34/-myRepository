import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RMIClient {
    public static void main(String args[])
    {
        RMIClient client = new RMIClient();
        client.connectServer();
        
    }

    private void connectServer() {
    
        try{
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
               RMI rmi = (RMI) reg.lookup("server");
               System.out.println("Connected to server");
               String naglowek="   pokoj cena imie";
               int opcja=-1,opcja2=-1;
               int pokoj = 0;
               int pom;
               
               while(true)
               {
               ////////////////////////////////
                   
               System.out.println("prosze wybrać opcje :");
               System.out.println("1. pokaz wszystkie pokoje");
               System.out.println("2. pokaz wolne pokoje");
               System.out.println("3. pokaz zajete pokoje");
               System.out.println("4. wyjście");
                   
               while(true)
               {
               pom=0;
               Scanner p1 = new Scanner(System.in); 
               try{opcja = Integer.parseInt(p1.nextLine());}
               catch(NumberFormatException e){pom=1;}
               if(opcja>=1 && opcja <= 4){break;}else{pom=1;}
               
               if(pom==1){System.out.println("Błąd ,wpisz jeszcze raz:");pom=0;}
                                              
               }
               
               List<String> text = new ArrayList<String>();
               
               if(opcja==1){text = rmi.get();}
               if(opcja==2){text = rmi.wolne();}
               if(opcja==3){text = rmi.zajete();}
               if(opcja==4){break;}
               
               System.out.println(naglowek);
               for(int i=0;i<text.size();i++)
               {
                   System.out.println(text.get(i));
               }
               /////////////////////////////////
               
               System.out.println("prosze wybrać pokoj : ");
               
               
                    while(true)
                    {
                       pom=0;
                       Scanner p1 = new Scanner(System.in); 
                       try{pokoj = Integer.parseInt(p1.nextLine());}
                       catch(NumberFormatException e){pom=1;}
                       if(pokoj>0 && pokoj <= rmi.tab()){break;}else{pom=1;}
                       //pokoj=pokoj-1;
                       if(pom==1){System.out.println("Błąd ,wpisz jeszcze raz:");pom=0;}
                                              
                   }
                    
                    //po wybraniu pokoju wybieram opcje:
                    
                    while(true)
                    {
                    System.out.println("prosze wybrać opcje :");
                    System.out.println("1. rezerwuj pokoj");
                    System.out.println("2. zwolnij pokoj");
                    System.out.println("3. wyjście");
                    
                    
                    while(true)
                    {
                    pom=0;
                    Scanner p1 = new Scanner(System.in); 
                    try{opcja2 = Integer.parseInt(p1.nextLine());}
                    catch(NumberFormatException e){pom=1;}
                    if(opcja2>=1 && opcja2 <= 3){break;}else{pom=1;}

                    if(pom==1){System.out.println("Błąd ,wpisz jeszcze raz:");pom=0;}
                    }
                    
                    
                    if(opcja2==1)
                    {
                        String osoba="";
                        System.out.println("prosze podac nazwe osoby :");
                        Scanner p1 = new Scanner(System.in); 
                        try{osoba = p1.nextLine();}
                        catch(NumberFormatException e){}
                        
                    System.out.println(rmi.wynajmij(pokoj,osoba));
                    }
                    
                    if(opcja2==2)
                    {
                        rmi.zwolnij(pokoj);
                    }
                    
                    if(opcja2==3)
                    {
                        break;
                    }
                    }
               
               }
               
        }catch(Exception e){
            System.out.println(e);
        }
    }
}