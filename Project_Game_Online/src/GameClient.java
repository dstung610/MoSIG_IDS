public class GameClient {
    static ConnectorClientServer m_cToServer;
    static ConnectorNodes m_cToNode;
    static String m_sPlayerName;

    static Player m_Player = null;

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
            // if (m_cToServer != null) 
                // m_cToServer.closePrivateChanelToServer();//close current connection
            if (m_cToNode!=null)
                m_cToNode.close();

            m_cToNode = new ConnectorNodes(m_sPlayerName, msg);
            GameUtils.LOG("New connection to : " + msg);

        } else if (msg.contains("PLAYERINFO@")) {
            // GameUtils.LOG("11111");
            m_Player = GameUtils.unpackPlayerInfo(msg);
            // GameUtils.LOG("11111" + m_Player.getName());
            m_Player.chooseARandomTarget();
        }
    }

    static void updatePlayer() {
        if (m_Player == null) {
            return;
        }

        m_Player.moveTowardTarget();
        m_cToNode.send(GameUtils.packPlayerInfo(m_Player.getName(), m_Player.getPosition().x, m_Player.getPosition().y));

        if (m_Player.hasReachedTarget()) {
            m_Player.chooseARandomTarget();
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
            updatePlayer();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                GameUtils.LOG(e.getMessage());
            }
        }

        System.exit(0);
    }
}