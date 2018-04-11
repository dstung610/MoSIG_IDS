import java.util.LinkedList;

import java.io.*;

public class GameServer //implements Runnable 
{
	static Node[] m_lNodes;

	static void ProcessMessage() {
		if (m_ConnectorWithClient == null)
			return;
		String msg = m_ConnectorWithClient.getMessage();
		if (msg == null)
			return;

		System.out.println("SERVER process message: " + msg);
	}

	static void ActionAssignPlayerToNode(String sPlayerName) {
		Player player = LoadPlayer(sPlayerName);
		Node node = null;
		if (player.getPosition().x < GameUtils.s_SettingZoneSize
				&& player.getPosition().y < GameUtils.s_SettingZoneSize)
			node = m_lNodes[0];
		if (player.getPosition().x >= GameUtils.s_SettingZoneSize
				&& player.getPosition().y < GameUtils.s_SettingZoneSize)
			node = m_lNodes[1];
		if (player.getPosition().x >= GameUtils.s_SettingZoneSize
				&& player.getPosition().y >= GameUtils.s_SettingZoneSize)
			node = m_lNodes[2];
		if (player.getPosition().x < GameUtils.s_SettingZoneSize
				&& player.getPosition().y >= GameUtils.s_SettingZoneSize)
			node = m_lNodes[3];

		if (node != null)
		{
			node.AddPlayer(player);
			
		}
	}

	// private void saveChatLog()
	// {
	// 	try  
	// 	{
	// 		PrintStream out = new PrintStream(new FileOutputStream(s_sChatLogFileName));
	// 		for (String line : m_qsChatLog)
	// 			out.println(line);
	// 		out.close();
	// 	}
	// 	catch (IOException e)
	// 	{
	// 		System.out.println(e);
	// 	}

	// }

	static Player LoadPlayer(String sPlayerName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(GameUtils.GeneratePlayerSaveFile(sPlayerName)));
			String line = null;
			String[] slPlayerInfo = new String[2];
			int i = 0;
			while ((line = reader.readLine()) != null) {
				slPlayerInfo[i] = line;
			}
			reader.close();

			//verify loaded data (x, y)	
			int x = Integer.parseInt(slPlayerInfo[0]);
			int y = Integer.parseInt(slPlayerInfo[1]);

			return new Player(sPlayerName, new vec2(x, y));
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Reset player " + sPlayerName + " to position (0, 0)");
			return new Player(sPlayerName, new vec2(0, 0));
		}
	}

	static String m_sServerName;
	static ConnectorServerClient m_ConnectorWithClient;

	static void SetupNodes() {

		m_lNodes = new Node[GameUtils.s_SettingNbZones];

		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			m_lNodes[i] = new Node("node_" + i);
		}
		// n1 -- n2
		//  |	 |
		// n4 -- n3

		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			m_lNodes[i]
					.setLeftNode(m_lNodes[(i - 1 + GameUtils.s_SettingNbZones) % GameUtils.s_SettingNbZones].getName());
			m_lNodes[i].setRightNode(m_lNodes[(i + 1) % GameUtils.s_SettingNbZones].getName());
		}

		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			m_lNodes[i].sendLeft("ServerReady");
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage:\nGameServer <Server Name>");
			System.exit(1);
		}
		m_sServerName = args[0];

		SetupNodes();

		m_ConnectorWithClient = new ConnectorServerClient();

		// //test connection
		// n1.sendLeft("ServerReady");
		// n2.sendLeft("ServerReady");
		// n3.sendLeft("ServerReady");
		// n4.sendLeft("ServerReady");

		//nen lam them 1 cai' broadcast chanel cho server

		//main loop
		boolean isRunning = true;
		while (isRunning) {
			ProcessMessage();
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		n1.Close();
		n2.Close();
		n3.Close();
		n4.Close();
		System.exit(0);
	}
}