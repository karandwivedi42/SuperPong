
public class Emulator {

	static GameManager game;
	
	public static boolean playing;
	
	public static void main(String[] args) {
		
		game = new GameManager();
		
		Board b = new Board();
		
		b.addPuck(new Puck(2,"BLUE"));
		
		b.addWall(new Wall(100,100,-100,100,"TOP"));
		b.addWall(new Wall(-100,100,-100,-100,"LEFT"));
		b.addWall(new Wall(-100,-100,100,-100,"BOTTOM"));
		b.addWall(new Wall(100,-100,100,100,"RIGHT"));
		
		game.addBoard(b);
		
		Player p1 = new Player("KARAN", "TOP",new Paddle(30,"HORIZONTAL"));
		Player p2 = new Player("AKSHIT", "LEFT",new Paddle(30,"VERTICAL"));
		Player p3 = new Player("RISHABH", "RIGHT",new Paddle(30,"VERTICAL"));
		Player p4 = new Player("BOT", "BOTTOM",new Paddle(30,"HORIZONTAL"));
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		game.addPlayer(p4);
		
		p4.makeAI();
		
		game.beginGame();
		playing = true;
		Updater updater = new Updater("asd");
	    updater.start();

	    
		
		
		

	}

	
	
	static class Updater implements Runnable {
		
		   private Thread t;
		   private String threadName;
		   
		   Updater( String name){
		       threadName = name;
		       System.out.println("Creating " +  threadName );
		   }
		   public void run() {
		      System.out.println("Running " +  threadName );
		      try {
		         while (playing) {
		        	 game.update(100);
		        	 Thread.sleep(100);
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

