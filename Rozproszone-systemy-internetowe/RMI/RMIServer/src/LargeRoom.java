
import java.io.Serializable;


public class LargeRoom implements Room,Serializable
{
    int nr;
    String osoba;
    public int cena=800;
    LargeRoom(int nr,String osoba) {
        this.nr=nr;
        this.osoba=osoba;
    }
    
    @Override
    public int getnr() {return nr;}

    @Override
    public int getcena() {return cena;}

    @Override
    public String getosoba() {return osoba;}

    @Override
    public void setosoba(String osoba) {this.osoba=osoba;}
    
    @Override
    public String pisz() {return "duzy pokoj";}
    
}
