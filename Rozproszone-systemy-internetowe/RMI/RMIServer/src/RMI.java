import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface RMI extends Remote {
    public List<String> get() throws RemoteException ;
    public List<String> wolne() throws RemoteException ;//wolne pokoje
    public List<String> zajete() throws RemoteException ;//zajete pokoje
    public void zwolnij(int numer) throws RemoteException;//zwolnij pokoj
    public String wynajmij(int numer,String osoba) throws RemoteException;//wynajmij pokoj
    public int tab() throws RemoteException;//zwraca liste numerow
}