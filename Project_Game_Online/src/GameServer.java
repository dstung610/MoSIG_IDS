// package Game;

import Game.Sender;
import Game.Receiver;
import Game.GameSettings;

public class GameServer
{
	
	static String m_sServerName;
	
	public static void main(String[] args) throws Exception 
	{
		if (args.length != 1)
		{
			System.out.println("Usage:\nGameServer <Server Name>");
			System.exit(1);
		}
		m_sServerName = args[0];
		
		Sender s1 = new Sender(GameSettings.host, GameSettings.GenerateChanelName(m_sServerName, m_sServerName));
		Receiver r1 = new Receiver(GameSettings.host, GameSettings.GenerateChanelName(m_sServerName, m_sServerName));
		
		s1.send("con meo ma treo cay cau");
	}
}