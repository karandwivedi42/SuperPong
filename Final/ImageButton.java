import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;

public class ImageButton extends JPanel{

    static PlayerNetworkManager nm; // nm stores a GameManager inside it.

    
    
    
    JRadioButton button;
    JRadioButton button1;
    JRadioButton button2;
    JRadioButton button3;
    Color color;
    AudioClip intro;
    JButton b;
    JButton b1;
    JTextField textArea;
    JTextField textArea1;
    ImageButton(){
        
    nm = new PlayerNetworkManager();
    
    
    
    button = new JRadioButton("Red");
    button1 = new JRadioButton("Blue");
    button2 = new JRadioButton("Green");
    button3 = new JRadioButton("Pink");
    b = new JButton("Create Game");
    b1 = new JButton("Join Game");
    intro = JApplet.newAudioClip(getClass().getResource("res/0783.aiff"));
    textArea = new JTextField("Your IP3: ");
    textArea1 = new JTextField("Enter Host IP");

    button.setBounds(30, 285, 150, 30);
    button1.setBounds(285, 30, 150, 30);
    button2.setBounds(420, 285, 150, 30);
    button3.setBounds(285, 540, 150, 30);
    b.setBounds(50, 140, 150, 30);
    b1.setBounds(400, 140, 150, 30);
    textArea.setBounds(50, 100, 150, 20);
    textArea1.setBounds(400, 100, 150, 20);
        
    b.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                System.out.print("sadfsa");

                nm.createGame();
                System.out.println("Create game called from gui");
/*
                String side = "NONE";
                Paddle p;
                if(button.isSelected()){
                    side = "LEFT";
                    p = new Paddle(150,"VERTICAL",4,300,Color.decode("#FFA000"));
                }
            	else if(button1.isSelected()){
                    side = "TOP";
                    p = new Paddle(150,"HORIZONTAL",300,596,Color.decode("#FFA000"));
                }
                else if(button2.isSelected()){
                    side = "RIGHT";
                    p = new Paddle(150,"VERTICAL",596,300,Color.decode("#FFA000"));
                }
                else if(button3.isSelected()){
                    side = "BOTTOM";
                    p = new Paddle(150,"HORIZONTAL",300,596,Color.decode("#FFA000"));
                }

                if (side == "NONE"){
                    //JOptionPane.showMessageDialog(this, "Please choose a side.");
                }
                else{
                    //Player name is his IP
                //    Player me = new Player("DUMMY NAME","dummy ip",side,p,w3);

                    
                }*/

         }   
    });
        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                 boolean status = nm.joinGame("10.208.20.239","3","TOP");
                System.out.println("Join game called from gui");
                if(status) loadGame();
                /*
                 String side = "NONE";
                Paddle p;
                if(button.isSelected()){
                    side = "LEFT";
                    p = new Paddle(150,"VERTICAL",4,300,Color.decode("#FFA000"));
                }
            	else if(button1.isSelected()){
                    side = "TOP";
                    p = new Paddle(150,"HORIZONTAL",300,596,Color.decode("#FFA000"));
                }
                else if(button2.isSelected()){
                    side = "RIGHT";
                    p = new Paddle(150,"VERTICAL",596,300,Color.decode("#FFA000"));
                }
                else if(button3.isSelected()){
                    side = "BOTTOM";
                    p = new Paddle(150,"HORIZONTAL",300,596,Color.decode("#FFA000"));
                }

                if (side == "NONE"){
                    //JOptionPane.showMessageDialog(this, "Please choose a side.");
                }
                else{
                //Player name is his IP
         //       Player me = new Player("ASDFASD ","SDFASDF",side,p,w3);
               

               
            }*/

        }   
    });
        
        ButtonGroup BG = new ButtonGroup();
        BG.add(button);
        BG.add(button1);
        BG.add(button2);
        BG.add(button3);
        //add(textArea);
        add(textArea1);
        add(b);
        add(b1);
        add(button);
        add(button1);
        add(button2);
        add(button3);
        setSize(300,300);
        setLayout(null);
        setVisible(true);
        JLabel choose = new JLabel
        ("CHOOSE YOUR SIDE", JLabel.CENTER);
        add(choose, BorderLayout.NORTH);
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
    
    
    public void loadGame(){
      //  new Game(nm).setVisible(true);
      System.out.println("Game Loaded!!");
      System.out.println("Tyagi this is where game loads and nm is passed as parameter expecting that nm stores an object that has all the data of the game.. yahaan check kar ki jo nm object hai usme jo gamestate hai us gamestate mein kitna players hain aur kitne pucks. Ideally nm ke gamestate me 1 player hona chahiye when server loads game and 2 players when client loads game. Cool?");
    }
    
    public void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        try{
            BufferedImage img1 = ImageIO.read(new File("res/nebula_brown.png"));
            BufferedImage img = scale(img1, 600, 600);
            BufferedImage img2 = ImageIO.read(new File("res/paddle_horizontal.png"));
            BufferedImage img_paddle_hor = scale(img2, 100, 30);
            BufferedImage img3 = ImageIO.read(new File("res/paddle_horizontal.png"));
            BufferedImage img_paddle_ver = scale(img3, 30, 100);
            g2.drawImage(img, 0, 0, this);
            g2.drawImage(img_paddle_hor, 250, 0, this);
            g2.drawImage(img_paddle_ver, 0, 250, this);
            g2.drawImage(img_paddle_hor, 250, 570, this);
            g2.drawImage(img_paddle_ver, 570, 250, this);
            g2.finalize();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        setBackground(Color.BLACK);
        graphics.setColor(Color.WHITE);
        
        /*graphics.setColor(Color.RED);
        graphics.fillRoundRect(0, 250, 30, 100, 5, 5);
        graphics.setColor(Color.BLUE);
        graphics.fillRoundRect(250, 0, 100, 30, 5, 5);
        graphics.setColor(Color.GREEN);
        graphics.fillRoundRect(570, 250, 30, 100, 5, 5);
        graphics.setColor(Color.PINK);
        graphics.fillRoundRect(250, 570, 100, 30, 5, 5);*/
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
        
        Receiver r = new Receiver(nm);
        
        Thread th = new Thread(r);
        th.start();        
        
    }
}
