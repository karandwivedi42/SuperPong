public class Player {

    double maindeltaAI = 0.1;
    double margin = 5;
    
    String UID;
    String IP;
    int score;
    String side; // Top Bottom Left Right
    boolean AI;
    String AIlevel;
    boolean alive;
    
    Paddle paddle;
    Wall wall2protect;
    
    public Player(String uid,String IP, String side, Paddle paddle, Wall wall) {
        this.UID = uid;
        this.IP = IP;
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

        if(paddle.orientation == "HORIZONTAL"){
            if(direction == "LEFT"){
                if(paddle.xc >= paddle.length/2 + margin){
                    paddle.xc -= paddle.delta;
                }
            }
            else if (direction == "RIGHT"){
                if(paddle.xc + paddle.length/2 < wall2protect.length - margin){
                    paddle.xc += paddle.delta;
                }
            }
        }
        else if (paddle.orientation == "VERTICAL"){
            if(direction == "UP"){
                if(paddle.yc >= paddle.length/2 + margin )
                    paddle.yc -= paddle.delta;
            }
            else{
                if(paddle.yc + paddle.length/2 < wall2protect.length - margin)
                    paddle.yc += paddle.delta;
            }
        }
    }

    public void movePaddleAI(String direction,String level){
        double deltaAI;
        deltaAI = maindeltaAI;
        if(level == "MEDIUM")
            deltaAI = maindeltaAI*2;
        else if (level == "HARD")
            deltaAI = maindeltaAI * 8;

        if(paddle.orientation == "HORIZONTAL"){
            if(direction == "LEFT"){
                if(paddle.xc >= paddle.length/2 + margin){
                    paddle.xc -= deltaAI;
                }
            }
            else if (direction == "RIGHT"){
                if(paddle.xc + paddle.length/2 < wall2protect.length - margin){
                    //System.out.println("Rmoved");
                    paddle.xc += deltaAI;
                }
            }
        }
        else if (paddle.orientation == "VERTICAL"){
            if(direction == "UP"){
                if(paddle.yc >= paddle.length/2 + margin)
                    paddle.yc -= deltaAI;
            }
            else if (direction == "DOWN"){
                if(paddle.yc + paddle.length/2 < wall2protect.length - margin)
                    paddle.yc += deltaAI;
            }
        }

    }
}
