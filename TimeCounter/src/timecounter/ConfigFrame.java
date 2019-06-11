package timecounter;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ConfigFrame {

    ConfigFrame(){}
    
    ConfigFrame(JFrame fr,String Title)
    {
        fr.setTitle(Title);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr.setLocation(dim.width/2-fr.getSize().width/2, dim.height/2-fr.getSize().height/2);  
        fr.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));              
    }
    
    //classic windows frame
    ConfigFrame(JFrame fr,String Title,boolean any)
    {
        fr.setTitle(Title);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr.setLocation(dim.width/2-fr.getSize().width/2, dim.height/2-fr.getSize().height/2);
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(fr);
        fr.pack();
        fr.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        } 
        catch (Exception e) {}
        //fr.setVisible(true);
    }
}
