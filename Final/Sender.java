import java.io.*;
import java.net.*;

public class Sender{

	int Port;
	public String IP;
	DatagramSocket senderSocket;
	
	public Sender(int port,String IP)
	{
		this.Port = port;
		this.IP = IP;
		
		senderSocket = null;
		try{
			senderSocket = new DatagramSocket();
			//senderSocket.setSoTimeout(2000);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;	
		}
	}	
	
	public void normalSend(String message)
	{
		System.out.println("Sending message: " + message);
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
			System.out.println("Unable to find host");
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
