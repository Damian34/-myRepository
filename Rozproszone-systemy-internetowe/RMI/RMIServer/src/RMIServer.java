
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIServer 
{
    
    public static void main(String args[])
    {
        try{
            Registry reg = LocateRegistry.createRegistry(1099) ;
            pokoje nowy=new pokoje();
            reg.rebind("server", (Remote) nowy);
            System.out.println("Server started");
            
            
        }catch(Exception e)
        {
            System.out.println("blad : "+e);
        }
    }
}
