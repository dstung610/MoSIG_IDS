import java.util.LinkedList;

class ConnectorClientServer {
    SenderBroadcast out;
    ReceiverOpened in;
    Receiver inPrivate;
    LinkedList<String> sBuffer;

    public ConnectorClientServer(String sPlayerName)
    {       
        sBuffer = new LinkedList<String>();
        out = new SenderBroadcast(GameUtils.host, GameUtils.s_sExchangeNameClientServer);
        in = new ReceiverOpened(GameUtils.host, GameUtils.s_sExchangeNameServerClient, sBuffer); 
    }

    public void send(String msg) {
        out.send(msg);
    }

    public String getMessage() {
        return sBuffer.pollFirst();
    }

    public void openPrivateChanelToServer(String sChanelName)
    {
        inPrivate = new Receiver(GameUtils.host, sChanelName, sBuffer); 
    }

    public void closePrivateChanelToServer()
    {
        if (inPrivate != null)
            inPrivate.close();
    }


    public void close() {
        out.close();
        in.close();
        if (inPrivate != null)
            inPrivate.close();
    }


}
