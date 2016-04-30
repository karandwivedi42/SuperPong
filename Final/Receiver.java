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
					    for(Player plr : h.gamestate.players){
					        if(plr.UID == splitt[2]){
					            plr.paddle.xc = Double.parseDouble(splitt[3]);
					            plr.paddle.yc = Double.parseDouble(splitt[4]);
					        }
					    }
					    
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
					            p.vx = Double.parseDouble(velx);
					            p.vy = Double.parseDouble(vely);
					        }
					    }
					    
                    }
                    else if(splitt[1].equals("gameStatus"))
                    {
                        if(splitt[2] =="1") h.gamestate.gameStatus = 1;
                        else h.gamestate.gameStatus = 2;
                    }
                    
                    else if(splitt[1].equals("alive"))
                    {
                        for(Player p : h.gamestate.players){
                            if(p.UID == splitt[2])
                            p.alive = false;
                        }
                    }
                    else if(splitt[1].equals("score"))
                    {
                        for(Player p : h.gamestate.players){
                            if(p.UID == splitt[2])
                            p.score = Integer.parseInt(splitt[3]);
                        }
                    }
                    else if(splitt[1].equals("winner"))
                    {
                        h.gamestate.winner = splitt[2];
                    }
                                        
                    
                    
				}
				if(splitt[0].equals("Ack"))
				{
					String[] splitter = splitt[1].split("#");
					for(int i=0;i<=splitt.length;i++)
					{
						System.out.println(splitt[i]);
					}
					String one = splitt[1];
					String ips ="";
					if(splitt.length>=4)
						ips = splitt[3];
					String[] ipss = new String[4];
					if(ips.length()>0)
						ipss = ips.split("^");
					
					for(int i=0;i<ipss.length;i++)
					{
						if(ipss[i].length()!=0)
						{
							h.listOfIps.add(ipss[i]);
							Sender s = new Sender(Port,ipss[i]);
							h.listOfSenders.add(s);
						}
					}
					
						h.getGameStateOnJoin(splitt[1],splitt[3],splitt[5]);
					
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
						
						int xc=0,yc=0;
						String orient="HORIZONTAL";
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
					    Player pl = new Player(i+"",IP,splitter[1], new Paddle(150,orient,xc,yc),h.gamestate.getWall(splitter[1]));
					    
					    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
					Sender s = new Sender(20000,IP);
					h.listOfSenders.add(s);
					h.listOfIps.add(IP);
					//list ofIps also has to be added
					s.normalSend("Ack~" + h.sendGameStateOnJoin(splitter[0])+"~IPS~"+ips+"~PlayerNo~"+i);
					
					h.gamestate.addPlayer(pl);
					}
				}
				else if(!h.isServer)
				{
					String[] splitted = str.split("~");
					if(splitted[0].equals("FWD"))
					{
						String ip = splitted[1];
						String data = splitted[2];
						Sender s = new Sender(20000,ip);
						h.listOfSenders.add(s);
						h.listOfIps.add(ip);
						h.handleFWDData(data,ip,splitted[3]);
					}
					
				}
					
				
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
