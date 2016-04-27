public class Sender implements Runnable{

	int Port = 20000;
	public String IP;
	public String message;
	
	public Sender(String IP)
	{
		this.IP = IP;
	}	
	// public Sender(String ip)
	// {	
	// 	this.IP = ip;
	// }
	public void sendMessage(String msg)
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
			sendData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,Port);
			try{
				senderSocket.send(sendPacket);
			}
			catch(IOException e){
				System.out.println(e.toString()+"\nUnnable to send");
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


    private volatile boolean isRunning;
	@Override
	public void run(){

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

	public void terminate()
	{
		isRunning = false;
	}



}