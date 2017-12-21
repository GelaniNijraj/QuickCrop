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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.jws.Oneway;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author buddha
 */
public class MainWindow extends JFrame implements KeyListener, MouseMotionListener {
    JLabel labelPosition;
    JPanel cropArea;
    Rectangle cropAreaPosition;
    String directoryPath;
    ArrayList<File> files;
    int currentIndex = 0, cropAreaHeight = 0, cropAreaWidth = 0;
    double imageScale = 0.7;
    
    public MainWindow(){
        setSize(200, 200);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        addComponents();
        files = new ArrayList<>();
    }
    
    private void addComponents(){
        try{
            cropAreaPosition = new Rectangle(0, 0, 0, 0);
            cropArea = new JPanel();
            cropArea.addMouseMotionListener(this);
            cropArea.setOpaque(false);
            cropArea.setBorder(BorderFactory.createDashedBorder(Color.red));
            labelPosition = new JLabel();
            add(cropArea);
            add(labelPosition);
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
            case KeyEvent.VK_COMMA:
                currentIndex--;
                if(currentIndex < 0)
                    currentIndex = files.size() - 1;
                updateImage();
                break;
            case KeyEvent.VK_PERIOD:
                currentIndex = (currentIndex + 1) % files.size();
                updateImage();
                break;
            case KeyEvent.VK_EQUALS:
                imageScale += 0.1;
                break;
            case KeyEvent.VK_MINUS:
                imageScale -= 0.1;
                break;
            case KeyEvent.VK_E:
                cropAreaWidth = (int)(Integer.parseInt(JOptionPane.showInputDialog("Enter width : ")) * imageScale);
                break;
            case KeyEvent.VK_R:
                cropAreaHeight = (int)(Integer.parseInt(JOptionPane.showInputDialog("Enter height : ")) * imageScale);
                break;
            case KeyEvent.VK_C:
                cropImage();
                break;
            case KeyEvent.VK_Q:
                dispose();
        }
        updateImage();
        updateCropAreaPosition();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    private void cropImage(){
        try{
            BufferedImage image = ImageIO
                    .read(files.get(currentIndex))
                    .getSubimage(
                            (int)(cropAreaPosition.x / imageScale), 
                            (int)(cropAreaPosition.y / imageScale),
                            (int)(cropAreaPosition.width / imageScale),
                            (int)(cropAreaPosition.height / imageScale)
                    );
            File file = new File(directoryPath + "/_" + files.get(currentIndex).getName());
            ImageIO.write(image, "png", file);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    private void updateImage(){
        try{
            BufferedImage image = ImageIO.read(files.get(currentIndex));
            labelPosition.setIcon(new ImageIcon(image.getScaledInstance((int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale), Image.SCALE_FAST)));
            labelPosition.setBounds(0, 0, (int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale));
            setSize((int)(image.getWidth() * imageScale), (int)(image.getHeight() * imageScale));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void updateCropAreaPosition(){
        cropAreaPosition.height = (int)(cropAreaHeight * imageScale);
        cropAreaPosition.width = (int)(cropAreaWidth * imageScale);
        if(cropAreaPosition.x < 0)
            cropAreaPosition.x = 0;
        if(cropAreaPosition.y < 0)
            cropAreaPosition.y = 0;
        cropArea.setBounds(cropAreaPosition);
    }

    private void chooseFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            directoryPath = fileChooser.getSelectedFile().getAbsolutePath();
            File folder = new File(directoryPath);
            for(File f:folder.listFiles()){
                if(f.getName().startsWith("_"))
                    continue;
                files.add(f);
            }
            currentIndex = 0;
            updateImage();
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
        cropAreaPosition.x = me.getX();
        cropAreaPosition.y = me.getY();
        cropArea.setBounds(cropAreaPosition);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }
    
}
