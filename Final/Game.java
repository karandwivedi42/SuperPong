import javax.swing.*;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import javax.imageio.ImageIO;
import java.lang.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener{
	
	NetworkManager nm;	
	GameManager game;
	Player me;
	
	Timer timer;
	
	public boolean playing;
	
	Player p1,p2,p3;
	Wall w1,w2,w3,w4;
	

    //Paddle Constants
    public static int padWidth = 5;
    //Ball Variables

    
    public Game() {
    	
    	game = new GameManager(nm);
		
		Board b = new Board(600, 600, new Color(0, 0, 0), new Color(255, 255, 255));
		
		Puck p = new Puck("puck1",10,Color.decode("#1976D2"));
		Puck pdash = new Puck("puck2",10,Color.decode("#19D276"));
		b.addPuck(p);
		b.addPuck(pdash);
		
		w1 = (new Wall(0,0,600,0,"TOP"));
		w2 = (new Wall(0,0,0,600,"LEFT"));
		w3 = (new Wall(0,600,600,600,"BOTTOM"));
		w4 = (new Wall(600,0,600,600,"RIGHT"));
		
		b.addWall(w1);
		b.addWall(w2);
		b.addWall(w3);
		b.addWall(w4);
		
		game.addBoard(b);
		
		me = new Player("KARAN","my ip" ,"TOP",new Paddle(150,"HORIZONTAL",300,4,Color.decode("#FFA000")),w1);	
		
		p2 = new Player("AKSHIT","" ,"LEFT",new Paddle(150,"VERTICAL",4,300,Color.decode("#FFA000")),w2);
		p3 = new Player("RISHABH","" ,"RIGHT",new Paddle(150,"VERTICAL",596,300,Color.decode("#FFA000")),w4);
		p1 = new Player("BOT","" ,"BOTTOM",new Paddle(150,"HORIZONTAL",300,596,Color.decode("#FFA000")),w3);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		game.addPlayer(me);
		game.me = me;
		
		me.makeAI("HARD");
		p3.makeAI("HARD");
		p1.makeAI("HARD");
		p2.makeAI("HARD");
		
		game.beginGame();
		
		setFocusable(true);
        addKeyListener(this);
        javax.swing.Timer timer = new javax.swing.Timer(1, this);
        timer.start();
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
    
    public void actionPerformed(ActionEvent e){
    	game.update(1);
    	repaint();
    }
    
    public void paintComponent(Graphics graphics){
    	
    	super.paintComponent(graphics);
    	

        Graphics2D g2 = (Graphics2D) graphics;
        try{
            BufferedImage img1 = ImageIO.read(new File("res/nebula_blue.png"));
            BufferedImage img = scale(img1, 600, 600);
            g2.drawImage(img, 0, 0, this);
            g2.finalize();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        setBackground(Color.BLACK);

        
        graphics.setColor(Color.decode("#F44336"));
        
        //Drawing Lines on board
        graphics.drawLine((int) game.board.width / 2, 0, (int)game.board.width / 2, (int) game.board.height);
        graphics.drawLine(0, (int) game.board.height / 2, (int) game.board.width, (int) game.board.height/2);
        
        //Drawing Balls
        for (Puck p : game.board.pucks){
        	//graphics.setColor(p.color);
        	//graphics.fillOval( (int) (p.x - p.radius), (int) (p.y - p.radius), (int) (2 * p.radius), (int) (2 * p.radius));
            try{
                BufferedImage img2 = ImageIO.read(new File("res/asteroid_blue.png"));
                BufferedImage img_ball = scale(img2, (int) (2 * p.radius), (int) (2 * p.radius));
                g2.drawImage(img_ball, (int) (p.x - p.radius), (int) (p.y - p.radius), this);
                g2.finalize();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        //Drawing Paddles
        
        //Vertical Paddles
        
        for (Player pl : game.players){
        	if(pl.alive){
	        	graphics.setColor(pl.paddle.color);
	        //	System.out.print("~"+pl.name + pl.paddle.xc + pl.paddle.yc + "~");
                if(pl.paddle.orientation == "VERTICAL"){
	        		//graphics.fillRect( (int) (pl.paddle.xc - padWidth / 2), (int) (pl.paddle.yc - pl.paddle.length/2), (int) padWidth, (int) pl.paddle.length);
                    try{
                        BufferedImage img3 = ImageIO.read(new File("paddle_vertical.png"));
                        BufferedImage img_paddle = scale(img3, (int) padWidth, (int) pl.paddle.length);
                        g2.drawImage(img_paddle, (int) (pl.paddle.xc - padWidth / 2), (int) (pl.paddle.yc - pl.paddle.length/2), this);
                        g2.finalize();
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
                else{
	        		//graphics.fillRect((int) (pl.paddle.xc - pl.paddle.length/2),(int) (pl.paddle.yc-padWidth/2), (int) (pl.paddle.length), (int) padWidth);
                    try{
                        BufferedImage img3 = ImageIO.read(new File("paddle_horizontal.png"));
                        BufferedImage img_paddle = scale(img3,  (int) (pl.paddle.length), (int) padWidth);
                        g2.drawImage(img_paddle, (int) (pl.paddle.xc - pl.paddle.length/2), (int) (pl.paddle.yc-padWidth/2), this);
                        g2.finalize();
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
           	}
        }
        
        graphics.setColor(Color.WHITE);
        
        //Writing Scores on board
        for (Player pl : game.players){
        	if(pl.paddle.orientation == "VERTICAL")
        		graphics.drawString(Integer.toString(pl.score),  (int) (pl.wall2protect.xa/2 + game.board.width/4), (int) (pl.wall2protect.ya/2 + pl.wall2protect.yb/2));
        	else
        		graphics.drawString(Integer.toString(pl.score), (int) (pl.wall2protect.xa/2 + pl.wall2protect.xb/2), (int)(pl.wall2protect.ya/2+game.board.height/4));
        		
        		
        }
    }
    
    public void keyTyped(KeyEvent e) {} //DON'T DO ANYTHING HERE
    
    public void keyPressed(KeyEvent e){
    	//System.out.println("FUCK");
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    		p1.movePaddle("RIGHT");
    	else if (e.getKeyCode() == KeyEvent.VK_LEFT)
    		p1.movePaddle("LEFT");
    	else if (e.getKeyCode() == KeyEvent.VK_UP)
    		p2.movePaddle("UP");
    	else if (e.getKeyCode() == KeyEvent.VK_DOWN)
    		p2.movePaddle("DOWN");
    }
    
    public void keyReleased(KeyEvent e){
        //TODO : Update on key released events
    }
    
    public static void main(String[] args){
        
        JFrame frame = new JFrame("Star Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Game game = new Game();
        frame.add(game, BorderLayout.CENTER);
        
        frame.setSize(600, 600);
        frame.setVisible(true);
        
    }
}
