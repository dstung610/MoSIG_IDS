import java.util.LinkedList;

public class Node implements Runnable
{
    static public final int s_iMaxZonePerNode = 1;
    // private Zone zones = new Zone[s_iMaxZonePerNode];//simple case
    LinkedList<String> sBuffer;

    class Connector
    {
        Sender out;
        Receiver in;
        
        public Connector(String sServerSrc, String sServerDst, LinkedList<String> sBuffer)
        {
            
            out = new Sender(GameSettings.host, GameSettings.GenerateChanelName(sServerSrc, sServerDst));
            in = new Receiver(GameSettings.host, GameSettings.GenerateChanelName(sServerDst, sServerSrc), sBuffer);
        }

        public void send(String msg)
        {
            out.send(msg);
        }

        public String get()
        {
            return sBuffer.pollFirst();
        }
    }

    Connector cLeft, cRight;//links to other server left and right;

    private String myName;
    
    public Node (String name)
    {
        myName = name;
        sBuffer = new LinkedList<String>();
        Thread threadPullServerMessage = new Thread(this);
        threadPullServerMessage.start();
    }

    public String getName()
    {
        return myName;
    }

    public void setLeftNode(String sNodeName)
    {
        cLeft = new Connector(myName, sNodeName, sBuffer);
    }
    public void setRightNode(String sNodeName)
    {
        cRight = new Connector(myName, sNodeName, sBuffer);
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

    public void ProcessMessage()
    {
        System.out.println(myName + " process message: " + sBuffer.pollFirst());
    }

    public void run()
    {
        while(true)
        {
            while (true)
            {		
                if (sBuffer.size() > 0)
                    ProcessMessage();
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