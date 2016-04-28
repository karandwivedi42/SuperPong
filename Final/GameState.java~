import java.util.ArrayList;

public class GameState{

	ArrayList<Player> players; //All players including me (remove me and keep only neighbours if you want)
	Player me;
	Board board;
	
	public GameState(Player me){ // Change this if needed
	
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
		
	this.board = b;
	this.players = new ArrayList<>();
	
	this.me = me;
	
	
	
	}
	
}
