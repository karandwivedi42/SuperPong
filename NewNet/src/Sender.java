import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Sender{

	int Port = 20000;
	public String IP;
	DatagramSocket senderSocket;
	
	public Sender(String IP)
	{
		this.IP = IP;
		
		senderSocket = null;
		try{
			senderSocket = new DatagramSocket();
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;	
		}
	}	
	public void normalSend(String message)
	{
		System.out.println("function message " + message);
		if(senderSocket == null)
		{
			try{
				senderSocket = new DatagramSocket();
			}
			catch(SocketException e){
				System.out.println(e.toString());
				return ;	
			}
		}
		
		InetAddress IPAddress = null;
		try{
			IPAddress = InetAddress.getByName(IP);
		}
		catch(UnknownHostException e){
			System.out.println("Unnable to find host");
			return;
		}
		
		byte[] sendData = new byte[2048];
		sendData = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
		try{
			senderSocket.send(sendPacket);
		}
		catch(IOException e){
			System.out.println(e.toString()+"\nUnnable to send");
		}
	}

}
