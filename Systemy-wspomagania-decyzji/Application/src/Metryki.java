
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import java.util.ArrayList;
import java.util.List;
import org.math.plot.utils.Array;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dami
 */
public class Metryki {

    public Metryki() {
    }
    
    public Metryki(Tabela kol) {
        //podczas wywoływania metod które biorą numer wiersza potrzebna jest aktualizacja w klasie Kolumny
        //lub można podać własne wiersze ,wiec tutaj jesli podam tą klase to ją odrazu zaktualizuje
        if(kol!=null)
        {
            kol.Aktualizacja();//jesli sie poda obiekty Kolumny == null to potem sie wszystko posypie 
        }//przy póżniejszym podawnaiu numerów wierszy ,czy liczeniu macierzy covariancji
    }

    public double Eukl(Tabela kol, int X0, int Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, X0, Y0, klas, 0, null, null);
    }

    public double Eukl(Tabela kol, List<String> X0, int Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, -1, Y0, klas, 0, X0, null);
    }
    
    public double Eukl(Tabela kol, int X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, X0, -1, klas, 0, null, Y0);
    }
    
    public double Eukl(Tabela kol, List<String> X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, -1, -2, klas, 0, X0, Y0);
    }
    ////////////////////////////////

    public double Miejska(Tabela kol, int X0, int Y0, int klas) {
        //metoda zwraca odległosć w metryce Miejskiej / Manhattan
        return Met(kol, X0, Y0, klas, 1, null, null);
    }

    public double Miejska(Tabela kol, List<String> X0, int Y0, int klas) {
        //metoda zwraca odległosć w metryce Miejskiej / Manhattan
        return Met(kol, -1, Y0, klas, 1, X0, null);
    }

    public double Miejska(Tabela kol, int X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, X0, -1, klas, 1, null, Y0);
    }
    
    public double Miejska(Tabela kol, List<String> X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, -1, -2, klas, 1, X0, Y0);
    }
    
    ///////////////////////////////////
    
    public double Infinity(Tabela kol, int X0, int Y0, int klas) {
        //metoda zwraca odległosć w metryce nieskończoność 
        return Met(kol, X0, Y0, klas, 2, null, null);
    }

    public double Infinity(Tabela kol, List<String> X0, int Y0, int klas) {
        //metoda zwraca odległosć w metryce nieskończoność 
        return Met(kol, -1, Y0, klas, 2, X0, null);
    }
    
    public double Infinity(Tabela kol, int X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, X0, -1, klas, 2, null, Y0);
    }
    
    public double Infinity(Tabela kol, List<String> X0, List<String> Y0, int klas) {
        //metoda zwraca odległosć euklidesową 
        return Met(kol, -1, -2, klas, 2, X0, Y0);
    }
    ///////////////////////////////////

    
    private double Met(Tabela kol, int X0, int Y0, int klas, int typ, List<String> X0_lista, List<String> Y0_lista)
    {
        //Y0_lista jest druga zmienna podana przeze mnie
        //X0_lista zakładam że bedzie on albo null albo bedzie mieć liczbe elementów zgodną z liczbą kolumn
        //X0_lista jest moim podanym wierszem który klasyfikuje
        //metoda zwraca odległosć miedzy punktem X0 - punkt względem którego szukam metryk
        //,a punktem Y1 - punkt ktory jest oddalony od X0 ,a szukam odległosci wzgledem X0 a Y1
        // klas - zmienna oznaczająca kolumne zwgledem ktorej klasyfikuje
        if (X0 == Y0) {
            return 0;
        }
        

        List<String> l1 = null;//= kol.WierszBezKolumny2(X0, klas);
        if (X0_lista == null)//jeśli nie podaje wiersza w postaci listy
        {
            if(klas>0 && klas<kol.ileKolumn)
            l1 = kol.WierszBezKolumny2.get(X0).get(klas);
            else
            l1 = kol.WierszBezKolumny2(X0,klas);//to w przypadku gdyby klas bylo z poza zakresu
            
        } else {
            l1 = kol.WierszBezKolumny2(X0_lista, klas);
        }
        
        //List<String> l2 = kol.WierszBezKolumny2(Y1 , klas);
        List<String> l2 = null;
        if (Y0_lista == null)//jeśli nie podaje wiersza w postaci listy
        {
        if(klas>0 && klas<kol.ileKolumn)
        l2= kol.WierszBezKolumny2.get(Y0).get(klas);
        else
        l2 = kol.WierszBezKolumny2(Y0,klas);//to w przypadku gdyby klas bylo z poza zakresu
        }else {
            l2 = kol.WierszBezKolumny2(Y0_lista, klas);
        }
        
        List<Double> X2 = new ArrayList<>();//wartosci z wiersza X0
        List<Double> Y2 = new ArrayList<>();//wartosci z wiersza Y1
        try {
            for (int i = 0; i < l1.size(); i++) {
                X2.add(Double.valueOf(l1.get(i)));
                Y2.add(Double.valueOf(l2.get(i)));
            }
            
            
        }catch (Exception e) 
        {
            System.out.println("blad: "+e);
            return -1;
        }

        double sum = 0;
        for (int i = 0; i < X2.size(); i++) {
            double pom = X2.get(i) - Y2.get(i);
            if (typ == 0) {
                sum = sum + pom * pom;
            }
            if (typ == 1) {
                sum = sum + Math.abs(pom);
            }
            if (typ == 2) {
                double pom2 = Math.abs(pom);
                if (pom2 > sum) {
                    sum = pom2;
                }
            }
        }
        

        double zwrot = 0;
        if (typ == 0) {
            zwrot = Math.sqrt(sum);
        }
        if (typ == 1 || typ == 2) {
            zwrot = sum;
        }
        
        return zwrot;
    }

    /////////////////////////////////////
    
    public double Mah(Tabela kol, int X0, int Y0, int klas, List<List<Double>> cov) {
        return Mah(kol, X0, Y0, klas, cov, null, null);
    }

    public double Mah(Tabela kol, List<String> X0, int Y0, int klas, List<List<Double>> cov) {
        return Mah(kol, -1, Y0, klas, cov, X0, null);
    }
    
    public double Mah(Tabela kol, int X0, List<String> Y0, int klas, List<List<Double>> cov) {
        return Mah(kol, X0, -1, klas, cov, null, Y0);
    }
    
    public double Mah(Tabela kol, List<String> X0, List<String> Y0, int klas, List<List<Double>> cov) {
        return Mah(kol, -1, -2, klas, cov, X0, Y0);
    }
    
    //Cov tablica kowariancji odwrucona lub nie odwrucona
    private double Mah(Tabela kol, int X0, int Y0, int klas, List<List<Double>> cov, List<String> X0_lista , List<String> Y0_lista)
    {
        //metryka Mahalanobisa
        //metoda zwraca odległosć miedzy punktem X0 - punkt względem którego szukam metryk
        //,a punktem Y1 - punkt ktory jest oddalony od X0 ,a szukam odległosci wzgledem X0 a Y1
        // klas - zmienna oznaczająca kolumne zwgledem ktorej klasyfikuje
        //X0_lista zakładam że bedzie on albo null albo bedzie mieć liczbe elementów zgodną z liczbą kolumn
        //X0_lista jest moim podanym wierszem który klasyfikuje
        if (X0 == Y0) {
            return 0;
        }
        
        if(cov==null)//zwracam że macierz covariancji odwrotna jest == null gdy jest nieodwracalna
        {
            return -1;
        }

        List<String> l1 = null;//= kol.WierszBezKolumny2(X0, klas);
        if (X0_lista == null)//jeśli nie podaje wiersza w postaci listy
        {
            if(klas>0 && klas<kol.ileKolumn)
            l1 = kol.WierszBezKolumny2.get(X0).get(klas);
            else
            l1 = kol.WierszBezKolumny2(X0,klas);//to w przypadku gdyby klas bylo z poza zakresu
            
        } else {
            l1 = kol.WierszBezKolumny2(X0_lista, klas);
        }

        //List<String> l2 = kol.WierszBezKolumny2(Y1,klas);
        //List<String> l2 = kol.WierszBezKolumny2.get(Y1).get(klas);
        List<String> l2 = null;
        if (Y0_lista == null)//jeśli nie podaje wiersza w postaci listy
        {
            if(klas>0 && klas<kol.ileKolumn)
            l2= kol.WierszBezKolumny2.get(Y0).get(klas);
            else
            l2 = kol.WierszBezKolumny2(Y0,klas);//to w przypadku gdyby klas bylo z poza zakresu

        } else {
            l2 = kol.WierszBezKolumny2(Y0_lista, klas);
        }
        
        
        
        
        
        List<Double> X2 = new ArrayList<>();//wartosci z wiersza X0
        List<Double> Y2 = new ArrayList<>();//wartosci z wiersza Y1
        try {
            for (int i = 0; i < l1.size(); i++) {
                X2.add(Double.valueOf(l1.get(i)));
                Y2.add(Double.valueOf(l2.get(i)));
            }
        } catch (Exception e) {
            return -1;
        }

        //tutaj nie jestem pewny co do odleglosci
        List<Double> odl = new ArrayList<>();//odleglosc punktu X0 od Y1

        for (int i = 0; i < X2.size(); i++) {
            odl.add(Y2.get(i) - X2.get(i));
        }//System.out.println(odl.get(i));}

        List<Double> licz1 = new ArrayList<>();//tutaj jest cov^(-1)*(X0-Y1)

        for (int i = 0; i < odl.size(); i++) {
            List<Double> wiersz = cov.get(i);//pobieram wiersz z cov^(-1)

            double sum = 0;
            for (int j = 0; j < odl.size(); j++) {
                sum = sum + odl.get(j) * wiersz.get(j);
            }
            licz1.add(sum);
        }

        double sum = 0;
        for (int i = 0; i < odl.size(); i++)//tutaj jest (X0-Y1)*(cov^(-1)*(X0-Y1))
        {
            sum = sum + odl.get(i) * licz1.get(i);
        }

        
        double wynik = Math.abs(Math.sqrt(sum));

        
        return wynik;
    }

    public List<List<Double>> Cov(Tabela kol, int klas ,int BezWiersza) 
    {        
        //parametr BezWiersza oznacza ignorowany wiersz z klasy Kolumny
        //metoda zwraca macierz kovariancji względem podanej kolumny klasyfikacji oznaczonej zmienną klas

        //na początek pobieram z kol liste kolumn
        List<List<Double>> list = kol.KolumnyWskazane(klas,BezWiersza);

        List<Double> srednie = new ArrayList<>();//lista srednich wzgledem kazdej zmiennej losowej
        
        for (int i = 0; i < list.size(); i++) {
            double sum = 0;
            for (int j = 0; j < list.get(i).size(); j++) {
                sum = sum + list.get(i).get(j);
            }
            srednie.add(sum / list.get(i).size());
        }
        /*
        for(int i=0;i<list.size();i++)
        System.out.println(list.get(i));
        */
        List<List<Double>> list2 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++)//przechodze po kolumnach w macierzy kowariancji
        {//ale jednoczesnie jest to moja pierwsza zmienna losowa
            List<Double> list3 = new ArrayList<>();

            //macież kowariancji jest symetryczna wiecj skróce ją i potem przepisze dane
            for (int j = 0; j < i; j++) {
                list3.add(0.0);
            }

            for (int j = i; j < list.size(); j++)//przechodze po wierszach w macierzy kowariancji
            {//ale jednoczesnie jest to moja druga zmienna losowa
                //błąd ,bierze tą samo zmienną losowo
                                
                List<Double> X1 = list.get(i);//1 zmienna losowa
                List<Double> X2 = list.get(j);//2 zmienna losowa
                //wyznaczam E(XY)
                double sum = 0;
                for (int k = 0; k < X1.size(); k++) {
                    sum = sum + X1.get(k) * X2.get(k);
                }

                double sum2 = sum / X1.size() - srednie.get(i) * srednie.get(j);
                //System.out.println(X1+"  |  "+X2+"  | "+sum+" | "+srednie.get(i)+" | "+srednie.get(j)+" | "+sum2);

                list3.add(sum2);
            }
            list2.add(list3);
        }
        
        //przepisuje dane ,po tym jak wczesniej wyliczylem macierz trujkątno
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                list2.get(i).set(j, list2.get(j).get(i));
            }
        }
        
        
        return list2;
    }
    
    
    public List<List<Double>> Cov2(Tabela kol, int klas ,int BezWiersza) 
    {
        //metoda zwraca odwruconą macierz kowariancji
        //parametr BezWiersza oznacza ignorowany wiersz z klasy Kolumny
        
        
        //jesli list bedzie macierzą 2 na 2 to wyznacznik == 0
        List<List<Double>> list = Cov(kol, klas ,BezWiersza);
        double[][] tab = new double[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            double[] tab2 = new double[list.size()];
            for (int j = 0; j < list.get(i).size(); j++) {
                tab2[j] = list.get(i).get(j);
                //System.out.print(list.get(i).get(j)+"  ");
            }
            tab[i] = tab2;
            //System.out.println();
        }
        

        //odwracam macierz
        SparseDoubleMatrix2D lap = new SparseDoubleMatrix2D(tab);
        Algebra b = new Algebra();
        double[][] tab2 =null;
        try{
        
        tab2 = b.inverse(lap).toArray();//odwracanie macierzy
        }
        catch(Exception e)
        {
            System.out.println("wyznacznik macierzy jest = "+b.det(lap));//wyznacnzik macierzy przed odwruceniem
                    
        }
        
        List<List<Double>> list2 = new ArrayList<>();

        for (int i = 0; i < tab2.length; i++) {
            List<Double> list3 = new ArrayList<>();
            for (int j = 0; j < tab2.length; j++) {
                list3.add(tab2[i][j]);
            }
            list2.add(list3);
        }
        
        

        return list2;
    }

    public Double Miara(Tabela kol ,List<String> X0_lista,List<String> Y0_lista, int klas, int typ,List<List<Double>> cov0)
    {
        
        //klasa - oznacza którą kolumne ignorować
        //List<Double> list = new ArrayList<>();
        Double wynik = 0.0;
        
        if (typ < 0 || typ > 3) {
            return null;
        }//zwracam null jeśli typ jest nie poprawny
        
        if(typ==0)
        {
            wynik = this.Eukl(kol, X0_lista, Y0_lista, klas);
        }
        
        if (typ == 1) 
        {
            wynik = this.Miejska(kol, X0_lista, Y0_lista, klas);
        }

        if (typ == 2) 
        {
            wynik = this.Infinity(kol, X0_lista, Y0_lista, klas);
        }
        
        if (typ == 3) 
        {
            List<List<Double>> cov = null;
            if(cov0==null)//jesli nie podam macierzy covariancji to losuj nową
            cov = Cov2(kol, klas ,-1);
            else
            cov = cov0;
            
            ////////
            wynik = this.Mah(kol, X0_lista, Y0_lista, klas,cov);
        }
        
        return wynik;
    }
  
}
