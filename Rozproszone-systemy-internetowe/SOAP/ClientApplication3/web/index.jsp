<%-- 
    Document   : index
    Created on : 2016-04-16, 20:00:25
    Author     : dami
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library</title>           
    </head>
    <body style='background-color:#F0F0F0;'>
        <h1>Aplikacja kliencka</h1>        
        <div>tutaj jest strona główna</div>
        <form action="książka.jsp" method="post">
            <input type="submit" value="      ksiązki      ">            
        </form>
        <form action="czytelnik.jsp" method="post">
            <input type="submit" value="    czytelnicy    ">            
        </form>
        <form action="wypożyczenie.jsp" method="post">
            <input type="submit" value="wypożyczenia">
        </form>
        <%-- start web service invocation --%><hr/>
        <form>
        <input type="submit" value="wczytaj nową baze" name="button1">
        </form>
 
    <%        
        Nowy.Format F=new Nowy.Format();
        F.zapis(new ArrayList<String>());//czyszcze plik
        
        service.Usluga_Service service = new service.Usluga_Service();
	service.Usluga port = service.getUslugaPort();
        
        Nowy.Baza2 baza = new Nowy.Baza2();
        List<service.Książka> lk =baza.k_wczytaj();
        List<service.Czytelnik> lc =baza.c_wczytaj();
        List<service.Wypożyczenie> lw =baza.w_wczytaj();
        
        
        //////////////////uaktualniam statusy
        service.Punkt4 B = port.updateAll(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        lk=F.dszyfrK2(B.getLk());
        lc=F.dszyfrC2(B.getLc());
        lw=F.dszyfrW2(B.getLw());
        //i zapisuje baze
        baza.k_zapisz(lk);
        baza.c_zapisz(lc);
        baza.w_zapisz(lw);
        
        //wczytue nową baze
        try{
        if(!request.getParameter("button1").equals(null)){
            out.println(baza.Wczytaj_nowa());
            lk =baza.k_wczytaj();//wczytanie bazy automatycznie i ją zapisuje
            lc =baza.c_wczytaj();
            lw =baza.w_wczytaj();
            List<String> pom = new ArrayList<String>();
            
            /////////////
            for(int i=0;i<lk.size();i++)
            {
                String pom2=""+lk.get(i).getIdKsiążka()+" "+lk.get(i).getTytuł()+" "+lk.get(i).getAutor();
                pom.add(pom2);
            }
            pom.add("");
            
            for(int i=0;i<lc.size();i++)
            {
                String pom2=""+lc.get(i).getIdCzytelnik()+" "+lc.get(i).getImie()+" "+lc.get(i).getNazwisko();
                pom.add(pom2);
            }
            pom.add("");
            
            for(int i=0;i<lw.size();i++)
            {
                String pom2=""+lw.get(i).getIdWypożyczenie()+" "+lw.get(i).getIdKsiążka()+" "+lw.get(i).getIdKsiążka()+" "+lw.get(i).getStatus()+" "+lw.get(i).getDataWypożyczenia()+" "+lw.get(i).getTerminZwrotu();
                pom.add(pom2);
            }
            /////////////
            
            F.zapis(pom);
            //out.println(lk.size());
        }
        }catch(Exception e){}
        
        
        
    %>
        
    <%
    //wczytuje dane na dowolna strone
    Nowy.Format p2=new Nowy.Format();
    List<String> p3  = new ArrayList<String>();
    p3=p2.czytaj();
    int p=15;
    if(p3.size()>p)
    p=p3.size();
    
    out.println("<h2>wynik :</h2>");
    out.println("<div id=\"output\" style=\"border: 1px solid black; color: black; height: "+1.22*(p+1)+"em; width: 100%; background-color:#FFFFFF;\">"); 
    
    
    out.println("<div>");
    for(int i=0;i<p3.size();i++)
    {
        out.println("<div><i>");//&nbsp; <--spacja
        out.println("&nbsp;"+p3.get(i));
        out.println("</i></div>");
    }
    
    out.println("</div>");
    %>
    </body>
</html>
