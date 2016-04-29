import java.util.ArrayList;

public class GameState{

    double puckRadius = 10;
    
    ArrayList<Player> players;
    Player me;
    Board board;
    
    public GameState(int numPucks, double boardDim){
    
        board = new Board(boardDim);
        
        for(int i = 1;i<=numPucks;i++){
            addPuck(new Puck("puck"+i,puckRadius));
        }
        
        this.players = new ArrayList<>();
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    
    public void addMe(Player me){
        players.add(me);
        this.me = me;        
    }
    
    public Wall getWall(String side){
        for (Wall w : board.walls){
            if(w.side == side) return w;
        }
        return null;
    }
    
    public void addPuck(Puck p){
        board.pucks.add(p);
    }
        
}
