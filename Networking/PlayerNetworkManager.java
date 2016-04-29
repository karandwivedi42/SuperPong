import java.util.ArrayList;

public class PlayerNetworkManager {
		
	public ArrayList<String> listOfIps;
	ArrayList<Sender> listOfSenders;
	boolean isServer;
	
	public PlayerNetworkManager()
	{
		listOfIps = new ArrayList<String>();
		listOfSenders = new ArrayList<Sender>();
		
		this.isServer = false;
	}
	
	public void createGame()
	{
		this.isServer = true;
	}
		
	public boolean joinGame(String ipToJoinTo,String playerNo,String playerPos)
	{
		Sender s = new Sender(ipToJoinTo);
		this.listOfIps.add(ipToJoinTo);
		this.listOfSenders.add(s);
		
		String message = "playerNo_"+playerNo+"_playerPos_"+playerPos;
		String[] list = s.highPrioritySend(message,1000);
		
		for(int i=1;i<list.length;i++)
		{
			listOfIps.add(list[i]);
			Sender adding = new Sender(list[i]);
			listOfSenders.add(adding);
		}
		
		if(list[0].equals("success"))
			return true;
		
		return false;
	}
}
