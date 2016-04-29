import java.awt.Color;

public class Paddle {
		
	static final double delta = 20;
	double length;
	String orientation;
	double xc,yc;

		
    //Constructor
    //Input : length of paddle, orientation of paddle, coordinates of top-left corner
	public Paddle(int len, String orient,int xc,int yc) {
		length = len;
		orientation = orient;
		this.xc = xc;
		this.yc = yc;

	}
}
