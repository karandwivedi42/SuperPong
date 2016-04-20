import java.util.ArrayList;

public class GameManager {

	private static final double v_factor = 20;
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
			p.x = 0;
			p.y = 0;
			p.vx = Math.random() * v_factor;
			p.vy = Math.random() * v_factor;
		}
		for (Player p : players) {
			p.paddle.xc = 0;
			p.paddle.yc = 0;
		}

	}

	public void beginGame() {
		currentRound = 0;

		startRound();

	}

	public void update(int i) {
		for (Puck p : board.pucks) {
			for (Player pl : players) {
				checkForBounce(p, pl.paddle); // changes vx and vy in p

				boolean crashed = checkForCrash(p, pl);
				if (crashed) {
					upScore(pl.side);
					startRound();
				}
			}

			p.update(i);
			System.out.println("PUCK: "+p.x + " "+p.y+ " # "+p.vx + " "+ p.vy);
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
