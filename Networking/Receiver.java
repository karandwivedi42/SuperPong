import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{

	int Port = 20000;
	boolean isRunning;
	HashMap<String,String>  currString;
	NetworkManager nm;
	ArrayList<String> update;
	DatagramSocket serverSocket;
	PlayerNetworkManager pm ;

	public Receiver(NetworkManager nm)
	{
		currString = new HashMap<String,String>();
		update = new ArrayList<String>();
		this.nm = nm;
		serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}
	}

	public Receiver(PlayerNetworkManager pm)
	{
		currString = new HashMap<String,String>();
		update = new ArrayList<String>();
		this.pm = pm;
		serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}
	}
	public Receiver()
	{
		currString = new HashMap<String,String>();
		update = new ArrayList<String>();
		serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}
	}
	@Override
	public void run() {
		//System.out.println("Receiver thread is running...");
		isRunning = true;
		//Port no has been chosen as 20000


		while(isRunning)
		{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				serverSocket.receive(receivePacket);
				String str = new String(receivePacket.getData());
				String[] splitted = str.split("_");
				//////////////////////////////////////////////
//				String ip = receivePacket.getAddress().toString();
//				int time = pm.timeouts.get(ip);
//				if(time == 0)
//				{
//					time = (int)System.currentTimeMillis();
//					pm.timeouts.put(ip,time);
//				}
//				else
//				{
//					if(System.currentTimeMillis() - time>1000)
//					{
//						//timeout has occured
//						timeout(ip);
//					}
//				}
				//////////////////////////////////////////////
				if(splitted.length!=0)
				{

					if(splitted[0].equals("playerNo") && pm.isServer)
					{
						String IP = receivePacket.getAddress().toString().replace("/","");
						System.out.println(IP);
						String sender = "success_";
						int i;
						for(i=0;i<pm.listOfSenders.size();i++)
						{
							sender = sender+pm.listOfIps.get(i)+"_";
							Sender curr = pm.listOfSenders.get(i);
							if(!curr.highPrioritySend(str+"_"+IP,500)[0].equals("success"))
								break;
						}
						if(i==pm.listOfSenders.size())
						{
							this.pm.listOfIps.add(IP);
							Sender s = new Sender(IP);
							this.pm.listOfSenders.add(s);

							pm.receivedData.put("playerNo", splitted[1]);
							pm.receivedData.put("playerPos",splitted[3]);

							byte[] toSend = new byte[2048];
							toSend = sender.getBytes();
							DatagramPacket sendPacket = new DatagramPacket(toSend,toSend.length,receivePacket.getAddress(),receivePacket.getPort());
							try{
								serverSocket.send(sendPacket);
							}
							catch(IOException e){
								System.out.println(e.toString()+"\nUnnable to send");
							}
						}

					}

					else if(splitted[0].equals("playerNo") && !pm.isServer)
					{
						System.out.println(splitted.toString());
						
						String ip = splitted[4];
						pm.listOfIps.add(ip);
						pm.listOfSenders.add(new Sender(ip));
						byte[] toSend = new byte[2048];
						String sender2="success_";
						toSend = sender2.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(toSend,toSend.length,receivePacket.getAddress(),receivePacket.getPort());
							try{
								serverSocket.send(sendPacket);
							}
							catch(IOException e){
								System.out.println(e.toString()+"\nUnnable to send");
							}

					}

					else if(splitted.length==2)
					{
						pm.receivedData.put(splitted[0],splitted[1]);
					}

					else
					{
						handleThePacket(receivePacket,splitted);
					}
				}
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			String receivedString = new String(receivePacket.getData());
			System.out.println("Received: " + receivedString + " From: " + receivePacket.getAddress());
		}
    }

	public void handleThePacket(DatagramPacket packet,String[] splitted)
	{

	}
//	public void timeout(String ip)
//	{
//		for(int i=0;i<pm.listOfIps.size();i++)
//		{
//			if(ip.equals(pm.listOfIps.get(i)))
//				pm.listOfIps.remove(i);
//			if(ip.equals(pm.listOfSenders.get(i).IP))
//				pm.listOfSenders.remove(i);
//		}
//
//		serverSocket.send
//	}
	public void playerAdded(String[] arr,InetAddress ip,String ms)
	{	System.out.println("adding player");
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
				curr.sendMessage(ms,true, m,20001);
				if(!m.myParam)
				{
					System.out.println("refused from " + getMachineAddress());
					break;
				}
			}

			Sender s = new Sender(ip.toString());
			s.sendMessage("success",false,null,20001);
		}
		else
		{
			Sender s = new Sender(ip.toString());
			s.sendMessage("success", false, null,0);
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
