import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JApplet;

public class GameUpdater extends JApplet{
	
	Handler h;
	Receiver r;
	GameState gamestate;
    //AudioClip bounce;
    //AudioClip crash;

	private static final double v_factor = 4.2;
	private static final double v_offset = 4.7;
	
	int padWidth = 8;
	int playersLeft;

	private double y_factor = 0.0;
	private double y_offset = 0.5;
	
	private int maxScore = 5;
	//Constructor
	public GameUpdater(Handler h, Receiver r, GameState g){

	    this.r = r;
	    gamestate = g;
        this.h = h;
//        bounce = JApplet.newAudioClip(getClass().getResource("res/0614.aiff"));
//        crash = JApplet.newAudioClip(getClass().getResource("res/0342.aiff"));
//        
        System.out.print(gamestate.me.side);
        System.out.print(gamestate.me.UID);
        for (Player p : gamestate.players){
            System.out.println(p.side);
        }

	}
   
    public void init()
    {
        
    }
    //Life lost
	public void upScore(String side) {
		for (Player p : gamestate.players) {
			if (p.side.equals(side)){
				p.score++;
				h.broadcast("Update~score~"+p.UID+"~"+p.score);
			}
		}
	}
    //Begin new round
	public void startRound() {
		if(h.isServer){
     
			for (Puck p : gamestate.board.pucks) {
				p.x = gamestate.board.width/2;
				p.y = gamestate.board.height/2;
				p.vx = (v_offset + Math.random() * v_factor )*(Math.random()*2-1);
				p.vy = (v_offset + Math.random() * v_factor)* (Math.random()*2-1);
				h.broadcast("Update~puckmove~"+p.name+"~"+p.x+"~"+p.y+"~"+p.vx+"~"+p.vy);
			}
		}
	//	else{
	//		for (Puck p : gamestate.board.pucks) {
	//			p.x = Double.parseDouble(nm.get(p.name+"-x"));
	//			p.y = Double.parseDouble(nm.get(p.name+"-y"));
	//			p.vx = Double.parseDouble(nm.get(p.name+"-vx"));
	//			p.vy = Double.parseDouble(nm.get(p.name+"-vy"));
	//		}
	//	}
		playersLeft = gamestate.players.size();
	}

	public void beginGame() {
	    h.broadcast("Update~gameStatus~1");
        gamestate.gameStatus = 1;
		startRound();
	}
    //Move AI Paddles
	public void movePaddles(){
	
		for(Player p : gamestate.players){
			if(p.AI){
				Puck p0 = gamestate.board.pucks.get(0);
				double minD = (p0.x - p.paddle.xc)*(p0.x - p.paddle.xc)+ (p0.y - p.paddle.yc)*(p0.y - p.paddle.yc);
				for(Puck pu : gamestate.board.pucks){
					if((pu.x - p.paddle.xc)*(pu.x - p.paddle.xc)+ (pu.y - p.paddle.yc)*(pu.y - p.paddle.yc)<minD){
						p0 = pu;
						minD = (pu.x - p.paddle.xc)*(pu.x - p.paddle.xc)+ (pu.y - p.paddle.yc)*(pu.y - p.paddle.yc);
					}
				}
				if (p.AIlevel == "EASY"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc > p0.y){
							p.movePaddleAI("UP","EASY");
						}
						else{
							p.movePaddleAI("DOWN","EASY");
						}
					}
					else{
						if(p.paddle.xc > p0.x){
							p.movePaddleAI("LEFT","EASY");
						}
						else{
							p.movePaddleAI("RIGHT","EASY");				
						}
					}
				}
				else if (p.AIlevel == "MEDIUM"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc > p0.y)
							p.movePaddleAI("UP","MEDIUM");
						else
							p.movePaddleAI("DOWN","MEDIUM");
					}
					else{
						if(p.paddle.xc > p0.x)
							p.movePaddleAI("LEFT","MEDIUM");
						else
							p.movePaddleAI("RIGHT","MEDIUM");				
					}
				}
				else if (p.AIlevel == "HARD"){
					if(p.paddle.orientation == "VERTICAL"){
						if(p.paddle.yc > p0.y)
							p.movePaddleAI("UP","HARD");
						else
							p.movePaddleAI("DOWN","HARD");
					}
					else{
						if(p.paddle.xc > p0.x)
							p.movePaddleAI("LEFT","HARD");
						else
							p.movePaddleAI("RIGHT","HARD");				
					}
				}
				
				h.broadcast("Update~movepaddle~"+p.UID+"~"+p.paddle.xc+"~"+p.paddle.yc);
			}
	//		else if (! p.equals(gamestate.me)){
//				p.paddle.xc = Double.parseDouble(nm.get(p.name+"-xc"));
//				p.paddle.yc = Double.parseDouble(nm.get(p.name+"-yc"));
	//		}

		}
	}
	//Update Puck data
	public void updatePuck(Puck p, int i) { // Change for friction
		
		p.x+=p.vx;
		p.y+=p.vy;	
		
		h.broadcast("Update~puckmove~"+p.name+"~"+p.x+"~"+p.y+"~"+p.vx+"~"+p.vy);
		
//		nm.put(p.name+"-x", p.x+"");
//		nm.put(p.name+"-y", p.y+"");
	
	/*	if(p.x < 0)
			p.x = p.radius + 1;
		if(p.x > board.width)
			p.x = board.width - p.radius -1;
		if(p.y < 0)
			p.y = p.radius + 1;
		if(p.y > board.height)
			p.y = board.height - p.radius -100; */
	}
	//Update overall and check for collissions
	public void update(int i) {
		
		if(h.isServer){
		
			movePaddles();
			
			for (Puck p : gamestate.board.pucks) {

				for (Player pl : gamestate.players) {
					if(pl.alive)
						checkForBounce(p, pl);
					boolean crashed = checkForCrash(p, pl);
					if (crashed) {
						upScore(pl.side);
						if(pl.score > maxScore ){
							pl.alive = false;
							h.broadcast("Update~alive~"+pl.UID+"~false");
							playersLeft --;
							if (playersLeft ==1 ){
								System.out.println("Game OVER!");
								gamestate.gameStatus = 2;
								for(Player plw : gamestate.players){
								    if(plw.alive){
								        gamestate.winner = plw.side;
							            h.broadcast("Update~winner~"+pl.side);
							        }
								}
								h.broadcast("Update~gameStatus~2");
							}
						}
					}
				}

				updatePuck(p,i);

			}
		}
	/*	else{
		    

		    if (state == "GameOn"){

		        
		        for(Player pl : gamestate.players){
//		            pl.score = Integer.parseInt(nm.get(p.name+"-score"));
//		            pl.alive = (nm.get(p.name+"-alive")=="false")?false:true;
		            if(!pl.alive) playersLeft--;
	            		if (playersLeft ==1 ){
								System.out.println("Only on player left! "+pl.UID);
						}
		            if(pl.alive && !(pl.UID == gamestate.me.UID)){
//		                pl.paddle.xc = Double.parseDouble(nm.get(p.UID+"-xc"));
//		                pl.paddle.yc = Double.parseDouble(nm.get(p.UID+"-yc"));
		            }
		        }
		        
			    for(Puck p : gamestate.board.pucks){
//				    p.x = Double.parseDouble(nm.get(p.name+"-x"));
//				    p.y = Double.parseDouble(nm.get(p.name+"-y"));
//				    p.vx = Double.parseDouble(nm.get(p.name+"-vx"));
//				    p.vy = Double.parseDouble(nm.get(p.name+"-vy"));
			    }
			
			}
			else{
			    System.out.println("Game Over received");
			}
			
		}*/
		

	}
    //Check for  Wall and ball crash
	private boolean checkForCrash(Puck p, Player pl) {
	double d = 0;
		if(pl.paddle.orientation == "VERTICAL")
			d = Math.abs(pl.wall2protect.xa - p.x);
		else
			d = Math.abs(pl.wall2protect.ya - p.y);

		if (d < p.radius) {
			if (pl.alive) {
				if( pl.paddle.orientation == "VERTICAL")

                {
                    p.vx = -p.vx;
                   // crash.play();
                }
				else
                {
                    p.vy = - p.vy;
                    //crash.play();
                }

				return true;
			}
			if (pl.side == "TOP" || pl.side == "BOTTOM") {
				p.vy = -p.vy;

                //crash.play();
			} else {
				p.vx = -p.vx;
                //crash.play();

			}
		}
		return false;
	}
    //Check for bounce between paddle and ball
	private void checkForBounce(Puck p, Player player) {
		if(player.side == "LEFT"){
			if(( p.x - player.paddle.xc - padWidth/2  <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2) && (p.vx < 0)){
				System.out.println(p.vx);
				p.vx = -p.vx;

                //bounce.play();

			//	p.vy =p.vy +  p.vy*Math.abs(p.y-player.paddle.yc-y_offset)*y_factor;
			}
		}
		else if(player.side == "RIGHT"){
			if((player.paddle.xc - padWidth/2- p.x <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2)&& (p.vx > 0)){
				p.vx = -p.vx;

               // bounce.play();

				//p.vy = p.vy +  p.vy*Math.abs(p.y-player.paddle.yc-y_offset)*y_factor;
			}
		}
		else if(player.side == "BOTTOM"){
			if((player.paddle.yc - padWidth/2 - p.y <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)&& (p.vy > 0)){
				p.vy = -p.vy;

                //bounce.play();

				//p.vx = p.vx + p.vx*Math.abs(p.x-player.paddle.xc-y_offset)*y_factor;
			}
		}
		else if(player.side == "TOP"){
			if((p.y - player.paddle.yc - padWidth/2 <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)&& (p.vy < 0)){
				p.vy = -p.vy;

                //bounce.play();

				//p.vx = p.vx + p.vx*Math.abs(p.x-player.paddle.xc-y_offset)*y_factor;
			}
		}
		


	}
}
