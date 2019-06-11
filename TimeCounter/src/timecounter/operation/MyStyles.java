package timecounter.operation;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MyStyles {

    public void changeSimpleButton(JButton button) {
        button.setForeground(Color.WHITE);
        //button.setBackground(new Color(0xff5050ff, true));
        button.setBackground(new Color(101, 101, 101));
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        Border line = new LineBorder(new Color(0x00ffffff, true));
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
    }

    public void setSimpleBackgroundPanel(JPanel panel) {
        panel.setBackground(new Color(0xfff4f4f4, true));
    }

    public void changeJLabelToBlack(JLabel label) {
        label.setForeground(Color.BLACK);
    }

    public void changeJLabelToWhiteBold(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
    }

    public void changeJLabelToBlackBold(JLabel label) {
        label.setForeground(Color.BLACK);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
    }

}
