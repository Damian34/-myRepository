package com.example.dami.przelicznik;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainLastActivity extends AppCompatActivity {
    TextView t1;
    Button t2;
    Button t3;
    TextView t4;
    TextView t5;

    EditText e1;

    Paczka paczka;

    int okno=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_last);
        t1  = (TextView) findViewById(R.id.text1);
        t2  = (Button) findViewById(R.id.text2);
        t3  = (Button) findViewById(R.id.text3);
        t4  = (TextView) findViewById(R.id.text4);
        t5  = (TextView) findViewById(R.id.text5);


        e1  = (EditText) findViewById(R.id.edit1);

        Bundle b = getIntent().getExtras();
        String[] text=b.getStringArray("paczka2");//klucz - paczka

        paczka = new Paczka(text);
        pisz();
        t5.setText("dane z dnia "+paczka.getData());

        if(paczka.getStan()==1)
        {t5.setText(t5.getText()+"\nbrak połączenia");}

        //sprawdzam czy jest liczba double
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String tekst = e1.getText().toString();
                try {
                    double pom = Double.parseDouble(tekst);
                    pom = Math.round(pom * 100) / 100;
                    e1.setText("" + pom);
                } catch (NumberFormatException e) {
                    e1.setText("");
                }
            }
        });

    }

    public void Sprawdz(View view)
    {
        List<Waluta> list = paczka.getlista();
        for(int i=0;i<list.size();i++)
        {
            Waluta w =list.get(i);
            double new_kurs=w.getkurs_sredni()/w.getPrzelicznik();
            w.setKurs_sredni(new_kurs);
            w.setPrzelicznik(1);
        }
        double w1=list.get(paczka.getokno1()).getkurs_sredni();//kurs waluty do zamiany
        double w2=list.get(paczka.getokno2()).getkurs_sredni();//kurs waluty na którą zamieniam

        double w3=w1/w2;

        double licz;
        try{licz=Double.parseDouble(e1.getText().toString());}catch (Exception e){e1.setText("");t4.setText("");return;}

        double licz2=Math.floor(licz * w3 * 10000)/10000;

        t4.setText("" + licz2);
    }

    public void Odwrot(View view)
    {
        int a=paczka.getokno1();
        int b=paczka.getokno2();
        paczka.setokno1(b);
        paczka.setokno2(a);
        pisz();


    }

    public void pisz()
    {
        String p="zamień "+paczka.getlista().get(paczka.getokno1()).getNazwa()+" na "+paczka.getlista().get(paczka.getokno2()).getNazwa();
        t1.setText(p);
        t2.setText(paczka.getlista().get(paczka.getokno1()).getKod_waluty());
        t3.setText(paczka.getlista().get(paczka.getokno2()).getKod_waluty());
        t4.setText("");
    }

    public void waluta1(View view)
    {slij(0);}

    public void waluta2(View view)
    {slij(1);}

    public void slij(int nr)//nr jest rowny 0 lub 1
    {
        okno=nr;

        Intent i = new Intent(this, HelpActivity.class);//tutaj otwiera nową aktywność

        String[] text=null;
        try {
            text=paczka.toTable();//to wysyłam do innej aktywności

        } catch (Exception e) {}

        i.putExtra("help", text);//to co pszesylam

        startActivityForResult(i, 1);//aktywnosc bedzie zwracać String

        onActivityResult(1, Activity.RESULT_CANCELED, i);

    }


    @Override
    protected  void
    onActivityResult(int requestCode, int resultCode, Intent data) {

        //zmienna zwrot nie pozwala na pobranie złego wskaźnika ,gdy jest za wcześnie
        if(requestCode == 1) {
            try {
                String response = data.getStringExtra(HelpActivity.RESPONSE);

                int p =Integer.parseInt(response);

                if(okno==0){paczka.setokno1(p);}
                if(okno==1){paczka.setokno2(p);}
                pisz();

            }catch(Exception e){}
        }

    }

}
