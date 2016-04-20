
public class Paddle {
		
	private static final double delta = 4;
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
	
	public void movePaddle(String direction){
		if(orientation == "HORIZONTAL"){
			if(direction == "LEFT"){
				xc -= delta;
			}
			else if (direction == "RIGHT"){
				xc += delta;
			}
		}
		else if (orientation == "VERTICAL"){
			if(direction == "UP"){
				yc+= delta;
			}
			else{
				yc-=delta;
			}
		}
		
		System.out.print(this.orientation + " - "+ this.xc + " " + this.yc);
	}
	
	
}
