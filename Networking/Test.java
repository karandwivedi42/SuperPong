import java.io.*;
import java.net.InetAddress;
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
		
		NetworkManager nm = new NetworkManager();
		Receiver r = new Receiver(nm);
		Thread th = new Thread(r);
		th.start();
		System.out.println("Enter y if wannabe server");
		
		Scanner s = new Scanner(System.in);
		String y = s.nextLine();
		if(y.equals("y"))
		{
			System.out.println("Enter side and player no");
			String s1 = s.nextLine();
			String s2 = s.nextLine();
			nm.createGame(s1,s2);
		}
		else
		{
			System.out.println("Enter Server ip");
			String s1 = s.nextLine();
			System.out.println("Enter side and player no");
			String s2 = s.nextLine();
			String s3 = s.nextLine();
			
			System.out.println(nm.join(s1,s2,s3));
		}
	}
}