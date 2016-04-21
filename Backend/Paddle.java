
public class Paddle {
		
	static final double delta = 4;
	double length;
	String orientation;
	double xc,yc;
	double angle;
		
	public Paddle(int len, String orient,int xc,int yc) {
		length = len;
		orientation = orient;
		this.xc = xc;
		this.yc = yc;
		if (orient == "HORIZONTAL") angle = 0;
		else angle = 90;
	}
	

	
	
}
