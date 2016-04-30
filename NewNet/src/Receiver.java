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
				
				if(h.isServer)
				{
					String[] splitter = str.split("~");
					if(splitter[0].equals("hello"))
					{
						for(Sender send: h.listOfSenders)
						{
							send.normalSend("FWD~"+IP+"~"+splitter[1]);
						}
					}
					
					Sender s = new Sender(IP);
					h.listOfSenders.add(s);
					h.listOfIps.add(IP);
					s.normalSend("ack" + h.sendGameState());
					
					
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
				}
					
				
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
