import java.util.LinkedList;

public class Node implements Runnable {
    static public final int s_iMaxZonePerNode = 1;
    // private Zone zones = new Zone[s_iMaxZonePerNode];//simple case

    ConnectorNodes cLeft, cRight;//links to other server left and right;

    LinkedList<ConnectorNodes> m_lcGameClient;
    LinkedList<String> m_lPlayers;

    private String myName;

    private vec2 m_v2TopLeft;
    private vec2 m_v2BottomRight;

    private char[] m_caMiniMap;

    public Node(String name, vec2 v2TopLeft, vec2 v2BottomRight) {
        myName = name;
        m_v2TopLeft = v2TopLeft;
        m_v2BottomRight = v2BottomRight;
        m_caMiniMap = new char[GameUtils.s_SettingZoneSize * GameUtils.s_SettingZoneSize];

        m_lPlayers = new LinkedList<String>();
        m_lcGameClient = new LinkedList<ConnectorNodes>();

        Thread threadPullServerMessage = new Thread(this);
        threadPullServerMessage.start();
    }

    public String getName() {
        return myName;
    }

    public void setLeftNode(String sNodeName) {
        cLeft = new ConnectorNodes(myName, sNodeName);
    }

    public void setRightNode(String sNodeName) {
        // cRight = new ConnectorNodes(myName, sNodeName);
    }

    public void sendLeft(String msg) {
        cLeft.send(msg);
    }

    public void sendRight(String msg) {
        // cRight.send(msg);
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
        String[] msgs;
        msg = msg.split("*")[1];
        msgs = msg.split("|");
        String srcNode = msgs[0];
        msg = msgs[1];
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
            float x = Float.parseFloat(sPos[0]);
            float y = Float.parseFloat(sPos[1]);
            if (isInsideMyZone(new vec2(x, y))) {
                if (!isPlayerInMyList(sPlayerName))
                    AddPlayer(sPlayerName);
            }
        }

        // m_cToServer.closePrivateChanelToServer();//close current connection

        // m_cToNode = new ConnectorNodes(m_sPlayerName, msg);

    }

    private void ProcessMessagePlayerMsg(String msg)
    {
        String[] msgs;
        msg = msg.split("*")[1];
        msgs = msg.split("|");
        String srcNode = msgs[0];
        msg = msgs[1];
        if (srcNode.compareTo(myName) == 0)
            return;//do nothing

            if (isInsideMyZone(new vec2(x, y))) 
            {
                if (isPlayerInMyList(sPlayerName))
                {
                    UpdateMiniMapInfo((int)x, (int)y);
                }
                else
                {
                    SendPlayerInfoToOtherNodes(sPlayerName, x, y);
                }
            }
        }
    }

    private void UpdateMiniMapInfo(int x, int y)
    {
        m_caMiniMap[x * GameUtils.s_SettingZoneSize + y] = 1;
    }

    private void SendPlayerInfoToOtherNodes(String sPlayerName, float x, float y)
    {
        cLeft.send(GameUtils.packPlayerForwardMsg(myName, sPlayerName, x, y));
    }

    public void ProcessMessage() {
        String msg = null;
        if (cLeft != null)
            msg = cLeft.getMessage();
        // if (msg == null)
        //     if (cRight != null)
        //         msg = cRight.getMessage();
        if (msg == null) {
            return;
        }

        if (msg.contains("PLAYER*")) {

            ProcessMessagePlayerMsg(msg);

        }

        if (msg.contains("ALLNODES*")) {

            ProcessMessageAllNodeMsg(msg);

        }
        GameUtils.LOG(myName + " process message: " + msg);
    }

    public void run() {
        while (true) {
            ProcessMessage();
            // UpdatePlayerPosition();

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                GameUtils.LOG(e.getMessage());
            }
        }
    }

    public void Close() {
        cLeft.close();
        // cRight.close();
    }

    public boolean AddPlayer(String sPlayerName) {
        m_lPlayers.add(sPlayerName);

        m_lcGameClient.add(new ConnectorNodes(myName, sPlayerName));
        return true;
    }
}