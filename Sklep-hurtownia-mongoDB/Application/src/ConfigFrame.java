
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class ConfigFrame {
    
    ConfigFrame(){}
    
    ConfigFrame(JFrame fr,String Title)
    {
        fr.setTitle(Title);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr.setLocation(dim.width/2-fr.getSize().width/2, dim.height/2-fr.getSize().height/2);
                
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(fr);
        fr.pack();
        } 
        catch (Exception e) {}
        //fr.setVisible(true);
    }
    
    ConfigFrame(JFrame fr)
    {
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(fr);
        fr.pack();
        } 
        catch (Exception e) {}
    }
}
