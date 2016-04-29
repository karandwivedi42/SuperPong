import java.util.ArrayList;
import java.util.HashMap;

public class PlayerNetworkManager {
		
	public ArrayList<String> listOfIps;
	public ArrayList<Sender> listOfSenders;
	boolean isServer;
	public HashMap<String,String> receivedData;
	public HashMap<String,Integer> timeouts;
	
	public PlayerNetworkManager()
	{
		listOfIps = new ArrayList<String>();
		listOfSenders = new ArrayList<Sender>();
		receivedData  = new HashMap<String,String>();
		this.isServer = false;
		timeouts = new HashMap<String,Integer>();
	}
	
	
	public void createGame()
	{
		this.isServer = true;
	}
		
	public String joinGame(String ipToJoinTo,String playerNo,String playerPos)
	{
		Sender s = new Sender(ipToJoinTo);
		this.listOfIps.add(ipToJoinTo);
		this.listOfSenders.add(s);
		this.timeouts.put(ipToJoinTo,0);
		
		String message = "playerNo_"+playerNo+"_playerPos_"+playerPos;
		String[] list = s.highPrioritySend(message,1000);
		
		for(int i=1;i<list.length;i++)
		{
			listOfIps.add(list[i]);
			Sender adding = new Sender(list[i]);
			listOfSenders.add(adding);
			timeouts.put(list[i],0);
		}
		
		if(list[0].equals("success"))
			return "1";
		
		return "0";
	}
	
	public String getValue(String key)
	{
		try{
			return receivedData.get(key);
		}
		catch(Exception e)
		{
			System.out.println("Unable to fetch key: "+key);
		}
		
		return "0";
	}
	
	public void broadcast(String message)
	{
		for(int i=0;i<listOfSenders.size();i++)
		{
			listOfSenders.get(i).normalSend(message);
		}
	}
}
