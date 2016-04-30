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
		
		String message = "hello~FUCK";
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
	
	public String sendGameState()
	{
		return "THE CURRENt GAME STATE";
	}
}
