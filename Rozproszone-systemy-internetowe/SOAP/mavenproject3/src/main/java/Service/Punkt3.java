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
public class Punkt3 {//klasa pomonicza dla metody ReaderStatus
    public Książka książka;
    public Wypożyczenie wypożyczenie;
        
    public Punkt3()
    {
        książka =new Książka();
        wypożyczenie=new Wypożyczenie();
    }
}
