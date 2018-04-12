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

		if (msg.contains("lOGINreqESt")) {
			msg = msg.split(":")[1];
			ActionAssignPlayerToNode(msg);
		}
		GameUtils.LOG("SERVER process message: " + msg);
	}

	static void ActionAssignPlayerToNode(String sPlayerName) 
	{
		Player player = LoadPlayerInfo(sPlayerName);

		// GameUtils.LOG("XXXXXXXX" + player.getName() + player.getPosition().toString());

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

		if (node != null) {
			// GameUtils.LOG("NNNN" + node.getName());
			node.AddPlayer(sPlayerName);
			m_ConnectorWithClient.openPrivateChanel(GameUtils.GenerateChanelName(sPlayerName, "SUPer_serVER"));
			m_ConnectorWithClient.sendPrivateMsg("SV_NODE:" + node.getName());
			m_ConnectorWithClient.sendPrivateMsg(
				GameUtils.packPlayerInfo(sPlayerName, player.getPosition().x, player.getPosition().y));
			m_ConnectorWithClient.closePrivateChanel();
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
	// 		GameUtils.LOG(e.getMessage());
	// 	}

	// }

	static Player LoadPlayerInfo(String sPlayerName) {
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
			GameUtils.LOG(e.getMessage());
			GameUtils.LOG("Reset player " + sPlayerName + " to position (0, 0)");
			return new Player(sPlayerName, new vec2(0, 0));
		}
	}

	static String m_sServerName;
	static ConnectorServerClient m_ConnectorWithClient;

	static void SetupNodes() {

		m_lNodes = new Node[GameUtils.s_SettingNbZones];

		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			m_lNodes[i] = new Node("node_" + i, 
									new vec2(GameUtils.s_faZoneTopX[i], GameUtils.s_faZoneTopY[i]),
									new vec2(GameUtils.s_faZoneBottomX[i], GameUtils.s_faZoneBottomY[i]));
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

	static void CloseNodes() {
		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			m_lNodes[i].Close();
		}

	}

	static void ProcessNodesRequests()
	{
		String[] msgs;
		for (int i = 0; i < GameUtils.s_SettingNbZones; i++) {
			String msg = m_lNodes[i].getRequest();
			if (msg != null)
			{
				if (msg.contains("ASSIGNTO@"))
				{
					msgs = msg.split("@");
					msg = msgs[1];
					msgs = msg.split(":");
					String sNodeName = msgs[0];
					String sPlayerName = msgs[1];

					m_ConnectorWithClient.openPrivateChanel(GameUtils.GenerateChanelName(sPlayerName, "SUPer_serVER"));
					m_ConnectorWithClient.sendPrivateMsg("SV_NODE:" + sNodeName);
					m_ConnectorWithClient.closePrivateChanel();
				}
			}
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

		// m_lNodes[0].sendLeft("ALLNODES@123");
		//main loop
		boolean isRunning = true;
		while (isRunning) {
			ProcessMessage();
			ProcessNodesRequests();
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				GameUtils.LOG(e.getMessage());
			}
		}

		System.exit(0);
	}
}