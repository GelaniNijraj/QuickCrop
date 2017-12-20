/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickcrop;

/**
 *
 * @author buddha
 */
public class QuickCrop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setFocusable(true);
        window.requestFocus();
        window.setVisible(true);
    }
    
}
