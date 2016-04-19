
public class Paddle {
		
	double length;
	String orientation;
	double xc,yc;
	double angle;
		
	public Paddle(int i, String string) {
		length = i;
		orientation = string;
		xc =0;
		yc = 0;
		if (string == "HORIZONTAL") angle = 0;
		else angle = 90;
	}

	
	
}
