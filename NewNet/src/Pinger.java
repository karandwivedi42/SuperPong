import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Pinger implements Runnable{
	
	Handler h;
	ArrayList<String> listofIPs;

	DatagramSocket senderSocket;
	int pingPort = 20001;
	public Pinger(Handler h)
	{
		this.h = h;
		for(Sender s : h.listOfSenders)
			this.listofIPs.add(s.IP);
		try{
			senderSocket = new DatagramSocket();
		}
		catch(SocketException e){
			System.out.println(e.toString());
		}

	}
	
	@Override
	public void run()
	{
		while(true)
		{
			for(String st : listofIPs){
				Sender s = new Sender(pingPort,st);
				String message = "ping";
				s.normalSend(message);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	
}
