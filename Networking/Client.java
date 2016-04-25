import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

class Receiver implements Runnable{

	int Port = 20000;

	@Override
	public void run() {
		//System.out.println("Receiver thread is running...");
		
		//Port no has been chosen as 20000
		DatagramSocket serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}

		while(true)
		{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				serverSocket.receive(receivePacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
			String receivedString = new String(receivePacket.getData());
			System.out.println("Received: " + receivedString + " From: " + receivePacket.getAddress());
		}
    }
	
}

class Sender implements Runnable{

	int Port = 20000;
	String IP;
	String message;
	public Sender(String message,String ip)
	{
		this.message = message;
		this.IP = ip;
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

    private volatile boolean isRunning;
	@Override
	public void run(){

		isRunning = true;
		while(isRunning)
		{
			DatagramSocket senderSocket = null;
			try{
				senderSocket = new DatagramSocket();
			}
			catch(SocketException e){
				System.out.println(e.toString());
				return;	
			}

			// String ip = getMachineAddress();
			// if(ip.equals("localhost"))
			// 	System.out.println("Connecting to localhost");

			// InetAddress IPAddress = null;
			// try{
			// 	IPAddress = InetAddress.getByName(getMachineAddress());
			// }
			// catch(UnknownHostException e){
			// 	System.out.println("Unnable to find host");
			// 	return;
			// }

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

	public void terminate()
	{
		isRunning = false;
	}



} 


public class Client{

	public static void main(String[] args) throws Exception{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter message to send:");
		String message = scanner.nextLine();
		System.out.println("Enter IP to send to:");
		String IP = scanner.nextLine();
		
		Receiver r = new Receiver();
		Thread threadReceive = new Thread(r);
		threadReceive.start();
		//Reception started

		Sender s = new Sender(message,IP);
		Thread threadSend = new Thread(s);
		threadSend.start();
		//sending thread started		
    }
	
}