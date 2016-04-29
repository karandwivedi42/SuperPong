import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Pinger{
	
	int Port = 20001;
	boolean isRunning;
	public PlayerNetworkManager pm;
	ArrayList<String> update;
	DatagramSocket serverSocket;
	String IP;
	
	public Pinger(PlayerNetworkManager playerNetworkManager){
		this.pm = playerNetworkManager;
		update = new ArrayList<String>();
		
		serverSocket = null;
		try{
			serverSocket = new DatagramSocket(Port);
		}
		catch(SocketException e){
			System.out.println(e.toString());
			return;
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		
		if(serverSocket == null)
		{
			try{
				serverSocket = new DatagramSocket();
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
		sendData = "ping_".getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
		try{
			serverSocket.send(sendPacket);
		}
		catch(IOException e){
			System.out.println(e.toString()+"\nUnnable to send");
		}

	}
	
}
