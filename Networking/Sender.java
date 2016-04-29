import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Sender{

	int Port = 20000;
	public String IP;
	public String msge;
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

	public String[] highPrioritySend(String message,int timeout)
	{
		if(senderSocket == null)
		{
			try{
				senderSocket = new DatagramSocket();
			}
			catch(SocketException e){
				System.out.println(e.toString());
				return null;	
			}
		}
		System.out.println("this.IP "+this.IP);
		
		InetAddress IPAddress = null;
		try{
			IPAddress = InetAddress.getByName(IP);
		}
		catch(UnknownHostException e){
			System.out.println("Unnable to find host");
			return null;
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

		long starttime = System.currentTimeMillis();
		boolean isRunnable = true;
		while(isRunnable && (System.currentTimeMillis()-starttime)<=timeout)
		{
			System.out.println(System.currentTimeMillis()-starttime);
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				senderSocket.receive(receivePacket);
				String received = new String(receivePacket.getData());
				System.out.println(System.currentTimeMillis()-starttime);
				
				System.out.println("receiveData "+received);
				String[] processed = received.split("_"); 
				if(processed[0].equals("success"))
				{
					isRunnable = false;
					return processed;
				}
				
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
				
				
			}
		}
		
		senderSocket = null;
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void sendMessage(String msg,boolean isForAddition,MyObj m,int port)
	{
		if(!isForAddition)
		{
			DatagramSocket senderSocket = null;
			try{
				senderSocket = new DatagramSocket();
			}
			catch(SocketException e){
				System.out.println(e.toString());
				return;	
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
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
			try{
				senderSocket.send(sendPacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to send");
			}
		}
		else
		{
			System.out.println("this.IP "+this.IP);
			DatagramSocket senderSocket = null;
			try{
				senderSocket = new DatagramSocket();
			}
			catch(SocketException e){
				System.out.println(e.toString());
				return;	
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
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
			try{
				senderSocket.send(sendPacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to send");
			}

			DatagramSocket serverSocket = null;
			try {
				serverSocket = new DatagramSocket(port);
			
			
			long starttime = System.currentTimeMillis();
			while(true && (System.currentTimeMillis()-starttime)<=1000)
			{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			System.out.println("receiveData "+receiveData);
			try{
				serverSocket.receive(receivePacket);
				if(receivePacket.getData().equals("success"))
				{
					m.myParam = true;
					return;
				}
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
		}

			m.myParam = false;
			return;
			}
			catch(SocketException e)
			{
				System.out.println("BOOB\n"+e.toString()+"/nMaakichu");
			}
		}
	}



	public static String getMachineAddress() throws RuntimeException
	{
		
		String requiredIp = "localhost";
		String ip;
    	try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                if(ip.contains("."))
                	requiredIp = ip;
                System.out.println(iface.getDisplayName() + " " + ip);
            }
           }
        } catch (SocketException e) {
        throw new RuntimeException(e);
    	}

    	return requiredIp;
    }



}
