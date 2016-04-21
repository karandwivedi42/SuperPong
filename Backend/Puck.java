
public class Puck {
	
	double x,y,vx,vy;
	
	double radius;
	String color;
	
	public Puck(double i, String string) {
		radius = i;
		color = string;
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
