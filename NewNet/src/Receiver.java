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
					if(splitt[1].equals("movepaddle"))
					{
					    String playerNo = splitt[2];
					    String move = splitt[3];
					    
					}
					else if(splitt[1].equals("puckmove"))
					{
					    String puckname = splitt[2];
					    String x = splitt[3];
					    String y = splitt[4];
					    String velx = splitt[5];
					    String vely = splitt[6];
					    
					    for(Puck p : h.gamestate.board.pucks){
					        if(p.name == puckname){
					            p.x = Double.parseDouble(x);
					            p.y = Double.parseDouble(y);
					            p.vx = Double.parseDouble(vx);
					            p.vy = Double.parseDouble(vy);
					        }
					    }
					    
                    }
                    else if(splitt[1].equals("aiplayer"))
                    {
                        String side = splitt[2];
                        String move = splitt[3];
                    }
                    
                    
                    
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
					
						h.getGameStateOnJoin(splitted[1],splitted[3],splitted[5]);
					
				}
				if(h.isServer)
				{
					String[] splitter = str.split("~");
					if(splitter[0].equals("Hello"))
					{	
						String ips ="";
						int i=h.listOfSenders.size()+1;
						for(Sender send: h.listOfSenders)
						{
							send.normalSend("FWD~"+IP+"~"+splitter[1]+"~"+i);
							
						}
						
						for(String ipss:h.listOfIps)
						{
							ips = ips+ipss+"^";
						}
						
						double xc,yc;
						String orient;
						if(splitter[1] == "TOP"){
						    orient = "HORIZONTAL";
						    xc = 350;
						    yc = 16;
						}
						else if(splitter[1] == "BOTTOM"){
						    orient = "HORIZONTAL";
						    xc = 350;
						    yc = 684;
						}
						else if(splitter[1] == "LEFT"){
						    orient = "VERTICAL";
						    xc = 16;
						    yc = 350;
						}
						else if(splitter[1] == "RIGHT"){
						    orient = "VERTICAL";
						    xc = 684;
						    yc = 350;
						}
					    Player pl = new Player(i+"",IP,splitter[1], new Paddle(150,orient,xc,yc),gamestate.getWall(side));
					    h.gamestate.addPlayer(pl);
					
					Sender s = new Sender(IP);
					h.listOfSenders.add(s);
					h.listOfIps.add(IP);
					//list ofIps also has to be added
					
					s.normalSend("Ack~" + h.sendGameStateOnJoin(splitter[0])+"~IPS~"+ips+"~PlayerNo~"+i);
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
					
				}
					
				
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
