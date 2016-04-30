import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Handler {
	
	public HashSet<String> listOfIps;
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	String acknowldge;
	HashMap<String,String> data;
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
	
	public void joinGame(String serverIP,String hello)
	{
		Sender s = new Sender(serverIP);
		this.listOfIps.add(serverIP);
		this.listOfSenders.add(s);
		
		String message = "Hello~"+hello;
		s.normalSend(message);
	}	
	
	
	
	public void handleFWDData(String input)
	{
		//////////////////////////////////////////////////////////////////////////////////
	}
	
	public void putValue(String key,String value)
	{
		data.put(key,value);
		String message = "Update~"+key+"_"+value;
		broadcast(message);
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
	
//	public String encode(String message,String)
//	{
//		
//	}
	
	
	
	
	
	public String sendGameStateOnJoin()
	{
		String str = "";
		
		for(Puck p:this.gamestate.board.pucks)
		{
			String pname = p.name;
			String px = p.x+"";
			String py = p.y+"";
			String pvx = p.vx+"";
			String pvy = p.vy+"";
			str = str + "pname_"+pname+"#"+"px_"+px+"#"+"py_"+py+"#"+"pvx_"+pvx+"#"+"pvy_"+pvy+"#";
		}
		
		for(Player py:this.gamestate.players)
		{
			if(!py.side.equals(""))
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	
	
	
	
	public void getGameStateOnJoin(String str)
	{
		String[] splitt = str.split("#");
		for(int i=0;i<splitt.length;i++)
		{
			String[] splitter = splitt[i].split("_");
			this.data.put(splitter[0],splitter[1]);
		}
	}	
	public String getGameStateOnAck()
	{
		
	}
	
	public String setGameStateOnAck()
	{
		
	}
	
	public String setGameStateOnFWD()
	{
		
	}
	
	public String getGameStateOnFWD()
	{
		
	}
	
	public String getGameStateOnUpdate()
	{
		
	}
	
	public String setGameStateOnUpdate()
	{
		
	}
	
	public String getGameStateOnBroken()
	{
		
	}
	
	public String setGameStateonBroken()
	{
		
	}
}
