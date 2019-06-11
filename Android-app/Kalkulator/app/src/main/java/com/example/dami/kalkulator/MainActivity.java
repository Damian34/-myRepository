package com.example.dami.kalkulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    List<String> znaki = new ArrayList<String>();
    int pozycja=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.textView);
    }

    public void wys()
    {
        String pom=" ";
        try {
            for (int i = 0; i < pozycja; i++) {
                pom = pom + znaki.get(i);
            }
            pom = pom + "_";
            for (int i = pozycja; i < znaki.size(); i++) {
                pom = pom + znaki.get(i);
            }

            pokaz(pom);
        }catch(Exception e){t1.setText("błąd: "+e);}
    }

    public void licz(String text)
    {
        //////
        List<String> znaki2 = new ArrayList<String>();
        for(int i=0;i<pozycja;i++)
        {
            znaki2.add(znaki.get(i));
        }
        znaki2.add(text);
        for(int i=pozycja;i<znaki.size();i++)
        {
            znaki2.add(znaki.get(i));
        }
        znaki=znaki2;

        /////////////
        pozycja++;
        wys();
    }

    public void lewo(View view)
    { if(pozycja>0){pozycja--;wys();  }}

    public void prawo(View view)
    {if(pozycja<znaki.size()){pozycja++;wys(); }}

    public void del(View view)
    {
        if(pozycja>0){
            znaki.remove(pozycja-1);
            pozycja--;wys();}
        if(znaki.size()==0){t1.setText(" _");}
    }

    public void on(View view)//czyszcze wszystko
    {
        znaki = new ArrayList<String>();
        pozycja=0;
        wys();
    }

    public void wynik(View view)
    {
        try {
            //jesli trafi sie liczba przed lub po pi,lub e to zwróć błąd
            if(znaki.size()>=2)
            for(int i=0;i<znaki.size();i++)
            if(znaki.get(i).equals("e") || znaki.get(i).equals("π"))
            {
                //jesli po e,pi jest liczba
                if(i<znaki.size()-1  && (znaki.get(i+1).charAt(0)>=48 && znaki.get(i+1).charAt(0)<=57))
                {pozycja = 0;znaki = new ArrayList<String>();;t1.setText("Error");return;}
                //jesli przed e ,pi jest liczba
                if(i>0  && (znaki.get(i-1).charAt(0)>=48 && znaki.get(i-1).charAt(0)<=57))
                {pozycja = 0;znaki = new ArrayList<String>();;t1.setText("Error");return;}
            }

            ////////////////////////////
        Liczenie l1=new Liczenie();
        String pom=l1.liczAll(zamien(znaki));

            double b = Double.parseDouble(pom);//zwróce error jesli to nie bedzie liczba

            //zwróć Error jesli liczba jest wieksza niż 9*10^13
            if(b > 9*Math.pow(10,13))
            {t1.setText("Error");pozycja=0;znaki = new ArrayList<String>();return;}

            if((double)(int)b==b){pom=(int)b+"";}
            else//jesli jest to double
            {
                pom=""+Math.round(b*100000)/(double)100000;
            }

            if (pom.indexOf("błąd") == -1)//jesli nie zróci niektórych błędów
            {
                t1.setText(" "+pom + "_");
                pozycja = pom.length();
                znaki = new ArrayList<String>();
                for(int i=0;i<pom.length();i++)
                znaki.add(""+pom.charAt(i));
            } else {
                t1.setText("Error");
                pozycja = 0;
                znaki = new ArrayList<String>();
            }
        }catch(Exception e){t1.setText("Error");pozycja=0;znaki = new ArrayList<String>();}
    }

    public List<String> zamien(List<String> lista)
    {
        List<String> pom = new ArrayList<String>();
        for(int i=0;i<lista.size();i++)
        {
            switch (lista.get(i)){
                case "x": pom.add("*");break;
                case "π": pom.add(Math.PI+"");break;
                case "e": pom.add(Math.E+"");break;
                case "E": pom.add("*");pom.add("1");pom.add("0");pom.add("^");break;
                //jesli trafi sie E z liczby double to zapisz to jako " *10^ "
                default : pom.add(lista.get(i));break;
            }
        }

        return pom;
    }

    public void pokaz(String text) {
        if (text.length() <= 25) {
            t1.setText(text);
        }//rozmar text jest min 2 tzn " _"

        if(text.length()>25)
        {
        int p = 0;
        List<String> text2 = new ArrayList<String>();
        for(int i=0;i<text.length();i++){text2.add(text.charAt(i)+"");}

        for(int i=0;i<text2.size();i++)if(text2.get(i).equals("_")){p=i;break;}

        if(p>25)//jesli znak "_" jest po 25 znakach to usuwam z początku
        {
            String pom = "",pom2="";
            for(int i=0;i<25;i++){pom=pom+text2.get(p-i);}
            for(int i=24;i>=0;i--){pom2=pom2+pom.charAt(i);}
            t1.setText(pom2);

            return;
        }
        else{//jesli znak "_" ma pozycje nizsza jak 25
            String pom="";
            for(int i=0;i<25;i++)
            pom=pom+text2.get(i);
            t1.setText(pom);
            return;
        }

        }

    }

    //////////////////////////////////////////////////////////////

    public void exp(View view)
    {licz("exp(");}

    public void ln(View view)
    {licz("ln(");}

    public void sin(View view)
    {licz("sin(");}

    public void cos(View view)
    {licz("cos(");}

    public void tan(View view)
    {licz("tan(");}

    public void ctg(View view)
    {licz("ctg(");}

    public void round(View view)
    {licz("round(");}

    public void abs(View view)
    {licz("abs(");}

    public void sqrt(View view)
    {licz("sqrt(");}

    public void znak1(View view)
    {licz("^");}

    public void znakpi(View view)
    {licz("π");}

    public void znake(View view)
    {licz("e");}

    public void znak2(View view)
    {licz("(");}

    public void znak3(View view)
    {licz(")");}

    public void znak4(View view)
    {licz("/");}

    public void znak5(View view)
    {licz("x");}

    public void znak6(View view)
    {licz("-");}

    public void znak7(View view)
    {licz("+");}

    public void znak8(View view)
    {licz(".");}

    public void nr0(View view)
    {licz("0");}

    public void nr1(View view)
    {licz("1");}

    public void nr2(View view)
    {licz("2");}

    public void nr3(View view)
    {licz("3");}

    public void nr4(View view)
    {licz("4");}

    public void nr5(View view)
    {licz("5");}

    public void nr6(View view)
    {licz("6");}

    public void nr7(View view)
    {licz("7");}

    public void nr8(View view)
    {licz("8");}

    public void nr9(View view)
    {licz("9");}
}
