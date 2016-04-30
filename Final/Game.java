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
	
	Handler h;
	GameState gamestate;
	GameUpdater gameupdater;
	Receiver r;
	Timer timer;
    public static int padWidth = 16;

    //Constructor
    //Input : Network manager, state of current game, receiver
    public Game(Handler h,GameState gamestate,Receiver r) {

    	this.r = r;
    	this.h = h;
    	this.gamestate = gamestate;
    	this.gameupdater = new GameUpdater(h,r,gamestate);
    	
        gameupdater.beginGame();
    	setFocusable(true);
        addKeyListener(this);
        javax.swing.Timer timer = new javax.swing.Timer(1, this);
        timer.start();
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
    
    //Default method for updating timer's events
    int i = 0;
    public void actionPerformed(ActionEvent e){
    	gameupdater.update(1);
    	//System.out.println(i++);
    	repaint();
    }
    
    //Method to draw the components on the JPanel
    public void paintComponent(Graphics graphics){
    	
    	super.paintComponent(graphics);
    	
        if(gamestate.gameStatus < 2){
        
            Graphics2D g2 = (Graphics2D) graphics;
            try{

                BufferedImage img1 = ImageIO.read(new File("res/nebula_blue.png"));

                BufferedImage img = scale(img1, 700, 700);
                g2.drawImage(img, 0, 0, this);
                g2.finalize();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            setBackground(Color.BLACK);

            
            graphics.setColor(Color.decode("#F44336"));
            
            //Drawing Lines on board
           // graphics.drawLine((int) gamestate.board.width / 2, 0, (int)gamestate.board.width / 2, (int) gamestate.board.height);
           // graphics.drawLine(0, (int) gamestate.board.height / 2, (int) gamestate.board.width, (int) gamestate.board.height/2);
            graphics.fillRect(0, 0, 700, padWidth);
            graphics.fillRect(0, 0, padWidth,700);
            graphics.fillRect(0,700-padWidth,700,padWidth);
            graphics.fillRect(700 - padWidth,0,padWidth,700);
            
            //Drawing Balls
            for (Puck p : gamestate.board.pucks){

            //	graphics.setColor(Color.WHITE);
            //	graphics.fillOval( (int) (p.x - p.radius), (int) (p.y - p.radius), (int) (2 * p.radius), (int) (2 * p.radius));
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
            
            for (Player pl : gamestate.players){
            	if(pl.alive){
	            //	System.out.print("~"+pl.name + pl.paddle.xc + pl.paddle.yc + "~");

                    if(pl.paddle.orientation == "VERTICAL"){
	            		//graphics.fillRect( (int) (pl.paddle.xc - padWidth / 2), (int) (pl.paddle.yc - pl.paddle.length/2), (int) padWidth, (int) pl.paddle.length);
                        try{
                            BufferedImage img3 = ImageIO.read(new File("res/paddle_vertical.png"));
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
                            BufferedImage img3 = ImageIO.read(new File("res/paddle_horizontal.png"));
                            BufferedImage img_paddle = scale(img3,  (int) pl.paddle.length, (int) padWidth);
                            g2.drawImage(img_paddle, (int) (pl.paddle.xc - pl.paddle.length/2), (int) (pl.paddle.yc-padWidth/2), this);
                            g2.finalize();
                        }catch(IOException e){
                            System.out.println(e.getMessage());
                        }
                    }

               	}
            }
            
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
            //Writing Scores on board
            for (Player pl : gamestate.players){
            	if(pl.paddle.orientation == "VERTICAL")
            		graphics.drawString(Integer.toString(pl.score),  (int) (pl.wall2protect.xa/2 + gamestate.board.width/4), (int) (pl.wall2protect.ya/2 + pl.wall2protect.yb/2));
            	else
            		System.out.println(Integer.toString(pl.score));
            		System.out.println((int)(pl.wall2protect.ya/2+gamestate.board.height/4));
            		System.out.println((int) (pl.wall2protect.xa/2 + pl.wall2protect.xb/2));
            		graphics.drawString(Integer.toString(pl.score), (int) (pl.wall2protect.xa/2 + pl.wall2protect.xb/2), (int)(pl.wall2protect.ya/2+gamestate.board.height/4));
            		
            		
            }
        }
        else if(gamestate.gameStatus == 2){
            Graphics2D g2 = (Graphics2D) graphics;
            try{
                
                BufferedImage img1 = ImageIO.read(new File("res/nebula_blue.png"));
                
                BufferedImage img = scale(img1, 700, 700);
                g2.drawImage(img, 0, 0, this);
                g2.finalize();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            graphics.drawString(gamestate.winner + " PLAYER WINS!", 65, 200);
            //g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            //g.drawString("Press space to restart.", 150, 400);
        }
    }
    
    public void keyTyped(KeyEvent e) {} //DON'T DO ANYTHING HERE
    
    //Default method to handle events when a keyboard key is pressed
    public void keyPressed(KeyEvent e){
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    		gamestate.me.movePaddle("RIGHT");
    	else if (e.getKeyCode() == KeyEvent.VK_LEFT)
    		gamestate.me.movePaddle("LEFT");
    	else if (e.getKeyCode() == KeyEvent.VK_UP)
    		gamestate.me.movePaddle("UP");
    	else if (e.getKeyCode() == KeyEvent.VK_DOWN)
    		gamestate.me.movePaddle("DOWN");
    }
    
    public void keyReleased(KeyEvent e){}
    
}
