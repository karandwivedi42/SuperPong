import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;


public class SplashScreen extends JWindow {

    private int duration;
    JLabel label;
    
    //Constructor
    //Input : Duration of splash screen
    public SplashScreen(int d) {
        duration = d;
    }
    
    
    //Function to resize image
    //Input : image to be scaled, scaling dimensions
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

    // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor
    public void showSplash() {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        // Set the window's bounds, centering the window
        int width = 600;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        // Build the splash screen
        try{
            BufferedImage image = ImageIO.read(new File("res/Pong.jpg"));
            BufferedImage image1 = scale(image, 400, 400);
            ImageIcon icon = new ImageIcon(image1);
            label = new JLabel(icon);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        JLabel copyrt = new JLabel
        ("Made By : Akshit Tyagi, Rishabh Kumar, Karan Dwivedi", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color grey = new Color(128, 128, 128,  255);
        content.setBorder(BorderFactory.createLineBorder(grey, 10));

        setVisible(true);

        try { Thread.sleep(duration); } catch (Exception e) {}

        setVisible(false);
    }

    public static void main(String[] args){
        SplashScreen splash = new SplashScreen(5000);
        splash.showSplash();
    }
}
