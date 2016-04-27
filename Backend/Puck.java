import java.awt.Color;


public class Puck {
	
	String name;
	
	double x,y,vx,vy;
	
	double radius;
	Color color;
	
	public Puck(String name,double i, Color color2) {
		this.name = name;
		radius = i;
		color = color2;
		x=0;
		y=0;
		vx = 0;
		vy = 0;
		
	}


}
