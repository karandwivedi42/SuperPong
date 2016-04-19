import java.util.ArrayList;


public class Board {

	ArrayList<Puck> pucks;
	ArrayList<Wall> walls;

	public Board() {
		// TODO Auto-generated constructor stub
	}
	
	public void addWall(Wall w){
		walls.add(w);
	}
	
	public void addPuck(Puck p){
		pucks.add(p);
	}


}
