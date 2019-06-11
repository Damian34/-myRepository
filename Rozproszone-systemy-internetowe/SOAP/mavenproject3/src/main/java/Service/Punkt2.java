/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author dami
 */
public class Punkt2 {//klasa pomonicza dla metody AllStatus , BorrowedStatus
    public Czytelnik czytelnik;
    public Książka książka;
    public Wypożyczenie wyp;
        
    public Punkt2()
    {
        czytelnik =new Czytelnik();
        książka=new Książka();
        wyp=new Wypożyczenie();
    }
}
