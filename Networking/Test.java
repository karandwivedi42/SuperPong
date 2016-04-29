import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
//class MyObj{
//	public boolean myParam;
//}
class Test{
	public static void function(boolean isMarking)
	{
		isMarking = true;
	}
	
	public static void function2(MyObj m)
	{
		m.myParam = true;
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

	public static void main(String[] args) throws UnknownHostException {
//		boolean isMarking = false;
//		System.out.println(isMarking);
//		function(isMarking);
//		System.out.println(isMarking);
//		MyObj m = new MyObj();
//		m.myParam = isMarking;
//		function2(m);
//		System.out.println(m.myParam);
		
//		InetAddress ip = InetAddress.getByName("10.10.78.22");
//		System.out.println(ip.toString());
		
//		NetworkManager nm = new NetworkManager();
//		Receiver r = new Receiver(nm);
//		Thread th = new Thread(r);
//		th.start();
//		System.out.println("Enter y if wannabe server");
//		
//		Scanner s = new Scanner(System.in);
//		String y = s.nextLine();
//		if(y.equals("y"))
//		{
//			System.out.println("Enter side and player no");
//			String s1 = s.nextLine();
//			String s2 = s.nextLine();
//			nm.createGame(s1,s2);
//		}
//		else
//		{
//			System.out.println("Enter Server ip");
//			String s1 = s.nextLine();
//			System.out.println(s1);
//			System.out.println("Enter side and player no");
//			String s2 = s.nextLine();
//			System.out.println(s2);
//			String s3 = s.nextLine();
//			System.out.println(s3);
//			
//			
//			System.out.println(nm.join(s1,s2,s3));
//		}
//		ArrayList<String> anc = new ArrayList<String>();
//		anc.add("abc");
//		anc.add("maakichu");
//		String a = anc.toString();
//		a.replace('[',',');
//		a.replace(']',',');
//		String[] arr = a.split(",");
//		for(int i=0;i<arr.length;i++)
//		{
//			System.out.println(arr[i]);
//		}
		
		PlayerNetworkManager pm = new PlayerNetworkManager();
		Receiver r = new Receiver(pm);
		Thread th = new Thread(r);
		th.start();
		System.out.println("Enter y if wannabe server");
		
		Scanner s = new Scanner(System.in);
		String y = s.nextLine();
		if(y.equals("y"))
		{
			pm.createGame();
		}
		else
		{
				System.out.println("Enter Server ip");
				String s1 = s.nextLine();
				System.out.println(s1);
				System.out.println("Enter playerno and side");
				String s2 = s.nextLine();
				System.out.println(s2);
				String s3 = s.nextLine();
				System.out.println(s3);
			
			
			System.out.println(pm.joinGame(s1,s2,s3));
			System.out.println(pm.listOfIps.toString());
		}
		
	}
}