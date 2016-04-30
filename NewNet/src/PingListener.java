import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class PingListener implements Runnable{

	int Port;
	boolean isRunning;
	DatagramSocket serverSocket;
	HashMap<String,Double> listento;
	ArrayList<Double> lastheard;
	Pinger p;
	
		
		public PingListener(Pinger p,int port,ArrayList<String> list)
		{
			this.Port = port;
			this.p = p;
			serverSocket = null;
			for(String str : list){
				this.listento.put(str, (double) System.currentTimeMillis());
			}
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
		


		while(isRunning)
		{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				serverSocket.receive(receivePacket);
				String str = "DUMMYSTR";
				str = new String(receivePacket.getData());
				if(str.equals("ping")){
					listento.put(receivePacket.getAddress().toString().replace("/1", "1"),(double) System.currentTimeMillis());
					System.out.println("Received ping from: " + receivePacket.getAddress().toString());
				}
				

			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
			
			for(int i=0;i<p.listofIPs.size();i++)
			{
				double time = listento.get(p.listofIPs.get(i));
				if(System.currentTimeMillis()-time>1000)
				{
					System.out.println("player disconnected "+p.listofIPs.get(i));
				}
			}
			
		}
    }
}
