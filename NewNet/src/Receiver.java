import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{

	int Port;
	boolean isRunning;
	DatagramSocket serverSocket;
	Handler h;
	Pinger p;
		public Receiver(Handler h,int port)
		{
			this.Port = port;
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
		
		public Receiver(Pinger p,int port)
		{
			this.Port = port;
			this.p = p;
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
				if(str.equals("ping"))
				{
					String con = "confirm";
					byte[] sendD = con.getBytes();
					DatagramPacket senderPacket = new DatagramPacket(sendD, sendD.length,receivePacket.getAddress(),receivePacket.getPort());
					try{
						serverSocket.send(senderPacket);
					}
					catch(IOException e){
						System.out.println(e.toString()+"\nUnnable to send");
					}
				}
				
				
				else{
				
				
				System.out.println("str "+str);
				String IP = "DUMMYIP";
				IP = receivePacket.getAddress().toString().replace("/1","1");
				System.out.println("IPofSender "+IP );
				//////////////////////////////////////////////////////////////////////////////////
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
							
							
							for(Sender ss:h.listOfSenders)
							{
								ips = ips+ss.IP+"^";
							}
						
						
						Sender s = new Sender(this.Port,IP);
						h.listOfSenders.add(s);
						
						//list ofIps also has to be added
						
						s.normalSend("Ack~"+"IPS~"+ips);
						}
					}
					else if(!h.isServer)
					{
						String[] splitted = str.split("~");
						if(splitted[0].equals("FWD"))
						{
							String ip = splitted[1];
							String data = splitted[2];
							Sender s = new Sender(this.Port,ip);
							h.listOfSenders.add(s);
							
							//h.handleFWDData(data);
						}

					}


					
				
			}
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
