/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service.service;

import Service.service.*;
import Service.Czytelnik;
import Service.Książka;
import Service.Text;
import Service.Wypożyczenie;
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
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dami
 */
@Stateless
@Path("Usluga")
public class Usluga {
    
    private final String host = "jdbc:derby://localhost:1527/Omega_14";
    private final String uzytkownik = "Omega_14";
    private final String haslo = "Omega_14";
    
    //metoda zaminia liste stringow na liste obiektow Text by móc przeformatowac to na xml'a
    private List<Text> zamien(List<String> list)
    {
        List<Text> list2= new ArrayList<Text>();
        
        for(int i=0;i<list.size();i++)
        {
            Text k = new Text();
            k.setId(i);
            k.setNapis(list.get(i));
            
            list2.add(k);
        }
        return list2;
    }
    
    private String zamien2(String txt)
    {
        return "<html><body><div>" + txt + "</body></div></html>";
    }
        
    //metoda pobiera liczbe kolumn do wyswietlenia i zapytanie do bazy i zwraca liste stringow
    private List<String> BaseWriter(int kolumny,String zapytanie)
    {
        List<String> lista = new ArrayList<String>();
        
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
                        
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //ResultSet jest tylko dla operacji SELECT
            ResultSet rs = stmt.executeQuery(zapytanie);
            while(rs.next()) {
                String pom="";
                for(int i=1;i<=kolumny;i++){pom=pom+rs.getString(i)+" ";}
                
                lista.add(pom);
            }
              
            rs.close();
            stmt.close();
            con.close();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    
    //zapisywanie List<Text> do bazy
    
    private void zapisz(List<Text> list)
    {
            try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //usuwam dane z tabeli Text
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Text");
            pstmt.executeUpdate();
            pstmt.close();
            
            for(int i=0;i<list.size();i++)
            {
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO Text(ID,NAPIS)VALUES(?,?)");
            pstmt2.setInt(1,list.get(i).getId());
            pstmt2.setString(2, list.get(i).getNapis());
            pstmt2.executeUpdate();
            pstmt2.close();
            }
            
            stmt.close();
            con.close();
            }catch(SQLException e){return;}
    }
    
    //zapisuje tylko jedno zdanie
    private void zapisz2(String text)
    {
        try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //usuwam dane z tabeli Text
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Text");
            pstmt.executeUpdate();
            pstmt.close();
            
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO Text(Id,Napis)VALUES(?,?)");
            pstmt2.setInt(1,0);
            pstmt2.setString(2, text);
            pstmt2.executeUpdate();
            pstmt2.close();
                        
            stmt.close();
            con.close();
            }catch(SQLException e){return;}
    }    
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String Log(){
        return "";
    }
    
    //dopisac metode do resetowania danych z bazy
    //metody do resta - biblioteka :
    
    @GET
    @Path("BooksAvailable")
    @Produces(MediaType.APPLICATION_XML)//{MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Text> BooksAvailable() {
        //zwraca liste dostępnych książek
        List<String> lista=BaseWriter(3,"SELECT id_książka,tytuł,autor FROM książka WHERE id_książka not in (SELECT id_książka FROM wypożyczenie WHERE status!='zwrócona') ");
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    private List<Text> ReaderAll(String text) { //blad z Response przy przekazywaniu sygnału
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
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
        
    @GET
    @Path("ReaderAll/{text}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> ReaderAll2(@PathParam("text") String text){
        
        return ReaderAll(text);
    }
    
    @GET
    @Path("ReaderAll")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> ReaderAll3(){
        
        return ReaderAll(null);
    }
    
    
    private List<Text> BooksAll(String text) {
        
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
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    @GET
    @Path("BooksAll/{text}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> BooksAll2(@PathParam("text") String text){
        
        return BooksAll(text);
    }
    
    @GET
    @Path("BooksAll")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> BooksAll3(){
        
        return BooksAll(null);
    }
    
    
    @GET
    @Path("BooksBorrowed")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> BooksBorrowed() { //blad z Response przy przekazywaniu sygnału
        
        String pom="SELECT k.id_książka,tytuł,autor,' - ',c.id_czytelnik,imie,nazwisko FROM książka k,wypożyczenie w,czytelnik c WHERE w.id_książka=k.id_książka AND w.id_czytelnika=c.id_czytelnik AND (status='wypożyczona' OR status='przetrzymana') ";
        
        List<String> lista=BaseWriter(7,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    @GET
    @Path("BooksHeld")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> BooksHeld() {
        
        String pom="SELECT k.id_książka,tytuł,autor,' - ',c.id_czytelnik,imie,nazwisko FROM książka k,wypożyczenie w,czytelnik c WHERE w.id_książka=k.id_książka AND w.id_czytelnika=c.id_czytelnik AND status='przetrzymana' ";
        
        List<String> lista=BaseWriter(7,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    
    @GET
    @Path("ReaderBorrower")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> ReaderBorrower() {
        
        String pom="SELECT c.id_czytelnik,c.imie,c.nazwisko,',',count(k.id_książka),'szt' FROM czytelnik c,wypożyczenie w,książka k WHERE c.id_czytelnik=w.id_czytelnika AND w.id_książka=k.id_książka AND w.status!='zwrócona' group by c.id_czytelnik,c.imie,c.nazwisko";
            
        List<String> lista=BaseWriter(6,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    private String ReaderAdd(String imie,String nazwisko) {
        
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
            
            zapisz2("dodano czytelnika");
            return "dodano czytelnika";
        }catch(SQLException e){zapisz2("dodanie czytelnika nie powiodło się");return "dodanie czytelnika nie powiodło się";}
    }
    
    
    //@GET
    @POST
    @Path("ReaderAdd/{imie}/{nazwisko}")
    @Produces(MediaType.TEXT_HTML)
    public String ReaderAdd2(@PathParam("imie") String imie,@PathParam("nazwisko") String nazwisko) {
        
        return zamien2(ReaderAdd(imie,nazwisko));
    }
    
    
    //@GET
    @POST
    @Path("ReaderAdd/{imie}")
    @Produces(MediaType.TEXT_HTML)
    public String ReaderAdd3(@PathParam("imie") String imie) {
        
        zapisz2("dodanie czytelnika nie powiodło się");
        return zamien2("dodanie czytelnika nie powiodło się");
    }
    
    
    //@GET
    @POST
    @Path("ReaderAdd")
    @Produces(MediaType.TEXT_HTML)
    public String ReaderAdd2() {
        
        zapisz2("dodanie czytelnika nie powiodło się");
        return zamien2("dodanie czytelnika nie powiodło się");
    }
    
    
    private String BookAdd(String tytul,String autor) {
        
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
            
            zapisz2("dodano książke");
            return "dodano książke";
        }catch(SQLException e){zapisz2("dodanie książki nie powiodło się");return "dodanie książki nie powiodło się";}
    }
    
    
    //@GET
    @POST
    @Path("BookAdd/{tytul}/{autor}")
    @Produces(MediaType.TEXT_HTML)
    public String BookAdd2(@PathParam("tytul") String tytul,@PathParam("autor") String autor) {
        
        return zamien2(BookAdd(tytul,autor));
    }
    
    
    //@GET
    @POST
    @Path("BookAdd/{tytul}")
    @Produces(MediaType.TEXT_HTML)
    public String BookAdd3(@PathParam("tytul") String tytul) {
        
        zapisz2("dodanie książki nie powiodło się");
        return zamien2("dodanie książki nie powiodło się");
    }
    
    //@GET
    @POST
    @Path("BookAdd")
    @Produces(MediaType.TEXT_HTML)
    public String BookAdd2() {
        
        zapisz2("dodanie książki nie powiodło się");
        return zamien2("dodanie książki nie powiodło się");
    }
    
    
    public String BookGive(Integer idk2,Integer idc2,Integer czas2)
    {
        //poprawic moze byc wiecej wypozyczen z ksiązko o podanym id
        //ale tylko jedna moze być zwrócona
        try{
        int idk=idk2,idc=idc2,czas=czas2;
        
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
            if(p1==0){zapisz2("nie ma takiej książki");return "nie ma takiej książki";}
            
            ResultSet rs3 = stmt.executeQuery(pom3);
            rs3.last();
            int p2 = rs3.getInt(1);//jesli nie ma takiej osoby to p2=0 ,w przeciwnym wypadku p2=1
            rs3.close();
            if(p2==0){zapisz2("nie ma takiej osoby");return "nie ma takiej osoby";}
            
                        
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
                zapisz2("wypożyczoną książke");
                return "wypożyczoną książke";//ta książka jest dostepna";
            }else{
                zapisz2("tą książke ma aktualnie inna osoba");
                return "tą książke ma aktualnie inna osoba";
            }
        }catch(SQLException e){return "blad: "+e;}
        
    }
    
    //@GET
    @POST
    @Path("BookGive/{idk}/{idc}/{czas}")
    @Produces(MediaType.TEXT_HTML)
    public String BookGive2(@PathParam("idk") int idk,@PathParam("idc") int idc,@PathParam("czas") int czas)
    {
        return zamien2(BookGive(idk,idc,czas));
    }
    
    //@GET
    @POST
    @Path("BookGive/{idk}/{idc}")
    @Produces(MediaType.TEXT_HTML)
    public String BookGive2(@PathParam("idk") int idk,@PathParam("idc") int idc)
    {
        return zamien2(BookGive(idk,idc,null));
    }
    
    //@GET
    @POST
    @Path("BookGive/{idk}")
    @Produces(MediaType.TEXT_HTML)
    public String BookGive2(@PathParam("idk") int idk)
    {
        return zamien2(BookGive(idk,null,null));
    }
    
    //@GET
    @POST
    @Path("BookGive")
    @Produces(MediaType.TEXT_HTML)
    public String BookGive2()
    {
        return zamien2(BookGive(null,null,null));
    }
    
    
    
    
    
    @GET
    @Path("AllStatus")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> AllStatus() {
        
        String pom = "SELECT w.id_wypożyczenie,w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu,'|',c.id_czytelnik,c.imie,c.nazwisko,'|', k.id_książka,k.tytuł,k.autor FROM wypożyczenie w,książka k,czytelnik c WHERE w.id_książka=k.id_książka AND c.id_czytelnik=w.id_czytelnika";
        
        List<String> lista=BaseWriter(13,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    public String BookReturn(Integer numer2) {
        //pobieram numer z tablicy wypożyczenie
        //nie musze sprawdzać czy istnieje czytelnik lub książka
        //bo nie powinno być możliwości by można było unoć książke ,która jest nie zwrócona,lub klienta ktory ma ksiązki do oddania
        //ale powinnienem sprawdzić czy istnieje taki rekord w tablicy wypożyczenia
        
        //moge sprawdzić czy jest oddana w terminie lub nie,tzn zwracam status z rekordu,ale nie musze
        //podmieniam wartość statusu na zwrócona
            try{
            int numer =numer2;
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            String pom = "Select count(*) FROM wypożyczenie WHERE id_wypożyczenie="+numer;
            
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma takiego wypozyczenia i 1 jesli jest bo moze byc tylko 1 id
            rs1.close();
            if(b==0){zapisz2("brak wypożyczenia o tym numerze");return "brak wypożyczenia o tym numerze";}
            
            
            
            String pom2 = "Select status FROM wypożyczenie WHERE id_wypożyczenie="+numer;
            String status = BaseWriter(1,pom2).get(0);
            
            
            PreparedStatement pstmt = con.prepareStatement("UPDATE wypożyczenie a SET status='zwrócona' where id_wypożyczenie="+numer);
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            con.close();
            
            zapisz2("książka była "+status);
            return "książka była "+status;
            }catch(SQLException e){zapisz2("blad: "+e);return "blad: "+e;}
        
    }
    
    
    //@GET
    @DELETE
    @Path("BookReturn/{numer}")
    @Produces(MediaType.TEXT_HTML)
    public String BookReturn2(@PathParam("numer") int numer) {
        return zamien2(BookReturn(numer));
    }
    
    //@GET
    @DELETE
    @Path("BookReturn")
    @Produces(MediaType.TEXT_HTML)
    public String BookReturn2() {
        return zamien2(BookReturn(null));
    }
    
    
    
    public String RemoveBook(Integer numer2) {
        //metoda przyjmuje jako parametr numer książki
        //jesli ksiązka nie istnieje w tabeli to konczy metode
        //ksiazki nie powinno sie dać usunąć jak ,w tabeli wypożyczenia jest nie zwrócona
        
        //przed usunieciem danej ksiażki pobrać nr rekordu z tabeli wypożyczenie i pod id książki wstawić 0
        //wtedy mozna usnąć książke z tabeli książka
        
        //pierw sprawdzam czy istnieje ksiązka by moc sprawdzic czy istnieje wypozyczenie
        //moze być wiecej niż 1 ksiązka w tabeli wypożyczenie
        try{
            int numer=numer2;
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //sprawdzam czy istnieje taka książka
            String pom = "Select count(*) FROM książka WHERE id_książka="+numer;
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma książki ,i b=1 jeśli jest 
            rs1.close();
            if(b==0){zapisz2("brak takiej książki");return "brak takiej książki";}
            
            //pobieram status dla tej książki i sprawdzam czy jest dostepna
            String pom2 ="Select w.status FROM książka k, wypożyczenie w WHERE k.id_książka=w.id_książka AND k.id_książka="+numer;
                    
            ResultSet rs2 = stmt.executeQuery(pom2);
            while(rs2.next())//jesli istnieje status
            {
                
            String status = rs2.getString(1);
            if(!status.equals("zwrócona")){zapisz2("ta ksiązka jest "+status);return "ta ksiązka jest "+status;}
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
            
            zapisz2("książka zostałą usunięta");
            return "książka zostałą usunięta";
                       
            }catch(SQLException e){zapisz2("blad: "+e);return "blad: "+e;}
        
    }
    
    //@GET
    @DELETE
    @Path("RemoveBook/{numer}")
    @Produces(MediaType.TEXT_HTML)
    public String RemoveBook2(@PathParam("numer") int numer) {
        
        return zamien2(RemoveBook(numer));
    }
    
    //@GET
    @DELETE
    @Path("RemoveBook")
    @Produces(MediaType.TEXT_HTML)
    public String RemoveBook2() {
        
        return zamien2(RemoveBook(null));
    }
    
    
    public String RemoveReader(Integer numer2) {
        //funkcja przyjmuje jako parametr numer czytelnika
        //jesli czytelnik nie istnieje w tabeli to konczy metode
        //czytelnika nie powinno sie dać usunąć jak ,gdy posiada książki
        
        //przed usunieciem danej czytelnika pobrać nr rekordu z tabeli wypożyczenie i pod id czytelnika wstawić 0
        //wtedy mozna usnąć czytelnika z tabeli czytelnik
        
        //pierw sprawdzam czy istnieje czytelnik by moc sprawdzic czy istnieje wypozyczenie
        //moze być wiecej niż 1 czytelnik w tabeli wypożyczenie,przpisany do zwróconych książek
        try{
            int numer=numer2;
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //sprawdzam czy istnieje taka osoba
            String pom = "Select count(*) FROM czytelnik WHERE id_czytelnik="+numer;
            ResultSet rs1 = stmt.executeQuery(pom);
            rs1.last();
            int b = rs1.getInt(1);//b=0 jesli nie ma książki ,i b=1 jeśli jest 
            rs1.close();
            if(b==0){zapisz2("brak takiej osoby");return "brak takiej osoby";}
            
            //pobieram status dla tej osoby i sprawdzam czy jest dostepna
            String pom2 ="Select w.status FROM czytelnik c, wypożyczenie w WHERE c.id_czytelnik=w.id_czytelnika AND c.id_czytelnik="+numer;
                    
            ResultSet rs2 = stmt.executeQuery(pom2);
            while(rs2.next())//jesli istnieje status
            {            
            String status = rs2.getString(1);
            if(!status.equals("zwrócona")){zapisz2("u tej osoby jest "+status+" książka");return "u tej osoby jest "+status+" książka";}
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
            
            zapisz2("czytelnik został usunięty");
            return "czytelnik został usunięty";
                       
            }catch(SQLException e){zapisz2("blad: "+e);return "blad: "+e;}
        
    }
    
    
    //@GET
    @DELETE
    @Path("RemoveReader/{numer}")
    @Produces(MediaType.TEXT_HTML)
    public String RemoveReader2(@PathParam("numer") int numer) {
        
        return zamien2(RemoveReader(numer));
    }
    
    //@GET
    @DELETE
    @Path("RemoveReader")
    @Produces(MediaType.TEXT_HTML)
    public String RemoveReader2() {
        
        return zamien2(RemoveReader(null));
    }
    
    
    public List<Text> ReaderStatus(Integer numer) {
        
        String pom="SELECT k.id_książka,k.tytuł,'-',w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu FROM wypożyczenie w,książka k WHERE w.id_książka=k.id_książka AND w.id_czytelnika="+numer;
        
        List<String> lista=BaseWriter(8,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    
    @GET
    @Path("ReaderStatus/{numer}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> ReaderStatus2(@PathParam("numer") int numer) {
        
        return ReaderStatus(numer);
    }
    
    @GET
    @Path("ReaderStatus")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> ReaderStatus2() {
        
        List<String> l=new ArrayList<String>();
        
        return zamien(l);
    }
    
    
    @GET
    @Path("BorrowedStatus")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> BorrowedStatus() {
        
        String pom = "SELECT w.id_wypożyczenie,w.status,',od',w.data_wypożyczenia,'do',w.termin_zwrotu,'|',c.id_czytelnik,c.imie,c.nazwisko,'|', k.id_książka,k.tytuł,k.autor FROM wypożyczenie w,książka k,czytelnik c WHERE w.id_książka=k.id_książka AND c.id_czytelnik=w.id_czytelnika AND w.status!='zwrócona'";
        
        List<String> lista=BaseWriter(13,pom);
        
        zapisz(zamien(lista));
        return zamien(lista);
    }
    
    //@GET
    @PUT
    @Path("UpdateAll")
    @Produces(MediaType.TEXT_HTML)
    public String UpdateAll() {
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
        zapisz2("");
        return zamien2("");
    }
    
    
    @GET
    @Path("Text")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> Text()
    {
        String pom = "SELECT napis FROM Text";
        List<String> lista=BaseWriter(1,pom);
        
        return zamien(lista);
    }
    
    //@GET
    @DELETE
    @Path("Clean")
    @Produces(MediaType.TEXT_HTML)
    public String Clean(){
        
        zapisz(new ArrayList<Text>());
        return zamien2("Wyczyszczono tabele");
    }
    
    //@GET
    @PUT
    @Path("Reset")
    @Produces(MediaType.APPLICATION_XML)
    public List<Text> Reset()
    {
        List<Text> list= new ArrayList<Text>();
        
        try{
        List<Książka> lk = new ArrayList<Książka>();
        List<Czytelnik> lc = new ArrayList<Czytelnik>();
        List<Wypożyczenie> lw = new ArrayList<Wypożyczenie>();
        
        
        Czytelnik b1= new Czytelnik();b1.setIdCzytelnik(1);b1.setImie("Tomek");b1.setNazwisko("Cebula");lc.add(b1);
        Czytelnik b2= new Czytelnik();b2.setIdCzytelnik(2);b2.setImie("Jan");b2.setNazwisko("Kowalski");lc.add(b2);
        Czytelnik b3= new Czytelnik();b3.setIdCzytelnik(3);b3.setImie("Romek");b3.setNazwisko("Lebowski");lc.add(b3);
        Czytelnik b4= new Czytelnik();b4.setIdCzytelnik(4);b4.setImie("Miachal");b4.setNazwisko("Woodyjowksi");lc.add(b4);
        Czytelnik b5= new Czytelnik();b5.setIdCzytelnik(5);b5.setImie("Jarek");b5.setNazwisko("Majewski");lc.add(b5);
        
        Książka c1=new Książka();c1.setIdKsiążka(1);c1.setTytuł("Kordian");c1.setAutor("Juliusz Slowacki");lk.add(c1);
        Książka c2=new Książka();c2.setIdKsiążka(2);c2.setTytuł("Lalka");c2.setAutor("Boleslaw Prus");lk.add(c2);
        Książka c3=new Książka();c3.setIdKsiążka(3);c3.setTytuł("Rewizja");c3.setAutor("Remigiusz Mroz");lk.add(c3);
        Książka c4=new Książka();c4.setIdKsiążka(4);c4.setTytuł("Idiota");c4.setAutor("Fiodor Dostojewski");lk.add(c4);
        Książka c5=new Książka();c5.setIdKsiążka(5);c5.setTytuł("Przeprawa");c5.setAutor("Samar Yazbek");lk.add(c5);
        Książka c6=new Książka();c6.setIdKsiążka(6);c6.setTytuł("Potop");c6.setAutor("Hendryk Sienkiewicz");lk.add(c6);
        Książka c7=new Książka();c7.setIdKsiążka(7);c7.setTytuł("Metro 2033");c7.setAutor("Dmitrij Gluchowski");lk.add(c7);
        Książka c8=new Książka();c8.setIdKsiążka(8);c8.setTytuł("Wilki");c8.setAutor("Adam Wajrak");lk.add(c8);
        Książka c9=new Książka();c9.setIdKsiążka(9);c9.setTytuł("Hobbit");c9.setAutor("John Ronald Reuel Tolkien");lk.add(c9);
        Książka c10=new Książka();c10.setIdKsiążka(10);c10.setTytuł("Życie Pi");c10.setAutor("Yann Martel");lk.add(c10);
        
        
        Calendar CC=Calendar.getInstance(),CC2=Calendar.getInstance();
        Wypożyczenie d1=new Wypożyczenie();d1.setIdWypożyczenie(1);d1.setIdCzytelnika(1);d1.setIdKsiążka(1);
        int[] p1={2016,03,16};CC.set(Calendar.YEAR, p1[0]);CC.set(Calendar.MONTH, p1[1]-1);CC.set(Calendar.DAY_OF_MONTH, p1[2]);
        int[] q1={2016,03,31};CC2.set(Calendar.YEAR, q1[0]);CC2.set(Calendar.MONTH, q1[1]-1);CC2.set(Calendar.DAY_OF_MONTH, q1[2]);
        java.sql.Date DDp1 = new java.sql.Date(CC.getTimeInMillis()),DDq1 = new java.sql.Date(CC2.getTimeInMillis());//2016-03-31
        d1.setStatus("zwrócona");d1.setDataWypożyczenia(DDp1);d1.setTerminZwrotu(DDq1);lw.add(d1);
        
        Calendar CC3=Calendar.getInstance(),CC4=Calendar.getInstance();
        Wypożyczenie d2=new Wypożyczenie();d2.setIdWypożyczenie(2);d2.setIdCzytelnika(3);d2.setIdKsiążka(5);
        int[] p2={2016,04,01};CC3.set(Calendar.YEAR, p2[0]);CC3.set(Calendar.MONTH, p2[1]-1);CC3.set(Calendar.DAY_OF_MONTH, p2[2]);
        int[] q2={2016,04,15};CC4.set(Calendar.YEAR, q2[0]);CC4.set(Calendar.MONTH, q2[1]-1);CC4.set(Calendar.DAY_OF_MONTH, q2[2]);
        java.sql.Date DDp2 = new java.sql.Date(CC3.getTimeInMillis()),DDq2 = new java.sql.Date(CC4.getTimeInMillis());//2016-03-31
        d2.setStatus("wypożyczona");d2.setDataWypożyczenia(DDp2);d2.setTerminZwrotu(DDq2);lw.add(d2);
        
        Calendar CC5=Calendar.getInstance(),CC6=Calendar.getInstance();
        Wypożyczenie d3=new Wypożyczenie();d3.setIdWypożyczenie(3);d3.setIdCzytelnika(3);d3.setIdKsiążka(7);
        int[] p3={2016,03,18};CC5.set(Calendar.YEAR, p3[0]);CC5.set(Calendar.MONTH, p3[1]-1);CC5.set(Calendar.DAY_OF_MONTH, p3[2]);
        int[] q3={2016,04,01};CC6.set(Calendar.YEAR, q3[0]);CC6.set(Calendar.MONTH, q3[1]-1);CC6.set(Calendar.DAY_OF_MONTH, q3[2]);
        java.sql.Date DDp3 = new java.sql.Date(CC5.getTimeInMillis()),DDq3 = new java.sql.Date(CC6.getTimeInMillis());//2016-03-31
        d3.setStatus("przetrzymana");d3.setDataWypożyczenia(DDp3);d3.setTerminZwrotu(DDq3);lw.add(d3);
        
        
        //i zapisuje baze                
        
            try{
            Connection con = DriverManager.getConnection(host,uzytkownik,haslo);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //usuwam dane z tabeli Czytelnik
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Czytelnik");
            pstmt.executeUpdate();
            pstmt.close();
            
            //dodaje rekordy to tabeli Czytelnik
            for(int i=0;i<lc.size();i++)
            {
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO czytelnik(id_czytelnik,imie, nazwisko)VALUES(?,?,?)");
            pstmt2.setInt(1,lc.get(i).getIdCzytelnik());
            pstmt2.setString(2, lc.get(i).getImie());
            pstmt2.setString(3, lc.get(i).getNazwisko());
            pstmt2.executeUpdate();
            pstmt2.close();
            }            
            
            //usuwam dane z tabeli książka
            PreparedStatement pstmt3 = con.prepareStatement("DELETE FROM książka");
            pstmt3.executeUpdate();
            pstmt3.close();
            
            //dodaje rekordy to tabeli książka
            for(int i=0;i<lk.size();i++)
            {
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO książka(id_książka,tytuł,autor)VALUES(?,?,?)");
            pstmt2.setInt(1,lk.get(i).getIdKsiążka());
            pstmt2.setString(2, lk.get(i).getTytuł());
            pstmt2.setString(3, lk.get(i).getAutor());
            pstmt2.executeUpdate();
            pstmt2.close();
            }
            
            
            //usuwam dane z tabeli książka
            PreparedStatement pstmt4 = con.prepareStatement("DELETE FROM wypożyczenie");
            pstmt4.executeUpdate();
            pstmt4.close();
            
            //dodaje rekordy to tabeli książka
            for(int i=0;i<lw.size();i++)
            {
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO wypożyczenie(id_wypożyczenie,id_czytelnika,id_książka,status,data_wypożyczenia,termin_zwrotu)VALUES(?,?,?,?,?,?)");
            pstmt2.setInt(1,lw.get(i).getIdWypożyczenie());
            pstmt2.setInt(2, lw.get(i).getIdCzytelnika());
            pstmt2.setInt(3, lw.get(i).getIdKsiążka());
            pstmt2.setString(4, lw.get(i).getStatus());
            pstmt2.setDate(5, (Date) lw.get(i).getDataWypożyczenia());
            pstmt2.setDate(6, (Date) lw.get(i).getTerminZwrotu());
            pstmt2.executeUpdate();
            pstmt2.close();
            }
                       
            
            
            stmt.close();
            con.close();
            }catch(SQLException e){
                List<Text> l2=new ArrayList<Text>();
                Text a=new Text();a.setId(0);a.setNapis("błąd1: "+e);l2.add(a);
                zapisz(l2);
                return l2;
            }
            
            //zapisuje dane do listy
            int z=0;
            for(int i=0;i<lk.size();i++,z++)
            {
                Text a=new Text();a.setId(z);
                a.setNapis(lk.get(i).getIdKsiążka()+" "+lk.get(i).getTytuł()+" "+lk.get(i).getAutor());
                list.add(a);
            }//z=lk.size();
            Text a2=new Text();a2.setId(z);a2.setNapis("");z++;
            list.add(a2);
        
            for(int i=0;i<lc.size();i++,z++)
            {
                Text a=new Text();a.setId(z);
                a.setNapis(lc.get(i).getIdCzytelnik()+" "+lc.get(i).getImie()+" "+lc.get(i).getNazwisko());
                list.add(a);
            }
            Text a3=new Text();a3.setId(z);a3.setNapis("");z++;
            list.add(a3);
            
            for(int i=0;i<lw.size();i++,z++)
            {
                Text a=new Text();a.setId(z);
                String pom=lw.get(i).getIdWypożyczenie()+" "+lw.get(i).getIdKsiążka()+" "+lw.get(i).getIdCzytelnika();
                a.setNapis(pom+" "+lw.get(i).getStatus()+" "+lw.get(i).getDataWypożyczenia()+" "+lw.get(i).getTerminZwrotu());
                list.add(a);
            }
        
        }catch(Exception e2){
                List<Text> l2=new ArrayList<Text>();
                Text a=new Text();a.setId(0);a.setNapis("błąd1: "+e2);l2.add(a);
                zapisz(l2);
                return l2;
        }
        
            zapisz(list);
            return list;
    }
    
    
}