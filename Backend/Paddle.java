import java.awt.Color;


public class Paddle {
		
	static final double delta = 20;
	double length;
	String orientation;
	double xc,yc;
	double angle;
	public Color color;
		
	public Paddle(int len, String orient,int xc,int yc,Color color) {
		length = len;
		orientation = orient;
		this.xc = xc;
		this.yc = yc;
		this.color = color;
		if (orient == "HORIZONTAL") angle = 0;
		else angle = 90;
	}
	

	
	
}
