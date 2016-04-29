import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JApplet;

public class GameManager extends JApplet{
	
	NetworkManager nm ;
    AudioClip bounce;
    AudioClip crash;

	private static final double v_factor = 0.2;
	private static final double v_offset = 0.4;
	
	int padWidth = 7;
	int active;
	ArrayList<Player> players;
	Player me;
	Board board;
	int currentRound;
	private double y_factor = 0.01;
	private double y_offset = 0.5;
	private int maxScore = 5;
	
	public boolean isAdmin = true;
	
	public GameManager(NetworkManager n){
		players = new ArrayList<>();
		nm = n;

        bounce = JApplet.newAudioClip(getClass().getResource("res/0614.aiff"));
        crash = JApplet.newAudioClip(getClass().getResource("res/0342.aiff"));

	}

	public void addPlayer(Player p) { // FUTURE: Ensure name uniqueness
		players.add(p);
	}

    
    public void init()
    {
        
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
		if(isAdmin){
			for (Puck p : board.pucks) {
				p.x = board.width/2;
				p.y = board.height/2;
				p.vx = (v_offset + Math.random() * v_factor )*(Math.random()*2-1);
				p.vy = (v_offset + Math.random() * v_factor)* (Math.random()*2-1);
				nm.put(p.name+"-x",""+p.x);
				nm.put(p.name+"-y",""+p.y);
				nm.put(p.name+"-vx",""+p.vx);
				nm.put(p.name+"-vy",""+p.vy);
			}
		}
		else{
			for (Puck p : board.pucks) {
				p.x = Double.parseDouble(nm.get(p.name+"-x"));
				p.y = Double.parseDouble(nm.get(p.name+"-y"));
				p.vx = Double.parseDouble(nm.get(p.name+"-vx"));
				p.vy = Double.parseDouble(nm.get(p.name+"-vy"));
			}
		}
		active = players.size();
	}

	public void beginGame() {
		currentRound = 0;
		startRound();

	}

	public void movePaddles(){
		for(Player p : players){
			if(p.AI && p.equals(me)){
				Puck p0 = board.pucks.get(0);
				double minD = (p0.x - p.paddle.xc)*(p0.x - p.paddle.xc)+ (p0.y - p.paddle.yc)*(p0.y - p.paddle.yc);
				for(Puck pu : board.pucks){
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
				
				nm.put(p.name+"-xc", p.paddle.xc+"");
				nm.put(p.name+"-yc", p.paddle.yc+"");
			}
			else if (! p.equals(me)){
				p.paddle.xc = Double.parseDouble(nm.get(p.name+"-xc"));
				p.paddle.yc = Double.parseDouble(nm.get(p.name+"-yc"));
			}

		}
	}
	
	public void updatePuck(Puck p, int i) { // Change for friction
		
		p.x+=p.vx;
		p.y+=p.vy;	
	/*	if(p.x < 0)
			p.x = p.radius + 1;
		if(p.x > board.width)
			p.x = board.width - p.radius -1;
		if(p.y < 0)
			p.y = p.radius + 1;
		if(p.y > board.height)
			p.y = board.height - p.radius -100; */
	}
	
	public void update(int i) {
		
		if(isAdmin){
			
			movePaddles();
			
			for (Puck p : board.pucks) {

				for (Player pl : players) {
					if(pl.alive)
						checkForBounce(p, pl); // changes vx and vy in p
					boolean crashed = checkForCrash(p, pl);
					if (crashed) {
						upScore(pl.side);
						if(pl.score > maxScore ){
							pl.alive = false;
							active --;
							if (active ==1 ){
								System.out.println("Game OVER!");
								p.vx=0;
								p.vy=0;
							}
						}
					}
				}

				updatePuck(p,i);
				
				
			//	System.out.println("PUCK: "+p.x + " "+p.y+ " # "+p.vx + " "+ p.vy);
			}
		}
		else{
			for(Puck p : board.pucks){
				p.x = Double.parseDouble(nm.get(p.name+":x"));
				p.y = Double.parseDouble(nm.get(p.name+":"+"y"));
				p.vx = Double.parseDouble(nm.get(p.name+":"+"vx"));
				p.vy = Double.parseDouble(nm.get(p.name+":"+"vy"));
			}
			
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

                {
                    p.vx = -p.vx;
                    crash.play();
                }
				else
                {
                    p.vy = - p.vy;
                    crash.play();
                }

				return true;
			}
			if (pl.side == "TOP" || pl.side == "BOTTOM") {
				p.vy = -p.vy;

                crash.play();
			} else {
				p.vx = -p.vx;
                crash.play();

			}
		}
		return false;
	}

	private void checkForBounce(Puck p, Player player) {
		if(player.side == "LEFT"){
			if(( p.x - player.paddle.xc - padWidth/2  <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2) && (p.vx < 0)){
				System.out.println(p.vx);
				p.vx = -p.vx*(1-y_factor);

                bounce.play();

			//	p.vy =p.vy +  p.vy*Math.abs(p.y-player.paddle.yc-y_offset)*y_factor;
			}
		}
		else if(player.side == "RIGHT"){
			if((player.paddle.xc - padWidth/2 - p.x <= p.radius) && (p.y <= player.paddle.yc + player.paddle.length/2 && p.y >= player.paddle.yc - player.paddle.length/2)&& (p.vx > 0)){
				p.vx = -p.vx*(1-y_factor);

                bounce.play();

				//p.vy = p.vy +  p.vy*Math.abs(p.y-player.paddle.yc-y_offset)*y_factor;
			}
		}
		else if(player.side == "BOTTOM"){
			if((player.paddle.yc - padWidth/2 - p.y <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)&& (p.vy > 0)){
				p.vy = -p.vy*(1-y_factor);

                bounce.play();

				//p.vx = p.vx + p.vx*Math.abs(p.x-player.paddle.xc-y_offset)*y_factor;
			}
		}
		else if(player.side == "TOP"){
			if((p.y - player.paddle.yc - padWidth/2 <= p.radius) && (p.x <= player.paddle.xc + player.paddle.length/2 && p.x >= player.paddle.xc - player.paddle.length/2)&& (p.vy < 0)){
				p.vy = -p.vy*(1-y_factor);

                bounce.play();

				//p.vx = p.vx + p.vx*Math.abs(p.x-player.paddle.xc-y_offset)*y_factor;
			}
		}
		


	}

