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
    
    //Constructor
    //Input : User id, User's IP address, side, paddle and wall corresponding to the side
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

    //Method to make an AI bot given a difficulty level
    public void makeAI(String level) {
        this.AI = true;
        this.AIlevel = level;
    }
    
    //Method to make the player human instead of AI bot
    public void makeHuman() {
        this.AI = false;
    }

    //Method handling the movements of paddles
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

    //Method handling the movements of paddles in case the player is an AI bot
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
