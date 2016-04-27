import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Receiver implements Runnable{
	
	int Port = 20000;
	boolean isRunning;
	HashMap<String,String>  currString;
	public Receiver()
	{
		currString = new HashMap<String,String>
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
				handlePacket(receivePacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			String receivedString = new String(receivePacket.getData());
			System.out.println("Received: " + receivedString + " From: " + receivePacket.getAddress());
		}
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
		if(splitted.length!=2)
			return;

		currString.put(splitted[0],splitted[1]);
	}

	public String getValue(String str)
	{
		return currString.get(str);		
	}	
}