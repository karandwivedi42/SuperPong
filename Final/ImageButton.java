import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;

public class ImageButton extends JPanel implements ActionListener{

    
    JRadioButton button;
    JRadioButton button1;
    JRadioButton button2;
    JRadioButton button3;
    Color color;
    AudioClip intro;
    JButton b;
    JTextArea textArea;
    ImageButton(){
        button = new JRadioButton("Red");
        button1 = new JRadioButton("Blue");
        button2 = new JRadioButton("Green");
        button3 = new JRadioButton("Pink");
        b = new JButton("Proceed");
        intro = JApplet.newAudioClip(getClass().getResource("res/0783.aiff"));
        textArea = new JTextArea("Some text\nSome other text");

        button.setBounds(30, 285, 150, 30);
        button1.setBounds(285, 30, 150, 30);
        button2.setBounds(420, 285, 150, 30);
        button3.setBounds(285, 540, 150, 30);
        b.setBounds(225, 300, 150, 30);
        textArea.setBounds(100, 100, 50, 20);
        
        b.addActionListener(this);
        ButtonGroup BG = new ButtonGroup();
        BG.add(button);
        BG.add(button1);
        BG.add(button2);
        BG.add(button3);
        add(textArea);
        add(b);
        add(button);
        add(button1);
        add(button2);
        add(button3);
        setSize(300,300);
        setLayout(null);
        setVisible(true);
        JLabel choose = new JLabel
        ("Choose Your Colour", JLabel.CENTER);
        add(choose, BorderLayout.NORTH);
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
            BufferedImage img1 = ImageIO.read(new File("res/nebula_brown.png"));
            BufferedImage img = scale(img1, 600, 600);
            g2.drawImage(img, 0, 0, this);
            g2.finalize();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        setBackground(Color.BLACK);
        graphics.setColor(Color.WHITE);
        
        graphics.setColor(Color.RED);
        graphics.fillRoundRect(0, 250, 30, 100, 5, 5);
        graphics.setColor(Color.BLUE);
        graphics.fillRoundRect(250, 0, 100, 30, 5, 5);
        graphics.setColor(Color.GREEN);
        graphics.fillRoundRect(570, 250, 30, 100, 5, 5);
        graphics.setColor(Color.PINK);
        graphics.fillRoundRect(250, 570, 100, 30, 5, 5);
    }
    
    public String getIP(){
        return textArea.getText();
    }
    
    public static void main(String[] args){
        SplashScreen splash = new SplashScreen(5000);
        splash.showSplash();
        JFrame frame = new JFrame("Star Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        ImageButton game = new ImageButton();
        frame.add(game, BorderLayout.CENTER);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(600, 624);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        game.intro.play();
        System.out.println(game.getIP());
    }
}
