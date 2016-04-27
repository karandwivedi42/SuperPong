import java.util.*;
public class NetworkManager{
	
	ArrayList<Sender> list;
	public NetworkManager(){
		list = new ArrayList<Sender>();
	}

	public join(String playerIP)
	{
		for(int i=0;i<list.size();i++)
		{
			Sender ith = list.get(i);
			ith.send("IP_"+playerIP);
		}

		Sender latestAdded = new Sender(playerIP);
		list.add(lastestAdded);
	}

}