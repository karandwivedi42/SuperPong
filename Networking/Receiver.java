import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{
	
	int Port = 20000;
	boolean isRunning;
	HashMap<String,String>  currString;
	NetworkManager nm;
	public Receiver(NetworkManager nm)
	{
		currString = new HashMap<String,String>();
		this.nm = nm;
	}
	@Override
	public void run() {
		//System.out.println("Receiver thread is running...");
		isRunning = true;
		//Port no has been chosen as 20000
		DatagramSocket serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}

		while(isRunning)
		{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				serverSocket.receive(receivePacket);
				String receivedString = new String(receivePacket.getData());
				String[] arr = receivedString.split("_");
				if(arr[0].equals("playerno"))
				{
					playerAdded(arr,receivePacket.getAddress(),receivedString);
				}
				handlePacket(receivePacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			String receivedString = new String(receivePacket.getData());
			System.out.println("Received: " + receivedString + " From: " + receivePacket.getAddress());
		}
    }
	
	public void playerAdded(String[] arr,InetAddress ip,String ms)
	{	
		String playerno = arr[1];
		String playerPos = arr[3];
		nm.ips.add(ip.toString());
		nm.list.add(new Sender(ip.toString()));
		if(nm.isServer)
		{
			for(int i=0;i<nm.list.size();i++)
			{
				Sender curr = nm.list.get(i);
				MyObj m = new MyObj();
				m.myParam = false;
				curr.sendMessage(ms,true, m);
				if(!m.myParam)
				{
					System.out.println("refused from "+getMachineAddress());
					break;
				}
			}
			
			Sender s = new Sender(ip.toString());
			s.sendMessage("success",false,null);
		}
		else
		{
			Sender s = new Sender(ip.toString());
			s.sendMessage("success", false, null);
		}
		
		
	}
	
	public static String getMachineAddress() throws RuntimeException
	{
		
		String requiredIp = "localhost";
		String ip;
    	try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                if(ip.contains("."))
                	requiredIp = ip;
                System.out.println(iface.getDisplayName() + " " + ip);
            }
           }
        } catch (SocketException e) {
        throw new RuntimeException(e);
    	}

    	return requiredIp;
    }

	
    public void terminate()
    {
    	isRunning = false;
    }

	
	public void handlePacket(DatagramPacket receivePacket)
	{
		if(receivePacket==null)
			return;

		String receivedString = new String(receivePacket.getData());
		String[] splitted = receivedString.split("_");
		
		if(splitted[0]=="playerno" && splitted.length == 4)
		{
			String playerNo = splitted[1];
			String playerPos = splitted[3];
			
		}

		if(splitted.length==2)
			currString.put(splitted[0],splitted[1]);
	}

	public String getValue(String str)
	{
		return currString.get(str);		
	}	
}