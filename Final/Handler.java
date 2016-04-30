import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Handler {
	
	public HashSet<String> listOfIps;
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	String acknowldge;
	GameState gamestate;
	public Handler()
	{
		listOfIps = new HashSet<String>();
		listOfSenders = new ArrayList<Sender>();
		this.isServer = false;
		acknowldge = "ok";
		data = new HashMap<String,String>();
	}
	
	
	public void createGame(GameState gamestate)
	{
		System.out.println("Server started waiitng ....");
		this.isServer = true;
		this.gamestate = gamestate;
	}
	
	public void joinGame(String serverIP,String side)
	{
		Sender s = new Sender(serverIP);
		this.listOfIps.add(serverIP);
		this.listOfSenders.add(s);
		
		String message = "Hello~"+side;
		s.normalSend(message);
	}	
	
	
	
	public void handleFWDData(String input)
	{
		String[] newData = input.split("#");
		for (String s : newData){
		    String[] spl = s.split("_");
		    data.put(spl[0],spl[1]);
		}
	}
	
		
	public String getValue(String key)
	{
		return data.get(key);
	}

	/*
	 *Ack~
	 *Broken~
	 */
	public void broadcast(String message)
	{
			for(Sender s: listOfSenders)
			{
				s.normalSend(message);
			}
		
	}
	
	public String sendGameStateOnJoin(String side)
	{
		String str = "";
		
		for(Puck p:this.gamestate.board.pucks)
		{
			String thispuck = "PUCK_"+p.name+"_"+p.x+"_"+p.y+"_"+p.vx+"_"+p.vy+"#";
			str=str+thispuck;
		}
		
		for(Player py:this.gamestate.players)
		{
			if(!py.side.equals(side)){
			    String thisplayer = "PLAYER_"+p.UID+"_"+p.IP+"_"+p.score+"_"+p.side+"_"+p.AI+"_"+p.AIlevel+"_"+p.alive+"_"+p.paddle.xc+"_"+p.paddle.yc+"#";
			    str+=thisplayer;
			}
		}
		return str;

	}
	
	public void populate(GameState gamestate)
	{
		String boo = (gamestate.me.AI?"true":"false");
		data.put(gamestate.me.UID+"-AI",boo);
		String uid = gamestate.me.UID;
		data.put(uid+"-AILevel",gamestate.me.AIlevel);
		boo = (gamestate.me.alive?"true":"false");

		data.put(uid+"-isAlive",boo);
		data.put(uid+"-ip",gamestate.me.IP);
		data.put(uid+"-maindeltai", gamestate.me.maindeltaAI+"");
		data.put(uid+"-side", gamestate.me.side);
		data.put(uid+"-score",gamestate.me.score+"");
		
		
	}
	

	public void getGameStateOnJoin(String sendGameState,String ips,String playerNo)
	{
		String[] splitt = str.split("#");

		for(int i=0;i<splitt.length;i++)
		{
			String[] splitter = splitt[i].split("_");
			if(splitter[0].equals("PUCK"))
			{
			    Puck p = new Puck(splitter[1],splitter[2],splitter[3],splitter[4],splitter[5]);

			    gamestate.addPuck(p);
			}
			else if(splitter[0].equals("PLAYER"))
			{
			    String orient = (splitter[4] == "TOP" || splitter[4] == "BOTTOM")?"HORIZONTAL":"VERTICAL";
			    Player p = new Player(splitter[1],splitter[2],splitter[4],new Paddle(new Paddle(150,orient,0,0),gamestate.getWall(splitter[4]));
			    p.score = Integer.parseInt(splitter[3]);
			    p.AI = (splitter[5]=="true");
			    p.AIlevel = splitter[6];
			    p.alive = splitter[7] == "true";
			    p.paddle.xc=Double.parseDouble(splitter[8]);
			    p.paddle.yc=Double.parseDouble(splitter[9]);

			    gamestate.addPlayer(p);
			}
		}
	}	
	
}