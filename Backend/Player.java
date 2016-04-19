
public class Player {
	
	String name;
	int score;
	String side; // Top Bottom Left Right
	boolean AI;
	Paddle paddle;
	boolean alive;
	Wall wall2protect;
	
	public Player(String name, String side, Paddle paddle) {
		this.name = name;
		this.score = 0;
		this.side = side;
		this.AI = false;
		this.paddle = paddle;
	}

	public void makeAI() {
		this.AI = true;
		
	}
	
	public void makeHuman() {
		this.AI = false;
	}

}
