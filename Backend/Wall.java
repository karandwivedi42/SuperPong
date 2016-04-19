
public class Wall {
	double xa,ya;
	double xb,yb;
	double m,c;
	String side;
	
	public Wall(double xa,double ya,double xb,double yb,String side) {
		this.xa = xa;
		this.xb = xb;
		this.ya = ya;
		this.yb = yb;
		m = (yb-ya)/(xb-xa);
		c = yb - m*xb;
		this.side = side;
	}
	
}
