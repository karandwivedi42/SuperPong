import java.awt.Color;


public class Puck {
	
	double x,y,vx,vy;
	
	double radius;
	Color color;
	
	public Puck(double i, Color color2) {
		radius = i;
		color = color2;
		x=0;
		y=0;
		vx = 0;
		vy = 0;
		
	}

	public void update(int i) { // Change for friction
		x+=vx;
		y+=vy;		
	}
}
