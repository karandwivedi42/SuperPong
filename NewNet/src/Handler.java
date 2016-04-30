import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Handler {
	
	public HashSet<String> listOfIps;
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	
	public Handler()
	{
		listOfIps = new HashSet<String>();
		listOfSenders = new ArrayList<Sender>();
		this.isServer = false;
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
		
		String message = hello;
		
		s.normalSend(message);
		
	}	
	
}
