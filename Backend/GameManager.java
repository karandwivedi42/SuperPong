import java.util.ArrayList;

public class GameManager {

	private static final double v_factor = 50;
	private static final double v_offset = 20;
	
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
			if (p.side == side)
				p.score++;
		}
	}

	public void startRound() {
		for (Puck p : board.pucks) {
			p.x = board.width/2;
			p.y = board.height/2;
			p.vx = v_offset; // + Math.random() * v_factor *(Math.random()*2-1);
			p.vy = v_offset; // + Math.random() * v_factor* (Math.random()*2-1);
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
				checkForBounce(p, pl.paddle); // changes vx and vy in p

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
		double d = (p.y - pl.wall2protect.m * p.x - pl.wall2protect.c)/ Math.sqrt(1 + pl.wall2protect.m * pl.wall2protect.m);
		if (Math.abs(d) < p.radius) {
			if (pl.alive) {
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

	private void checkForBounce(Puck p, Paddle paddle) {
		if(paddle.orientation == "VERTICAL"){
			double d = Math.abs(paddle.xc - p.x);
			if(d<p.radius)
				p.vx = -p.vx;
		}
		else if(paddle.orientation == "HORIZONTAL"){
			double d = Math.abs(paddle.yc - p.y);
			if (d<p.radius)
				p.vy = - p.vy;
		}

	}

}
