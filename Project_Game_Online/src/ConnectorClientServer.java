import java.util.LinkedList;

class ConnectorClientServer {
    SenderBroadcast out;
    ReceiverOpened in;
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

    public void close() {
        out.close();
        in.close();
    }
}
