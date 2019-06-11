package com.example.dami.przelicznik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HelpActivity extends AppCompatActivity {
    public static final String RESPONSE = "Response";

    TextView t1;
    ListView l1;

    Paczka paczka;
    Paczka paczka2;

    int okno=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        t1  = (TextView) findViewById(R.id.text1);

        l1 = (ListView)findViewById(R.id.listview);

        Bundle b = getIntent().getExtras();
        String[] text=b.getStringArray("help");//klucz - help

        try {

            paczka = new Paczka(text);
            paczka2 = new Paczka(text);

            //modyfikuje paczka
            List<Waluta> list=paczka.getlista();
            for(int i=0;i<list.size();i++)//zamapietuje index
            {
                list.get(i).index=i;
            }
            list.remove(paczka.getokno1());//usuwam 1 element

            for(int i=0;i<list.size();i++)
            if(list.get(i).index==paczka.getokno2()){list.remove(i);}//usuwam 2 element

            paczka.setlista(list);


        }catch (Exception e){t1.setText("błąd: "+e);}

        pisz(paczka);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ustawiam ze po kliknieciu na element listy otiwera sie drugie okno

                aktywny(position);
            }
        });
    }

    public void pisz(Paczka paczka)//wywoluje w klasie Task
    {
        List<Waluta> p = paczka.getlista();

        //wyswietlam liste
        try {
            if (p != null) {

                String[] pom = new String[p.size()];
                for(int i=0;i<p.size();i++)
                {
                    pom[i]=p.get(i).getNazwa();
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pom);
                l1.setAdapter(adapter2);
            }
        }catch (Exception e){}
    }

    public void aktywny(int indexx)
    {
        //przesylam string z nr
        try {
            int index2=0;

            List<Waluta> list=paczka.getlista();

            index2 = list.get(indexx).index;//pobieram index początkowy


            prepareResponse(index2+"");


        }catch (Exception e)
        {
            if(okno==0){prepareResponse(paczka2.getokno1()+"");}
            if(okno==1){prepareResponse(paczka2.getokno2()+"");}
        }
    }


    private void
    prepareResponse(String response) {

        Intent resultIntent = new Intent(HelpActivity.this,MainLastActivity.class);

        resultIntent.putExtra(RESPONSE, response);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
