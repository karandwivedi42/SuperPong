public class Wall {
    double xa,ya;
    double xb,yb;
    String side;

    double length;

    //Constructor
    //Input : coordinates of the endpoints of the wall, side on which the wall is located
    public Wall(double xa,double ya,double xb,double yb,String side) {
        this.xa = xa;
        this.xb = xb;
        this.ya = ya;
        this.yb = yb;
        this.side = side;
        this.length = Math.sqrt((xa-xb)*(xa-xb)+(ya-yb)*(ya-yb));
    }
}
