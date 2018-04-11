// package Game;

// import Game.Sender;
// import Game.Receiver;
// import Game.GameUtils;

public class GameServer //implements Runnable 
{

	static void ProcessMessage()
	{
		if (m_ConnectorWithClient == null)
			return;
		String msg = m_ConnectorWithClient.getMessage();
		if (msg == null)
			return;
		
		System.out.println("SERVER process message: " + msg);
	}

	static void ActionAssignPlayerToNode()
	{
		
	}

	static String m_sServerName;
	static ConnectorServerClient m_ConnectorWithClient;

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage:\nGameServer <Server Name>");
			System.exit(1);
		}
		m_sServerName = args[0];

		Node n1 = new Node("node1");
		Node n2 = new Node("node2");
		Node n3 = new Node("node3");
		Node n4 = new Node("node4");
		// n1 -- n2
		//  |	 |
		// n4 -- n3

		n1.setLeftNode(n4.getName());
		n2.setLeftNode(n1.getName());
		n3.setLeftNode(n2.getName());
		n4.setLeftNode(n3.getName());
		n1.setRightNode(n2.getName());
		n2.setRightNode(n3.getName());
		n3.setRightNode(n4.getName());
		n4.setRightNode(n1.getName());

		m_ConnectorWithClient = new ConnectorServerClient();

		//test connection
		n1.sendLeft("ServerReady");
		n2.sendLeft("ServerReady");
		n3.sendLeft("ServerReady");
		n4.sendLeft("ServerReady");

		//nen lam them 1 cai' broadcast chanel cho server
		
		//main loop
		boolean isRunning = true;
		while (true)
		{
			ProcessMessage();
			try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
		}

		// n1.Close();
		// n2.Close();
		// n3.Close();
		// n4.Close();
		// System.exit(0);
	}
}