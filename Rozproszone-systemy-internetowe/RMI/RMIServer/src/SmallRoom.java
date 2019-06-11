
import java.io.Serializable;

public class SmallRoom implements Room,Serializable 
{
    int nr;
    String osoba;
    int cena=500;
    SmallRoom(int nr,String osoba) {
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
    public String pisz() {return "maly pokoj";}
}