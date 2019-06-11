<%-- 
    Document   : wypożyczenie
    Created on : 2016-04-16, 20:03:00
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
        <div>tutaj jest strona wypożyczeń</div>
        <form action="książka.jsp" method="post">
            <input type="submit" value="      ksiązki      ">            
        </form>
        <form action="czytelnik.jsp" method="post">
            <input type="submit" value="    czytelnicy    ">            
        </form>
        <%-- start web service invocation --%><hr/>
        
        <div style="white-space: pre-wrap">zwróć książke,podaj nr wypożyczenia</div>
        <form>
        <input type="text" name="wyp1">
        <input type="submit" value="zwróć" name="button1">
        
        <input type="submit" value="wypożyczenia" name="button2">
        <input type="submit" value="tylko wypożyczone" name="button3">
        </form>
        
        <%        
	Nowy.Format F=new Nowy.Format();
        service.Usluga_Service service = new service.Usluga_Service();
	service.Usluga port = service.getUslugaPort();
        
        Nowy.Baza2 baza = new Nowy.Baza2();
        List<service.Książka> lk =baza.k_wczytaj();
        List<service.Czytelnik> lc =baza.c_wczytaj();
        List<service.Wypożyczenie> lw =baza.w_wczytaj();
        
        try{
        if(!request.getParameter("button1").equals(null)){           
	int a = Integer.parseInt(request.getParameter("wyp1"));
        
        service.Punkt4 l1 = port.bookReturn(F.szyfr(a),F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
        String q1 = F.dszyfr(l1.getText());
        lk=F.dszyfrK2(l1.getLk());
        lc=F.dszyfrC2(l1.getLc());
        lw=F.dszyfrW2(l1.getLw());
        
        List<String> pom2  = new ArrayList<String>();
        pom2.add(q1.replace(",","").replace("błąd",""));
        F.zapis(pom2);
        }       
        }catch(Exception e){}

        
        
        //wyswietla wczystkie dane z tabel
        try{
        if(!request.getParameter("button2").equals(null)){ 
            
            List<service.Punkt2> q1 = port.allStatus(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
            
            List<String> pom2  = new ArrayList<String>();
            for(int i=0;i<q1.size();i++)
            {
                service.Punkt2 q2=q1.get(i);
                service.Czytelnik q3 = F.dszyfrC(q2.getCzytelnik());//deszyfruje
                service.Książka q4= F.dszyfrK(q2.getKsiążka());
                service.Wypożyczenie q5 = F.dszyfrW(q2.getWyp());
                
                String pom3=""+q5.getIdWypożyczenie()+" "+q5.getStatus()+",od "+q5.getDataWypożyczenia()+" do "+q5.getTerminZwrotu()+" | ";
                if((int)q3.getIdCzytelnik()!=0)
                pom3=pom3+q3.getIdCzytelnik()+" "+q3.getImie()+" "+q3.getNazwisko()+" | ";
                else
                pom3=pom3+" | ";
                if((int)q4.getIdKsiążka()!=0)
                pom3=pom3+q4.getIdKsiążka()+" "+q4.getTytuł()+" "+q4.getAutor();
                
                pom2.add(pom3);
            }
            F.zapis(pom2);
            
        }       
        }catch(Exception e){}
        
        
        
        //wyswietla wczystkie dane z tabel gdzie kisążka jest wypożyczona
        try{
        if(!request.getParameter("button3").equals(null)){ 
            List<service.Punkt2> q1 = port.borrowedStatus(F.szyfrK2(lk),F.szyfrC2(lc),F.szyfrW2(lw));
            List<String> pom2  = new ArrayList<String>();
            
            for(int i=0;i<q1.size();i++)
            {
                service.Czytelnik q3 = new service.Czytelnik();
                service.Książka q4 = new service.Książka();
                service.Wypożyczenie q5 =  new service.Wypożyczenie();
                service.Punkt2 q2=q1.get(i);
                
                q3 = F.dszyfrC(q2.getCzytelnik());//deszyfruje
                q4 = F.dszyfrK(q2.getKsiążka());
                q5  = F.dszyfrW(q2.getWyp());
                String pom3=""+q5.getIdWypożyczenie()+" "+q5.getStatus()+",od "+q5.getDataWypożyczenia()+" do "+q5.getTerminZwrotu()+" | ";
                pom3=pom3+q3.getIdCzytelnik()+" "+q3.getImie()+" "+q3.getNazwisko()+" | ";
                pom3=pom3+q4.getIdKsiążka()+" "+q4.getTytuł()+" "+q4.getAutor();
                pom2.add(pom3);
            }
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
