/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickcrop;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author buddha
 */
public class CropSizeDialog extends JOptionPane {
    public CropSizeDialog(){
        addComponents();
    }
    
    private void addComponents(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JTextField());
        panel.add(new JTextField());
        panel.add(new JButton("Set"));
        add(panel);
    }
}
