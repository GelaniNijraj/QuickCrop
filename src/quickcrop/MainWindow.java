/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickcrop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.jws.Oneway;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author buddha
 */
public class MainWindow extends JFrame implements KeyListener {
    JLabel labelPosition;
    JPanel cropArea;
    Rectangle cropAreaPosition;
    String directoryPath;
    double imageScale = 0.7;
    
    public MainWindow(){
        setSize(200, 200);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        addComponents();
        System.out.println("nijraj");
    }
    
    private void addComponents(){
        try{
            JButton button = new JButton("Click Me!");
            cropAreaPosition = new Rectangle(0, 0, 10, 10);
            BufferedImage image = ImageIO.read(new File("/home/buddha/blah/1.png"));
            labelPosition = new JLabel(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale), Image.SCALE_FAST)));
            labelPosition.setBounds(0, 0, (int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale));
            setSize((int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale));
            cropArea = new JPanel();
            cropArea.setBounds(cropAreaPosition);
            cropArea.setOpaque(false);
            cropArea.setBorder(BorderFactory.createDashedBorder(Color.red));
            add(cropArea);
            add(labelPosition);
            add(button);
        }catch(Exception e){
            
        }
    }

    public double getImageScale() {
        return imageScale;
    }

    public void setImageScale(double imageScale) {
        this.imageScale = imageScale;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()){
            case KeyEvent.VK_A:
                if(ke.isShiftDown())
                    cropAreaPosition.x--;
                else
                    cropAreaPosition.x -= 10;
                break;
            case KeyEvent.VK_D:
                if(ke.isShiftDown())
                    cropAreaPosition.x++;
                else
                    cropAreaPosition.x += 10;
                break;
            case KeyEvent.VK_W:
                if(ke.isShiftDown())
                    cropAreaPosition.y--;
                else
                    cropAreaPosition.y -= 10;
                break;
            case KeyEvent.VK_S:
                if(ke.isShiftDown())
                    cropAreaPosition.y++;
                else
                    cropAreaPosition.y += 10;
                break;
            case KeyEvent.VK_O:
                chooseFolder();
                break;
        }
        cropArea.setBounds(cropAreaPosition);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void chooseFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            directoryPath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(directoryPath);
        }
    }
    
}
