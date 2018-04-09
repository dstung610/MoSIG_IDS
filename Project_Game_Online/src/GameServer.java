// package Game;

// import Game.Sender;
// import Game.Receiver;
// import Game.GameSettings;

public class GameServer {

	static String m_sServerName;

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

		//test connection
		// n1.sendLeft("test test");
		n1.sendLeft("test test 1");
		n1.sendLeft("test test 2");
		n1.sendLeft("test test 3");
	}
}