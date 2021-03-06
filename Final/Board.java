import java.awt.Color;
import java.util.ArrayList;


public class Board {

    ArrayList<Puck> pucks;
    ArrayList<Wall> walls;
    
    double height,width;

    //Constructor
    //Input : dimensions of the board
    public Board(double dim,double wallWidth) {
        
        this.height = dim;
        this.width = dim;
        
        pucks = new ArrayList<>();
        walls = new ArrayList<>();
        
        walls.add(new Wall(wallWidth,wallWidth,dim-wallWidth,wallWidth,"TOP"));
        walls.add(new Wall(wallWidth,wallWidth,wallWidth,dim-wallWidth,"LEFT"));
        walls.add(new Wall(wallWidth,dim-wallWidth,dim-wallWidth,dim-wallWidth,"BOTTOM"));
        walls.add(new Wall(dim-wallWidth,wallWidth,dim-wallWidth,dim-wallWidth,"RIGHT"));
    
    }
    


}
