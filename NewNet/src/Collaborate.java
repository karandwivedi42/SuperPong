import java.util.Scanner;

public class Collaborate {
	
	public static void main(String args[])
	{
		Handler h = new Handler();
		Receiver r = new Receiver(h);
		Thread th = new Thread(r);
		th.start();
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		if(str.equals("y") || str.equals("Y"))
		{
			h.createGame();
		}
		else
		{
			System.out.println("Server IP: ");
			String serverIP = s.nextLine();
			System.out.println("Details :");
			String hello = s.nextLine();
			h.joinGame(serverIP,hello);
			Scanner see = new Scanner(System.in);
			String srr = see.nextLine();
			h.broadcast("MUHMEHLELO");
		}
		
		
		
		
	}

}
