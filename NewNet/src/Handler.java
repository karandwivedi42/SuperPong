import java.util.ArrayList;


public class Handler {
	//This is the class that handle the networking of one player 
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	String acknowldge = "OK";
	int sendPort = 20000;
	public Handler()
	{
		listOfSenders = new ArrayList<Sender>();
		this.isServer = false;
	}
	
	
	public void createGame()
	{
		//Game created for the current player, and player made a pseudo server
		System.out.println("Server started waiitng ....");
		this.isServer = true;
	}
	
	public void joinGame(String serverIP,String hello)
	{
		//Game joining for current machine on serverIP
		Sender s = new Sender(sendPort,serverIP);
		this.listOfSenders.add(s);
		
		String message = "Hello~"+hello;
		s.normalSend(message);
	}	
	
	public void broadcast(String message)
	{
		//send message to all IPs stored
			for(Sender s: listOfSenders)
			{
				s.normalSend(message);
			}
		
	}
	
}
