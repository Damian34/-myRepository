<%-- 
    Document   : czytelnik
    Created on : 2016-04-16, 20:02:31
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
        <div>tutaj jest strona czytelników</div>
        <form action="książka.jsp" method="post">
            <input type="submit" value="      ksiązki      ">            
        </form>
        <form action="wypożyczenie.jsp" method="post">
            <input type="submit" value="wypożyczenia">
        </form>
        <%-- start web service invocation --%><hr/>
    
        <div style="white-space: pre-wrap">szukaj osoby                                   pokaż status,pod nr osoby</div>
        <form>
        <input type="text" name="osoba1">
        <input type="submit" value="szukaj" name="button1">
        
        <input type="text" name="osoba2">
        <input type="submit" value="pokaż" name="button2">
        <input type="submit" value="pokaż wypożyczających" name="button3">
        </form>
        
        <div style="white-space: pre-wrap">dodaj czytelnika ,podaj imie i nazwisko                                 usuń czytelnika</div>
        <form>
        <input type="text" name="osoba3">
        <input type="text" name="osoba4">
        <input type="submit" value="dodaj" name="button4">
        
        <input type="text" name="osoba5">
        <input type="submit" value="usuń" name="button5">
        </form>
       
    <%
        Nowy.Format F=new Nowy.Format();
        service.Usluga_Service service = new service.Usluga_Service();
	service.Usluga port = service.getUslugaPort();
        
        Nowy.Baza2 baza = new Nowy.Baza2();
        List<service.Książka> lk =baza.k_wczytaj();
        List<service.Czytelnik> lc =baza.c_wczytaj();
        List<service.Wypożyczenie> lw =baza.w_wczytaj();
        
        //wyswietlam czytelnikow
        try{
        if(!request.getParameter("button1").equals(null)){            
	String a = request.getParameter("osoba1");
        List<service.Czytelnik> k1 = F.dszyfrC2(port.readerAll(F.szyfr(a),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw)));
        
        List<String> pom2  = new ArrayList<String>();
        for(int i=0;i<k1.size();i++)
        {
            String pom=k1.get(i).getIdCzytelnik()+" "+k1.get(i).getImie()+" "+k1.get(i).getNazwisko();
            pom2.add(pom);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
    
        //wyswietlam status czytelnika
        try{
        if(!request.getParameter("button2").equals(null)){            
	int numer = Integer.parseInt(request.getParameter("osoba2"));
        List<service.Punkt3> pom3 = port.readerStatus(F.szyfr(numer),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        
        List<String> pom2  = new ArrayList<String>();
        if(pom3.size()>0)
        pom2.add("ta osoba wypożyczyła: ");
        
        for(int i=0;i<pom3.size();i++)
        {
        service.Punkt3 pom4 = pom3.get(i);
        service.Książka q1 = F.dszyfrK(pom4.getKsiążka());//deszyfruje
        service.Wypożyczenie q2 =  F.dszyfrW(pom4.getWypożyczenie());
        String pom5=""+q1.getIdKsiążka()+" "+q1.getTytuł()+" "+q1.getAutor()+" | "+q2.getIdWypożyczenie()+" "+q2.getStatus()+" "+q2.getDataWypożyczenia()+" "+q2.getTerminZwrotu();
        pom2.add(pom5);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        
        //wyswietlam czytelnikow wypożyczajacych kiążki i liczbe książek 
        try{
        if(!request.getParameter("button3").equals(null)){
        List<service.Punkt1> pom3 = port.readerBorrower(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        List<String> pom2  = new ArrayList<String>();
        
        for(int i=0;i<pom3.size();i++)
        {
            service.Punkt1 pom4 = pom3.get(i);
            service.Czytelnik q1 = F.dszyfrC(pom4.getCzytelnik());//deszyfruje
            int q2 =F.dszyfr(pom4.getIlość());
            String pom5="";
            if(q2==1)
            {pom5=""+q1.getIdCzytelnik()+" "+q1.getImie()+" "+q1.getNazwisko()+"  "+q2+"książka";}
            else                
            {pom5=""+q1.getIdCzytelnik()+" "+q1.getImie()+" "+q1.getNazwisko()+"  "+q2+"książki";}
            pom2.add(pom5);
        }
        F.zapis(pom2);
        }
        }catch(Exception e){}
        


        //dodaje czytelnika
        try{
        if(!request.getParameter("button4").equals(null)){
	String imie = request.getParameter("osoba3");
	String nazwisko = request.getParameter("osoba4");
        
        service.Punkt4 l1 = port.readerAdd(F.szyfr(imie),F.szyfr(nazwisko),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String a = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(a.replace(",","").replace("błąd",""));
        F.zapis(pom2);
        }
        }catch(Exception e){}
    

        //usuń czytelnika
        try{
	int numer = Integer.parseInt(request.getParameter("osoba5"));
        
        service.Punkt4 l1 = port.removeReader(F.szyfr(numer),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String a = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(a.replace(",","").replace("błąd",""));
        F.zapis(pom2);
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
