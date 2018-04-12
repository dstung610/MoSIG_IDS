public class GameClient {
    static ConnectorClientServer m_cToServer;
    static ConnectorNodes m_cToNode;
    static String m_sPlayerName;

    static Player m_Player;

    static void ProcessMessage() {
        String msg = null;
        if (m_cToServer != null)
            msg = m_cToServer.getMessage();

        if (msg == null && m_cToNode != null)
                msg = m_cToNode.getMessage();

        if (msg == null)
            return;

        GameUtils.LOG("Process message: " + msg);

        //setup connection to server node
        if (msg.contains("SV_NODE")) {
            msg = msg.split(":")[1];
            m_cToServer.closePrivateChanelToServer();//close current connection

            m_cToNode = new ConnectorNodes(m_sPlayerName, msg);



            m_Player = new Player(sPlayerName, position);

        } else if (msg.contains("PLAYER*")) 
        {
            msg = msg.split(":")[1];
            String[] sPos = msg.split(" ");
            float x = Float.parseFloat(sPos[0]);
            float y = Float.parseFloat(sPos[1]);

            GameUtils.LOG("Receive Player position: " + x + " " + y);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage:\nGameClient <Player Name>");
            System.exit(1);
        }
        m_sPlayerName = args[0];
        // vec2 playerPos = new vec2(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        m_cToServer = new ConnectorClientServer(m_sPlayerName);

        m_cToServer.openPrivateChanelToServer(GameUtils.GenerateChanelName(m_sPlayerName, "SUPer_serVER"));

        m_cToServer.send(GameUtils.GenerateLoginMsg(m_sPlayerName));

        //main loop
        boolean isRunning = true;
        while (isRunning) {
            ProcessMessage();
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                GameUtils.LOG(e.getMessage());
            }
        }

        System.exit(0);
    }
}