import java.util.ArrayList;

public class GameManager {

	private static final double v_factor = 20;
	ArrayList<Player> players;
	Board board;
	int currentRound;

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
				boolean crashed = checkForCrash(p, pl.wall2protect);
				if (crashed) {
					upScore(pl.side);
					startRound();
				}
			}
			
			p.update(i);

		}

	}

	private boolean checkForCrash(Puck p, Wall w) {
		// Distance calc then reverse velocity if player is dead and bounced.. otherwise send true 
		return false;
	}

	private void checkForBounce(Puck p, Paddle paddle) {
		// TODO Auto-generated method stub

	}

}
