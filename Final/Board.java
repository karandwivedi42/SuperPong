import java.awt.Color;
import java.util.ArrayList;


public class Board {

    ArrayList<Puck> pucks;
    ArrayList<Wall> walls;
    
    double height,width;

    //Constructor
    //Input : dimensions of the board
    public Board(double dim) {
        
        this.height = dim;
        this.width = dim;
        
        pucks = new ArrayList<>();
        walls = new ArrayList<>();
        
        walls.add(new Wall(0,0,dim,0,"TOP"));
        walls.add(new Wall(0,0,0,dim,"LEFT"));
        walls.add(new Wall(0,dim,dim,dim,"BOTTOM"));
        walls.add(new Wall(dim,0,dim,dim,"RIGHT"));
    
    }
    


}
