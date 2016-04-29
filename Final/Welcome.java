import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;

public class Welcome extends JPanel{  

    static PlayerNetworkManager nm;
    
    double boardDim = 700;
    double padLength = 150;
    double margin = 4;
    
    GameState gamestate;
    Receiver r;
    JRadioButton button;
    JRadioButton button1;
    JRadioButton button2;
    JRadioButton button3;
    AudioClip intro;
    JButton b;
    JButton b1;
    JLabel textArea;
    JTextField textArea1;

    private static int height;
    private static int width;

    Welcome(int frameWidth, int frameHeight){
        
        nm = new PlayerNetworkManager();

        width = frameWidth;
        height = frameHeight;
        button = new JRadioButton("");
        button1 = new JRadioButton("");
        button2 = new JRadioButton("");
        button3 = new JRadioButton("");
        b = new JButton("Create Game");
        b1 = new JButton("Join Game");
        intro = JApplet.newAudioClip(getClass().getResource("res/0783.aiff"));
        textArea = new JLabel("Your IP:");
        textArea1 = new JTextField("Enter Host IP");
        button.setBounds(30, height/2 - 15, 150, 30);
        button1.setBounds(width/2 - 15, 30, 150, 30);
        button2.setBounds(width - 60, height/2 - 15, 30, 30);
        button3.setBounds(width/2 - 15, height - 60, 150, 30);
        b.setBounds(width/2 - 75, 140, 150, 30);
        b1.setBounds(width/2 - 75, 300, 150, 30);
        textArea.setBounds(width/2 - 75, 200, 150, 20);
        textArea1.setBounds(width/2 - 75, 350, 150, 20);
            
        b.addActionListener(new ActionListener() {
            
                public void actionPerformed(ActionEvent e) {
                    String side = "NONE";
                    Paddle p;
                    
                    if(button.isSelected()){
                        side = "LEFT";
                        p = new Paddle(padLength,"VERTICAL",margin,height/2);
                    }
                	else if(button1.isSelected()){
                        side = "TOP";
                        p = new Paddle(padLength,"HORIZONTAL",width/2,margin);
                    }
                    else if(button2.isSelected()){
                        side = "RIGHT";
                        p = new Paddle(padLength,"VERTICAL",width-margin,height/2);
                    }
                    else if(button3.isSelected()){
                        side = "BOTTOM";
                        p = new Paddle(padLength,"HORIZONTAL",width/2,height-margin);
                    }

                    if (side == "NONE"){
                        System.out.println("Please choose a side.");
                    }
                    else{
                        gamestate = new GameState(2,height); // Replace 2 by getNumBalls
                        Player me = new Player("1",getMachineAddress(),side,p,gamestate.getWall(side));
                        nm.createGame();
                        gamestate.addPlayer(me);
                        loadGame();
                    }
             }   
        });
            
            b1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    System.out.println("Join game called");
                    
                    String side = "NONE";
                    Paddle p;
                    
                    if(button.isSelected()){
                        side = "LEFT";
                        p = new Paddle(padLength,"VERTICAL",margin,height/2);
                    }
                	else if(button1.isSelected()){
                        side = "TOP";
                        p = new Paddle(padLength,"HORIZONTAL",width/2,margin);
                    }
                    else if(button2.isSelected()){
                        side = "RIGHT";
                        p = new Paddle(padLength,"VERTICAL",width-margin,height/2);
                    }
                    else if(button3.isSelected()){
                        side = "BOTTOM";
                        p = new Paddle(padLength,"HORIZONTAL",width/2,height-margin);
                    }

                    if (side == "NONE"){
                        System.out.println("Please choose a side.");
                    }
                    else{
                        String status = nm.joinGame("10.208.20.239","TOP"); //get from gui components
                        if(status.equals("0") || status.equals("")){
                            System.out.println("Error in joining game");                            
                        }
                        else{
                            int numPucks = 2; //Get num pucks from network
                            ArrayList<Player> others =  getOthersFromNetwork();  // fill this with values from network 
                            // I cant avoid this as I need to know what is the mapping of other playrs of number to IP
                            // Also number to side and who is alive and who is not.. also their scores
                            //Also if I have to become server, then I need to have all data beforehand, even AILevel and other stats
                            
                            //I can wait for this.. for few seconds to receive all data.. All games take few seconds to join.
                            
                            gamestate = new GameState(numPucks,boardDim);
                            Player me = new Player(status,getMachineAddress(),side,p,gamestate.getWall(side));
                            
                            gamestate.addMe(me);
                            
                            for(Player p : others){
                                gamestate.addPlayer(p);
                            }
                            
                            loadGame();
                        
                       }

                   
                }

            }   
        });
            
            ButtonGroup BG = new ButtonGroup();
            BG.add(button);
            BG.add(button1);
            BG.add(button2);
            BG.add(button3);
            add(textArea);
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
            JLabel choose = new JLabel("CHOOSE YOUR SIDE", JLabel.CENTER);
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
    
    
    public String getMachineAddress(){
        return "Tyagi"
    }

    public void loadGame(){
      System.out.println("GameLoading game!!");
      new Game(nm,gamestate,r).setVisible(true);
    }

    
    public void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        try{
            BufferedImage img1 = ImageIO.read(new File("res/nebula_brown.png"));
            BufferedImage img = scale(img1, width, height);
            BufferedImage img2 = ImageIO.read(new File("res/blue_saber_hor.png"));
            BufferedImage img_paddle_hor = scale(img2, 100, 30);
            BufferedImage img3 = ImageIO.read(new File("res/red_saber_ver.png"));
            BufferedImage img_paddle_ver = scale(img3, 30, 100);
            g2.drawImage(img, 0, 0, this);
            g2.drawImage(img_paddle_hor, width/2 - 50, 0, this);
            g2.drawImage(img_paddle_ver, 0, height/2 - 50, this);
            g2.drawImage(img_paddle_hor, width/2 - 50, height - 30, this);
            g2.drawImage(img_paddle_ver, width - 30, height/2 - 50, this);
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
        SplashScreen splash = new SplashScreen(1000);
        splash.showSplash();
        JFrame frame = new JFrame("Star Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());
        
        Welcome game = new Welcome(700, 700);
        frame.add(game, BorderLayout.CENTER);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(width, height);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        game.intro.play();
        
        r = new Receiver(nm);
        Thread th = new Thread(r);
        th.start();
        
    }
    
    public void getStateFromNM(GameState gamestate){
       // String numPucks = Double.parseDouble(nm.getValue("NumPucks"));
        
    }
}
