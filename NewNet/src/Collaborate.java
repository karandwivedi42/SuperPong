import java.util.Scanner;

public class Collaborate {
	
	public static void main(String args[])
	{
		Handler h = new Handler();
		Receiver r = new Receiver();
		Thread th = new Thread(r);
		th.start();
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		if(str=="y")
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
		}
	}

}
