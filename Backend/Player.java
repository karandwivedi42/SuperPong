
public class Player {
	
	String name;
	int score;
	String side; // Top Bottom Left Right
	boolean AI;
	String AIlevel;
	Paddle paddle;
	boolean alive;
	Wall wall2protect;
	
	public Player(String name, String side, Paddle paddle, Wall wall) {
		this.name = name;
		this.score = 0;
		this.side = side;
		this.AI = false;
		this.paddle = paddle;
		this.wall2protect = wall;
		this.alive = true;
	}

	public void makeAI(String level) {
		this.AI = true;
		this.AIlevel = level;
	}
	
	public void makeHuman() {
		this.AI = false;
	}
	
	
	
	public void movePaddle(String direction){
		//System.out.println("Here");
		if(paddle.orientation == "HORIZONTAL"){
			//System.out.println("Horz");
			if(direction == "LEFT"){
				//System.out.println("Lft");
				if(paddle.xc >= paddle.length/2){
					//System.out.println("del");
					paddle.xc -= paddle.delta;
				}
			}
			else if (direction == "RIGHT"){
				if(paddle.xc + paddle.length/2 < wall2protect.length){
					//System.out.println("Rmoved");
					paddle.xc += paddle.delta;
				}
			}
		}
		else if (paddle.orientation == "VERTICAL"){
			if(direction == "UP"){
				if(paddle.yc >= paddle.length/2 )
					paddle.yc -= paddle.delta;
			}
			else{
				if(paddle.yc + paddle.length/2 < wall2protect.length)
					paddle.yc += paddle.delta;
			}
		}
		
		//System.out.print(paddle.orientation + " - "+ paddle.xc + " " + paddle.yc);
	}

}
