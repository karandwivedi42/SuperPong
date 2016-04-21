import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Emulator {

	GameManager game;
	Timer timer;
	
	public boolean playing;
	Player p1,p2,p3,p4;
	Wall w1,w2,w3,w4;
	
	public Emulator(){
		
		System.out.println( "Hi");
		
		game = new GameManager();
		
		Board b = new Board();
		Puck p = new Puck(2,"BLUE");
		b.addPuck(p);
		
		w1 = (new Wall(100,100,-100,100,"TOP"));
		w2 = (new Wall(-100,100,-100,-100,"LEFT"));
		w3 = (new Wall(-100,-100,100,-100,"BOTTOM"));
		w4 = (new Wall(100,-100,100,100,"RIGHT"));
		
		game.addBoard(b);
		
		p1 = new Player("KARAN", "TOP",new Paddle(30,"HORIZONTAL",0,90),w1);
		p2 = new Player("AKSHIT", "LEFT",new Paddle(30,"VERTICAL",-90,0),w2);
		p3 = new Player("RISHABH", "RIGHT",new Paddle(30,"VERTICAL",90,0),w4);
		p4 = new Player("BOT", "BOTTOM",new Paddle(30,"HORIZONTAL",0,-90),w3);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		game.addPlayer(p4);
		
		p4.makeAI("EASY");
		
		game.beginGame();
		playing = true;
		
		Updater updater = new Updater( "Thread-1");
	    updater.start();
	      
	    KeyPresser kp = new KeyPresser( "Thread-2");
	    kp.start();
	    
	    BotRunner br = new BotRunner("Thread -3");
	    br.start();

        
	}
	
	public static void main(String[] args) {
			new Emulator();
			System.out.print("Here");
			
			JFrame.setDefaultLookAndFeelDecorated(true);
		    JFrame frame = new JFrame();
		    frame.setTitle("My First Swing Application");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JLabel label = new JLabel("Welcome");
		    frame.add(label);
		    frame.pack();
		    frame.setVisible(true);
			System.out.print("Done");
	    	
	}

	class Updater implements Runnable {
		   private Thread t;
		   private String threadName;
		   
		   Updater( String name){
		       threadName = name;
		       System.out.println("Creating " +  threadName );
		   }
		   public void run() {
		      System.out.println("Running " +  threadName );
				while(playing){
					game.update(5);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("yo");
				}
		      
		      System.out.println("Thread " +  threadName + " exiting.");
		   }
		   
		   public void start ()
		   {
		      System.out.println("Starting " +  threadName );
		      if (t == null)
		      {
		         t = new Thread (this, threadName);
		         t.start ();
		      }
		   }

		}
	
	class KeyPresser implements Runnable {
		   private Thread t;
		   private String threadName;
		   
		   KeyPresser( String name){
		       threadName = name;
		       System.out.println("Creating " +  threadName );
		   }
		   public void run() {
		      System.out.println("Running " +  threadName );
		      try {
		         while(playing){
		        	 System.out.print(p3.name+ " ");
		        	 p3.movePaddle("UP");
		        	 Thread.sleep(500);
		        	 System.out.print(p2.name+ " ");
		        	 p2.movePaddle("DOWN");
	        	 	 Thread.sleep(500);
		         }
		     } catch (InterruptedException e) {
		         System.out.println("Thread " +  threadName + " interrupted.");
		     }
		     System.out.println("Thread " +  threadName + " exiting.");
		   }
		   
		   public void start ()
		   {
		      System.out.println("Starting " +  threadName );
		      if (t == null)
		      {
		         t = new Thread (this, threadName);
		         t.start ();
		      }
		   }

		}
	
	class BotRunner implements Runnable {
		   private Thread t;
		   private String threadName;
		   
		   BotRunner( String name){
		       threadName = name;
		       System.out.println("Creating " +  threadName );
		   }
		   public void run() {
		      System.out.println("Running " +  threadName );
		      try {
		         while(playing){
		        	 game.triggerAI();
		        	 Thread.sleep(300);
		        	 game.triggerAI();
	        	 	 Thread.sleep(800);
		         }
		     } catch (InterruptedException e) {
		         System.out.println("Thread " +  threadName + " interrupted.");
		     }
		     System.out.println("Thread " +  threadName + " exiting.");
		   }
		   
		   public void start ()
		   {
		      System.out.println("Starting " +  threadName );
		      if (t == null)
		      {
		         t = new Thread (this, threadName);
		         t.start ();
		      }
		   }

		}
	
	
}