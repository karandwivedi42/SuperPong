import java.util.ArrayList;

public class PlayerNetworkManager {
		
	ArrayList<String> listOfIps;
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
		
		for(int i=0;i<list.length;i++)
		{
			System.out.println(list[i]);
		}
		
		if(list.length>0)
			return true;
		return false;
	}
}
