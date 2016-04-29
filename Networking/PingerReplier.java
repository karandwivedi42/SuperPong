import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PingerReplier implements Runnable{

	int Port = 20001;
	boolean isRunning;
	public PlayerNetworkManager pm;
	ArrayList<String> update;
	DatagramSocket serverSocket;
	
	public PingerReplier(PlayerNetworkManager playerNetworkManager)
	{
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		isRunning = true;
		//Port no has been chosen as 20000


		while(isRunning)
		{
			byte[] receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			try{
				serverSocket.receive(receivePacket);
				String receivedString = new String(receivePacket.getData());
				if(receivedString.split("_")[0].equals("ping"))
				{
					byte[] sendData = new byte[2048];
					String send = "pinged";
					sendData = send.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),Port);
				}
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to receive");
			}
		}

		
	}
	
	public void terminate()
	{
		isRunning = true;
	}
	

	
}
