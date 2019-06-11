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

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public static final String RESPONSE = "Response";
    TextView t1;
    ListView l1;

    Paczka paczka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t1  = (TextView) findViewById(R.id.text1);

        l1 = (ListView)findViewById(R.id.listview);

        Bundle b = getIntent().getExtras();
        String[] text=b.getStringArray("paczka");//klucz - paczka

        paczka = new Paczka(text);

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
        //usuwam element wcisniety wczesniej
        List<Waluta> p = paczka.getlista();
        p.remove(paczka.getokno1());
        //paczka.setlista(p);
        //wyswietlam liste
        try {
            if (p != null) {
                //List<Waluta> list = paczka.getlista();
                String[] pom = new String[p.size()];
                for(int i=0;i<p.size();i++)
                {
                    pom[i]=p.get(i).getNazwa();
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pom);
                l1.setAdapter(adapter2);
            }
        }catch (Exception e){}//try{t1.setText("błąd: "+e);}catch (Exception e2){}}
    }

    public void aktywny(int index)
    {
        try {
            Intent i = new Intent(this, MainLastActivity.class);//tutaj otwiera nową aktywność

            if(index < paczka.getokno1())
            {paczka.setokno2(index);}
            else
            {paczka.setokno2(index+1);}

            String[] text=null;
            try {
                text=paczka.toTable();
            } catch (Exception e) {}

            //putExtra umieszcza argument
            i.putExtra("paczka2", text);//"To właśnie przesłano do drugiej aktywności");

            startActivity(i);//aktywnosc nic nie bedzie zwracac

            prepareResponse("1");//wysyłam coś by zamknąć potem 1 aktywnośc wraz z obecną
            finish();

        }catch (Exception e){}
    }


    private void
    prepareResponse(String response) {

        Intent resultIntent = new Intent(Main2Activity.this,MainActivity.class);

        resultIntent.putExtra(RESPONSE, response);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
