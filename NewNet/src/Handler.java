import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Handler {
	
	public HashSet<String> listOfIps;
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	String acknowldge;
	HashMap<String,String> data;
	
	public Handler()
	{
		listOfIps = new HashSet<String>();
		listOfSenders = new ArrayList<Sender>();
		this.isServer = false;
		acknowldge = "ok";
		data = new HashMap<String,String>();
	}
	
	
	public void createGame()
	{
		System.out.println("Server started waiitng ....");
		this.isServer = true;
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
		System.out.println("DATA RECEIVED IN CONNECTED PORTS TO SERVER: " + input);
		for(String str: listOfIps)
		{
			System.out.println("IP:  " + str);
		}
	}
	
	public void putValue(String key,String value)
	{
		data.put(key,value);
	}
	public String getValue(String key)
	{
		return data.get(key);
	}

	/*
	 *Ack~
	 *Hello~
	 *FWD~
	 *Update~
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
		return "THE CURRENt GAME STATE";
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
	
	
	
	
	public String getGameStateOnJoin()
	{
						
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
