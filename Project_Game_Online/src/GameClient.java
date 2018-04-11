public class GameClient
{
    static ConnectorClientServer m_cToServer;
    static ConnectorNodes m_cToNode;

    static void ProcessMessage()
	{
		if (m_ConnectorWithClient == null)
			return;
		String msg = m_ConnectorWithClient.getMessage();
		if (msg == null)
			return;
		
		System.out.println("SERVER process message: " + msg);
	}
    
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage:\nGameClient <Player Name>");
            System.exit(1);
        }
        String sPlayerName = args[0];
        // vec2 playerPos = new vec2(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        m_cToServer = new ConnectorClientServer(sPlayerName);

        m_cToServer.send(GameUtils.GenerateLoginMsg(sPlayerName));

        //main loop
		boolean isRunning = true;
		while (isRunning)
		{
			ProcessMessage();
			try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
		}
        
        System.exit(0);
    }
}