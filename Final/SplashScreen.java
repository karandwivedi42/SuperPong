import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {

    private int duration;
    public SplashScreen(int d) {
        duration = d;
    }
    
    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(120,140,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 120, 150);
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
        JLabel label = new JLabel(new ImageIcon("res/Pong.jpg"));
        JLabel copyrt = new JLabel
        ("Made By, Akshit Tyagi, Rishabh Kumar, Karan Dwivedi", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(156, 20, 20,  255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

        setVisible(true);

        try { Thread.sleep(duration); } catch (Exception e) {}

        setVisible(false);
    }

}
