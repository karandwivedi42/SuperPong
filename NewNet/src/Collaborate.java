import java.util.Scanner;

public class Collaborate {
	
	public static void main(String args[])
	{
		Handler h = new Handler();
		Receiver r = new Receiver(h,20000);
		Thread th = new Thread(r);
		th.start();
		
		Pinger p = new Pinger(h);
		PingListener r2 = new PingListener(p,20001);
		Thread th2 = new Thread(r2);
		th2.start();
		
		Thread th3 = new Thread(p);
		th3.start();
		
//		Scanner s = new Scanner(System.in);
//		String str = s.nextLine();
//		if(str.equals("y") || str.equals("Y"))
//		{
//			h.createGame();
//		}
//		else
//		{
//			System.out.println("Server IP: ");
//			String serverIP = s.nextLine();
//			System.out.println("Details :");
//			String hello = s.nextLine();
//			h.joinGame(serverIP,hello);
//			Scanner see = new Scanner(System.in);
//			String srr = see.nextLine();
//			h.broadcast("MUHMEHLELO");
//		}
		
	}

}
