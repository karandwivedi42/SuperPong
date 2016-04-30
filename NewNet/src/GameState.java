import java.util.ArrayList;

public class GameState{

    double puckRadius = 10;
    
    ArrayList<Player> players;
    Player me;
    Board board;
    
    //Constructor
    //Input : number of pucks, dimensions of the board
    public GameState(int numPucks, double boardDim){
    
        board = new Board(boardDim);
        
        for(int i = 1;i<=numPucks;i++){
            addPuck(new Puck("puck"+i,puckRadius));
        }
        
        this.players = new ArrayList<>();
    }
    
    //Method to add player to current game
    public void addPlayer(Player player){
        players.add(player);
    }
    
    //Method to add this machine's player
    public void addMe(Player me){
        players.add(me);
        this.me = me;        
    }
    
    //Method to return the wall corresponding to a given side
    public Wall getWall(String side){
        for (Wall w : board.walls){
            if(w.side == side) return w;
        }
        return null;
    }
    
    //Method to add a new puck to the game
    public void addPuck(Puck p){
        board.pucks.add(p);
    }
        
}
