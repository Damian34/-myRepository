<%-- 
    Document   : książka
    Created on : 2016-04-16, 20:02:45
    Author     : dami
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library</title>        
    </head>
    <body style='background-color:#F0F0F0;'>
        <h1>Aplikacja kliencka</h1>
        <div>tutaj jest strona książek</div>      
        <form action="czytelnik.jsp" method="post">
            <input type="submit" value="    czytelnicy    ">            
        </form>
        <form action="wypożyczenie.jsp" method="post">
            <input type="submit" value="wypożyczenia">
        </form>
        <%-- start web service invocation --%><hr/>
        
        <div style="white-space: pre-wrap">szukaj książki</div>
        <form>
        <input type="text" name="ksiazka1">
        <input type="submit" value="szukaj" name="button1">
        
        <input type="submit" value="pokaż dostępne" name="button2">
        <input type="submit" value="pokaż pożyczone" name="button3">
        <input type="submit" value="pokaż przetrzymane" name="button4">
        </form>
            
        <div style="white-space: pre-wrap">dodaj książke ,podaj tytuł i autora                                         usuń książke</div>
        <form>
        <input type="text" name="ksiazka2">
        <input type="text" name="ksiazka3">
        <input type="submit" value="dodaj" name="button5">
        
        <input type="text" name="ksiazka4">
        <input type="submit" value="usuń" name="button6">
        </form>
        
        <div style="white-space: pre-wrap">wypożycz książke,podaj nr ksiązki,czytelnika i liczbe dni</div>
        <form>
        <input type="text" name="ksiazka5">
        <input type="text" name="ksiazka6">
        <input type="text" name="ksiazka7">
        <input type="submit" value="wypożycz" name="button7">
        </form>
        
    <%        
	Nowy.Format F=new Nowy.Format();
        service.Usluga_Service service = new service.Usluga_Service();
	service.Usluga port = service.getUslugaPort();
        
        Nowy.Baza2 baza = new Nowy.Baza2();
        List<service.Książka> lk =baza.k_wczytaj();
        List<service.Czytelnik> lc =baza.c_wczytaj();
        List<service.Wypożyczenie> lw =baza.w_wczytaj();
        
        //wyswietlam książki
        try{
        if(!request.getParameter("button1").equals(null)){            
	String a = request.getParameter("ksiazka1");
        List<service.Książka> k1 = F.dszyfrK2(port.booksAll(F.szyfr(a),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw)));
        
        List<String> pom2  = new ArrayList<String>();
        for(int i=0;i<k1.size();i++)
        {
            String pom=k1.get(i).getIdKsiążka()+" "+k1.get(i).getTytuł()+" "+k1.get(i).getAutor();
            pom2.add(pom);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        try{
        if(!request.getParameter("button2").equals(null)){
        List<service.Książka> k1 = F.dszyfrK2(port.booksAvailable(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw)));
        
        List<String> pom2  = new ArrayList<String>();
        for(int i=0;i<k1.size();i++)
        {
            String pom=k1.get(i).getIdKsiążka()+" "+k1.get(i).getTytuł()+" "+k1.get(i).getAutor();
            pom2.add(pom);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        try{
        if(!request.getParameter("button3").equals(null)){
        List<service.Książka> k1 = F.dszyfrK2(port.booksBorrowed(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw)));
        
        List<String> pom2  = new ArrayList<String>();
        for(int i=0;i<k1.size();i++)
        {
            String pom=k1.get(i).getIdKsiążka()+" "+k1.get(i).getTytuł()+" "+k1.get(i).getAutor();
            pom2.add(pom);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        try{
        if(!request.getParameter("button4").equals(null)){
        List<service.Książka> k1 = F.dszyfrK2(port.booksHeld(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw)));
        
        List<String> pom2  = new ArrayList<String>();
        for(int i=0;i<k1.size();i++)
        {
            String pom=k1.get(i).getIdKsiążka()+" "+k1.get(i).getTytuł()+" "+k1.get(i).getAutor();
            pom2.add(pom);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        
        //dodaje książke
        try{
         if(!request.getParameter("button5").equals(null)){
	String tytul = request.getParameter("ksiazka2");
	String autor = request.getParameter("ksiazka3");
        
        service.Punkt4 l1 = port.bookAdd(F.szyfr(tytul),F.szyfr(autor),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String a = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(a.replace(",","").replace("błąd",""));
        F.zapis(pom2);
         }
        }catch(Exception e){}

        //usuń książke
        try{
        if(!request.getParameter("button6").equals(null)){
	int numer = Integer.parseInt(request.getParameter("ksiazka4"));
        
        service.Punkt4 l1 = port.removeBook(F.szyfr(numer),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String a = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(a.replace(",","").replace("błąd",""));
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        //wypożycz ksiązke
        try{
        if(!request.getParameter("button7").equals(null)){
	int idk = Integer.parseInt(request.getParameter("ksiazka5"));
	int idc = Integer.parseInt(request.getParameter("ksiazka6"));
	int czas = Integer.parseInt(request.getParameter("ksiazka7"));
        
        service.Punkt4 l1 = port.bookGive(F.szyfr(idk),F.szyfr(idc),F.szyfr(czas),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String pom = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(pom.replace(",","").replace("błąd",""));
        F.zapis(pom2);
        }
        }catch(Exception e){}


        //i zapisuje baze
        baza.k_zapisz(lk);
        baza.c_zapisz(lc);
        baza.w_zapisz(lw);
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