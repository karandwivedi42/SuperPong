import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{

	int Port = 20000;
	boolean isRunning;
	DatagramSocket serverSocket;
	
		public Receiver()
		{
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
				System.out.println("str "+str);
				String IP = receivePacket.getAddress().toString().replace("/1","1");
				System.out.println("IPofSender "+IP );
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			
		}
    }
}
