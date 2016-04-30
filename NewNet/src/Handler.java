import java.util.ArrayList;


public class Handler {
	
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
		System.out.println("Server started waiitng ....");
		this.isServer = true;
	}
	
	public void joinGame(String serverIP,String hello)
	{
		Sender s = new Sender(sendPort,serverIP);
		this.listOfSenders.add(s);
		
		String message = "Hello~"+hello;
		s.normalSend(message);
	}	
	
	public void broadcast(String message)
	{
			for(Sender s: listOfSenders)
			{
				s.normalSend(message);
			}
		
	}
	
}
