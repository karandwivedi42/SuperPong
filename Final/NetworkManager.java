import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
//
//class Receiver implements Runnable{
//	
//	int Port = 20000;
//	boolean isRunning;
//	HashMap<String,String>  currString;
//	public Receiver()
//	{
//		currString = new HashMap<String,String>
//	}
//	@Override
//	public void run() {
//		//System.out.println("Receiver thread is running...");
//		isRunning = true;
//		//Port no has been chosen as 20000
//		DatagramSocket serverSocket = null;
//		try{
//			serverSocket = new DatagramSocket(Port);
//		}
//		catch(SocketException e){
//			System.out.println(e.toString());
//			return;
//		}
//
//		while(isRunning)
//		{
//			byte[] receiveData = new byte[2048];
//			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
//			try{
//				serverSocket.receive(receivePacket);
//				handlePacket(receivePacket);
//			}
//			catch(IOException e){
//				System.out.println(e.toString()+"\nUnnable to receive");
//			}
//			String receivedString = new String(receivePacket.getData());
//			System.out.println("Received: " + receivedString + " From: " + receivePacket.getAddress());
//		}
//    }
//
//    public void terminate()
//    {
//    	isRunning = false;
//    }
//
//	
//	public void handlePacket(DatagramPacket receivePacket)
//	{
//		if(receivePacket==null)
//			return;
//
//		String receivedString = new String(receivePacket.getData());
//		String[] splitted = receivedString.split("_");
//		if(splitted[0]=="IP")
//		{
//			String ip = splitted[2];
//			currString.put("IP",splitted[0]);
//			//KARAN ADD THIS TO THE LIST OF IPS THAT THE SPECIFIC PLAYER WILL CONTAIN
//			
//		}
//		if(splitted.length==2)
//			currString.put(splitted[0],splitted[1]);
//	}
//
//	public String getValue(String str)
//	{
//		return currString.get(str);		
//	}	
//}
//
// class Sender implements Runnable{
//
//	int Port = 20000;
//	public String IP;
//	public String message;
//	
//	public Sender(String IP)
//	{
//		this.IP = IP;
//	}	
//	// public Sender(String ip)
//	// {	
//	// 	this.IP = ip;
//	// }
//	public void sendMessage(String msg)
//	{
//		DatagramSocket senderSocket = null;
//			try{
//				senderSocket = new DatagramSocket();
//			}
//			catch(SocketException e){
//				System.out.println(e.toString());
//				return;	
//			}
//
//			InetAddress IPAddress = null;
//			try{
//				IPAddress = InetAddress.getByName(IP);
//			}
//			catch(UnknownHostException e){
//				System.out.println("Unnable to find host");
//				return;
//			}
//
//			byte[] sendData = new byte[2048];
//			sendData = message.getBytes();
//			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
//			try{
//				senderSocket.send(sendPacket);
//			}
//			catch(IOException e){
//				System.out.println(e.toString()+"\nUnnable to send");
//			}		
//	}
//
//
//
//	public static String getMachineAddress() throws RuntimeException
//	{
//		
//		String requiredIp = "localhost";
//		String ip;
//    	try {
//        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//        while (interfaces.hasMoreElements()) {
//            NetworkInterface iface = interfaces.nextElement();
//            // filters out 127.0.0.1 and inactive interfaces
//            if (iface.isLoopback() || !iface.isUp())
//                continue;
//
//            Enumeration<InetAddress> addresses = iface.getInetAddresses();
//            while(addresses.hasMoreElements()) {
//                InetAddress addr = addresses.nextElement();
//                ip = addr.getHostAddress();
//                if(ip.contains("."))
//                	requiredIp = ip;
//                System.out.println(iface.getDisplayName() + " " + ip);
//            }
//           }
//        } catch (SocketException e) {
//        throw new RuntimeException(e);
//    	}
//
//    	return requiredIp;
//    }
//
//
//    private volatile boolean isRunning;
//	@Override
//	public void run(){
//
//			DatagramSocket senderSocket = null;
//			try{
//				senderSocket = new DatagramSocket();
//			}
//			catch(SocketException e){
//				System.out.println(e.toString());
//				return;	
//			}
//
//			// String ip = getMachineAddress();
//			// if(ip.equals("localhost"))
//			// 	System.out.println("Connecting to localhost");
//
//			// InetAddress IPAddress = null;
//			// try{
//			// 	IPAddress = InetAddress.getByName(getMachineAddress());
//			// }
//			// catch(UnknownHostException e){
//			// 	System.out.println("Unnable to find host");
//			// 	return;
//			// }
//
//			InetAddress IPAddress = null;
//			try{
//				IPAddress = InetAddress.getByName(IP);
//			}
//			catch(UnknownHostException e){
//				System.out.println("Unnable to find host");
//				return;
//			}
//
//			byte[] sendData = new byte[2048];
//			sendData = message.getBytes();
//			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
//			try{
//				senderSocket.send(sendPacket);
//			}
//			catch(IOException e){
//				System.out.println(e.toString()+"\nUnnable to send");
//			}
//		
//	}
//
//	public void terminate()
//	{
//		isRunning = false;
//	}
//
//
//
//}
//
public class NetworkManager{
	
	public ArrayList<Sender> list;
	public ArrayList<String> ips;
	public boolean isServer;
	public NetworkManager()
	{
		list = new ArrayList<Sender>();
		ips = new ArrayList<String>();
		isServer = false;
	}

	public void createGame(String sidechosen,String playerNo)
	{
		isServer = true;
	}

	public boolean join(String adminIP,String playerPosition,String playerNo)
	{
		Sender s = new Sender(adminIP);
		MyObj m = new MyObj();
		m.myParam = false;
		s.sendMessage("playerno_"+playerNo+"_playerpos_"+playerPosition,true,m,0);

//		boolean isRunning = true;
//		//Port no has been chosen as 20000
//		int Port = 20000;
//		DatagramSocket serverSocket = null;
//		try{
//			serverSocket = new DatagramSocket(20000);
//		}
//		catch(SocketException e){
//			System.out.println(e.toString());
//			return false;
//		}
//		int count = 0;
//		while(isRunning)
//		{
//			byte[] receiveData = new byte[2048];
//			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
//			try{
//				serverSocket.receive(receivePacket);
//				if(receivePacket.getData().equals("success"))
//				{
//					count++;
//				}
//				if(count ==3)
//					{break;}
//			}
//			catch(IOException e){
//				System.out.println(e.toString()+"\nUnnable to receive");
//				System.out.println("User not added");	
//				return false;
//			}
//		}
//		
		
		if(m.myParam)
		{System.out.println("Added user "+playerNo+" with position "+playerPosition);	
		return true;}
		return false;

	}	
	
	
}