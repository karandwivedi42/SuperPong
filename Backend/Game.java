import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener{
	
	GameManager game;
	Timer timer;
	
	public boolean playing;
	Player p1,p2,p3,p4;
	Wall w1,w2,w3,w4;
	
    private static String[] choice = {"Left", "Right"};
    //Paddle Constants
    public static int padWidth = 30;
    //Ball Variables


    private int randomChoice;
    
    public Game() {

                
        game = new GameManager();
		
		Board b = new Board(600, 600, new Color(0, 0, 0), new Color(255, 255, 255));
		
		Puck p = new Puck(10,new Color (0,0,255));
		b.addPuck(p);
		
		w1 = (new Wall(0,0,600,0,"TOP"));
		w2 = (new Wall(0,0,0,600,"LEFT"));
		w3 = (new Wall(0,600,600,600,"BOTTOM"));
		w4 = (new Wall(600,0,600,600,"RIGHT"));
		
		b.addWall(w1);
		b.addWall(w2);
		b.addWall(w3);
		b.addWall(w4);
		
		game.addBoard(b);
		
		p1 = new Player("KARAN", "TOP",new Paddle(150,"HORIZONTAL",300,4,Color.BLUE),w1);		
		p2 = new Player("AKSHIT", "LEFT",new Paddle(150,"VERTICAL",4,300,Color.RED),w2);
		p3 = new Player("RISHABH", "RIGHT",new Paddle(150,"VERTICAL",596,300,Color.GREEN),w4);
		p4 = new Player("BOT", "BOTTOM",new Paddle(150,"HORIZONTAL",300,596,Color.WHITE),w3);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		game.addPlayer(p4);
		
		p4.makeAI("EASY");
		
		game.beginGame();
		

        addKeyListener(this);
        javax.swing.Timer timer = new javax.swing.Timer(40, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e){
    	game.update(40);
    	repaint();
        //TODO : Perform Checks on ball and Paddles
        //TODO : if ball goes out of bounds, call spawn_ball() such that initial velocity of the ball is towards the winner of the last point; function and update scores
        //TODO : Update Position of ball
        //TODO : Update Position of Paddles; keep paddles inside the board
        //TODO : Call repaint() function
    }
    
    public void paintComponent(Graphics graphics){
    	
    	super.paintComponent(graphics);

        setBackground(Color.BLACK);
        
        graphics.setColor(Color.BLUE);
        
        //Drawing Lines on board
        graphics.drawLine((int) game.board.width / 2, 0, (int)game.board.width / 2, (int) game.board.height);
        graphics.drawLine(0, (int) game.board.height / 2, (int) game.board.width, (int) game.board.height/2);
   //     graphics.drawLine(padWidth, 0, padWidth, frameHeight);
   //     graphics.drawLine(frameWidth - padWidth, 0, frameWidth - padWidth, frameHeight);
   //     graphics.drawLine(0, padWidth, frameWidth, padWidth);
   //     graphics.drawLine(0, frameHeight - padWidth, frameWidth, frameHeight - padWidth);
        
        //Drawing Balls
        for (Puck p : game.board.pucks){
        	graphics.fillOval( (int) (p.x - p.radius), (int) (p.y - p.radius), (int) (2 * p.radius), (int) (2 * p.radius));
        }
        //Drawing Paddles
        
        //Vertical Paddles
        
        for (Player pl : game.players){
        	graphics.setColor(pl.paddle.color);
        	System.out.print("~"+pl.name + pl.paddle.xc + pl.paddle.yc + "~");
        	if(pl.paddle.orientation == "VERTICAL")
        		graphics.fillRect( (int) (pl.paddle.xc - padWidth / 2), (int) (pl.paddle.yc - pl.paddle.length/2), (int) padWidth, (int) pl.paddle.length);
        	else
        		graphics.fillRect((int) (pl.paddle.xc - pl.paddle.length/2),(int) (pl.paddle.yc-padWidth/2), (int) (pl.paddle.length), (int) padWidth);
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
        //TODO : Update on key press events
    }
    
    public void keyReleased(KeyEvent e){
        //TODO : Update on key released events
    }
    
    public static void main(String[] args){
        
        JFrame frame = new JFrame("Ping The Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Game game = new Game();
        frame.add(game, BorderLayout.CENTER);
        
        frame.setSize(600, 624);
        frame.setVisible(true);
        
    }
}