import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{

	int Port = 20000;
	boolean isRunning;
	DatagramSocket serverSocket;
	Handler h;
		public Receiver(Handler h)
		{
			this.h = h;
			serverSocket = null;
			try{
				serverSocket = new DatagramSocket(Port);
				//serverSocket.setSoTimeout(2000);
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
				String str = "DUMMYSTR";
				str = new String(receivePacket.getData());
				System.out.println("str "+str);
				String IP = "DUMMYIP";
				IP = receivePacket.getAddress().toString().replace("/1","1");
				System.out.println("IPofSender "+IP );
				
				String[] splitt = str.split("~");
				if(splitt[0].equals("Update"))
				{
					String[] keyValue = splitt[1].split("_");
					String key = keyValue[0];
					String value = keyValue[1];
					h.data.put(key,value);
				}
				if(splitt[0].equals("Ack"))
				{
					String one = splitt[1];
					String ips = splitt[3];
					String[] ipss = ips.split("^");
					for(int i=0;i<ipss.length;i++)
					{
						if(ipss[i].length()!=0)
						{
							h.listOfIps.add(ipss[i]);
							Sender s = new Sender(ipss[i]);
							h.listOfSenders.add(s);
						}
					}
				}
				if(h.isServer)
				{
					String[] splitter = str.split("~");
					if(splitter[0].equals("Hello"))
					{	
						String ips ="";
						
						for(Sender send: h.listOfSenders)
						{
							send.normalSend("FWD~"+IP+"~"+splitter[1]);
							
						}
						
						for(String ipss:h.listOfIps)
						{
							ips = ips+ipss+"^";
						}
					
					
					Sender s = new Sender(IP);
					h.listOfSenders.add(s);
					h.listOfIps.add(IP);
					//list ofIps also has to be added
					
					s.normalSend("Ack~" + h.sendGameStateOnJoin()+"~IPS~"+ips);
					}
				}
				else if(!h.isServer)
				{
					String[] splitted = str.split("~");
					if(splitted[0].equals("FWD"))
					{
						String ip = splitted[1];
						String data = splitted[2];
						Sender s = new Sender(ip);
						h.listOfSenders.add(s);
						h.listOfIps.add(ip);
						h.handleFWDData(data);
					}
					if(splitted[0].equals("Ack"))
					{
						h.getGameStateOnJoin(splitted[1]);
					}
				}
					
				
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
