import java.util.ArrayList;


public class Board {

	ArrayList<Puck> pucks;
	ArrayList<Wall> walls;

	public Board() {
		pucks = new ArrayList<>();
		walls = new ArrayList<>();
	}
	
	public void addWall(Wall w){
		walls.add(w);
	}
	
	public void addPuck(Puck p){
		pucks.add(p);
	}


}
