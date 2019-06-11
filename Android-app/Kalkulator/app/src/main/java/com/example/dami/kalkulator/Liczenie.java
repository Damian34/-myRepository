package com.example.dami.kalkulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dami on 2016-04-23.
 */
public class Liczenie {

    public String spr2(List<String> text)//tutaj licze proste działania -,+,*,/,^
    {
        //wykonuje proste działanie na +,-,*,/,^

        List<String> t1 =text;


        int tp=0;//zmienna pomocnicza by dzialania wykonywaly sie jedna na petle

        while(true)
        {
            tp=0;
            //jesli pozostanie 1 liczba to wydzodze z petli
            if(t1.size()==1)
            {
                try{Double.parseDouble(t1.get(0));break;}catch(Exception e){}
            }
            //potegowanie ma najwyższy priorytet
            for(int i=0;i<t1.size();i++)
            {
                if(t1.get(i).equals("^")){
                    double v1=Double.parseDouble(t1.get(i-1));
                    double v2=Double.parseDouble(t1.get(i+1));
                    double v3=Math.pow(v1, v2);
                    t1.set(i,Double.toString(v3));
                    t1.remove(i+1);
                    t1.remove(i-1);
                }

            }

            //mnozenie i dzielenie ma ten sam priorytet ,ale niższy od potegowania
            for(int i=0;i<t1.size();i++)
            {

                if(t1.get(i).equals("/") && tp==0){

                    double v1=Double.parseDouble(t1.get(i-1));
                    double v2=Double.parseDouble(t1.get(i+1));
                    double v3=v1/v2;
                    if(v2==0){return "błąd";}

                    t1.set(i,Double.toString(v3));
                    t1.remove(i+1);
                    t1.remove(i-1);
                    tp=1;break;
                }

                if(t1.get(i).equals("*") && tp==0) {
                    double v1=Double.parseDouble(t1.get(i-1));
                    double v2=Double.parseDouble(t1.get(i+1));
                    double v3=v1*v2;

                    t1.set(i,Double.toString(v3));
                    t1.remove(i+1);
                    t1.remove(i-1);
                    tp=1;break;
                }
            }

            //dodawanie i odejmowanie ma najniszy priorytet i wykonuje sie na koncu
            for(int i=0;i<t1.size();i++){

                if(t1.get(i).equals("+") && tp==0){

                    double v1=Double.parseDouble(t1.get(i-1));
                    double v2=Double.parseDouble(t1.get(i+1));
                    double v3=v1+v2;

                    t1.set(i,Double.toString(v3));
                    t1.remove(i+1);
                    t1.remove(i-1);
                    tp=1;break;
                }

                if(t1.get(i).equals("-") && tp==0) {
                    double v1=Double.parseDouble(t1.get(i-1));
                    double v2=Double.parseDouble(t1.get(i+1));
                    double v3=v1-v2;

                    t1.set(i,Double.toString(v3));
                    t1.remove(i+1);
                    t1.remove(i-1);
                    tp=1;break;
                }
            }

        }
        return t1.get(0);
    }

    //od 48 do 57 jest cyfra //od 97 do 122mala litera
    public String liczAll(List<String> text)
    {
        //metoda przyjmuje liste stringow ,postaci takiej ze cyfry,kropki,znaki arytmetyczne sa odzielnie
        //jedynie znaki funkcji np sin( maja taką forme i są traktowane jak nawias


        List<String> t1 =text; /*new ArrayList<>();//{"2","^","(","(","-","2",")","+","8",")","^","7"};//
        String[] t2={"round(","sin(","4","+","(","3","+","sqrt(","4","^","2","-","2",")","-","5",")",")","^","2","-","1",")","+","3"};//{"-","2",".","2","+","(","-","1","-","2",".","5",")","*","sin(","-","3","0",".","2","/","4",".","1","6",")","-","5",".","1","+","6","7"};//np"1-2*3+4^5"
        for(int i=0;i<t2.length;i++){t1.add(t2[i]);}//tutaj narazie dodaje lementy do listy
        */

        //tabele znaków:
        String[] znak1={"(","sin(","cos(","tan(","ctg(","exp(","ln(","sqrt(","abs(","round("};//nawiasy otwierające
        String[] znak2={"+","-","*","/","^"};
        String[] znak3={"(","sin(","cos(","tan(","ctg(","exp(","ln(","sqrt(","abs(","round(",")"};
        String[] znak4={"(","sin(","cos(","tan(","ctg(","exp(","ln(","sqrt(","abs(","round(",")","+","-","*","/","^"};


        int q1=0,q2=0;//oznaczaja kolejno liczbe nawiasów zamykających i otwierających

        //sprawdzam błędy na początku i na końcu nie może być kropek i nawiasów typu )(
        if(t1.get(0).charAt(0)=='.' || t1.get(0).charAt(0)==')' || t1.get(t1.size()-1).charAt(0)=='.' || t1.get(t1.size()-1).charAt(0)=='(')
        {return "błąd1";}

        //najpierw poprawność formatu i łącze cyfry
        //pierw przechodze po liście i łącze cyfry
        for(int i=0;i<t1.size()-1;i++)
        {
            if(t1.get(i).charAt(0)>=48 && t1.get(i).charAt(0)<=57)
                if(t1.get(i+1).charAt(0)>=48 && t1.get(i+1).charAt(0)<=57)
                {t1.set(i,t1.get(i)+t1.get(i+1));t1.remove(i+1);i--;}
        }
        //teraz sprawdzam poprawność kropek
        for(int i=1;i<t1.size()-1;i++)
            if(t1.get(i).charAt(0)=='.')
                if(!(t1.get(i-1).charAt(0)>=48 && t1.get(i-1).charAt(0)<=57) || !(t1.get(i+1).charAt(0)>=48 && t1.get(i+1).charAt(0)<=57))
                {return "błąd2";}
        //teraz łącze cyfry do kropek
        for(int i=1;i<t1.size()-1;i++)
            if(t1.get(i).charAt(0)=='.')
            {t1.set(i,t1.get(i-1)+t1.get(i)+t1.get(i+1));t1.remove(i+1);t1.remove(i-1);}

        //teraz sprawdzam poprawność cyfr połączonych do kropek żeby nie bylo np 1.1.1
        for(int i=0;i<t1.size();i++)
        if(t1.get(i).charAt(0)=='.'){return "błąd8";}

        //teraz sprawdzam liczbe nawiasów ,liczba otwierających i zamykających powinna być równa
        for(int i=0;i<t1.size();i++)
            for(int j=0;j<znak1.length;j++)
                if(t1.get(i).equals(znak1[j]))
                {q1++;}
        for(int i=0;i<t1.size();i++)if(t1.get(i).equals(")")){q2++;}

        //jesli liczby nawiasów nie są równe
        if(q1!=q2){return "błąd3";}
        //teraz sprawdzam (-1 i dodaje ten - do liczby, i jesli na pozycji 0 jest minus i na pozycji 1 jes liczba to je łącz
        if(t1.get(0).equals("-") && (t1.get(1).charAt(0)>=48 && t1.get(1).charAt(0)<=57))
        {t1.set(1,t1.get(0)+t1.get(1));t1.remove(0);}
        for(int i=1;i<t1.size()-1;i++)
            for(int j=0;j<znak1.length;j++)
                if(t1.get(i).equals("-") && t1.get(i-1).equals(znak1[j]) && (t1.get(i+1).charAt(0)>=48 && t1.get(i+1).charAt(0)<=57))
                {t1.set(i+1,t1.get(i)+t1.get(i+1));t1.remove(i);}


        //sprawdzam czy nie ma 2 nawiasów kolo siebie
        for(int i=0;i<t1.size()-1;i++)
            for(int j=0;j<znak1.length;j++)
                if(t1.get(i).equals(znak1[j]) && t1.get(i+1).equals(")")){return "błąd5";}

        //////
        for(int i=0;i<znak2.length;i++)if(t1.get(0).equals(znak2[i]) || t1.get(t1.size()-1).equals(znak2[i])){return "błąd4";}
        //teraz sprawdzam dla każdego znaku jest nawias lub liczba z każdej strony
        for(int i=1;i<t1.size()-1;i++)//musze sprawdzić czy choćby z 1 strony od nawiasu jest znak
        {
            int z1=0,z2=0;//z1=1 oznacza znak z lewej strony,a z2=1 z prawej
            int z3=0;

            for(int j=0;j<znak2.length;j++)if(t1.get(i-1).equals(znak2[j])){z1=1;}
            for(int j=0;j<znak2.length;j++)if(t1.get(i+1).equals(znak2[j])){z2=1;}
            //trzeba dodac ze moze byc nawias tego samego typu np z prawej strony,moze byc prawostronny

            /////////////
            for(int j=0;j<znak3.length;j++)
                if(t1.get(i).equals(znak3[j]))//jesli element jest nawiasem
                {
                    //z3=0 oznacza brak kolejnego nawiasu
                    if(t1.get(i).equals(")"))//pod i jest nawias ,wiec mzoe byc lewo ,lub prawostrony
                    {
                        if(t1.get(i+1).equals(")"))//jesli z prawej strony jest nawias prawostronny ,od nawiasu prawostronnego
                        {z3=1;}
                    }else{
                        //if(!t1.get(i-1).equals(")"))
                        for(int k=0;k<znak1.length;k++)
                            if(t1.get(i-1).equals(znak1[k]))//jesli z lewej strony jest nawias lewostronny ,od nawiasu lewostronnego
                            {z3=1;}
                    }

                    //zwraca błąd tzn w przypadku braku takiego nawiasu lub w przypadku braku nzaku z conajmniej 1 strony
                    if(!(z1==1 || z2==1 || z3==1))//oznacza ze z conajmniej 1 strony jest liczba
                    {
                        return "błąd6";
                    }
                }


        }


        //teraz dane przyjmują prosty format ,w miare bez błędów


        int q3=0,q4=0;//oznaczaja indexy nawiasów
        String q5="";//pobiera typ nawiasu
        int o=0;
        while(true)//o<4)
        {
            q3=-1;q4=-1;q5="";//moze nie być żadnego nawiasu
            //trzeba dodać if dla braku nawiasów

            //sprawdzam czy t1 ma tylko 1 liczbe,wtedy wychodze
            if(t1.size()==1){break;}
            //jesli nie ma nawiasów ale jest wiecej znaków niż 1 to licze odrazu
            if(t1.size()>0 && q1==0 && q2==0)
            {
                List<String> pom = new ArrayList<>();
                String pom2=spr2(t1);
                pom.add(pom2);
                t1=pom;
                break;
            }

            //teraz pobieram indexy nawiasów q3 i q4
            for(int i=0;i<t1.size();i++)
                for(int j=0;j<znak1.length;j++)
                    if(t1.get(i).equals(znak1[j]))
                    {q3=i;q5=znak1[j];}//pobieram poczatek i typ ,najbardziej wewnetrznego nawiasu

            for(int i=q3+1;i<t1.size();i++)
                if(t1.get(i).equals(")"))
                {q4=i;break;}




            //teraz sprawdzam dla nawiasu każdego typu ,w przypadku ,przy nieistniejących typ'ach zwróci błąd
            //przy dodawaniu nawiasow nowego typu trzeba dodac je tez do funkcji licz()

            //tyle wystarczy by to działało dla wszystkich nawiasów bo w przypadku 1 elementu wyśle go i zwróci
            List<String> pom = new ArrayList<>();
            for(int i=q3+1;i<q4;i++) pom.add(t1.get(i));

            String pom6=spr2(pom);
            String pom5=licz(pom6,q5);

            if(pom5.equals("błąd")){return "błąd7";}

            List<String> pom3 = new ArrayList<>();

            /////
            for(int i=0;i<q3;i++)
                pom3.add(t1.get(i));
            pom3.add(pom5);
            for(int i=q4+1;i<t1.size();i++)
                pom3.add(t1.get(i));

            t1=pom3;


            q1--;q2--;//liczba nawiasów sie zmniejsza
            o++;
        }



        return t1.get(0);//zwracam string ktory powinien na koncu zostać
    }

    String licz(String liczba,String typ)
    {
        String[] znak1={"(","sqrt(","ln(","exp(","sin(","cos(","tan(","ctg(","abs(","round("};
        int z=0;
        for(int i=0;i<znak1.length;i++)if(typ.equals(znak1[i])){z=1;}
        if(z==0){return "błąd";}

        double pom=Double.parseDouble(liczba);
        String pom2="";
        if(typ.equals("(")){pom2=pom+"";}
        if(typ.equals("sqrt("))if(pom<0){return "błąd";}else{pom2=Math.sqrt(pom)+"";}
        if(typ.equals("ln("))if(pom<0){return "błąd";}else{pom2=Math.log(pom)+"";}
        if(typ.equals("exp(")){pom2=Math.exp(pom)+"";}
        if(typ.equals("sin(")){pom2=Math.sin(pom)+"";}
        if(typ.equals("cos(")){pom2=Math.cos(pom)+"";}
        if(typ.equals("tan(")){pom2=Math.tan(pom)+"";}
        if(typ.equals("ctg(")){pom2=(1/Math.tan(pom))+"";}
        if(typ.equals("abs(")){pom2=Math.abs(pom)+"";}
        if(typ.equals("round(")){pom2=Math.round(pom)+"";}

        return pom2;
    }
}
