package com.example.dami.przelicznik;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String  DEBUG_TAG  = "HttpExample";
    TextView t1;
    ListView l1;

    ProgressBar pb;
    TextView pt;

    private Handler mHandler = new Handler();

    Paczka paczka2=new Paczka();
    Task pobrane;
    int stan=0;
    boolean stan2=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1  = (TextView) findViewById(R.id.text1);

        l1 = (ListView)findViewById(R.id.listview);

        pt  = (TextView) findViewById(R.id.progress_text);
        pb = (ProgressBar)findViewById(R.id.progress_bar);


        /////////////////////////////
        String[] pom=new String[0];
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pom);
        l1.setAdapter(adapter2);

        //pobieram dane o kursach
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()) {
            pobrane = new Task();
            pobrane.execute();
        }
        else
        {
            try {
                paczka2 = czytaj();
                t1.setText("wybierz walute do zamiany :");
                pisz(paczka2);
                paczka2.setStan(1);//po przeslaniu do MainLastActivity ,do poinformowania

                stan2 = true;//wylaczam hendlera jesli nie ma połączenia
                Toast.makeText(getBaseContext(), "brak połączenia", Toast.LENGTH_SHORT).show();
            }catch (Exception e){t1.setText("błąd ,brak danych ,prosze połączyć sie z siecią i pobrać najnowsze dane");}
        }
        //////////////////////////

        new Thread(
                new Runnable(){
                    public void run(){
                        while(true)
                        {
                            // Update the progress bar
                            //znak () -> { jest raczej istotny
                            mHandler.post(
                                    new Runnable(){
                                        public void run() {
                                            String q0=".";
                                            for(int i=0;i<stan;i++){q0=q0+".";}
                                            pt.setText("wait"+q0);
                                        } });

                            if(stan2){//wykonuje po zakonczeniu operacji w metodzie Task()

                                ////////////////

                                stan2=false;break;}//wylaczam hendlera jesli nie ma połączenia lub zakoncze działanie metody Task
                        }
                    }
                }).start();


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ustawiam ze po kliknieciu na element listy otiwera sie drugie okno

                aktywny2(position);
            }
        });


    }


    ////////////


    //klasa ma parametry: parametr,stan,wynik
    private class Task extends AsyncTask<String,Void,String> {
        public Paczka paczka=null;

        @Override
        protected String doInBackground(String... urls) {

            try{

                Paczka p=polacz();
                paczka=p;

                return "";
            }catch(Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPost Execute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            pb.setVisibility(View.GONE);


            pb.setVisibility(ProgressBar.INVISIBLE);
            pt.setVisibility(ProgressBar.INVISIBLE);


            //wyswietlam liste
            t1.setText("wybierz walute do zamiany :");
            pisz(paczka);
            paczka2=paczka;
            stan2=true;

            //zapisuje dane jeśli jest dostępne połączenie
            zapisz(paczka);
        }

        @Override
        protected void onPreExecute(){

            pb.setVisibility(ProgressBar.VISIBLE);
            pt.setVisibility(ProgressBar.VISIBLE);
        }
        //@Override
        protected void onProgressUpdate(Integer ...values)
        {
            //pb.setProgress(values[0]);
        }
    }


    public Paczka polacz()
    {
        try{
            String html="http://www.nbp.pl/home.aspx?f=/kursy/kursya.html";

            URL url = new URL(html);

            String pom="";
            Scanner buff = new Scanner(url.openStream());

            String data="";

            //pobieram date i aktualny link
            while(buff.hasNext()){
                stan=(stan+1)%3;//po pobraniu lini zmieniam stan
                String dane = buff.nextLine();

                ///////pobieram date wystawienia tabeli
                if(dane.indexOf("Tabela nr")!=-1 && dane.indexOf("z dnia ")!=-1)
                {
                    int a = dane.indexOf("z dnia ")+"z dnia ".length();
                    data=dane.substring(a, a+"rrrr-mm-dd".length());
                }
                ///////

                //na tej stronie po "/kursy/xml/" nasepuje poszukiwana część do strony z najnowszymi danymi
                if(dane.indexOf("/kursy/xml/")!=-1)
                {pom=dane;break;}
            }

            String str="";
            int p=pom.indexOf("/kursy/xml/");
            while(pom.charAt(p)!='"')//przechodze po tej linijce i wczytuje np /kursy/xml/a083z160429.xml
            {
                str=str+pom.charAt(p);
                p++;
            }

            //przechodze na pobraną strone
            String html2="http://www.nbp.pl"+str;

            URL url2 = new URL(html2);
            Scanner buff2 = new Scanner(url2.openStream(),"ISO-8859-2");//ISO-8859-2 do czytania polskich znaków

            List<Waluta> wal = new ArrayList<Waluta>();
            List<String> q1 = new ArrayList<String>();

            int k=0;
            //pobieram dane o kursach
            while(buff2.hasNext()){
                stan=(stan+1)%3;//po pobraniu lini zmieniam stan
                String dane = buff2.nextLine();
                q1.add(dane);
            }

            List<String[]> q2 = new ArrayList<String[]>();

            //dodaje złotówke
            wal.add(new Waluta("złoty polski",1,"PLN",1));

            for(int i=0;i<q1.size();i++)
            {
                if(q1.get(i).indexOf("<pozycja>")!=-1)
                {
                    //zakładam że kolejność może sie zmienić, a przyjmuje Stringi wg ustalonej kolejnosci:
                    //nazwa,przelicznik,kod,kurs sredni

                    String[] a = new String[4];

                    for(int j=i+1;j<=i+4;j++)
                        if(q1.get(j).indexOf("<nazwa_waluty>")!=-1)
                        {a[0]=q1.get(i+1).replace("<nazwa_waluty>","").replace("</nazwa_waluty>","").trim();break;}

                    for(int j=i+1;j<=i+4;j++)
                        if(q1.get(j).indexOf("<przelicznik>")!=-1)
                        {a[1]=q1.get(i+2).replace("<przelicznik>","").replace("</przelicznik>","").trim();break;}

                    for(int j=i+1;j<=i+4;j++)
                        if(q1.get(j).indexOf("<kod_waluty>")!=-1)
                        {a[2]=q1.get(i+3).replace("<kod_waluty>","").replace("</kod_waluty>","").trim();break;}

                    for(int j=i+1;j<=i+4;j++)
                        if(q1.get(j).indexOf("<kurs_sredni>")!=-1)
                        {a[3]=q1.get(i+4).replace("<kurs_sredni>","").replace("</kurs_sredni>","").trim().replace(",",".");break;}

                    q2.add(a);

                    Waluta w = new Waluta(a[0],Integer.parseInt(a[1]),a[2],Double.parseDouble(a[3]));
                    wal.add(w);

                    i=i+4;
                }
            }

            return new Paczka(zmien(data),wal);
        }catch(Exception e){return null;}
    }

    private Date zmien(String data)
    {
        String p=data;//"2016-04-16";
        String[] p2=p.replace(" ","").split("-");
        int[] p3=new int[p2.length];
        for(int i=0;i<p2.length;i++){try{p3[i]=Integer.parseInt(p2[i]);}catch(Exception e){}}
        GregorianCalendar b = new GregorianCalendar(p3[0],p3[1]-1,p3[2]);
        Date b2 =new Date(b.getTimeInMillis());

        return b2;
    }


    public void pisz(Paczka paczka)//wywoluje w klasie Task
    {
        try {
            if (paczka != null) {
                List<Waluta> list = paczka.getlista();
                String[] pom = new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    pom[i]=list.get(i).getNazwa();
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pom);
                l1.setAdapter(adapter2);
            }
        }catch (Exception e){t1.setText("błąd: "+e);}//try{t1.setText("błąd: "+e);}catch (Exception e2){}}
    }


    public void aktywny2(int index)
    {
        try {
            Intent i = new Intent(this, Main2Activity.class);//tutaj otwiera nową aktywność

            paczka2.setokno1(index);
            String[] text=null;
            try {
                text=paczka2.toTable();
            } catch (Exception e) {}

            //putExtra umieszcza argument
            i.putExtra("paczka", text);//"To właśnie przesłano do drugiej aktywności");

            startActivityForResult(i, 1);
            onActivityResult(1, Activity.RESULT_CANCELED, i);

            //startActivity(i);//aktywnosc nic nie bedzie zwracac

        }catch (Exception e){}
    }


    @Override
    protected  void
    onActivityResult(int requestCode, int resultCode, Intent data) {

        //zmienna zwrot nie pozwala na pobranie złego wskaźnika ,gdy jest za wcześnie
        if(requestCode == 1) {
            try {
                String response = data.getStringExtra(HelpActivity.RESPONSE);

                //zamykam tą aktywność po zamknieciu wywołanej
                if(response.equals("1")){finish();}
            }catch(Exception e){}
        }

    }

    //zapis do pliku internal/wewnetrzny
    public void zapisz(Paczka p)
    {
        try{
            String[] p2=p.toTable();
            String dane="";
            for(int i=0;i<p2.length;i++)
            {
                if(i==0)
                {dane=p2[i];}
                else
                {dane=dane+":"+p2[i];}
            }

            String file="dane";

            FileOutputStream out = openFileOutput(file,MODE_PRIVATE);
            out.write(dane.getBytes());
            out.close();
            //Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();

        }catch(Exception e){}
    }

    //czytanie z pliku
    public Paczka czytaj()
    {
        String text2="";
        try{
            String text="";
            String file="dane";
            FileInputStream in = openFileInput(file);
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader buffer = new BufferedReader(read);

            while((text=buffer.readLine())!=null)
            {
                text2=text2+text;
            }
            String[] s = text2.split(":");
            Paczka p = new Paczka(s);

            //Toast.makeText(getBaseContext(),"file read",Toast.LENGTH_SHORT).show();
            return p;



        }catch(Exception e){Toast.makeText(getApplicationContext(),"brak pliku",Toast.LENGTH_LONG ).show();}

        return null;
    }

}
