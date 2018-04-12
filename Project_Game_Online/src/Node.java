import java.util.LinkedList;

public class Node implements Runnable {
    static public final int s_iMaxZonePerNode = 1;
    // private Zone zones = new Zone[s_iMaxZonePerNode];//simple case

    ConnectorNodes cLeft, cRight;//links to other server left and right;
    ConnectorNodes cToGUI;

    LinkedList<ConnectorNodes> m_lcGameClients;
    LinkedList<String> m_lPlayers;

    private String myName;

    private vec2 m_v2TopLeft;
    private vec2 m_v2BottomRight;

    private char[] m_caMiniMap;

    LinkedList<String> m_lsRequestServer;

    public Node(String name, vec2 v2TopLeft, vec2 v2BottomRight) {
        myName = name;
        m_v2TopLeft = v2TopLeft;
        m_v2BottomRight = v2BottomRight;
        m_caMiniMap = new char[GameUtils.s_SettingZoneSize * GameUtils.s_SettingZoneSize];
        for (int i = 0; i < GameUtils.s_SettingZoneSize * GameUtils.s_SettingZoneSize; i++) {
            m_caMiniMap[i] = '0';
        }

        m_lPlayers = new LinkedList<String>();
        m_lcGameClients = new LinkedList<ConnectorNodes>();
        m_lsRequestServer = new LinkedList<String>();

        Thread threadPullServerMessage = new Thread(this);
        threadPullServerMessage.start();
        cToGUI = new ConnectorNodes(myName, "GUI");
    }

    public String getName() {
        return myName;
    }

    public void setLeftNode(String sNodeName) {
        cLeft = new ConnectorNodes(myName, sNodeName);
    }

    public void setRightNode(String sNodeName) {
        cRight = new ConnectorNodes(myName, sNodeName);
    }

    public void sendLeft(String msg) {
        cLeft.send(msg);
    }

    public void sendRight(String msg) {
        cRight.send(msg);
    }

    public void boardcast(String msg) {

    }

    public boolean isInsideMyZone(vec2 p) {
        return p.x >= m_v2TopLeft.x && p.y >= m_v2TopLeft.y && p.x < m_v2BottomRight.x && p.y < m_v2BottomRight.y;
    }

    public boolean isPlayerInMyList(String sPlayerName) {
        for (int i = 0; i < m_lPlayers.size(); i++)
            if (sPlayerName.compareTo(m_lPlayers.get(i)) == 0)
                return true;
        return false;
    }

    public void ProcessMessageAllNodeMsg(String msg) {
        String originMsg = msg;
        // GameUtils.LOG(myName + " process message: " + msg);
        String[] msgs;
        msgs = msg.split("@");
        msg = msgs[1];
        // GameUtils.LOG(" 1111 " + msg);
        msgs = msg.split("#");
        String srcNode = msgs[0];
        msg = msgs[1];
        // GameUtils.LOG(" 2222 " + msg);
        if (srcNode.compareTo(myName) == 0)
            return;//do nothing
        
            
        msgs = msg.split("-");
        String task = msgs[0];
        msg = msgs[1];
        if (task.compareTo("FoRwArD") == 0) {
            msgs = msg.split(":");
            String sPlayerName = msgs[0];
            msg = msgs[1];
            msgs = msg.split(" ");
            float x = Float.parseFloat(msgs[0]);
            float y = Float.parseFloat(msgs[1]);
            if (isInsideMyZone(new vec2(x, y))) {
                if (!isPlayerInMyList(sPlayerName))
                {
                    AddPlayer(sPlayerName);
                    sendLeft(GameUtils.packPlayerRemoveMsg(myName, sPlayerName));
                }
            }
        }
        if (task.compareTo("REMOVE") == 0) {
            msgs = msg.split(":");
            String sPlayerName = msgs[0];
            if (isPlayerInMyList(sPlayerName))
            {                
                int i = m_lPlayers.indexOf(sPlayerName);

                m_lsRequestServer.add("ASSIGNTO@"+srcNode+":" + sPlayerName);

                m_lPlayers.remove(i);
                m_lcGameClients.get(i).close();
                m_lcGameClients.remove(i);
            }
        }

        sendLeft(originMsg);

        // m_cToServer.closePrivateChanelToServer();//close current connection

        // m_cToNode = new ConnectorNodes(m_sPlayerName, msg);

    }

    private void ProcessMessagePlayerMsg(String msg) {
        GameUtils.LOG(myName + " process message: " + msg);

        Player player = GameUtils.unpackPlayerInfo(msg);
        if (isInsideMyZone(player.getPosition())) {
            if (isPlayerInMyList(player.getName())) {
                updateMiniMapInfo((int) player.getPosition().x, (int) player.getPosition().y);
            }
        } else {
            int i = m_lPlayers.indexOf(player.getName());
            m_lcGameClients.get(i).stopReciver();
            sendPlayerInfoToOtherNodes(player.getName(), player.getPosition().x, player.getPosition().y);
        }
    }

    private void updateMiniMapInfo(int x, int y) {
        x = x - (int) m_v2TopLeft.x;
        y = y - (int) m_v2TopLeft.y;
        m_caMiniMap[x * GameUtils.s_SettingZoneSize + y] = '1';
        cToGUI.send(String.valueOf(m_caMiniMap));
        // GameUtils.LOG(String.valueOf(m_caMiniMap));
    }

    private void sendPlayerInfoToOtherNodes(String sPlayerName, float x, float y) {
        cLeft.send(GameUtils.packPlayerForwardMsg(myName, sPlayerName, x, y));
    }

    public void ProcessMessage() {
        String msg = null;
        for (ConnectorNodes c : m_lcGameClients) {
            msg = c.getMessage();
            if (msg != null && msg.contains("PLAYERINFO@")) {
                ProcessMessagePlayerMsg(msg);
            }
        }

        // GameUtils.LOG(myName + "ProcessMessage");
        msg = null;
        if (cLeft != null)
            msg = cLeft.getMessage();
        if (msg == null && cRight != null)
            msg = cRight.getMessage();

        

        if (msg != null && msg.contains("ALLNODES@")) {

            ProcessMessageAllNodeMsg(msg);
        }
    }

    public void run() {
        while (true) {

            // GameUtils.LOG(myName);
            //clear map
            for (int i = 0; i < GameUtils.s_SettingZoneSize * GameUtils.s_SettingZoneSize; i++) {
                m_caMiniMap[i] = '0';
            }
            ProcessMessage();

            try {
                Thread.sleep(50);
            } catch (Exception e) {
                GameUtils.LOG(e.getMessage());
            }
        }
    }

    public void Close() {
        cLeft.close();
        cRight.close();
    }

    public boolean AddPlayer(String sPlayerName) {
        GameUtils.LOG(myName + " AddPlayer " + sPlayerName);
        m_lPlayers.add(sPlayerName);

        m_lcGameClients.add(new ConnectorNodes(myName, sPlayerName));
        return true;
    }

    public String getRequest()
    {
        return m_lsRequestServer.poll();
    }
}