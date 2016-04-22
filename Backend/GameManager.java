import java.util.ArrayList;

public class GameManager {

	private static final double v_factor = 3;
	private static final double v_offset = 0.3;
	
	int padWidth = 8;
	
	ArrayList<Player> players;
	Board board;
	int currentRound;
	
	public GameManager(){
		players = new ArrayList<>();
	}

	public void addPlayer(Player p) { // FUTURE: Ensure name uniqueness
		players.add(p);
	}

	public void addBoard(Board b) {
		board = b;
	}

	public void upScore(String side) {
		for (Player p : players) {
			if (p.side == side){
				//System.out.println (p.name);
				p.score++;
			}
		}
	}

	public void startRound() {
		for (Puck p : board.pucks) {
			p.x = board.width/2;
			p.y = board.height/2;
			p.vx = v_offset + Math.random() * v_factor *(Math.random()*2-1);
			p.vy = v_offset + Math.random() * v_factor* (Math.random()*2-1);
		}


	}

	public void beginGame() {
		currentRound = 0;

		startRound();

	}

	public void triggerAI(){
		for(Player p : players){
			if(p.AI){
				if (p.AIlevel == "EASY"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc < board.pucks.get(0).y)
							p.paddle.yc += p.paddle.delta;
						else
							p.paddle.yc -= p.paddle.delta;
					}
					else{
						if(p.paddle.xc < board.pucks.get(0).x)
							p.paddle.xc += p.paddle.delta;
						else
							p.paddle.xc -= p.paddle.delta;						
					}
				}
				else if (p.AIlevel == "MEDIUM"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc < board.pucks.get(0).y)
							p.paddle.yc += p.paddle.delta*2;
						else
							p.paddle.yc -= p.paddle.delta*2;
					}
					else{
						if(p.paddle.xc < board.pucks.get(0).x)
							p.paddle.xc += p.paddle.delta*2;
						else
							p.paddle.xc -= p.paddle.delta*2;						
					}
				}
				else if (p.AIlevel == "HARD"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc < board.pucks.get(0).y)
							p.paddle.yc += p.paddle.delta*4;
						else
							p.paddle.yc -= p.paddle.delta*4;
					}
					else{
						if(p.paddle.xc < board.pucks.get(0).x)
							p.paddle.xc += p.paddle.delta*4;
						else
							p.paddle.xc -= p.paddle.delta*4;						
					}
				}
			}
		}
	}
	
	public void update(int i) {
		for (Puck p : board.pucks) {
			for (Player pl : players) {
	     		checkForBounce(p, pl); // changes vx and vy in p
				boolean crashed = checkForCrash(p, pl);
				if (crashed) {
					upScore(pl.side);
				}
			}

			p.update(i);
		//	System.out.println("PUCK: "+p.x + " "+p.y+ " # "+p.vx + " "+ p.vy);
		}

	}

	private boolean checkForCrash(Puck p, Player pl) {
	double d = 0;
		if(pl.paddle.orientation == "VERTICAL")
			d = Math.abs(pl.wall2protect.xa - p.x);
		else
			d = Math.abs(pl.wall2protect.ya - p.y);

		if (d < p.radius) {
			if (pl.alive) {
				if( pl.paddle.orientation == "VERTICAL")
					p.vx = -p.vx;
				else
					p.vy = - p.vy;
				return true;
			}
			if (pl.side == "TOP" || pl.side == "BOTTOM") {
				p.vy = -p.vy;
			} else {
				p.vx = -p.vx;
			}
		}
		return false;
	}

	private void checkForBounce(Puck p, Player player) {
		if(player.side == "LEFT"){
			if(( p.x - player.paddle.xc - padWidth/2  <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2)){
				System.out.println(p.vx);
				p.vx = -p.vx;
			}
		}
		else if(player.side == "RIGHT"){
			if((player.paddle.xc - padWidth/2 - p.x <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2)){
				p.vx = -p.vx;
			}
		}
		else if(player.side == "BOTTOM"){
			if((player.paddle.yc - padWidth/2 - p.y <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)){
				p.vy = -p.vy;
			}
		}
		else if(player.side == "TOP"){
			if((p.y - player.paddle.yc - padWidth/2 <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)){
				p.vy = -p.vy;
			}
		}
		


	}

}
