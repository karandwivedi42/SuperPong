import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageButton extends JPanel implements ActionListener{

    
    JRadioButton button;
    JRadioButton button1;
    JRadioButton button2;
    JRadioButton button3;
    Color color;

    JButton b;
    ImageButton(){
        button = new JRadioButton("Red");
        button1 = new JRadioButton("Blue");
        button2 = new JRadioButton("Green");
        button3 = new JRadioButton("Pink");
        b = new JButton("Proceed");


        button.setBounds(200, 100, 150, 30);
        button1.setBounds(200, 150, 150, 30);
        button2.setBounds(200, 200, 150, 30);
        button3.setBounds(200, 250, 150, 30);
        b.setBounds(225, 300, 150, 30);
        b.addActionListener(this);
        ButtonGroup BG = new ButtonGroup();
        BG.add(button);
        BG.add(button1);
        BG.add(button2);
        BG.add(button3);
        add(b);
        add(button);
        add(button1);
        add(button2);
        add(button3);
        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(button.isSelected()){
            JOptionPane.showMessageDialog(this, "Red");
            color = Color.RED;
        }
    	else if(button1.isSelected()){
            JOptionPane.showMessageDialog(this, "Blue");
            color = Color.BLUE;
        }
        else if(button2.isSelected()){
            JOptionPane.showMessageDialog(this, "Green");
            color = Color.GREEN;
        }
        else if(button3.isSelected()){
            JOptionPane.showMessageDialog(this, "Pink");
            color = Color.PINK;
        }
    }
    
    public Color getColor(){
        return color;
    }
    
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    
    public void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        try{
            BufferedImage img1 = ImageIO.read(new File("/Users/Shakti/Desktop/SuperPong/Pong.jpg"));
            BufferedImage img = scale(img1, 600, 600);
            g2.drawImage(img, 0, 0, this);
            g2.finalize();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        setBackground(Color.BLACK);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Choose Your Paddle Colour", 220, 50);
        graphics.setColor(Color.RED);
        graphics.fillRoundRect(270, 100, 100, 30, 5, 5);
        graphics.setColor(Color.BLUE);
        graphics.fillRoundRect(270, 150, 100, 30, 5, 5);
        graphics.setColor(Color.GREEN);
        graphics.fillRoundRect(270, 200, 100, 30, 5, 5);
        graphics.setColor(Color.PINK);
        graphics.fillRoundRect(270, 250, 100, 30, 5, 5);
    }
    
    public static void main(String[] args){
        SplashScreen splash = new SplashScreen(10000);
        splash.showSplash();
        JFrame frame = new JFrame("Ping The Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        ImageButton game = new ImageButton();
        frame.add(game, BorderLayout.CENTER);
        
        frame.setSize(600, 624);
        frame.setVisible(true);
        
    }
}
