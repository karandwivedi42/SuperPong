import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Welcome extends JPanel{




    

    static PlayerNetworkManager nm; // nm stores a GameManager inside it.

    
    GameState gamestate;
    
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

    private static int height;
    private static int width;

    Welcome(int frameWidth, int frameHeight){
        
    //nm = new PlayerNetworkManager();
    

    
        width = frameWidth;
        height = frameHeight;
    button = new JRadioButton("");
    button1 = new JRadioButton("");
    button2 = new JRadioButton("");
    button3 = new JRadioButton("");
    b = new JButton("Create Game");
    b1 = new JButton("Join Game");
    intro = JApplet.newAudioClip(getClass().getResource("res/0783.aiff"));
    textArea = new JTextField("Your IP3: ");
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

                //nm.createGame();
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
                    System.out.println("Please choose a side.");
                }
                else{

                Player me = new Player("DUMMY NAME","dummy ip",side,p,w3);
                nm.createGame();
                System.out.println("Create game called from gui");
                gamestate = new GameState();
                gamestate.addPlayer(me);
                loadGame();
                }
*/
         }   
    });
        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                

                 //boolean status = nm.joinGame("10.208.20.239","3","TOP");
                System.out.println("Join game called from gui");
                //if(status) loadGame();
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
                Player me = new Player("ASDFASD ","SDFASDF",side,p,w3);
                boolean status = nm.joinGame("10.208.20.239","3","TOP");
                if(status){
                        System.out.println("Join game called from gui");
                        gamestate = new GameState(me);
                        getStateFromNM(gamestate);
                        loadGame();
               }
               else{
                        System.out.println("Error in joining game");
               }

               
            }*/

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
     
    //  new Game(gamestate).setVisible(true);
      System.out.println("Game Loaded!!");
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
        
        /*Receiver r = new Receiver(nm);
        
        Thread th = new Thread(r);
        th.start();*/
        
    }
    
    public void getStateFromNM(GameState gamestate){
   //     String numPucks = Double.parseDouble(nm.getValue("NumPucks"));
        
    }
    
    public static String getMachineAddress() throws RuntimeException
	{
		
		String requiredIp = "localhost";
		String ip;
    	try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                if(ip.contains("."))
                	requiredIp = ip;
                System.out.println(iface.getDisplayName() + " " + ip);
            }
           }
        } catch (SocketException e) {
        throw new RuntimeException(e);
    	}

    	return requiredIp;
    }

}
