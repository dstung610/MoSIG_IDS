public class Node implements Runnable
{
    static public final int s_iMaxZonePerNode = 1;
    // private Zone zones = new Zone[s_iMaxZonePerNode];//simple case

    class Connector
    {
        Sender out;
        Receiver in;
        StringBuffer sBuffer;
        public Connector(String sServerSrc, String sServerDst)
        {
            sBuffer = new StringBuffer();
            out = new Sender(GameSettings.host, GameSettings.GenerateChanelName(sServerSrc, sServerDst));
            in = new Receiver(GameSettings.host, GameSettings.GenerateChanelName(sServerDst, sServerSrc), sBuffer);
        }

        public void send(String msg)
        {
            out.send(msg);
        }

        public void get()
        {
            sBuffer.toString();
        }
    }

    Connector cLeft, cRight;//links to other server left and right;

    private String myName;
    
    public Node (String name)
    {
        myName = name;
    }

    public String getName()
    {
        return myName;
    }

    public void setLeftNode(String sNodeName)
    {
        cLeft = new Connector(myName, sNodeName);
    }
    public void setRightNode(String sNodeName)
    {
        cRight = new Connector(myName, sNodeName);
    }

    public void sendLeft(String msg)
    {
        cLeft.send(msg);
    }
    public void sendRight(String msg)
    {
        cRight.send(msg);
    }
    public void boardcast(String msg)
    {
        
    }

    public void run()
    {
        while(true)
        {
            while (true)
            {		
                if (GetMessageCount() > 0)
                    PullMessage();
                try 
                {		
                    Thread.sleep(300);
                } 
                catch (Exception e) 
                {
                    System.out.println(e);
                }
            }
        }
    }
}