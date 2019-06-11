/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nowy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dami
 */
public class Text implements Serializable{
    private List<String> text = new ArrayList<String>();
    
    public Text(){}
    public Text(List<String> a)
    {
        this.text=a;
    }
    public List<String> get()
    {
        return this.text;
    }
    public void set(List<String> a)
    {
        this.text=a;
    }
}
