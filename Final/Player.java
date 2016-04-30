public class Player {

    double maindeltaAI = 3;
    
    String UID;
    String IP;
    int score;
    public String side; // Top Bottom Left Right
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

        if(paddle.orientation.equals("HORIZONTAL")){
            if(direction.equals("LEFT")){
                if(paddle.xc >= paddle.length/2 ){
                    paddle.xc -= paddle.delta;
                }
            }
            else if (direction.equals("RIGHT")){
                if(paddle.xc + paddle.length/2 < wall2protect.length){
                    paddle.xc += paddle.delta;
                }
            }
        }
        else if (paddle.orientation.equals("VERTICAL")){
            if(direction.equals("UP")){
                if(paddle.yc >= paddle.length/2  )
                    paddle.yc -= paddle.delta;
            }
            else if (direction == "DOWN"){
                if(paddle.yc + paddle.length/2 < wall2protect.length)
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
            deltaAI = maindeltaAI * 4;

        if(paddle.orientation == "HORIZONTAL"){
            if(direction == "LEFT"){
                if(paddle.xc >= paddle.length/2 ){
                    paddle.xc -= deltaAI;
                }
            }
            else if (direction == "RIGHT"){
                if(paddle.xc + paddle.length/2 < wall2protect.length){
                    //System.out.println("Rmoved");
                    paddle.xc += deltaAI;
                }
            }
        }
        else if (paddle.orientation == "VERTICAL"){
            if(direction == "UP"){
                if(paddle.yc >= paddle.length/2 )
                    paddle.yc -= deltaAI;
            }
            else if (direction == "DOWN"){
                if(paddle.yc + paddle.length/2 < wall2protect.length)
                    paddle.yc += deltaAI;
            }
        }

    }
}
