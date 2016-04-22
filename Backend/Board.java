import java.awt.Color;
import java.util.ArrayList;


public class Board {

	ArrayList<Puck> pucks;
	ArrayList<Wall> walls;
	
	double height,width;
	
	Color bg,lines;

	public Board(double height,double width, Color bgColor,Color lineColor) {
		
		this.height = height;
		this.width = width;
		this.bg = bgColor;
		this.lines = lineColor;
		
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
