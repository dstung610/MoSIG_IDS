import java.util.LinkedList;

public class Node implements Runnable {
    static public final int s_iMaxZonePerNode = 1;
    // private Zone zones = new Zone[s_iMaxZonePerNode];//simple case
    LinkedList<Player> m_lPlayers;

    ConnectorNodes cLeft, cRight;//links to other server left and right;

    LinkedList<ConnectorNodes> m_lcGameClient;

    private String myName;

    public Node(String name) {
        myName = name;
        m_lPlayers = new LinkedList<Player>();
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

    public void ProcessMessage() {
        String msg = null;
        if (cLeft != null)
            msg = cLeft.getMessage();
        if (msg == null)
            if (cRight != null)
                msg = cRight.getMessage();
        if (msg == null)
            return;
        //Do something
        System.out.println(myName + " process message: " + msg);
    }

    void UpdatePlayerPosition()
    {        
        for (int i = 0; i < m_lPlayers.size(); i++)
        {
            vec2 p = m_lPlayers.get(i).getPosition();
            m_lcGameClient.get(i).send("pos:" + p.x + " " + p.y);
        }
    }
    public void run() {
        while (true) {
            ProcessMessage();

            

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void Close() {
        cLeft.close();
        cRight.close();
    }

    public boolean AddPlayer(Player player)
    {
        m_lPlayers.add(player);
        
        m_lcGameClient.add(new ConnectorNodes(myName, player.getName()));
        return true;
    }
}