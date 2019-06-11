
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class StackRoom implements Serializable {
    List<Room> pokoje = new ArrayList<>();  
    public StackRoom(List<Room> nowe)
    {
        this.pokoje=nowe;
    }
    
    public List<Room> get()
    {
        return pokoje;
    }
}
