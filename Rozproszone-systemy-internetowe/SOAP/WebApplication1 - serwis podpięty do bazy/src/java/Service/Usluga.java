/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;

/**
 *
 * @author dami
 */
@WebService(serviceName = "Usluga")
@Stateless()
public class Usluga {
    private final String host = "jdbc:derby://localhost:1527/Omega_14";
    private final String uzytkownik = "Omega_14";
    private final String haslo = "Omega_14";
    
    
    private String szyfr(String text,boolean A) //true szyfruje ,a false deszyfruje
    {
        //tab służy do zaszyfrowania,a tab2 do odszyfrowania
        int[] tab={166,112,376,180,38,275,175,301,115,5,309,271,349,304,371,229,295,137,64,61,33,394,110,153,2,374,129,319,328,56,225,141,116,122,321,227,253,250,209,323,278,23,91,265,176,348,142,263,138,338,232,75,313,48,182,136,359,279,54,204,8,97,118,237,89,1,100,119,343,111,345,254,283,354,212,178,72,366,302,83,314,3,104,355,71,395,255,6,306,21,35,277,286,11,300,211,350,76,30,260,222,261,296,353,152,344,148,32,196,389,40,220,341,192,335,96,179,367,320,18,289,274,41,101,332,31,55,252,272,68,224,157,307,103,85,217,57,291,383,165,198,364,53,352,117,108,14,15,193,208,218,42,329,143,322,186,190,210,159,310,4,288,226,49,139,169,184,365,236,66,151,317,12,334,381,384,303,28,177,70,360,398,284,382,29,215,149,170,393,203,268,269,197,206,267,163,58,46,241,174,246,114,19,84,144,264,146,156,125,27,34,201,233,134,305,24,160,131,128,88,361,259,230,346,205,293,214,292,78,195,297,213,315,36,188,392,373,44,257,370,235,372,298,347,140,123,285,318,50,25,162,147,133,280,369,245,325,95,39,385,106,60,37,391,256,127,26,239,340,158,379,357,399,294,207,81,396,221,77,154,47,168,342,281,185,73,199,135,287,86,266,79,132,63,121,312,397,107,113,390,62,105,238,377,202,270,223,337,145,333,161,187,228,311,251,150,189,356,67,93,276,82,231,339,316,244,363,126,59,10,155,9,247,387,324,378,242,240,290,351,17,120,171,94,45,16,282,200,98,74,336,43,358,216,243,80,7,258,69,13,368,124,51,90,249,65,262,181,299,194,22,130,92,99,219,326,308,191,273,183,375,167,164,87,362,386,380,330,234,248,327,109,331,388,102,172,52,20,173,0};
        int[] tab2={399,65,24,81,160,9,87,356,60,331,329,93,172,359,146,147,345,340,119,202,397,89,370,41,215,249,266,209,177,184,98,125,107,20,210,90,233,262,4,258,110,122,151,351,237,344,197,280,53,163,248,362,396,142,58,126,29,136,196,328,261,19,300,293,18,365,169,318,129,358,179,84,76,285,349,51,97,278,228,291,355,275,321,79,203,134,289,383,219,64,363,42,372,319,343,257,115,61,348,373,66,123,394,133,82,301,260,297,145,391,22,69,1,298,201,8,32,144,62,67,341,294,33,245,361,208,327,265,218,26,371,217,292,252,213,287,55,17,48,164,244,31,46,153,204,308,206,251,106,186,315,170,104,23,279,330,207,131,269,158,216,310,250,195,382,139,0,381,281,165,187,342,395,398,199,6,44,178,75,116,3,367,54,379,166,284,155,311,234,316,156,377,113,148,369,229,108,192,140,286,347,211,304,189,59,224,193,274,149,38,157,95,74,231,226,185,353,135,150,374,111,277,100,306,130,30,162,35,312,15,222,322,50,212,388,240,168,63,302,267,337,198,336,354,325,255,200,332,389,364,37,314,127,36,71,86,264,238,357,221,99,101,366,47,205,43,290,194,190,191,305,11,128,378,121,5,320,91,40,57,253,283,346,72,182,246,92,288,161,120,338,137,227,225,273,16,102,230,242,368,94,7,78,176,13,214,88,132,376,10,159,313,295,52,80,232,324,171,247,27,118,34,154,39,334,256,375,390,28,152,387,392,124,309,173,114,350,307,49,323,268,112,282,68,105,70,223,243,45,12,96,339,143,103,73,83,317,271,352,56,180,220,384,326,141,167,77,117,360,254,239,14,241,236,25,380,2,303,335,270,386,174,183,138,175,259,385,333,393,109,299,263,235,188,21,85,276,296,181,272};
        String pom="";
        
        if(A)//A=true
        {            
        for(int i=0;i<text.length();i++)
        {pom=pom+(char)tab[(int)text.charAt(i)];}
        return pom;
        }
        else//A=false
        {        
        for(int i=0;i<text.length();i++)
        {pom=pom+(char)tab2[(int)text.charAt(i)];}
        return pom;
        }
        
    }
    
    
    private List<String> BaseWriter(int kolumny,String zapytanie)
    {
        List<String> lista = new ArrayList<String>();
        
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            
            //Statement stmt = con.createStatement();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //ResultSet jest tylko dla operacji SELECT
            ResultSet rs = stmt.executeQuery(zapytanie);
            while(rs.next()) {
                String pom="";
                for(int i=1;i<=kolumny;i++){pom=pom+rs.getString(i)+" ";}
                
                //lista.add(rs.getInt("id_książka")+" "+rs.getString("tytuł") + " " + rs.getString("autor"));
                lista.add(pom);
                //lista.add(rs.getInt(1)+" "+rs.getString(2) + " " + rs.getString(3));
            }
              
            rs.close();
            stmt.close();
            con.close();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "Add")
    public int Add(@WebParam(name = "a") int a, @WebParam(name = "b") int b) {
        //TODO write your implementation code here:
        return a+b;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksAvailable")
    public List<String> BooksAvailable() {
        //zwraca liste dostępnych książek
        //String[] pom={"id_książka","tytuł","autor"};
        List<String> lista=BaseWriter(3,"SELECT id_książka,tytuł,autor FROM książka WHERE id_książka not in (SELECT id_książka FROM wypożyczenie WHERE status!='zwrócona') ");
        
        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderAll")
    public List<String> ReaderAll(@WebParam(name = "text") String text) { //blad z Response przy przekazywaniu sygnału
        //////////
            String pom="";
            if(text!=null)
            pom=text;
            
            String pom2="";
            String pom3=" LOWER(imie) like LOWER('%"+pom+"%') OR LOWER(nazwisko) like LOWER('%"+pom+"%')";
            boolean n1;
            try{
                Integer.parseInt(pom);//sprawdzam czy znak jest liczba
                n1=true;
            }catch(Exception e){n1=false;}
            if(n1==true)
            {
                pom2=pom2+" id_czytelnik="+pom+" OR ";
            }
            String pom4="SELECT * FROM czytelnik WHERE"+pom2+pom3;
        //////////
        List<String> lista=BaseWriter(3,pom4);
        
        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksAll")
    public List<String> BooksAll(@WebParam(name = "text") String text) {
        
        ////////////////////
        String pom="";
        if(text!=null)
        pom=text;
        
            String pom2="";
            String pom3=" LOWER(tytuł) like LOWER('%"+pom+"%') OR LOWER(autor) like LOWER('%"+pom+"%')";
            boolean n1;
            try{
                Integer.parseInt(pom);//sprawdzam czy znak jest liczba
                n1=true;
            }catch(Exception e){n1=false;}
            if(n1==true)
            {
                pom2=pom2+" id_książka="+pom+" OR ";
            }
            String pom4="SELECT id_książka FROM książka WHERE"+pom2+pom3;
        //////////////////
        
        
        String p ="SELECT k.id_książka,tytuł,autor,status FROM książka k,wypożyczenie w WHERE k.id_książka=w.id_książka AND w.status!='zwrócona' AND k.id_książka in ("+pom4+")";
        String p2="SELECT k.id_książka,tytuł,autor,' ' FROM książka k,wypożyczenie w WHERE k.id_książka=w.id_książka AND w.status='zwrócona' AND k.id_książka in ("+pom4+")";
        String p3="SELECT k.id_książka FROM książka k,wypożyczenie w WHERE k.id_książka=w.id_książka";
        String p4="SELECT id_książka,tytuł,autor,' ' FROM książka k WHERE k.id_książka not in"+"("+p3+") AND k.id_książka in ("+pom4+")";
        
        List<String> lista=BaseWriter(4,"("+p+")"+"UNION"+"("+p2+")"+"UNION"+"("+p4+")");
        
        return lista;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksBorrowed")
    public List<String> BooksBorrowed() { //blad z Response przy przekazywaniu sygnału
        
        String pom="SELECT k.id_książka,tytuł,autor,' - ',c.id_czytelnik,imie,nazwisko FROM książka k,wypożyczenie w,czytelnik c WHERE w.id_książka=k.id_książka AND w.id_czytelnika=c.id_czytelnik AND (status='wypożyczona' OR status='przetrzymana') ";
        
        List<String> lista=BaseWriter(7,pom);
        
        return lista;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BooksHeld")
    public List<String> BooksHeld() {
        
        String pom="SELECT k.id_książka,tytuł,autor,' - ',c.id_czytelnik,imie,nazwisko FROM książka k,wypożyczenie w,czytelnik c WHERE w.id_książka=k.id_książka AND w.id_czytelnika=c.id_czytelnik AND status='przetrzymana' ";
        
        List<String> lista=BaseWriter(7,pom);
        
        return lista;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderBorrower")
    public List<String> ReaderBorrower() {
        
        String pom="SELECT c.id_czytelnik,c.imie,c.nazwisko,'-',count(k.id_książka),'szt' FROM czytelnik c,wypożyczenie w,książka k WHERE c.id_czytelnik=w.id_czytelnika AND w.id_książka=k.id_książka AND w.status!='zwrócona' group by c.id_czytelnik,c.imie,c.nazwisko";
            
        List<String> lista=BaseWriter(6,pom);
        
        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderAdd")
    @Oneway
    public void ReaderAdd(@WebParam(name = "imie") String imie, @WebParam(name = "nazwisko") String nazwisko) {
        
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement();
            
            int nr=Integer.parseInt(BaseWriter(1,"SELECT MAX(id_czytelnik) FROM czytelnik").get(0).replace(" ",""));
            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO czytelnik(id_czytelnik,imie, nazwisko)VALUES(?,?,?)");
            pstmt.setInt(1, nr+1);
            pstmt.setString(2, imie);
            pstmt.setString(3, nazwisko);
            pstmt.executeUpdate();
            pstmt.close();
            
            stmt.close();
            con.close();
        }catch(SQLException e){}
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookAdd")
    @Oneway
    public void BookAdd(@WebParam(name = "tytul") String tytul, @WebParam(name = "autor") String autor) {
        //niech zwraca String!
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement();
            
            int nr=Integer.parseInt(BaseWriter(1,"SELECT MAX(id_książka) FROM książka").get(0).replace(" ",""));
            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO książka(id_książka,tytuł,autor)VALUES(?,?,?)");
            pstmt.setInt(1, nr+1);
            pstmt.setString(2, tytul);
            pstmt.setString(3, autor);
            pstmt.executeUpdate();
            pstmt.close();
            
            stmt.close();
            con.close();
        }catch(SQLException e){}
    }

    //BookGive (idksiazka,idczytelnik,(int)czasdni)(zwraca string ,jesli sie powiodlo to "" 
    //,a jesli nie to np "ta ksiozke ma aktualnie inna osoba",lub "nie ma ksiazki o tym nr" lub itd)

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookGive")
    public String BookGive(@WebParam(name = "idk") int idk, @WebParam(name = "idc") int idc, @WebParam(name = "czas") int czas) {
        //poprawic moze byc wiecej wypozyczen z ksiązko o podanym id
        //ale tylko jedna moze być zwrócona
        try{
        Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        int c1=idc,k1=idk;
            String pom="SELECT count(*) FROM wypożyczenie WHERE "+"id_książka="+k1+" AND id_czytelnika="+c1+" AND status!='zwrócona'";
            String pom2="SELECT count(*) FROM książka WHERE id_książka="+k1;
            String pom3="SELECT count(*) FROM czytelnik k WHERE id_czytelnik="+c1;
                        
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);
            rs1.close();
            
            ResultSet rs2 = stmt.executeQuery(pom2);
            rs2.last();
            int p1 = rs2.getInt(1);//jesli nie ma takiej książki to p1=0 ,w przeciwnym wypadku p1=1
            rs2.close();
            if(p1==0){return "nie ma takiej książki";}
            
            ResultSet rs3 = stmt.executeQuery(pom3);
            rs3.last();
            int p2 = rs3.getInt(1);//jesli nie ma takiej osoby to p2=0 ,w przeciwnym wypadku p2=1
            rs3.close();
            if(p2==0){return "nie ma takiej osoby";}
            
                        
            if(b==0)//tzn ze nikt nie ma tej książki,ewentualnie została oddana
            {
                /////////////////////////////tutaj bedzie dodawanie tej książki
                Calendar c2=Calendar.getInstance();
                Calendar c3=Calendar.getInstance();

                c3.add(5, czas);
                int nr=Integer.parseInt(BaseWriter(1,"SELECT MAX(id_wypożyczenie) FROM wypożyczenie").get(0).replace(" ",""));
                Date D2 = new Date(c2.getTimeInMillis());//nie dziala??
                Date D3 = new Date(c3.getTimeInMillis());
                
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO wypożyczenie(id_wypożyczenie,id_czytelnika,id_książka,status,data_wypożyczenia,termin_zwrotu)VALUES(?,?,?,?,?,?)");
                
                pstmt.setInt(1, nr+1);
                pstmt.setInt(2, idc);
                pstmt.setInt(3, idk);
                pstmt.setString(4, "wypożyczona");
                pstmt.setDate(5, D2);
                pstmt.setDate(6, D3);
                pstmt.executeUpdate();
                pstmt.close();
                stmt.close();
                con.close();
                ///////////////////////////
                
                return "";//ta książka jest dostepna";
            }else{
                return "tą książke ma aktualnie inna osoba";
            }
        }catch(SQLException e){return "blad: "+e;}
        
        //return "cos";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AllStatus")
    public List<String> AllStatus() {
        
        String pom = "SELECT c.id_czytelnik,c.imie,c.nazwisko,'-', k.id_książka,k.tytuł,'-',w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu FROM wypożyczenie w,książka k,czytelnik c WHERE w.id_książka=k.id_książka AND c.id_czytelnik=w.id_czytelnika";
        
        List<String> lista=BaseWriter(8,pom);
        
        return lista;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "BookReturn")
    public String BookReturn(@WebParam(name = "numer") int numer) {
        //pobieram numer z tablicy wypożyczenie
        //nie musze sprawdzać czy istnieje czytelnik lub książka
        //bo nie powinno być możliwości by można było unoć książke ,która jest nie zwrócona,lub klienta ktory ma ksiązki do oddania
        //ale powinnienem sprawdzić czy istnieje taki rekord w tablicy wypożyczenia
        
        //moge sprawdzić czy jest oddana w terminie lub nie,tzn zwracam status z rekordu,ale nie musze
        //podmieniam wartość statusu na zwrócona
            try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            String pom = "Select count(*) FROM wypożyczenie WHERE id_wypożyczenie="+numer;
            
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma takiego wypozyczenia i 1 jesli jest bo moze byc tylko 1 id
            rs1.close();
            if(b==0){return "brak wypożyczenia o tym numerze";}
            
            
            
            String pom2 = "Select status FROM wypożyczenie WHERE id_wypożyczenie="+numer;
            String status = BaseWriter(1,pom2).get(0);
            
            
            PreparedStatement pstmt = con.prepareStatement("UPDATE wypożyczenie a SET status='zwrócona' where id_wypożyczenie="+numer);
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            con.close();
            return "książka była "+status;
            }catch(SQLException e){return "blad: "+e;}
        
        //return "błąd";
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "RemoveBook")
    public String RemoveBook(@WebParam(name = "numer") int numer) {
        //metoda przyjmuje jako parametr numer książki
        //jesli ksiązka nie istnieje w tabeli to konczy metode
        //ksiazki nie powinno sie dać usunąć jak ,w tabeli wypożyczenia jest nie zwrócona
        
        //przed usunieciem danej ksiażki pobrać nr rekordu z tabeli wypożyczenie i pod id książki wstawić 0
        //wtedy mozna usnąć książke z tabeli książka
        
        //pierw sprawdzam czy istnieje ksiązka by moc sprawdzic czy istnieje wypozyczenie
        //moze być wiecej niż 1 ksiązka w tabeli wypożyczenie
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //sprawdzam czy istnieje taka książka
            String pom = "Select count(*) FROM książka WHERE id_książka="+numer;
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma książki ,i b=1 jeśli jest 
            rs1.close();
            if(b==0){return "brak takiej książki";}
            
            //pobieram status dla tej książki i sprawdzam czy jest dostepna
            String pom2 ="Select w.status FROM książka k, wypożyczenie w WHERE k.id_książka=w.id_książka AND k.id_książka="+numer;
                    
            ResultSet rs2 = stmt.executeQuery(pom2);
            while(rs2.next())//jesli istnieje status
            {
                
            String status = rs2.getString(1);
            if(!status.equals("zwrócona")){return "ta ksiązka jest "+status;}
            }
            rs2.close();
            //pobieram nr tego wypożyczenia
            String pom3 ="Select w.id_wypożyczenie FROM książka k, wypożyczenie w WHERE k.id_książka=w.id_książka AND k.id_książka="+numer;
                    
            
            ResultSet rs3 = stmt.executeQuery(pom3);
            while(rs3.next())
            {
            int b3 = rs3.getInt(1);//b3 oznacza id wypożyczenia dla danego id książka
            //zmieniam id_ksiązki w tabeli wypożyczenie na 0
            PreparedStatement pstmt = con.prepareStatement("UPDATE wypożyczenie a SET id_książka=0 WHERE id_wypożyczenie="+b3);
            
            pstmt.executeUpdate();
            pstmt.close();
            }
            rs3.close();
            //usuwam ksiązke po podanum id książki
            PreparedStatement pstmt2 = con.prepareStatement("DELETE FROM książka WHERE id_książka="+numer);
            
            pstmt2.executeUpdate();
            pstmt2.close();
            
            stmt.close();
            con.close();
            return "książka zostałą usunięta";
                       
            }catch(SQLException e){return "blad: "+e;}
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "RemoveReader")
    public String RemoveReader(@WebParam(name = "numer") int numer) {
        //funkcja przyjmuje jako parametr numer czytelnika
        //jesli czytelnik nie istnieje w tabeli to konczy metode
        //czytelnika nie powinno sie dać usunąć jak ,gdy posiada książki
        
        //przed usunieciem danej czytelnika pobrać nr rekordu z tabeli wypożyczenie i pod id czytelnika wstawić 0
        //wtedy mozna usnąć czytelnika z tabeli czytelnik
        
        //pierw sprawdzam czy istnieje czytelnik by moc sprawdzic czy istnieje wypozyczenie
        //moze być wiecej niż 1 czytelnik w tabeli wypożyczenie,przpisany do zwróconych książek
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //sprawdzam czy istnieje taka osoba
            String pom = "Select count(*) FROM czytelnik WHERE id_czytelnik="+numer;
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma książki ,i b=1 jeśli jest 
            rs1.close();
            if(b==0){return "brak takiej osoby";}
            
            //pobieram status dla tej osoby i sprawdzam czy jest dostepna
            String pom2 ="Select w.status FROM czytelnik c, wypożyczenie w WHERE c.id_czytelnik=w.id_czytelnika AND c.id_czytelnik="+numer;
                    
            ResultSet rs2 = stmt.executeQuery(pom2);
            while(rs2.next())//jesli istnieje status
            {            
            String status = rs2.getString(1);
            if(!status.equals("zwrócona")){return "u tej osoby jest "+status+" książka";}
            }
            rs2.close();
            //pobieram nr tego wypożyczenia
            String pom3 ="Select w.id_wypożyczenie FROM czytelnik c, wypożyczenie w WHERE c.id_czytelnik=w.id_czytelnika AND c.id_czytelnik="+numer;
                    
            ResultSet rs3 = stmt.executeQuery(pom3);
            while(rs3.next())
            {
            int b3 = rs3.getInt(1);//b3 oznacza id wypożyczenia dla danego id osoby
            //zmieniam id_czytelnik w tabeli wypożyczenie na 0
            PreparedStatement pstmt = con.prepareStatement("UPDATE wypożyczenie a SET id_czytelnika=0 WHERE id_wypożyczenie="+b3);
            
            pstmt.executeUpdate();
            pstmt.close();
            /////////////////
            }
            rs3.close();
            //usuwam ksiązke po podanum id książki
            PreparedStatement pstmt2 = con.prepareStatement("DELETE FROM czytelnik WHERE id_czytelnik="+numer);
            
            pstmt2.executeUpdate();
            pstmt2.close();
            
            
            stmt.close();
            con.close();
            return "czytelnik został usunięty";
                       
            }catch(SQLException e){return "blad: "+e;}
        
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReaderStatus")
    public List<String> ReaderStatus(@WebParam(name = "numer") int numer) {
        
        String pom="SELECT k.id_książka,k.tytuł,'-',w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu FROM wypożyczenie w,książka k WHERE w.id_książka=k.id_książka AND w.id_czytelnika="+numer;
        
        List<String> lista=BaseWriter(8,pom);
        
        return lista;
    }

    //BorrowedStatus

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BorrowedStatus")
    public List<String> BorrowedStatus() {
        
        String pom = "SELECT c.id_czytelnik,c.imie,c.nazwisko,'-', k.id_książka,k.tytuł,'-',w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu FROM wypożyczenie w,książka k,czytelnik c WHERE w.id_książka=k.id_książka AND c.id_czytelnik=w.id_czytelnika AND w.status!='zwrócona'";
        
        List<String> lista=BaseWriter(8,pom);
        
        return lista;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "UpdateAll")
    @Oneway
    public void UpdateAll() {
        //ustawia status niezwróconych książek na przetrzymaną jeśli upłynoł termin
        try{
        Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
        Calendar c4=Calendar.getInstance();
        Date DD3 = new Date(c4.getTimeInMillis());
        
        PreparedStatement pstmt = con.prepareStatement("UPDATE wypożyczenie a SET status='przetrzymana' where termin_zwrotu<'"+DD3+"' AND status='wypożyczona'");
        pstmt.executeUpdate();
        pstmt.close();
        stmt.close();
        con.close();
        }catch(SQLException e){}
    }
    

}
